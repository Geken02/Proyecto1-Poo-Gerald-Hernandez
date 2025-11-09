package poo.proyecto1.vistas;

import poo.proyecto1.persona.estudiante.Estudiante;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class ConsultarPerfilEstudiante extends JDialog {
    public ConsultarPerfilEstudiante(Frame parent, Estudiante estudiante) {
        super(parent, "Mi Perfil - Vista de Consulta", true);
        setLayout(new BorderLayout());

        // Panel con información (solo lectura)
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Datos personales
        infoPanel.add(new JLabel("Nombre completo:"));
        infoPanel.add(new JLabel(estudiante.getNombre() + " " + 
                                estudiante.getApellido1() + " " + 
                                estudiante.getApellido2()));

        infoPanel.add(new JLabel("ID de identificación:"));
        infoPanel.add(new JLabel(estudiante.getIdentificacionPersonal()));

        infoPanel.add(new JLabel("Teléfono:"));
        infoPanel.add(new JLabel(estudiante.getTelefono()));

        infoPanel.add(new JLabel("Correo electrónico:"));
        infoPanel.add(new JLabel(estudiante.getCorreoElectronico()));

        infoPanel.add(new JLabel("Dirección física:"));
        infoPanel.add(new JLabel(estudiante.getDireccionFisica()));

        infoPanel.add(new JLabel("Organización laboral:"));
        infoPanel.add(new JLabel(estudiante.getOrgLabora()));

        infoPanel.add(new JLabel("Temas de interés:"));
        String temas = estudiante.getTemasInteres().stream()
            .collect(Collectors.joining(", "));
        infoPanel.add(new JLabel(temas.isEmpty() ? "Ninguno" : temas));

        add(new JScrollPane(infoPanel), BorderLayout.CENTER);

        // Botón de cerrar
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.addActionListener(e -> dispose());
        buttonPanel.add(cerrarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(parent);
    }
}