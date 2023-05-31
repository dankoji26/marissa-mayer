package com.esisalama.marissamayer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvaluationMapperTest {

    private EvaluationMapper evaluationMapper;

    @BeforeEach
    public void setUp() {
        evaluationMapper = new EvaluationMapperImpl();
    }
}
