package com.esisalama.marissamayer.service.mapper;

import com.esisalama.marissamayer.domain.Catalogue;
import com.esisalama.marissamayer.domain.User;
import com.esisalama.marissamayer.service.dto.CatalogueDTO;
import com.esisalama.marissamayer.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Catalogue} and its DTO {@link CatalogueDTO}.
 */
@Mapper(componentModel = "spring")
public interface CatalogueMapper extends EntityMapper<CatalogueDTO, Catalogue> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    CatalogueDTO toDto(Catalogue s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
