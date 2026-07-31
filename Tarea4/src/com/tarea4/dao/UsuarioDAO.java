package com.tarea4.dao;

import com.tarea4.modelo.Usuario;

import java.util.List;

/**
 * Contrato de acceso a datos para Usuario.
 * Permite desacoplar la lógica de negocio de la implementación concreta (MySQL).
 */
public interface UsuarioDAO {
    boolean registrar(Usuario usuario);
    Usuario login(String username, String password);
    List<Usuario> listarTodos();
    boolean actualizar(Usuario usuario);
    boolean eliminar(int id);
    boolean existeUsername(String username);
}
