// poo/proyecto1/vistas/EditarEstudianteDialog.java
package poo.proyecto1.vistas;

import poo.proyecto1.persona.estudiante.Estudiante;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaEditarEstudiante extends JDialog {
    private JTextField nombreField, apellido1Field, apellido2Field, idField;
    private JTextField telefonoField, correoField, direccionField;
    private JPasswordField contrasenaField;
    private JTextField orgLaboralField;
    private JTextArea temasArea;
    private boolean confirmado = false;

    public VentanaEditarEstudiante(Frame parent, Estudiante estudiante) {
        super(parent, "Editar Estudiante", true);
        setLayout(new BorderLayout());

        // Rellenar con datos actuales
        nombreField = new JTextField(estudiante.getNombre());
        apellido1Field = new JTextField(estudiante.getApellido1());
        apellido2Field = new JTextField(estudiante.getApellido2());
        idField = new JTextField(estudiante.getIdentificacionPersonal());
        idField.setEditable(false); // ID no editable
        telefonoField = new JTextField(estudiante.getTelefono());
        correoField = new JTextField(estudiante.getCorreoElectronico());
        direccionField = new JTextField(estudiante.getDireccionFisica());
        contrasenaField = new JPasswordField(estudiante.getContrasena());
        orgLaboralField = new JTextField(estudiante.getOrgLabora());

        String temasStr = estudiante.getTemasInteres().stream().collect(Collectors.joining(", "));
        temasArea = new JTextArea(temasStr);
        temasArea.setLineWrap(true);
        temasArea.setWrapStyleWord(true);

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
        formPanel.add(new JLabel("Organización Laboral:"));
        formPanel.add(orgLaboralField);
        formPanel.add(new JLabel("Temas de interés (separados por comas):"));
        formPanel.add(new JScrollPane(temasArea));

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

    public boolean isConfirmado() { return confirmado; }

    public String getNombre() { return nombreField.getText().trim(); }
    public String getApellido1() { return apellido1Field.getText().trim(); }
    public String getApellido2() { return apellido2Field.getText().trim(); }
    public String getIdentificacion() { return idField.getText().trim(); }
    public String getTelefono() { return telefonoField.getText().trim(); }
    public String getCorreo() { return correoField.getText().trim(); }
    public String getDireccion() { return direccionField.getText().trim(); }
    public String getContrasena() { return new String(contrasenaField.getPassword()); }
    public String getOrgLaboral() { return orgLaboralField.getText().trim(); }

    public List<String> getTemasInteres() {
        String text = temasArea.getText().trim();
        return text.isEmpty() ? java.util.Collections.emptyList() : Arrays.asList(text.split("\\s*,\\s*"));
    }
}