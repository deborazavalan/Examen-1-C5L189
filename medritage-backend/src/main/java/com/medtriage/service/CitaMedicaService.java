package com.medtriage.service;

import com.medtriage.dto.CitaMedicaDTO;
import com.medtriage.dto.CrearCitaDTO;
import com.medtriage.exception.DoctorNoDisponibleException;
import com.medtriage.model.CitaMedica;
import com.medtriage.model.Doctor;
import com.medtriage.model.Paciente;
import com.medtriage.repository.CitaMedicaRepository;
import com.medtriage.repository.DoctorRepository;
import com.medtriage.repository.PacienteRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CitaMedicaService {

    private final CitaMedicaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final DoctorRepository doctorRepository;

    public CitaMedicaService(CitaMedicaRepository citaRepository,
                             PacienteRepository pacienteRepository,
                             DoctorRepository doctorRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<CitaMedicaDTO> obtenerTodas() {
        return citaRepository.findAllByOrderByFechaCitaDesc().stream().map(this::toDTO).toList();
    }

    public List<CitaMedicaDTO> obtenerPorPrioridad(String prioridad) {
        return citaRepository.findByNivelPrioridadOrderByFechaCitaDesc(prioridad.toUpperCase())
                .stream().map(this::toDTO).toList();
    }

    public CitaMedicaDTO crearCita(CrearCitaDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Doctor doctor = doctorRepository.findById(dto.doctorId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        if (!doctor.getDisponible()) {
            throw new DoctorNoDisponibleException("El doctor no esta disponible");
        }

        CitaMedica cita = new CitaMedica();
        cita.setCodigoCita(String.format("CIT-2026-%04d", citaRepository.count() + 1));
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);
        cita.setNivelPrioridad(dto.nivelPrioridad());
        cita.setMotivoConsulta(dto.motivoConsulta());
        cita.setEstado("PENDIENTE");
        cita.setMontoConsulta(dto.montoConsulta());
        cita.setFechaCita(LocalDateTime.now());

        return toDTO(citaRepository.save(cita));
    }

    public CitaMedicaDTO actualizarEstado(Long id, String nuevoEstado) {
        CitaMedica cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setEstado(nuevoEstado);
        return toDTO(citaRepository.save(cita));
    }

    private CitaMedicaDTO toDTO(CitaMedica c) {
        return new CitaMedicaDTO(
                c.getId(),
                c.getCodigoCita(),
                c.getPaciente().getNombreCompleto(),
                c.getDoctor().getNombre(),
                c.getNivelPrioridad(),
                c.getMotivoConsulta(),
                c.getEstado(),
                c.getMontoConsulta(),
                c.getFechaCita());
    }
}
