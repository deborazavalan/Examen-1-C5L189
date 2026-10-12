package com.medtriage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String especialidad;
    private Boolean disponible;

    public Doctor() {
    }

    public Doctor(Long id, String nombre, String especialidad, Boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponible = disponible;
    }

    public Long getId() {
         return id; }
    public void setId(Long id) { 
        this.id = id; }
    public String getNombre() {
         return nombre; }
    public void setNombre(String nombre) {
         this.nombre = nombre; }
    public String getEspecialidad() {
         return especialidad; }
    public void setEspecialidad(String especialidad) {
         this.especialidad = especialidad; }
    public Boolean getDisponible() {
        return disponible; }
    public void setDisponible(Boolean disponible) {
         this.disponible = disponible; }
}
