package com.esisalama.marissamayer.web.rest;

import com.esisalama.marissamayer.repository.CatalogueRepository;
import com.esisalama.marissamayer.service.CatalogueService;
import com.esisalama.marissamayer.service.dto.CatalogueDTO;
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
 * REST controller for managing {@link com.esisalama.marissamayer.domain.Catalogue}.
 */
@RestController
@RequestMapping("/api")
public class CatalogueResource {

    private final Logger log = LoggerFactory.getLogger(CatalogueResource.class);

    private static final String ENTITY_NAME = "catalogue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatalogueService catalogueService;

    private final CatalogueRepository catalogueRepository;

    public CatalogueResource(CatalogueService catalogueService, CatalogueRepository catalogueRepository) {
        this.catalogueService = catalogueService;
        this.catalogueRepository = catalogueRepository;
    }

    /**
     * {@code POST  /catalogues} : Create a new catalogue.
     *
     * @param catalogueDTO the catalogueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catalogueDTO, or with status {@code 400 (Bad Request)} if the catalogue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/catalogues")
    public ResponseEntity<CatalogueDTO> createCatalogue(@RequestBody CatalogueDTO catalogueDTO) throws URISyntaxException {
        log.debug("REST request to save Catalogue : {}", catalogueDTO);
        if (catalogueDTO.getId() != null) {
            throw new BadRequestAlertException("A new catalogue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatalogueDTO result = catalogueService.save(catalogueDTO);
        return ResponseEntity
            .created(new URI("/api/catalogues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /catalogues/:id} : Updates an existing catalogue.
     *
     * @param id the id of the catalogueDTO to save.
     * @param catalogueDTO the catalogueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalogueDTO,
     * or with status {@code 400 (Bad Request)} if the catalogueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catalogueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/catalogues/{id}")
    public ResponseEntity<CatalogueDTO> updateCatalogue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CatalogueDTO catalogueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Catalogue : {}, {}", id, catalogueDTO);
        if (catalogueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catalogueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!catalogueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CatalogueDTO result = catalogueService.update(catalogueDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catalogueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /catalogues/:id} : Partial updates given fields of an existing catalogue, field will ignore if it is null
     *
     * @param id the id of the catalogueDTO to save.
     * @param catalogueDTO the catalogueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalogueDTO,
     * or with status {@code 400 (Bad Request)} if the catalogueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the catalogueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the catalogueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/catalogues/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CatalogueDTO> partialUpdateCatalogue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CatalogueDTO catalogueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Catalogue partially : {}, {}", id, catalogueDTO);
        if (catalogueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catalogueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!catalogueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CatalogueDTO> result = catalogueService.partialUpdate(catalogueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catalogueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /catalogues} : get all the catalogues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catalogues in body.
     */
    @GetMapping("/catalogues")
    public List<CatalogueDTO> getAllCatalogues() {
        log.debug("REST request to get all Catalogues");
        return catalogueService.findAll();
    }

    /**
     * {@code GET  /catalogues/:id} : get the "id" catalogue.
     *
     * @param id the id of the catalogueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catalogueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/catalogues/{id}")
    public ResponseEntity<CatalogueDTO> getCatalogue(@PathVariable Long id) {
        log.debug("REST request to get Catalogue : {}", id);
        Optional<CatalogueDTO> catalogueDTO = catalogueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catalogueDTO);
    }

    /**
     * {@code DELETE  /catalogues/:id} : delete the "id" catalogue.
     *
     * @param id the id of the catalogueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/catalogues/{id}")
    public ResponseEntity<Void> deleteCatalogue(@PathVariable Long id) {
        log.debug("REST request to delete Catalogue : {}", id);
        catalogueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
