package com.esisalama.marissamayer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.esisalama.marissamayer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreneauTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Creneau.class);
        Creneau creneau1 = new Creneau();
        creneau1.setId(1L);
        Creneau creneau2 = new Creneau();
        creneau2.setId(creneau1.getId());
        assertThat(creneau1).isEqualTo(creneau2);
        creneau2.setId(2L);
        assertThat(creneau1).isNotEqualTo(creneau2);
        creneau1.setId(null);
        assertThat(creneau1).isNotEqualTo(creneau2);
    }
}
