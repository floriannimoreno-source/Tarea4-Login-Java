package com.tarea4.modelo;

/**
 * Clase abstracta que representa a una persona.
 * Aplica el pilar de ABSTRACCIÓN: define el "qué" (mostrarInfo)
 * sin implementar el "cómo", dejando eso a las subclases.
 */
public abstract class Persona {

    // ENCAPSULAMIENTO: atributos protegidos, accesibles solo mediante getters/setters
    protected String nombre;
    protected String apellido;
    protected String telefono;
    protected String correo;

    public Persona(String nombre, String apellido, String telefono, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Método abstracto: cada subclase decide cómo mostrar su información.
     * Punto de apoyo para el POLIMORFISMO.
     */
    public abstract String mostrarInfo();
}
