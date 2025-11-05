// poo/proyecto1/vistas/EditarProfesorDialog.java
package poo.proyecto1.vistas;

import poo.proyecto1.persona.profesor.Profesor;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaEditarProfesor extends JDialog {
    private JTextField nombreField, apellido1Field, apellido2Field, idField;
    private JTextField telefonoField, correoField, direccionField;
    private JPasswordField contrasenaField;
    private JTextArea titulosArea, certificacionesArea;
    private boolean confirmado = false;

    public VentanaEditarProfesor(Frame parent, Profesor profesor) {
        super(parent, "Editar Profesor", true);
        setLayout(new BorderLayout());

        // Rellenar campos con datos actuales
        nombreField = new JTextField(profesor.getNombre());
        apellido1Field = new JTextField(profesor.getApellido1());
        apellido2Field = new JTextField(profesor.getApellido2());
        idField = new JTextField(profesor.getIdentificacionPersonal());
        idField.setEditable(false); // ID no se puede cambiar
        telefonoField = new JTextField(profesor.getTelefono());
        correoField = new JTextField(profesor.getCorreoElectronico());
        direccionField = new JTextField(profesor.getDireccionFisica());
        contrasenaField = new JPasswordField(profesor.getContrasena());

        String titulosStr = profesor.getTitulosObtenidos().stream().collect(Collectors.joining(", "));
        titulosArea = new JTextArea(titulosStr);
        titulosArea.setLineWrap(true);
        titulosArea.setWrapStyleWord(true);

        String certifStr = profesor.getCertificacionesEstudios().stream().collect(Collectors.joining(", "));
        certificacionesArea = new JTextArea(certifStr);
        certificacionesArea.setLineWrap(true);
        certificacionesArea.setWrapStyleWord(true);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Apellido 1:"));
        formPanel.add(apellido1Field);
        formPanel.add(new JLabel("Apellido 2:"));
        formPanel.add(apellido2Field);
        formPanel.add(new JLabel("ID (no editable):"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Teléfono:"));
        formPanel.add(telefonoField);
        formPanel.add(new JLabel("Correo:"));
        formPanel.add(correoField);
        formPanel.add(new JLabel("Dirección:"));
        formPanel.add(direccionField);
        formPanel.add(new JLabel("Contraseña:"));
        formPanel.add(contrasenaField);
        formPanel.add(new JLabel("Títulos (separados por comas):"));
        formPanel.add(new JScrollPane(titulosArea));
        formPanel.add(new JLabel("Certificaciones (separadas por comas):"));
        formPanel.add(new JScrollPane(certificacionesArea));

        add(formPanel, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Guardar Cambios");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            confirmado = true;
            setVisible(false);
        });
        cancelarBtn.addActionListener(e -> setVisible(false));

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public String getNombre() { return nombreField.getText().trim(); }
    public String getApellido1() { return apellido1Field.getText().trim(); }
    public String getApellido2() { return apellido2Field.getText().trim(); }
    public String getIdentificacion() { return idField.getText().trim(); }
    public String getTelefono() { return telefonoField.getText().trim(); }
    public String getCorreo() { return correoField.getText().trim(); }
    public String getDireccion() { return direccionField.getText().trim(); }
    public String getContrasena() { return new String(contrasenaField.getPassword()); }

    public List<String> getTitulos() {
        String text = titulosArea.getText().trim();
        return text.isEmpty() ? java.util.Collections.emptyList() : Arrays.asList(text.split("\\s*,\\s*"));
    }

    public List<String> getCertificaciones() {
        String text = certificacionesArea.getText().trim();
        return text.isEmpty() ? java.util.Collections.emptyList() : Arrays.asList(text.split("\\s*,\\s*"));
    }
}