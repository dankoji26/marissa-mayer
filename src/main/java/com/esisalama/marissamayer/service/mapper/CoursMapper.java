package com.esisalama.marissamayer.service.mapper;

import com.esisalama.marissamayer.domain.Catalogue;
import com.esisalama.marissamayer.domain.Cours;
import com.esisalama.marissamayer.service.dto.CatalogueDTO;
import com.esisalama.marissamayer.service.dto.CoursDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cours} and its DTO {@link CoursDTO}.
 */
@Mapper(componentModel = "spring")
public interface CoursMapper extends EntityMapper<CoursDTO, Cours> {
    @Mapping(target = "catalogue", source = "catalogue", qualifiedByName = "catalogueId")
    CoursDTO toDto(Cours s);

    @Named("catalogueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CatalogueDTO toDtoCatalogueId(Catalogue catalogue);
}
