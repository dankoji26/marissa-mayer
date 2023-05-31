package com.esisalama.marissamayer.web.rest;

import com.esisalama.marissamayer.repository.CreneauRepository;
import com.esisalama.marissamayer.service.CreneauService;
import com.esisalama.marissamayer.service.dto.CreneauDTO;
import com.esisalama.marissamayer.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.esisalama.marissamayer.domain.Creneau}.
 */
@RestController
@RequestMapping("/api")
public class CreneauResource {

    private final Logger log = LoggerFactory.getLogger(CreneauResource.class);

    private static final String ENTITY_NAME = "creneau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreneauService creneauService;

    private final CreneauRepository creneauRepository;

    public CreneauResource(CreneauService creneauService, CreneauRepository creneauRepository) {
        this.creneauService = creneauService;
        this.creneauRepository = creneauRepository;
    }

    /**
     * {@code POST  /creneaus} : Create a new creneau.
     *
     * @param creneauDTO the creneauDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creneauDTO, or with status {@code 400 (Bad Request)} if the creneau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/creneaus")
    public ResponseEntity<CreneauDTO> createCreneau(@RequestBody CreneauDTO creneauDTO) throws URISyntaxException {
        log.debug("REST request to save Creneau : {}", creneauDTO);
        if (creneauDTO.getId() != null) {
            throw new BadRequestAlertException("A new creneau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreneauDTO result = creneauService.save(creneauDTO);
        return ResponseEntity
            .created(new URI("/api/creneaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /creneaus/:id} : Updates an existing creneau.
     *
     * @param id the id of the creneauDTO to save.
     * @param creneauDTO the creneauDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creneauDTO,
     * or with status {@code 400 (Bad Request)} if the creneauDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creneauDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/creneaus/{id}")
    public ResponseEntity<CreneauDTO> updateCreneau(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreneauDTO creneauDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Creneau : {}, {}", id, creneauDTO);
        if (creneauDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creneauDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creneauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreneauDTO result = creneauService.update(creneauDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creneauDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /creneaus/:id} : Partial updates given fields of an existing creneau, field will ignore if it is null
     *
     * @param id the id of the creneauDTO to save.
     * @param creneauDTO the creneauDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creneauDTO,
     * or with status {@code 400 (Bad Request)} if the creneauDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creneauDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creneauDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/creneaus/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreneauDTO> partialUpdateCreneau(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreneauDTO creneauDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Creneau partially : {}, {}", id, creneauDTO);
        if (creneauDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creneauDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creneauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreneauDTO> result = creneauService.partialUpdate(creneauDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creneauDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /creneaus} : get all the creneaus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creneaus in body.
     */
    @GetMapping("/creneaus")
    public List<CreneauDTO> getAllCreneaus() {
        log.debug("REST request to get all Creneaus");
        return creneauService.findAll();
    }

    /**
     * {@code GET  /creneaus/:id} : get the "id" creneau.
     *
     * @param id the id of the creneauDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creneauDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/creneaus/{id}")
    public ResponseEntity<CreneauDTO> getCreneau(@PathVariable Long id) {
        log.debug("REST request to get Creneau : {}", id);
        Optional<CreneauDTO> creneauDTO = creneauService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creneauDTO);
    }

    /**
     * {@code DELETE  /creneaus/:id} : delete the "id" creneau.
     *
     * @param id the id of the creneauDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/creneaus/{id}")
    public ResponseEntity<Void> deleteCreneau(@PathVariable Long id) {
        log.debug("REST request to delete Creneau : {}", id);
        creneauService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
