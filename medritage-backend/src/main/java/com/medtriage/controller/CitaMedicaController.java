package com.medtriage.controller;

import com.medtriage.dto.CitaMedicaDTO;
import com.medtriage.dto.CrearCitaDTO;
import com.medtriage.service.CitaMedicaService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/citas")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaMedicaController {

    private final CitaMedicaService service;

    public CitaMedicaController(CitaMedicaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CitaMedicaDTO> obtenerTodas() {
        return service.obtenerTodas();
    }

    @GetMapping("/prioridad/{prioridad}")
    public List<CitaMedicaDTO> obtenerPorPrioridad(@PathVariable String prioridad) {
        return service.obtenerPorPrioridad(prioridad);
    }

    @PostMapping
    public CitaMedicaDTO crear(@RequestBody CrearCitaDTO dto) {
        return service.crearCita(dto);
    }

    @PatchMapping("/{id}/estado")
    public CitaMedicaDTO actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.actualizarEstado(id, body.get("estado"));
    }
}
