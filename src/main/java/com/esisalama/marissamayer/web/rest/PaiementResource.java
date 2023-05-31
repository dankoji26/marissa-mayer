package com.esisalama.marissamayer.web.rest;

import com.esisalama.marissamayer.repository.PaiementRepository;
import com.esisalama.marissamayer.service.PaiementService;
import com.esisalama.marissamayer.service.dto.PaiementDTO;
import com.esisalama.marissamayer.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.esisalama.marissamayer.domain.Paiement}.
 */
@RestController
@RequestMapping("/api")
public class PaiementResource {

    private final Logger log = LoggerFactory.getLogger(PaiementResource.class);

    private static final String ENTITY_NAME = "paiement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaiementService paiementService;

    private final PaiementRepository paiementRepository;

    public PaiementResource(PaiementService paiementService, PaiementRepository paiementRepository) {
        this.paiementService = paiementService;
        this.paiementRepository = paiementRepository;
    }

    /**
     * {@code POST  /paiements} : Create a new paiement.
     *
     * @param paiementDTO the paiementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paiementDTO, or with status {@code 400 (Bad Request)} if the paiement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paiements")
    public ResponseEntity<PaiementDTO> createPaiement(@Valid @RequestBody PaiementDTO paiementDTO) throws URISyntaxException {
        log.debug("REST request to save Paiement : {}", paiementDTO);
        if (paiementDTO.getId() != null) {
            throw new BadRequestAlertException("A new paiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaiementDTO result = paiementService.save(paiementDTO);
        return ResponseEntity
            .created(new URI("/api/paiements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paiements/:id} : Updates an existing paiement.
     *
     * @param id the id of the paiementDTO to save.
     * @param paiementDTO the paiementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paiementDTO,
     * or with status {@code 400 (Bad Request)} if the paiementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paiementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paiements/{id}")
    public ResponseEntity<PaiementDTO> updatePaiement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaiementDTO paiementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Paiement : {}, {}", id, paiementDTO);
        if (paiementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paiementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paiementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaiementDTO result = paiementService.update(paiementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paiementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paiements/:id} : Partial updates given fields of an existing paiement, field will ignore if it is null
     *
     * @param id the id of the paiementDTO to save.
     * @param paiementDTO the paiementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paiementDTO,
     * or with status {@code 400 (Bad Request)} if the paiementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paiementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paiementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paiements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaiementDTO> partialUpdatePaiement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaiementDTO paiementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Paiement partially : {}, {}", id, paiementDTO);
        if (paiementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paiementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paiementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaiementDTO> result = paiementService.partialUpdate(paiementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paiementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /paiements} : get all the paiements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paiements in body.
     */
    @GetMapping("/paiements")
    public List<PaiementDTO> getAllPaiements() {
        log.debug("REST request to get all Paiements");
        return paiementService.findAll();
    }

    /**
     * {@code GET  /paiements/:id} : get the "id" paiement.
     *
     * @param id the id of the paiementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paiementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paiements/{id}")
    public ResponseEntity<PaiementDTO> getPaiement(@PathVariable Long id) {
        log.debug("REST request to get Paiement : {}", id);
        Optional<PaiementDTO> paiementDTO = paiementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paiementDTO);
    }

    /**
     * {@code DELETE  /paiements/:id} : delete the "id" paiement.
     *
     * @param id the id of the paiementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paiements/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        log.debug("REST request to delete Paiement : {}", id);
        paiementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
