package com.esisalama.marissamayer.service.mapper;

import com.esisalama.marissamayer.domain.Notification;
import com.esisalama.marissamayer.domain.Utilisateur;
import com.esisalama.marissamayer.service.dto.NotificationDTO;
import com.esisalama.marissamayer.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    NotificationDTO toDto(Notification s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
