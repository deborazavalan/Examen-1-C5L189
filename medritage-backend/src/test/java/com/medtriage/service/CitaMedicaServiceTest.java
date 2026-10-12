package com.medtriage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.medtriage.dto.CitaMedicaDTO;
import com.medtriage.dto.CrearCitaDTO;
import com.medtriage.model.CitaMedica;
import com.medtriage.model.Doctor;
import com.medtriage.model.Paciente;
import com.medtriage.repository.CitaMedicaRepository;
import com.medtriage.repository.DoctorRepository;
import com.medtriage.repository.PacienteRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CitaMedicaServiceTest {

    @Mock
    private CitaMedicaRepository citaRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private CitaMedicaService service;

    @Test
    void crearCita_guardaCitaPendiente() {
        Paciente paciente = new Paciente(1L, "120360994", "Débora Zavala Núñez", "debora.zavala@ucr.ac.cr", "71791439");
        Doctor doctor = new Doctor(1L, "Dr. Carlos Alvarado", "Medicina General", true);

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(citaRepository.count()).thenReturn(2L);
        when(citaRepository.save(any(CitaMedica.class))).thenAnswer(inv -> inv.getArgument(0));

        CrearCitaDTO dto = new CrearCitaDTO(1L, 1L, "ALTA", "Fiebre alta", new BigDecimal("45000.00"));
        CitaMedicaDTO resultado = service.crearCita(dto);

        assertEquals("CIT-2026-0003", resultado.codigoCita());
        assertEquals("PENDIENTE", resultado.estado());
    }
}
