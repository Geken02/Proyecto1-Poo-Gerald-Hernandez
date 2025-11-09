// poo/proyecto1/vistas/ConsultarPerfilProfesor.java
package poo.proyecto1.vistas;

import poo.proyecto1.persona.profesor.Profesor;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class ConsultarPerfilProfesor extends JDialog {
    public ConsultarPerfilProfesor(Frame parent, Profesor profesor) {
        super(parent, "Mi Perfil - Vista de Consulta", true);
        setLayout(new BorderLayout());

        // Panel con información (solo lectura)
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Datos personales
        infoPanel.add(new JLabel("Nombre completo:"));
        infoPanel.add(new JLabel(profesor.getNombre() + " " + 
                                profesor.getApellido1() + " " + 
                                profesor.getApellido2()));

        infoPanel.add(new JLabel("ID de identificación:"));
        infoPanel.add(new JLabel(profesor.getIdentificacionPersonal()));

        infoPanel.add(new JLabel("Teléfono:"));
        infoPanel.add(new JLabel(profesor.getTelefono()));

        infoPanel.add(new JLabel("Correo electrónico:"));
        infoPanel.add(new JLabel(profesor.getCorreoElectronico()));

        infoPanel.add(new JLabel("Dirección física:"));
        infoPanel.add(new JLabel(profesor.getDireccionFisica()));

        infoPanel.add(new JLabel("Títulos obtenidos:"));
        String titulos = profesor.getTitulosObtenidos().stream()
            .collect(Collectors.joining(", "));
        infoPanel.add(new JLabel(titulos.isEmpty() ? "Ninguno" : titulos));

        infoPanel.add(new JLabel("Certificaciones:"));
        String certificaciones = profesor.getCertificacionesEstudios().stream()
            .collect(Collectors.joining(", "));
        infoPanel.add(new JLabel(certificaciones.isEmpty() ? "Ninguna" : certificaciones));

        add(new JScrollPane(infoPanel), BorderLayout.CENTER);

        // Botón de cerrar
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.addActionListener(e -> dispose());
        buttonPanel.add(cerrarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(parent);
    }
}