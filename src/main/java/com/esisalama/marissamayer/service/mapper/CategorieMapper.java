package com.esisalama.marissamayer.service.mapper;

import com.esisalama.marissamayer.domain.Categorie;
import com.esisalama.marissamayer.service.dto.CategorieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categorie} and its DTO {@link CategorieDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategorieMapper extends EntityMapper<CategorieDTO, Categorie> {}
