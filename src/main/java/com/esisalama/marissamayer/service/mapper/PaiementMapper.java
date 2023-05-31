package com.esisalama.marissamayer.service.mapper;

import com.esisalama.marissamayer.domain.Paiement;
import com.esisalama.marissamayer.domain.Reservation;
import com.esisalama.marissamayer.service.dto.PaiementDTO;
import com.esisalama.marissamayer.service.dto.ReservationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {
    @Mapping(target = "reservation", source = "reservation", qualifiedByName = "reservationId")
    PaiementDTO toDto(Paiement s);

    @Named("reservationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationDTO toDtoReservationId(Reservation reservation);
}
