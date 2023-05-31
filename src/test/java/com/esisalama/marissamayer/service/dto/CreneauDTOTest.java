package com.esisalama.marissamayer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.esisalama.marissamayer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreneauDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreneauDTO.class);
        CreneauDTO creneauDTO1 = new CreneauDTO();
        creneauDTO1.setId(1L);
        CreneauDTO creneauDTO2 = new CreneauDTO();
        assertThat(creneauDTO1).isNotEqualTo(creneauDTO2);
        creneauDTO2.setId(creneauDTO1.getId());
        assertThat(creneauDTO1).isEqualTo(creneauDTO2);
        creneauDTO2.setId(2L);
        assertThat(creneauDTO1).isNotEqualTo(creneauDTO2);
        creneauDTO1.setId(null);
        assertThat(creneauDTO1).isNotEqualTo(creneauDTO2);
    }
}
