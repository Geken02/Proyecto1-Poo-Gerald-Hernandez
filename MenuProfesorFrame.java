package poo.proyecto1.persona.profesor;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import poo.proyecto1.persona.personalogin.LoginFrame;
import poo.proyecto1.persona.Persona;

public class MenuProfesorFrame extends JFrame{
    public MenuProfesorFrame(Persona usuario, List<Persona> listaUsuarios) {
                // --- Configuración de la Ventana ---
                setTitle("Menú Principal de Profesores");
                setSize(500, 400);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana, no la app
                setLocationRelativeTo(null);

                // --- Panel Principal ---
                JPanel panel = new JPanel(new BorderLayout());

                // --- Etiqueta de Bienvenida ---
                JLabel welcomeLabel = new JLabel("Bienvenido(a), " + usuario.getNombre(), SwingConstants.CENTER);
                welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
                panel.add(welcomeLabel, BorderLayout.NORTH);

                // --- Creación de la Barra de Menú ---
                JMenuBar menuBar = new JMenuBar();

                // 1. Menú "Perfil Profesor"
                JMenu perfilMenu = new JMenu("Perfil Profesor");
                perfilMenu.setMnemonic(KeyEvent.VK_P); // Atajo: Alt + P
                perfilMenu
                                .addActionListener(e -> JOptionPane.showMessageDialog(this,
                                                "Opción 'Perfil Profesor' seleccionada."));

                // 2. Menú "Evaluaciones"
                JMenu evaluacionesMenu = new JMenu("Evaluaciones");
                evaluacionesMenu.setMnemonic(KeyEvent.VK_E); // Atajo: Alt + E

                JMenuItem agregarEvaluacionItem = new JMenuItem("Agregar");
                JMenuItem consultarEvaluacionItem = new JMenuItem("Consultar");
                JMenuItem modificarEvaluacionItem = new JMenuItem("Modificar");
                JMenuItem eliminarEvaluacionItem = new JMenuItem("Eliminar");

                // Añadir acciones a los submenús
                agregarEvaluacionItem.addActionListener(
                                e -> JOptionPane.showMessageDialog(this, "Opción 'Agregar Evaluación' seleccionada."));
                consultarEvaluacionItem.addActionListener(
                                e -> JOptionPane.showMessageDialog(this,
                                                "Opción 'Consultar Evaluación' seleccionada."));
                modificarEvaluacionItem.addActionListener(
                                e -> JOptionPane.showMessageDialog(this,
                                                "Opción 'Modificar Evaluación' seleccionada."));
                eliminarEvaluacionItem.addActionListener(
                                e -> JOptionPane.showMessageDialog(this, "Opción 'Eliminar Evaluación' seleccionada."));

                // Añadir los submenús al menú "Evaluaciones"
                evaluacionesMenu.add(agregarEvaluacionItem);
                evaluacionesMenu.add(consultarEvaluacionItem);
                evaluacionesMenu.add(modificarEvaluacionItem);
                evaluacionesMenu.add(eliminarEvaluacionItem);

                // --- Añadir todos los menús a la barra de menú ---
                menuBar.add(perfilMenu);
                menuBar.add(evaluacionesMenu);

                // --- Establecer la barra de menú en la ventana ---
                this.setJMenuBar(menuBar);

                // --- Botón de Cerrar Sesión ---
                JButton logoutButton = new JButton("Cerrar Sesión");
                logoutButton.addActionListener(e -> {
                        // 1. Cierra la ventana del menú actual
                        dispose();
                        // 2. Crea una nueva ventana de login, pasándole la lista de usuarios
                        SwingUtilities.invokeLater(() -> {
                                LoginFrame loginFrame = new LoginFrame(listaUsuarios);
                                loginFrame.setVisible(true);
                        });
                });
                panel.add(logoutButton, BorderLayout.SOUTH);

                // --- Añadir el panel a la ventana y hacerla visible ---
                add(panel);
                setVisible(true);
        }
}
