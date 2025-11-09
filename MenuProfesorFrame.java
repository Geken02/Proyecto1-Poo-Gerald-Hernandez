// poo.proyecto1/usuarios/profesor/MenuProfesorFrame.java

package poo.proyecto1.persona.profesor;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import poo.proyecto1.persona.Persona;
import poo.proyecto1.persona.personalogin.LoginFrame;
import poo.proyecto1.vistas.ConsultarPerfilProfesor;
import poo.proyecto1.vistas.SelectorGrupoProfesor;
import poo.proyecto1.evaluacion.creacionEvaluacion.MenuEvaluacionesGUI;
import poo.proyecto1.evaluacion.modificarEvaluacion.ModificarEvaluacionDialog;
import poo.proyecto1.vistas.VentanaConsultarEvaluacion;
import poo.proyecto1.vistas.VentanaEliminarEvaluacion;
import poo.proyecto1.vistas.VentanaAsociarEvaluacion;
import poo.proyecto1.vistas.VentanaDesasociarEvaluacion;
import poo.proyecto1.vistas.SelectorGrupoProfesor;

public class MenuProfesorFrame extends JFrame {
    private Profesor profesor;
    
    public MenuProfesorFrame(Profesor usuario, List<Persona> listaUsuarios) {
        this.profesor = usuario;
        
        // --- Configuración de la Ventana ---
        setTitle("Menú Principal de Profesores");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Panel Principal ---
        JPanel panel = new JPanel(new BorderLayout());

        // --- Etiqueta de Bienvenida ---
        JLabel welcomeLabel = new JLabel("Bienvenido(a), " + usuario.getNombre(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // --- Creación de la Barra de Menú ---
        JMenuBar menuBar = new JMenuBar();

        // 1. Menú "Perfil profesor"
        JMenu perfilMenu = new JMenu("Perfil profesor");
        perfilMenu.setMnemonic(KeyEvent.VK_P);
        JMenuItem verPerfilItem = new JMenuItem("Consultar Mi Perfil");
        verPerfilItem.addActionListener(e -> {
            new ConsultarPerfilProfesor(MenuProfesorFrame.this, usuario).setVisible(true);
        });
        perfilMenu.add(verPerfilItem);
        menuBar.add(perfilMenu);

        // 2. Menú "Evaluaciones"
        JMenu evaluacionesMenu = new JMenu("Evaluaciones");
        evaluacionesMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem agregarEvaluacionItem = new JMenuItem("Crear Nueva Evaluación");
        JMenuItem consultarEvaluacionItem = new JMenuItem("Consultar Evaluaciones");
        JMenuItem modificarEvaluacionItem = new JMenuItem("Modificar Evaluación");
        JMenuItem eliminarEvaluacionItem = new JMenuItem("Eliminar Evaluación");

        agregarEvaluacionItem.addActionListener(e -> {
            JDialog evaluacionesDialog = new JDialog(MenuProfesorFrame.this, "Crear Evaluación", true);
            MenuEvaluacionesGUI menuEvaluaciones = new MenuEvaluacionesGUI();
            evaluacionesDialog.setContentPane(menuEvaluaciones.getContentPane());
            evaluacionesDialog.pack();
            evaluacionesDialog.setLocationRelativeTo(MenuProfesorFrame.this);
            evaluacionesDialog.setVisible(true);
        });
        
        consultarEvaluacionItem.addActionListener(e -> {
            VentanaConsultarEvaluacion dialog = new VentanaConsultarEvaluacion(MenuProfesorFrame.this);
            dialog.setVisible(true);
        });
        
        modificarEvaluacionItem.addActionListener(e -> {
            ModificarEvaluacionDialog dialog = new ModificarEvaluacionDialog(MenuProfesorFrame.this);
            dialog.setVisible(true);
        });
        
        eliminarEvaluacionItem.addActionListener(e -> {
            VentanaEliminarEvaluacion dialog = new VentanaEliminarEvaluacion(MenuProfesorFrame.this);
            dialog.setVisible(true);
        });

        evaluacionesMenu.add(agregarEvaluacionItem);
        evaluacionesMenu.add(consultarEvaluacionItem);
        evaluacionesMenu.add(modificarEvaluacionItem);
        evaluacionesMenu.add(eliminarEvaluacionItem);
        menuBar.add(evaluacionesMenu);

        // 3. Menú "Asociar Evaluaciones" 
        JMenu asociarMenu = new JMenu("Asociar Evaluaciones");
        asociarMenu.setMnemonic(KeyEvent.VK_A);
        
        JMenuItem asociarEvaluacionItem = new JMenuItem("Asociar Evaluación a Grupo");
        JMenuItem desasociarEvaluacionItem = new JMenuItem("Desasociar Evaluación de Grupo");
        
        asociarEvaluacionItem.addActionListener(e -> {
            new VentanaAsociarEvaluacion(MenuProfesorFrame.this, usuario).setVisible(true);
        });
        
        desasociarEvaluacionItem.addActionListener(e -> {
            new VentanaDesasociarEvaluacion(MenuProfesorFrame.this, usuario).setVisible(true);
        });
        
        asociarMenu.add(asociarEvaluacionItem);
        asociarMenu.add(desasociarEvaluacionItem);
        menuBar.add(asociarMenu);

        // 4. Menú "Previsualizar"
        JMenu previsualizarMenu = new JMenu("Previsualizar");
        previsualizarMenu.setMnemonic(KeyEvent.VK_V);
        JMenuItem previsualizarEvaluacionItem = new JMenuItem("Previsualizar Evaluación");
        previsualizarEvaluacionItem.addActionListener(e -> {
        new SelectorGrupoProfesor(MenuProfesorFrame.this, usuario).setVisible(true);
        });
        previsualizarMenu.add(previsualizarEvaluacionItem);
        menuBar.add(previsualizarMenu);

        // 5. Menú "Ver Evaluaciones Asignadas" (BOTÓN INDEPENDIENTE)
        JMenu verAsignadasMenu = new JMenu("Ver Evaluaciones Asignadas");
        verAsignadasMenu.setMnemonic(KeyEvent.VK_S);
        JMenuItem verAsignadasItem = new JMenuItem("Ver Evaluaciones Asignadas");
        verAsignadasItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Mostrando evaluaciones asignadas a grupos.", "Evaluaciones Asignadas", JOptionPane.INFORMATION_MESSAGE);
            // Aquí iría la lógica real
        });
        verAsignadasMenu.add(verAsignadasItem);
        menuBar.add(verAsignadasMenu);

        // 6. Menú "Ver Evaluaciones Realizadas" (BOTÓN INDEPENDIENTE)  
        JMenu verRealizadasMenu = new JMenu("Ver Evaluaciones Realizadas");
        verRealizadasMenu.setMnemonic(KeyEvent.VK_R);
        JMenuItem verRealizadasItem = new JMenuItem("Ver Evaluaciones Realizadas por Estudiantes");
        verRealizadasItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Mostrando evaluaciones realizadas por estudiantes.", "Evaluaciones Realizadas", JOptionPane.INFORMATION_MESSAGE);
            // Aquí iría la lógica real
        });
        verRealizadasMenu.add(verRealizadasItem);
        menuBar.add(verRealizadasMenu);

        // 7. Menú "Reportes" (BOTÓN INDEPENDIENTE)
        JMenu reportesMenu = new JMenu("Reportes");
        reportesMenu.setMnemonic(KeyEvent.VK_T);
        JMenuItem reportesItem = new JMenuItem("Generar Reportes");
        reportesItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Generando reportes de evaluaciones.", "Reportes", JOptionPane.INFORMATION_MESSAGE);
            // Aquí iría la lógica real de reportes
        });
        reportesMenu.add(reportesItem);
        menuBar.add(reportesMenu);

        // --- Establecer la barra de menú en la ventana ---
        this.setJMenuBar(menuBar);

        // --- Botón de Cerrar Sesión ---
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.addActionListener(e -> {
            dispose();
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