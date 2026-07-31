package com.tarea4.vista;

import com.tarea4.dao.UsuarioDAO;
import com.tarea4.factory.DAOFactory;
import com.tarea4.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PrincipalFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private final UsuarioDAO usuarioDAO;

    public PrincipalFrame() {
        usuarioDAO = DAOFactory.crearUsuarioDAO();
        inicializarComponentes();
        cargarUsuarios();
    }

    private void inicializarComponentes() {
        setTitle("Clientes Registrados");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Clientes Registrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Apellido", "Teléfono", "Correo electrónico", "Usuario"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.setRowHeight(24);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrarSesion);
        add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void cargarUsuarios() {
        modelo.setRowCount(0);
        List<Usuario> lista = usuarioDAO.listarTodos();
        for (Usuario u : lista) {
            // Uso polimórfico: cada Usuario resuelve su propia mostrarInfo() en tiempo de ejecución
            modelo.addRow(new Object[]{u.getId(), u.getNombre(), u.getApellido(),
                    u.getTelefono(), u.getCorreo(), u.getUsername()});
        }
    }

    private int obtenerFilaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario de la lista",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        return fila;
    }

    private void actualizarUsuario() {
        int fila = obtenerFilaSeleccionada();
        if (fila == -1) return;

        int id = (int) modelo.getValueAt(fila, 0);
        String nombreActual = (String) modelo.getValueAt(fila, 1);
        String apellidoActual = (String) modelo.getValueAt(fila, 2);
        String telefonoActual = (String) modelo.getValueAt(fila, 3);
        String correoActual = (String) modelo.getValueAt(fila, 4);
        String usuarioActualNombre = (String) modelo.getValueAt(fila, 5);

        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtApellido = new JTextField(apellidoActual);
        JTextField txtTelefono = new JTextField(telefonoActual);
        JTextField txtCorreo = new JTextField(correoActual);
        JTextField txtUsuario = new JTextField(usuarioActualNombre);
        JPasswordField txtPassword = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Apellido:")); panel.add(txtApellido);
        panel.add(new JLabel("Teléfono:")); panel.add(txtTelefono);
        panel.add(new JLabel("Correo:")); panel.add(txtCorreo);
        panel.add(new JLabel("Usuario:")); panel.add(txtUsuario);
        panel.add(new JLabel("Nueva contraseña (dejar en blanco para no cambiarla):"));
        panel.add(txtPassword);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Actualizar Usuario",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()
                    || txtTelefono.getText().trim().isEmpty() || txtCorreo.getText().trim().isEmpty()
                    || txtUsuario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuarioExistente = usuarioDAO.listarTodos().stream()
                    .filter(u -> u.getId() == id)
                    .findFirst()
                    .orElse(null);

            String passwordFinal = new String(txtPassword.getPassword()).trim();
            if (passwordFinal.isEmpty() && usuarioExistente != null) {
                passwordFinal = usuarioExistente.getPassword();
            }

            Usuario actualizado = new Usuario(id, txtNombre.getText().trim(), txtApellido.getText().trim(),
                    txtTelefono.getText().trim(), txtCorreo.getText().trim(),
                    txtUsuario.getText().trim(), passwordFinal);

            boolean exito = usuarioDAO.actualizar(actualizado);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarUsuario() {
        int fila = obtenerFilaSeleccionada();
        if (fila == -1) return;

        int id = (int) modelo.getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = usuarioDAO.eliminar(id);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cerrarSesion() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
