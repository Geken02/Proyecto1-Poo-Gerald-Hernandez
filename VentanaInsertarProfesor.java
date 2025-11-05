// poo/proyecto1/vistas/InsertarProfesorDialog.java
package poo.proyecto1.vistas;

import poo.proyecto1.persona.profesor.Profesor;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VentanaInsertarProfesor extends JDialog {
    private JTextField nombreField, apellido1Field, apellido2Field, idField;
    private JTextField telefonoField, correoField, direccionField;
    private JPasswordField contrasenaField;
    private JTextArea titulosArea, certificacionesArea;
    private boolean confirmado = false;

    public VentanaInsertarProfesor(Frame parent) {
        super(parent, "Registrar Nuevo Profesor", true);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Apellido 1:"));
        apellido1Field = new JTextField();
        formPanel.add(apellido1Field);

        formPanel.add(new JLabel("Apellido 2:"));
        apellido2Field = new JTextField();
        formPanel.add(apellido2Field);

        formPanel.add(new JLabel("ID (único):"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        formPanel.add(telefonoField);

        formPanel.add(new JLabel("Correo:"));
        correoField = new JTextField();
        formPanel.add(correoField);

        formPanel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        formPanel.add(direccionField);

        formPanel.add(new JLabel("Contraseña:"));
        contrasenaField = new JPasswordField();
        formPanel.add(contrasenaField);

        formPanel.add(new JLabel("Títulos (separados por comas):"));
        titulosArea = new JTextArea(2, 20);
        formPanel.add(new JScrollPane(titulosArea));

        formPanel.add(new JLabel("Certificaciones (separadas por comas):"));
        certificacionesArea = new JTextArea(2, 20);
        formPanel.add(new JScrollPane(certificacionesArea));

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Aceptar");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            if (validarCampos()) {
                confirmado = true;
                setVisible(false);
            }
        });

        cancelarBtn.addActionListener(e -> setVisible(false));

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private boolean validarCampos() {
        if (nombreField.getText().trim().isEmpty() ||
            idField.getText().trim().isEmpty() ||
            new String(contrasenaField.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre, ID y contraseña son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Profesor getProfesor() {
        if (!confirmado) return null;

        String nombre = nombreField.getText().trim();
        String apellido1 = apellido1Field.getText().trim();
        String apellido2 = apellido2Field.getText().trim();
        String id = idField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String correo = correoField.getText().trim();
        String direccion = direccionField.getText().trim();
        String contrasena = new String(contrasenaField.getPassword());

        List<String> titulos = new ArrayList<>();
        String titulosTexto = titulosArea.getText().trim();
        if (!titulosTexto.isEmpty()) {
            titulos.addAll(Arrays.asList(titulosTexto.split("\\s*,\\s*")));
        }

        List<String> certificaciones = new ArrayList<>();
        String certificacionesTexto = certificacionesArea.getText().trim();
        if (!certificacionesTexto.isEmpty()) {
            certificaciones.addAll(Arrays.asList(certificacionesTexto.split("\\s*,\\s*")));
        }

        return new Profesor(nombre, apellido1, apellido2, id, telefono, correo, direccion, contrasena, titulos, certificaciones);
    }
}