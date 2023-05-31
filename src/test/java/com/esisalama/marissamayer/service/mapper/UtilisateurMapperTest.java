package com.esisalama.marissamayer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilisateurMapperTest {

    private UtilisateurMapper utilisateurMapper;

    @BeforeEach
    public void setUp() {
        utilisateurMapper = new UtilisateurMapperImpl();
    }
}
