package com.esisalama.marissamayer.service;

import com.esisalama.marissamayer.domain.Creneau;
import com.esisalama.marissamayer.repository.CreneauRepository;
import com.esisalama.marissamayer.service.dto.CreneauDTO;
import com.esisalama.marissamayer.service.mapper.CreneauMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Creneau}.
 */
@Service
@Transactional
public class CreneauService {

    private final Logger log = LoggerFactory.getLogger(CreneauService.class);

    private final CreneauRepository creneauRepository;

    private final CreneauMapper creneauMapper;

    public CreneauService(CreneauRepository creneauRepository, CreneauMapper creneauMapper) {
        this.creneauRepository = creneauRepository;
        this.creneauMapper = creneauMapper;
    }

    /**
     * Save a creneau.
     *
     * @param creneauDTO the entity to save.
     * @return the persisted entity.
     */
    public CreneauDTO save(CreneauDTO creneauDTO) {
        log.debug("Request to save Creneau : {}", creneauDTO);
        Creneau creneau = creneauMapper.toEntity(creneauDTO);
        creneau = creneauRepository.save(creneau);
        return creneauMapper.toDto(creneau);
    }

    /**
     * Update a creneau.
     *
     * @param creneauDTO the entity to save.
     * @return the persisted entity.
     */
    public CreneauDTO update(CreneauDTO creneauDTO) {
        log.debug("Request to update Creneau : {}", creneauDTO);
        Creneau creneau = creneauMapper.toEntity(creneauDTO);
        creneau = creneauRepository.save(creneau);
        return creneauMapper.toDto(creneau);
    }

    /**
     * Partially update a creneau.
     *
     * @param creneauDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CreneauDTO> partialUpdate(CreneauDTO creneauDTO) {
        log.debug("Request to partially update Creneau : {}", creneauDTO);

        return creneauRepository
            .findById(creneauDTO.getId())
            .map(existingCreneau -> {
                creneauMapper.partialUpdate(existingCreneau, creneauDTO);

                return existingCreneau;
            })
            .map(creneauRepository::save)
            .map(creneauMapper::toDto);
    }

    /**
     * Get all the creneaus.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CreneauDTO> findAll() {
        log.debug("Request to get all Creneaus");
        return creneauRepository.findAll().stream().map(creneauMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one creneau by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CreneauDTO> findOne(Long id) {
        log.debug("Request to get Creneau : {}", id);
        return creneauRepository.findById(id).map(creneauMapper::toDto);
    }

    /**
     * Delete the creneau by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Creneau : {}", id);
        creneauRepository.deleteById(id);
    }
}
