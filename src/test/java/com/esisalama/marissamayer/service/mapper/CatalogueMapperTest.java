package com.esisalama.marissamayer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatalogueMapperTest {

    private CatalogueMapper catalogueMapper;

    @BeforeEach
    public void setUp() {
        catalogueMapper = new CatalogueMapperImpl();
    }
}
