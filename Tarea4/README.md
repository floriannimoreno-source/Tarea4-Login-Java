# Tarea 4 — Sistema de Login y Registro de Usuarios

Aplicación de escritorio en **Java Swing** con persistencia en **MySQL**, que cumple con el mandato:
login, registro, listado de usuarios, actualizar y eliminar.

## Estructura del proyecto

```
Tarea4/
├── sql/
│   └── tarea4_db.sql          -> Script para crear la base de datos y la tabla
└── src/com/tarea4/
    ├── Main.java               -> Punto de entrada
    ├── modelo/
    │   ├── Persona.java         -> Clase abstracta (ABSTRACCIÓN)
    │   └── Usuario.java         -> Hereda de Persona (HERENCIA, ENCAPSULAMIENTO, POLIMORFISMO)
    ├── conexion/
    │   └── ConexionDB.java      -> Patrón SINGLETON para la conexión a MySQL
    ├── dao/
    │   ├── UsuarioDAO.java      -> Interfaz de acceso a datos
    │   └── UsuarioDAOImpl.java  -> Implementación JDBC
    ├── factory/
    │   └── DAOFactory.java      -> Patrón FÁBRICA para crear los DAO
    └── vista/
        ├── LoginFrame.java      -> Pantalla de login
        ├── RegistroFrame.java   -> Pantalla de registro
        └── PrincipalFrame.java  -> Listado + actualizar/eliminar/cerrar sesión
```

## Cómo cumple los requisitos técnicos

**Orientación a objetos**
- **Abstracción:** `Persona` es una clase abstracta con el método abstracto `mostrarInfo()`.
- **Encapsulamiento:** todos los atributos son `private`/`protected`, con getters y setters.
- **Herencia:** `Usuario extends Persona`.
- **Polimorfismo:** `Usuario` sobrescribe `mostrarInfo()`; en tiempo de ejecución cada objeto resuelve su propio comportamiento.

**Patrones de diseño**
- **Singleton:** `ConexionDB` asegura una única conexión activa a la base de datos.
- **Fábrica (Factory):** `DAOFactory` centraliza la creación de los objetos `UsuarioDAO`.

**Requisitos funcionales cubiertos**
- Contraseña oculta con `JPasswordField` (login y registro).
- Validación de campos vacíos en login con el mensaje exacto pedido.
- Validación de todos los campos obligatorios en el registro, indicando el campo faltante.
- Validación de que contraseña y confirmación coincidan.
- Al iniciar sesión correctamente se cierra el login y se abre la pantalla principal con el listado.
- Botón "Cerrar Sesión" que regresa al login.
- Actualizar y eliminar usuarios, con recarga automática de la tabla tras el cambio.

## Configuración para ejecutar

1. **Instalar MySQL** y ejecutar el script `sql/tarea4_db.sql` (crea la base `tarea4_db` y la tabla `usuarios`).
2. **Descargar el conector JDBC de MySQL** (`mysql-connector-j`, archivo `.jar`) y agregarlo al *Build Path* del proyecto en Eclipse:
   - Clic derecho en el proyecto → Build Path → Add External Archives → seleccionar el `.jar`.
3. **Ajustar credenciales** en `ConexionDB.java` (usuario y contraseña de tu MySQL local) si son distintas a `root` / vacío.
4. Importar la carpeta `src` como proyecto Java en Eclipse (New → Java Project → From Existing Source).
5. Ejecutar `Main.java`.

