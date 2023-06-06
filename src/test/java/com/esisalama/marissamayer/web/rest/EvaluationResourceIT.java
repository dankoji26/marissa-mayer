package com.esisalama.marissamayer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esisalama.marissamayer.IntegrationTest;
import com.esisalama.marissamayer.domain.Cours;
import com.esisalama.marissamayer.domain.Evaluation;
import com.esisalama.marissamayer.domain.Utilisateur;
import com.esisalama.marissamayer.repository.EvaluationRepository;
import com.esisalama.marissamayer.service.dto.EvaluationDTO;
import com.esisalama.marissamayer.service.mapper.EvaluationMapper;
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
 * Integration tests for the {@link EvaluationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvaluationResourceIT {

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/evaluations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationMockMvc;

    private Evaluation evaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evaluation createEntity(EntityManager em) {
        Evaluation evaluation = new Evaluation().commentaire(DEFAULT_COMMENTAIRE).createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        Cours cours;
        if (TestUtil.findAll(em, Cours.class).isEmpty()) {
            cours = CoursResourceIT.createEntity(em);
            em.persist(cours);
            em.flush();
        } else {
            cours = TestUtil.findAll(em, Cours.class).get(0);
        }
        evaluation.setCours(cours);
        // Add required entity
        Utilisateur utilisateur;
        if (TestUtil.findAll(em, Utilisateur.class).isEmpty()) {
            utilisateur = UtilisateurResourceIT.createEntity(em);
            em.persist(utilisateur);
            em.flush();
        } else {
            utilisateur = TestUtil.findAll(em, Utilisateur.class).get(0);
        }
        evaluation.setUser(utilisateur);
        return evaluation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evaluation createUpdatedEntity(EntityManager em) {
        Evaluation evaluation = new Evaluation().commentaire(UPDATED_COMMENTAIRE).createdAt(UPDATED_CREATED_AT);
        // Add required entity
        Cours cours;
        if (TestUtil.findAll(em, Cours.class).isEmpty()) {
            cours = CoursResourceIT.createUpdatedEntity(em);
            em.persist(cours);
            em.flush();
        } else {
            cours = TestUtil.findAll(em, Cours.class).get(0);
        }
        evaluation.setCours(cours);
        // Add required entity
        Utilisateur utilisateur;
        if (TestUtil.findAll(em, Utilisateur.class).isEmpty()) {
            utilisateur = UtilisateurResourceIT.createUpdatedEntity(em);
            em.persist(utilisateur);
            em.flush();
        } else {
            utilisateur = TestUtil.findAll(em, Utilisateur.class).get(0);
        }
        evaluation.setUser(utilisateur);
        return evaluation;
    }

    @BeforeEach
    public void initTest() {
        evaluation = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluation() throws Exception {
        int databaseSizeBeforeCreate = evaluationRepository.findAll().size();
        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);
        restEvaluationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeCreate + 1);
        Evaluation testEvaluation = evaluationList.get(evaluationList.size() - 1);
        assertThat(testEvaluation.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testEvaluation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createEvaluationWithExistingId() throws Exception {
        // Create the Evaluation with an existing ID
        evaluation.setId(1L);
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        int databaseSizeBeforeCreate = evaluationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCommentaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationRepository.findAll().size();
        // set the field null
        evaluation.setCommentaire(null);

        // Create the Evaluation, which fails.
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        restEvaluationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationRepository.findAll().size();
        // set the field null
        evaluation.setCreatedAt(null);

        // Create the Evaluation, which fails.
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        restEvaluationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEvaluations() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList
        restEvaluationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get the evaluation
        restEvaluationMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluation.getId().intValue()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEvaluation() throws Exception {
        // Get the evaluation
        restEvaluationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();

        // Update the evaluation
        Evaluation updatedEvaluation = evaluationRepository.findById(evaluation.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluation are not directly saved in db
        em.detach(updatedEvaluation);
        updatedEvaluation.commentaire(UPDATED_COMMENTAIRE).createdAt(UPDATED_CREATED_AT);
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(updatedEvaluation);

        restEvaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
        Evaluation testEvaluation = evaluationList.get(evaluationList.size() - 1);
        assertThat(testEvaluation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testEvaluation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();
        evaluation.setId(count.incrementAndGet());

        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();
        evaluation.setId(count.incrementAndGet());

        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();
        evaluation.setId(count.incrementAndGet());

        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluationWithPatch() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();

        // Update the evaluation using partial update
        Evaluation partialUpdatedEvaluation = new Evaluation();
        partialUpdatedEvaluation.setId(evaluation.getId());

        partialUpdatedEvaluation.commentaire(UPDATED_COMMENTAIRE).createdAt(UPDATED_CREATED_AT);

        restEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluation))
            )
            .andExpect(status().isOk());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
        Evaluation testEvaluation = evaluationList.get(evaluationList.size() - 1);
        assertThat(testEvaluation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testEvaluation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateEvaluationWithPatch() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();

        // Update the evaluation using partial update
        Evaluation partialUpdatedEvaluation = new Evaluation();
        partialUpdatedEvaluation.setId(evaluation.getId());

        partialUpdatedEvaluation.commentaire(UPDATED_COMMENTAIRE).createdAt(UPDATED_CREATED_AT);

        restEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluation))
            )
            .andExpect(status().isOk());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
        Evaluation testEvaluation = evaluationList.get(evaluationList.size() - 1);
        assertThat(testEvaluation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testEvaluation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();
        evaluation.setId(count.incrementAndGet());

        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();
        evaluation.setId(count.incrementAndGet());

        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();
        evaluation.setId(count.incrementAndGet());

        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(evaluationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        int databaseSizeBeforeDelete = evaluationRepository.findAll().size();

        // Delete the evaluation
        restEvaluationMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
