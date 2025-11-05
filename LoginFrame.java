package poo.proyecto1.persona.personalogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;
import poo.proyecto1.persona.Persona;
import poo.proyecto1.admin.Administrador;


public class LoginFrame extends JFrame {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private final List<Persona> usuarios;

    public LoginFrame(List<Persona> usuarios) {
        this.usuarios = usuarios;
        setTitle("Sistema de Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(15);
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Iniciar Sesión");
        panel.add(loginButton, gbc);

        gbc.gridy = 3;
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel, gbc);

        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            if (Administrador.ID_ADMIN.equals(id) && Administrador.CONTRASENA_ADMIN.equals(password))
            {
                Administrador admin = new Administrador();
                admin.mostrarMenu(usuarios);
            }else{
            Optional<Persona> usuarioOpt = LoginUsuario.autenticar(id, password, usuarios);

            if (usuarioOpt.isPresent()) {
                Persona usuario = usuarioOpt.get();
                dispose(); // Cierra la ventana de login actual
                // Pasa la lista de usuarios al método mostrarMenu
                usuario.mostrarMenu(usuarios);
            } else {
                messageLabel.setText("ID o contraseña incorrectos.");
            }
        }
        });

        add(panel);
    }
}