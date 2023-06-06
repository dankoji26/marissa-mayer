package com.esisalama.marissamayer.service;

import com.esisalama.marissamayer.domain.Cours;
import com.esisalama.marissamayer.repository.CoursRepository;
import com.esisalama.marissamayer.service.dto.CoursDTO;
import com.esisalama.marissamayer.service.mapper.CoursMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cours}.
 */
@Service
@Transactional
public class CoursService {

    private final Logger log = LoggerFactory.getLogger(CoursService.class);

    private final CoursRepository coursRepository;

    private final CoursMapper coursMapper;

    public CoursService(CoursRepository coursRepository, CoursMapper coursMapper) {
        this.coursRepository = coursRepository;
        this.coursMapper = coursMapper;
    }

    /**
     * Save a cours.
     *
     * @param coursDTO the entity to save.
     * @return the persisted entity.
     */
    public CoursDTO save(CoursDTO coursDTO) {
        log.debug("Request to save Cours : {}", coursDTO);
        Cours cours = coursMapper.toEntity(coursDTO);
        cours = coursRepository.save(cours);
        return coursMapper.toDto(cours);
    }

    /**
     * Update a cours.
     *
     * @param coursDTO the entity to save.
     * @return the persisted entity.
     */
    public CoursDTO update(CoursDTO coursDTO) {
        log.debug("Request to update Cours : {}", coursDTO);
        Cours cours = coursMapper.toEntity(coursDTO);
        cours = coursRepository.save(cours);
        return coursMapper.toDto(cours);
    }

    /**
     * Partially update a cours.
     *
     * @param coursDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CoursDTO> partialUpdate(CoursDTO coursDTO) {
        log.debug("Request to partially update Cours : {}", coursDTO);

        return coursRepository
            .findById(coursDTO.getId())
            .map(existingCours -> {
                coursMapper.partialUpdate(existingCours, coursDTO);

                return existingCours;
            })
            .map(coursRepository::save)
            .map(coursMapper::toDto);
    }

    /**
     * Get all the cours.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CoursDTO> findAll() {
        log.debug("Request to get all Cours");
        return coursRepository.findAll().stream().map(coursMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the cours with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CoursDTO> findAllWithEagerRelationships(Pageable pageable) {
        return coursRepository.findAllWithEagerRelationships(pageable).map(coursMapper::toDto);
    }

    /**
     * Get one cours by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CoursDTO> findOne(Long id) {
        log.debug("Request to get Cours : {}", id);
        return coursRepository.findOneWithEagerRelationships(id).map(coursMapper::toDto);
    }

    /**
     * Delete the cours by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cours : {}", id);
        coursRepository.deleteById(id);
    }
}
