package com.esisalama.marissamayer.service;

import com.esisalama.marissamayer.domain.Catalogue;
import com.esisalama.marissamayer.repository.CatalogueRepository;
import com.esisalama.marissamayer.service.dto.CatalogueDTO;
import com.esisalama.marissamayer.service.mapper.CatalogueMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Catalogue}.
 */
@Service
@Transactional
public class CatalogueService {

    private final Logger log = LoggerFactory.getLogger(CatalogueService.class);

    private final CatalogueRepository catalogueRepository;

    private final CatalogueMapper catalogueMapper;

    public CatalogueService(CatalogueRepository catalogueRepository, CatalogueMapper catalogueMapper) {
        this.catalogueRepository = catalogueRepository;
        this.catalogueMapper = catalogueMapper;
    }

    /**
     * Save a catalogue.
     *
     * @param catalogueDTO the entity to save.
     * @return the persisted entity.
     */
    public CatalogueDTO save(CatalogueDTO catalogueDTO) {
        log.debug("Request to save Catalogue : {}", catalogueDTO);
        Catalogue catalogue = catalogueMapper.toEntity(catalogueDTO);
        catalogue = catalogueRepository.save(catalogue);
        return catalogueMapper.toDto(catalogue);
    }

    /**
     * Update a catalogue.
     *
     * @param catalogueDTO the entity to save.
     * @return the persisted entity.
     */
    public CatalogueDTO update(CatalogueDTO catalogueDTO) {
        log.debug("Request to update Catalogue : {}", catalogueDTO);
        Catalogue catalogue = catalogueMapper.toEntity(catalogueDTO);
        catalogue = catalogueRepository.save(catalogue);
        return catalogueMapper.toDto(catalogue);
    }

    /**
     * Partially update a catalogue.
     *
     * @param catalogueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CatalogueDTO> partialUpdate(CatalogueDTO catalogueDTO) {
        log.debug("Request to partially update Catalogue : {}", catalogueDTO);

        return catalogueRepository
            .findById(catalogueDTO.getId())
            .map(existingCatalogue -> {
                catalogueMapper.partialUpdate(existingCatalogue, catalogueDTO);

                return existingCatalogue;
            })
            .map(catalogueRepository::save)
            .map(catalogueMapper::toDto);
    }

    /**
     * Get all the catalogues.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CatalogueDTO> findAll() {
        log.debug("Request to get all Catalogues");
        return catalogueRepository.findAll().stream().map(catalogueMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one catalogue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatalogueDTO> findOne(Long id) {
        log.debug("Request to get Catalogue : {}", id);
        return catalogueRepository.findById(id).map(catalogueMapper::toDto);
    }

    /**
     * Delete the catalogue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Catalogue : {}", id);
        catalogueRepository.deleteById(id);
    }
}
