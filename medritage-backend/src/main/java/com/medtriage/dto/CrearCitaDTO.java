package com.medtriage.dto;

import java.math.BigDecimal;

public record CrearCitaDTO(
        Long pacienteId,
        Long doctorId,
        String nivelPrioridad,
        String motivoConsulta,
        BigDecimal montoConsulta) {
}
