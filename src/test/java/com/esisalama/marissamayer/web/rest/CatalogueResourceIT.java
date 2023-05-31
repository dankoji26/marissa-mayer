package com.esisalama.marissamayer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esisalama.marissamayer.IntegrationTest;
import com.esisalama.marissamayer.domain.Catalogue;
import com.esisalama.marissamayer.repository.CatalogueRepository;
import com.esisalama.marissamayer.service.dto.CatalogueDTO;
import com.esisalama.marissamayer.service.mapper.CatalogueMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CatalogueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CatalogueResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/catalogues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private CatalogueMapper catalogueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatalogueMockMvc;

    private Catalogue catalogue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue().nom(DEFAULT_NOM).createdAt(DEFAULT_CREATED_AT);
        return catalogue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createUpdatedEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue().nom(UPDATED_NOM).createdAt(UPDATED_CREATED_AT);
        return catalogue;
    }

    @BeforeEach
    public void initTest() {
        catalogue = createEntity(em);
    }

    @Test
    @Transactional
    void createCatalogue() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();
        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);
        restCatalogueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isCreated());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate + 1);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCatalogue.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createCatalogueWithExistingId() throws Exception {
        // Create the Catalogue with an existing ID
        catalogue.setId(1L);
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogueRepository.findAll().size();
        // set the field null
        catalogue.setNom(null);

        // Create the Catalogue, which fails.
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        restCatalogueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isBadRequest());

        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogueRepository.findAll().size();
        // set the field null
        catalogue.setCreatedAt(null);

        // Create the Catalogue, which fails.
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        restCatalogueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isBadRequest());

        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCatalogues() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList
        restCatalogueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogue.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get the catalogue
        restCatalogueMockMvc
            .perform(get(ENTITY_API_URL_ID, catalogue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catalogue.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCatalogue() throws Exception {
        // Get the catalogue
        restCatalogueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue
        Catalogue updatedCatalogue = catalogueRepository.findById(catalogue.getId()).get();
        // Disconnect from session so that the updates on updatedCatalogue are not directly saved in db
        em.detach(updatedCatalogue);
        updatedCatalogue.nom(UPDATED_NOM).createdAt(UPDATED_CREATED_AT);
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(updatedCatalogue);

        restCatalogueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, catalogueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catalogueDTO))
            )
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCatalogue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, catalogueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catalogueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catalogueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCatalogueWithPatch() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue using partial update
        Catalogue partialUpdatedCatalogue = new Catalogue();
        partialUpdatedCatalogue.setId(catalogue.getId());

        partialUpdatedCatalogue.nom(UPDATED_NOM);

        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCatalogue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCatalogue))
            )
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCatalogue.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCatalogueWithPatch() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue using partial update
        Catalogue partialUpdatedCatalogue = new Catalogue();
        partialUpdatedCatalogue.setId(catalogue.getId());

        partialUpdatedCatalogue.nom(UPDATED_NOM).createdAt(UPDATED_CREATED_AT);

        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCatalogue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCatalogue))
            )
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCatalogue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, catalogueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(catalogueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(catalogueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(catalogueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeDelete = catalogueRepository.findAll().size();

        // Delete the catalogue
        restCatalogueMockMvc
            .perform(delete(ENTITY_API_URL_ID, catalogue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
