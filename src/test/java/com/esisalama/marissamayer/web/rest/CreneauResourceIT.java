package com.esisalama.marissamayer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esisalama.marissamayer.IntegrationTest;
import com.esisalama.marissamayer.domain.Cours;
import com.esisalama.marissamayer.domain.Creneau;
import com.esisalama.marissamayer.domain.Utilisateur;
import com.esisalama.marissamayer.domain.enumeration.CreneauStatuts;
import com.esisalama.marissamayer.domain.enumeration.Jour;
import com.esisalama.marissamayer.repository.CreneauRepository;
import com.esisalama.marissamayer.service.dto.CreneauDTO;
import com.esisalama.marissamayer.service.mapper.CreneauMapper;
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
 * Integration tests for the {@link CreneauResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreneauResourceIT {

    private static final Jour DEFAULT_JOUR = Jour.LUNDI;
    private static final Jour UPDATED_JOUR = Jour.MARDI;

    private static final String DEFAULT_HEURE_DEBUT = "15:18";
    private static final String UPDATED_HEURE_DEBUT = "19:13";

    private static final String DEFAULT_HEURE_FIN = "17:12";
    private static final String UPDATED_HEURE_FIN = "10:36";

    private static final CreneauStatuts DEFAULT_STATUTS = CreneauStatuts.LIBRE;
    private static final CreneauStatuts UPDATED_STATUTS = CreneauStatuts.OCCUPEE;

    private static final String ENTITY_API_URL = "/api/creneaus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreneauRepository creneauRepository;

    @Autowired
    private CreneauMapper creneauMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreneauMockMvc;

    private Creneau creneau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creneau createEntity(EntityManager em) {
        Creneau creneau = new Creneau()
            .jour(DEFAULT_JOUR)
            .heureDebut(DEFAULT_HEURE_DEBUT)
            .heureFin(DEFAULT_HEURE_FIN)
            .statuts(DEFAULT_STATUTS);
        // Add required entity
        Cours cours;
        if (TestUtil.findAll(em, Cours.class).isEmpty()) {
            cours = CoursResourceIT.createEntity(em);
            em.persist(cours);
            em.flush();
        } else {
            cours = TestUtil.findAll(em, Cours.class).get(0);
        }
        creneau.setCours(cours);
        // Add required entity
        Utilisateur utilisateur;
        if (TestUtil.findAll(em, Utilisateur.class).isEmpty()) {
            utilisateur = UtilisateurResourceIT.createEntity(em);
            em.persist(utilisateur);
            em.flush();
        } else {
            utilisateur = TestUtil.findAll(em, Utilisateur.class).get(0);
        }
        creneau.setUtilisateur(utilisateur);
        return creneau;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creneau createUpdatedEntity(EntityManager em) {
        Creneau creneau = new Creneau()
            .jour(UPDATED_JOUR)
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN)
            .statuts(UPDATED_STATUTS);
        // Add required entity
        Cours cours;
        if (TestUtil.findAll(em, Cours.class).isEmpty()) {
            cours = CoursResourceIT.createUpdatedEntity(em);
            em.persist(cours);
            em.flush();
        } else {
            cours = TestUtil.findAll(em, Cours.class).get(0);
        }
        creneau.setCours(cours);
        // Add required entity
        Utilisateur utilisateur;
        if (TestUtil.findAll(em, Utilisateur.class).isEmpty()) {
            utilisateur = UtilisateurResourceIT.createUpdatedEntity(em);
            em.persist(utilisateur);
            em.flush();
        } else {
            utilisateur = TestUtil.findAll(em, Utilisateur.class).get(0);
        }
        creneau.setUtilisateur(utilisateur);
        return creneau;
    }

    @BeforeEach
    public void initTest() {
        creneau = createEntity(em);
    }

    @Test
    @Transactional
    void createCreneau() throws Exception {
        int databaseSizeBeforeCreate = creneauRepository.findAll().size();
        // Create the Creneau
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);
        restCreneauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creneauDTO)))
            .andExpect(status().isCreated());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeCreate + 1);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getJour()).isEqualTo(DEFAULT_JOUR);
        assertThat(testCreneau.getHeureDebut()).isEqualTo(DEFAULT_HEURE_DEBUT);
        assertThat(testCreneau.getHeureFin()).isEqualTo(DEFAULT_HEURE_FIN);
        assertThat(testCreneau.getStatuts()).isEqualTo(DEFAULT_STATUTS);
    }

    @Test
    @Transactional
    void createCreneauWithExistingId() throws Exception {
        // Create the Creneau with an existing ID
        creneau.setId(1L);
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);

        int databaseSizeBeforeCreate = creneauRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreneauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creneauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatutsIsRequired() throws Exception {
        int databaseSizeBeforeTest = creneauRepository.findAll().size();
        // set the field null
        creneau.setStatuts(null);

        // Create the Creneau, which fails.
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);

        restCreneauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creneauDTO)))
            .andExpect(status().isBadRequest());

        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCreneaus() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        // Get all the creneauList
        restCreneauMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creneau.getId().intValue())))
            .andExpect(jsonPath("$.[*].jour").value(hasItem(DEFAULT_JOUR.toString())))
            .andExpect(jsonPath("$.[*].heureDebut").value(hasItem(DEFAULT_HEURE_DEBUT)))
            .andExpect(jsonPath("$.[*].heureFin").value(hasItem(DEFAULT_HEURE_FIN)))
            .andExpect(jsonPath("$.[*].statuts").value(hasItem(DEFAULT_STATUTS.toString())));
    }

    @Test
    @Transactional
    void getCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        // Get the creneau
        restCreneauMockMvc
            .perform(get(ENTITY_API_URL_ID, creneau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creneau.getId().intValue()))
            .andExpect(jsonPath("$.jour").value(DEFAULT_JOUR.toString()))
            .andExpect(jsonPath("$.heureDebut").value(DEFAULT_HEURE_DEBUT))
            .andExpect(jsonPath("$.heureFin").value(DEFAULT_HEURE_FIN))
            .andExpect(jsonPath("$.statuts").value(DEFAULT_STATUTS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCreneau() throws Exception {
        // Get the creneau
        restCreneauMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();

        // Update the creneau
        Creneau updatedCreneau = creneauRepository.findById(creneau.getId()).get();
        // Disconnect from session so that the updates on updatedCreneau are not directly saved in db
        em.detach(updatedCreneau);
        updatedCreneau.jour(UPDATED_JOUR).heureDebut(UPDATED_HEURE_DEBUT).heureFin(UPDATED_HEURE_FIN).statuts(UPDATED_STATUTS);
        CreneauDTO creneauDTO = creneauMapper.toDto(updatedCreneau);

        restCreneauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creneauDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creneauDTO))
            )
            .andExpect(status().isOk());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getJour()).isEqualTo(UPDATED_JOUR);
        assertThat(testCreneau.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testCreneau.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testCreneau.getStatuts()).isEqualTo(UPDATED_STATUTS);
    }

    @Test
    @Transactional
    void putNonExistingCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // Create the Creneau
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creneauDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creneauDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // Create the Creneau
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creneauDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // Create the Creneau
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creneauDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreneauWithPatch() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();

        // Update the creneau using partial update
        Creneau partialUpdatedCreneau = new Creneau();
        partialUpdatedCreneau.setId(creneau.getId());

        partialUpdatedCreneau.jour(UPDATED_JOUR).heureFin(UPDATED_HEURE_FIN).statuts(UPDATED_STATUTS);

        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreneau.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreneau))
            )
            .andExpect(status().isOk());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getJour()).isEqualTo(UPDATED_JOUR);
        assertThat(testCreneau.getHeureDebut()).isEqualTo(DEFAULT_HEURE_DEBUT);
        assertThat(testCreneau.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testCreneau.getStatuts()).isEqualTo(UPDATED_STATUTS);
    }

    @Test
    @Transactional
    void fullUpdateCreneauWithPatch() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();

        // Update the creneau using partial update
        Creneau partialUpdatedCreneau = new Creneau();
        partialUpdatedCreneau.setId(creneau.getId());

        partialUpdatedCreneau.jour(UPDATED_JOUR).heureDebut(UPDATED_HEURE_DEBUT).heureFin(UPDATED_HEURE_FIN).statuts(UPDATED_STATUTS);

        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreneau.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreneau))
            )
            .andExpect(status().isOk());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getJour()).isEqualTo(UPDATED_JOUR);
        assertThat(testCreneau.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testCreneau.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testCreneau.getStatuts()).isEqualTo(UPDATED_STATUTS);
    }

    @Test
    @Transactional
    void patchNonExistingCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // Create the Creneau
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creneauDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creneauDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // Create the Creneau
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creneauDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();
        creneau.setId(count.incrementAndGet());

        // Create the Creneau
        CreneauDTO creneauDTO = creneauMapper.toDto(creneau);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreneauMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(creneauDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        int databaseSizeBeforeDelete = creneauRepository.findAll().size();

        // Delete the creneau
        restCreneauMockMvc
            .perform(delete(ENTITY_API_URL_ID, creneau.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
