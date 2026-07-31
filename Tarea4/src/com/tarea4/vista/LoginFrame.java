package com.tarea4.vista;

import com.tarea4.dao.UsuarioDAO;
import com.tarea4.factory.DAOFactory;
import com.tarea4.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private final UsuarioDAO usuarioDAO;

    public LoginFrame() {
        usuarioDAO = DAOFactory.crearUsuarioDAO();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Login");
        setSize(360, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(179, 196, 232));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("LOGIN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15); // oculta el texto automáticamente
        panel.add(txtPassword, gbc);

        JButton btnEntrar = new JButton("Entrar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnEntrar, gbc);

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setForeground(Color.BLUE.darker());
        gbc.gridy = 4;
        panel.add(btnRegistrarse, gbc);

        add(panel);

        btnEntrar.addActionListener(e -> iniciarSesion());
        btnRegistrarse.addActionListener(e -> abrirRegistro());
        getRootPane().setDefaultButton(btnEntrar);
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario u = usuarioDAO.login(usuario, password);
        if (u != null) {
            this.dispose();
            new PrincipalFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        this.dispose();
        new RegistroFrame().setVisible(true);
    }
}
