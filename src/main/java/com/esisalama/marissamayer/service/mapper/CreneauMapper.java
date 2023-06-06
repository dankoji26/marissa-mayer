package com.esisalama.marissamayer.service.mapper;

import com.esisalama.marissamayer.domain.Cours;
import com.esisalama.marissamayer.domain.Creneau;
import com.esisalama.marissamayer.domain.Utilisateur;
import com.esisalama.marissamayer.service.dto.CoursDTO;
import com.esisalama.marissamayer.service.dto.CreneauDTO;
import com.esisalama.marissamayer.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Creneau} and its DTO {@link CreneauDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreneauMapper extends EntityMapper<CreneauDTO, Creneau> {
    @Mapping(target = "cours", source = "cours", qualifiedByName = "coursId")
    @Mapping(target = "user", source = "user", qualifiedByName = "utilisateurId")
    CreneauDTO toDto(Creneau s);

    @Named("coursId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CoursDTO toDtoCoursId(Cours cours);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
