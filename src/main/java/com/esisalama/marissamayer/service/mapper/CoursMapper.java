package com.esisalama.marissamayer.service.mapper;

import com.esisalama.marissamayer.domain.Catalogue;
import com.esisalama.marissamayer.domain.Categorie;
import com.esisalama.marissamayer.domain.Cours;
import com.esisalama.marissamayer.service.dto.CatalogueDTO;
import com.esisalama.marissamayer.service.dto.CategorieDTO;
import com.esisalama.marissamayer.service.dto.CoursDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cours} and its DTO {@link CoursDTO}.
 */
@Mapper(componentModel = "spring")
public interface CoursMapper extends EntityMapper<CoursDTO, Cours> {
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categorieIdSet")
    @Mapping(target = "catalogue", source = "catalogue", qualifiedByName = "catalogueId")
    CoursDTO toDto(Cours s);

    @Mapping(target = "removeCategories", ignore = true)
    Cours toEntity(CoursDTO coursDTO);

    @Named("categorieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieDTO toDtoCategorieId(Categorie categorie);

    @Named("categorieIdSet")
    default Set<CategorieDTO> toDtoCategorieIdSet(Set<Categorie> categorie) {
        return categorie.stream().map(this::toDtoCategorieId).collect(Collectors.toSet());
    }

    @Named("catalogueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CatalogueDTO toDtoCatalogueId(Catalogue catalogue);
}
