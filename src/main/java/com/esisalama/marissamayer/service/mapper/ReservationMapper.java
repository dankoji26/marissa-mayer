package com.esisalama.marissamayer.service.mapper;

import com.esisalama.marissamayer.domain.Reservation;
import com.esisalama.marissamayer.domain.Utilisateur;
import com.esisalama.marissamayer.service.dto.ReservationDTO;
import com.esisalama.marissamayer.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    ReservationDTO toDto(Reservation s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
