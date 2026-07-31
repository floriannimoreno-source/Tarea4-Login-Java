package com.tarea4.vista;

import com.tarea4.dao.UsuarioDAO;
import com.tarea4.factory.DAOFactory;
import com.tarea4.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class RegistroFrame extends JFrame {

    private JTextField txtNombre, txtApellido, txtTelefono, txtCorreo, txtUsuario;
    private JPasswordField txtPassword, txtConfirmar;
    private final UsuarioDAO usuarioDAO;

    public RegistroFrame() {
        usuarioDAO = DAOFactory.crearUsuarioDAO();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Registro de Usuario");
        setSize(420, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(179, 196, 232));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("REGISTRO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        String[] etiquetas = {"Nombre:", "Apellido:", "Teléfono:", "Correo:", "Usuario:",
                "Contraseña:", "Confirmar Contraseña:"};

        txtNombre = new JTextField(18);
        txtApellido = new JTextField(18);
        txtTelefono = new JTextField(18);
        txtCorreo = new JTextField(18);
        txtUsuario = new JTextField(18);
        txtPassword = new JPasswordField(18);   // oculta el texto
        txtConfirmar = new JPasswordField(18);  // oculta el texto

        JComponent[] campos = {txtNombre, txtApellido, txtTelefono, txtCorreo, txtUsuario,
                txtPassword, txtConfirmar};

        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panel.add(new JLabel(etiquetas[i]), gbc);
            gbc.gridx = 1;
            panel.add(campos[i], gbc);
        }

        JButton btnRegistrar = new JButton("Registrar");
        gbc.gridx = 0;
        gbc.gridy = etiquetas.length + 1;
        gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);

        JButton btnVolver = new JButton("Volver al Login");
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setForeground(Color.BLUE.darker());
        gbc.gridy = etiquetas.length + 2;
        panel.add(btnVolver, gbc);

        add(panel);

        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e -> volverLogin());
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String confirmar = new String(txtConfirmar.getPassword()).trim();

        if (nombre.isEmpty()) { mostrarError("Debe ingresar el nombre"); return; }
        if (apellido.isEmpty()) { mostrarError("Debe ingresar el apellido"); return; }
        if (telefono.isEmpty()) { mostrarError("Debe ingresar el número de teléfono"); return; }
        if (correo.isEmpty()) { mostrarError("Debe ingresar el correo electrónico"); return; }
        if (usuario.isEmpty()) { mostrarError("Debe ingresar el nombre de usuario"); return; }
        if (password.isEmpty()) { mostrarError("Debe ingresar la contraseña"); return; }
        if (confirmar.isEmpty()) { mostrarError("Debe confirmar la contraseña"); return; }

        if (!password.equals(confirmar)) {
            mostrarError("La contraseña y la confirmación no coinciden");
            return;
        }

        if (usuarioDAO.existeUsername(usuario)) {
            mostrarError("El nombre de usuario ya está registrado");
            return;
        }

        Usuario nuevo = new Usuario(nombre, apellido, telefono, correo, usuario, password);
        boolean exito = usuarioDAO.registrar(nuevo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            volverLogin();
        } else {
            mostrarError("Ocurrió un error al registrar el usuario");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void volverLogin() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
