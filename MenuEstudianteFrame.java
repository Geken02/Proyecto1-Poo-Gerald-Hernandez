package poo.proyecto1.persona.estudiante;

import javax.swing.*;

import poo.proyecto1.persona.personalogin.LoginFrame;
import poo.proyecto1.persona.Persona;
import poo.proyecto1.vistas.ConsultarPerfilEstudiante;
import poo.proyecto1.vistas.SelectorCursoEstudiante;
import poo.proyecto1.vistas.VentanaMatricularEnCurso;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;


public class MenuEstudianteFrame extends JFrame {

    public MenuEstudianteFrame(Estudiante usuario, List<Persona> listaUsuarios) {
        // --- Configuración de la Ventana ---
        setTitle("Menú Principal de Estudiantes");
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

        // 1. Menú "Perfil estudiante"
       JMenu perfilMenu = new JMenu("Perfil estudiante");
        perfilMenu.setMnemonic(KeyEvent.VK_P);
        JMenuItem verPerfilItem = new JMenuItem("Consultar Mi Perfil");
        verPerfilItem.addActionListener(e -> {
            new ConsultarPerfilEstudiante(MenuEstudianteFrame.this, usuario).setVisible(true);
        });
        perfilMenu.add(verPerfilItem);
        // 2. Menú "Matricular"
        JMenu matricularMenu = new JMenu("Matricular");
        matricularMenu.setMnemonic(KeyEvent.VK_M); 
        JMenuItem matricularItem = new JMenuItem("Matricular en Curso");
        matricularItem.addActionListener(e -> {
        new VentanaMatricularEnCurso(MenuEstudianteFrame.this, usuario).setVisible(true);
        });
        matricularMenu.add(matricularItem);
        // 3. Menú "Evaluaciones"
        JMenu evaluacionesMenu = new JMenu("Evaluaciones");
        evaluacionesMenu.setMnemonic(KeyEvent.VK_E); // Atajo: Alt + E
        evaluacionesMenu
                .addActionListener(e -> JOptionPane.showMessageDialog(this, "Opción 'Evaluaciones' seleccionada."));
        JMenuItem realizarEvaluacionItem = new JMenuItem("Realizar Evaluación");
        realizarEvaluacionItem.addActionListener(e -> {
            new SelectorCursoEstudiante(MenuEstudianteFrame.this, usuario).setVisible(true);
        });
        evaluacionesMenu.add(realizarEvaluacionItem);
        // 4. Menú "Desempeño personal"
        JMenu desempenoMenu = new JMenu("Desempeño personal");
        desempenoMenu.setMnemonic(KeyEvent.VK_D); // Atajo: Alt + D
        desempenoMenu.addActionListener(
                e -> JOptionPane.showMessageDialog(this, "Opción 'Desempeño personal' seleccionada."));

        // --- Añadir todos los menús a la barra de menú ---
        menuBar.add(perfilMenu);
        menuBar.add(matricularMenu);
        menuBar.add(evaluacionesMenu);
        menuBar.add(desempenoMenu);

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