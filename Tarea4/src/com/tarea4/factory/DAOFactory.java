package com.tarea4.factory;

import com.tarea4.dao.UsuarioDAO;
import com.tarea4.dao.UsuarioDAOImpl;

/**
 * PATRÓN DE DISEÑO: FÁBRICA (Factory)
 * Centraliza la creación de objetos DAO. Si el día de mañana se cambia
 * de motor de base de datos, solo se modifica esta clase.
 */
public class DAOFactory {

    public static UsuarioDAO crearUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
}
