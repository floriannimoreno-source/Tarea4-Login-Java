package com.tarea4.modelo;

/**
 * Usuario del sistema.
 * HERENCIA: extiende de Persona, reutilizando nombre, apellido, teléfono y correo.
 * ENCAPSULAMIENTO: id, username y password son privados con getters/setters.
 * POLIMORFISMO: sobrescribe (override) el método mostrarInfo() de Persona.
 */
public class Usuario extends Persona {

    private int id;
    private String username;
    private String password;

    public Usuario(String nombre, String apellido, String telefono, String correo,
                    String username, String password) {
        super(nombre, apellido, telefono, correo);
        this.username = username;
        this.password = password;
    }

    public Usuario(int id, String nombre, String apellido, String telefono, String correo,
                    String username, String password) {
        this(nombre, apellido, telefono, correo, username, password);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String mostrarInfo() {
        return nombre + " " + apellido + " | Tel: " + telefono + " | Correo: " + correo + " | Usuario: " + username;
    }
}
