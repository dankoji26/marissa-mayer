package com.esisalama.marissamayer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.esisalama.marissamayer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CatalogueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatalogueDTO.class);
        CatalogueDTO catalogueDTO1 = new CatalogueDTO();
        catalogueDTO1.setId(1L);
        CatalogueDTO catalogueDTO2 = new CatalogueDTO();
        assertThat(catalogueDTO1).isNotEqualTo(catalogueDTO2);
        catalogueDTO2.setId(catalogueDTO1.getId());
        assertThat(catalogueDTO1).isEqualTo(catalogueDTO2);
        catalogueDTO2.setId(2L);
        assertThat(catalogueDTO1).isNotEqualTo(catalogueDTO2);
        catalogueDTO1.setId(null);
        assertThat(catalogueDTO1).isNotEqualTo(catalogueDTO2);
    }
}
