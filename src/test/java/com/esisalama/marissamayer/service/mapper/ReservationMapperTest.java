package com.esisalama.marissamayer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationMapperTest {

    private ReservationMapper reservationMapper;

    @BeforeEach
    public void setUp() {
        reservationMapper = new ReservationMapperImpl();
    }
}
