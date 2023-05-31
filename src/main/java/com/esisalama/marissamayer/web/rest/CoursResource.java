package com.esisalama.marissamayer.web.rest;

import com.esisalama.marissamayer.repository.CoursRepository;
import com.esisalama.marissamayer.service.CoursService;
import com.esisalama.marissamayer.service.dto.CoursDTO;
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
 * REST controller for managing {@link com.esisalama.marissamayer.domain.Cours}.
 */
@RestController
@RequestMapping("/api")
public class CoursResource {

    private final Logger log = LoggerFactory.getLogger(CoursResource.class);

    private static final String ENTITY_NAME = "cours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoursService coursService;

    private final CoursRepository coursRepository;

    public CoursResource(CoursService coursService, CoursRepository coursRepository) {
        this.coursService = coursService;
        this.coursRepository = coursRepository;
    }

    /**
     * {@code POST  /cours} : Create a new cours.
     *
     * @param coursDTO the coursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coursDTO, or with status {@code 400 (Bad Request)} if the cours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cours")
    public ResponseEntity<CoursDTO> createCours(@RequestBody CoursDTO coursDTO) throws URISyntaxException {
        log.debug("REST request to save Cours : {}", coursDTO);
        if (coursDTO.getId() != null) {
            throw new BadRequestAlertException("A new cours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoursDTO result = coursService.save(coursDTO);
        return ResponseEntity
            .created(new URI("/api/cours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cours/:id} : Updates an existing cours.
     *
     * @param id the id of the coursDTO to save.
     * @param coursDTO the coursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursDTO,
     * or with status {@code 400 (Bad Request)} if the coursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cours/{id}")
    public ResponseEntity<CoursDTO> updateCours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CoursDTO coursDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cours : {}, {}", id, coursDTO);
        if (coursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CoursDTO result = coursService.update(coursDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cours/:id} : Partial updates given fields of an existing cours, field will ignore if it is null
     *
     * @param id the id of the coursDTO to save.
     * @param coursDTO the coursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursDTO,
     * or with status {@code 400 (Bad Request)} if the coursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the coursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the coursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cours/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CoursDTO> partialUpdateCours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CoursDTO coursDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cours partially : {}, {}", id, coursDTO);
        if (coursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CoursDTO> result = coursService.partialUpdate(coursDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cours} : get all the cours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cours in body.
     */
    @GetMapping("/cours")
    public List<CoursDTO> getAllCours() {
        log.debug("REST request to get all Cours");
        return coursService.findAll();
    }

    /**
     * {@code GET  /cours/:id} : get the "id" cours.
     *
     * @param id the id of the coursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cours/{id}")
    public ResponseEntity<CoursDTO> getCours(@PathVariable Long id) {
        log.debug("REST request to get Cours : {}", id);
        Optional<CoursDTO> coursDTO = coursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coursDTO);
    }

    /**
     * {@code DELETE  /cours/:id} : delete the "id" cours.
     *
     * @param id the id of the coursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cours/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        log.debug("REST request to delete Cours : {}", id);
        coursService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
