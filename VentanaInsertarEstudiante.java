// poo/proyecto1/vistas/VentanaInsertarEstudiante.java

package poo.proyecto1.vistas;

import poo.proyecto1.persona.estudiante.Estudiante;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VentanaInsertarEstudiante extends JDialog {
    private JTextField nombreField, apellido1Field, apellido2Field, idField;
    private JTextField telefonoField, correoField, direccionField;
    private JPasswordField contrasenaField;
    private JTextField orgLaboralField;
    private JTextArea temasArea;
    private boolean confirmado = false;

    // Patrón para correo: parte1@parte2 (mínimo 3 caracteres cada parte, sin espacios)
    private static final Pattern CORREO_PATTERN = Pattern.compile("^[^\\s@]{3,}@[^\\s@]{3,}$");

    public VentanaInsertarEstudiante(Frame parent) {
        super(parent, "Registrar Nuevo Estudiante", true);
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

        formPanel.add(new JLabel("ID (único, ≥9 caracteres):"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Teléfono (≥8 caracteres):"));
        telefonoField = new JTextField();
        formPanel.add(telefonoField);

        formPanel.add(new JLabel("Correo (parte1@parte2):"));
        correoField = new JTextField();
        formPanel.add(correoField);

        formPanel.add(new JLabel("Dirección (5-60 caracteres):"));
        direccionField = new JTextField();
        formPanel.add(direccionField);

        formPanel.add(new JLabel("Contraseña (≥8 caracteres, con mayúsculas, minúsculas y números):"));
        contrasenaField = new JPasswordField();
        formPanel.add(contrasenaField);

        formPanel.add(new JLabel("Organización Laboral (≤40 caracteres):"));
        orgLaboralField = new JTextField();
        formPanel.add(orgLaboralField);

        formPanel.add(new JLabel("Temas de interés (5-30 caracteres cada uno, separados por comas):"));
        temasArea = new JTextArea(3, 20);
        temasArea.setLineWrap(true);
        temasArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(temasArea));

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Aceptar");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            String error = validarCampos();
            if (error == null) {
                confirmado = true;
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarBtn.addActionListener(e -> setVisible(false));

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private String validarCampos() {
        // Validar nombre
        String nombre = nombreField.getText().trim();
        if (nombre.isEmpty()) return "Nombre no puede estar vacío.";
        if (nombre.length() < 2) return "Nombre debe tener al menos 2 caracteres.";
        if (nombre.length() > 20) return "Nombre no puede exceder 20 caracteres.";

        // Validar apellido 1
        String apellido1 = apellido1Field.getText().trim();
        if (apellido1.isEmpty()) return "Apellido 1 no puede estar vacío.";
        if (apellido1.length() < 2) return "Apellido 1 debe tener al menos 2 caracteres.";
        if (apellido1.length() > 20) return "Apellido 1 no puede exceder 20 caracteres.";

        // Validar apellido 2
        String apellido2 = apellido2Field.getText().trim();
        if (apellido2.isEmpty()) return "Apellido 2 no puede estar vacío.";
        if (apellido2.length() < 2) return "Apellido 2 debe tener al menos 2 caracteres.";
        if (apellido2.length() > 20) return "Apellido 2 no puede exceder 20 caracteres.";

        // Validar ID
        String id = idField.getText().trim();
        if (id.isEmpty()) return "ID no puede estar vacío.";
        if (id.length() < 9) return "ID debe tener al menos 9 caracteres.";

        // Validar teléfono
        String telefono = telefonoField.getText().trim();
        if (telefono.isEmpty()) return "Teléfono no puede estar vacío.";
        if (telefono.length() < 8) return "Teléfono debe tener al menos 8 caracteres.";

        // Validar correo
        String correo = correoField.getText().trim();
        if (correo.isEmpty()) return "Correo no puede estar vacío.";
        if (!CORREO_PATTERN.matcher(correo).matches()) {
            return "Correo debe tener el formato parte1@parte2, donde ambas partes tienen al menos 3 caracteres y no contienen espacios.";
        }

        // Validar dirección
        String direccion = direccionField.getText().trim();
        if (direccion.isEmpty()) return "Dirección no puede estar vacía.";
        if (direccion.length() < 5) return "Dirección debe tener al menos 5 caracteres.";
        if (direccion.length() > 60) return "Dirección no puede exceder 60 caracteres.";

        // Validar contraseña
        String contrasena = new String(contrasenaField.getPassword());
        if (contrasena.isEmpty()) return "Contraseña no puede estar vacía.";
        if (contrasena.length() < 8) return "Contraseña debe tener al menos 8 caracteres.";
        if (!contrasena.matches(".*[a-z].*")) return "Contraseña debe contener al menos una letra minúscula.";
        if (!contrasena.matches(".*[A-Z].*")) return "Contraseña debe contener al menos una letra mayúscula.";
        if (!contrasena.matches(".*\\d.*")) return "Contraseña debe contener al menos un número.";

        // Validar organización laboral
        String orgLaboral = orgLaboralField.getText().trim();
        if (orgLaboral.length() > 40) return "Organización laboral no puede exceder 40 caracteres.";

        // Validar temas de interés
        String temasTexto = temasArea.getText().trim();
        if (!temasTexto.isEmpty()) {
            String[] temasArray = temasTexto.split("\\s*,\\s*");
            for (String tema : temasArray) {
                if (tema.isEmpty()) continue; // Saltar temas vacíos
                if (tema.length() < 5) {
                    return "Cada tema de interés debe tener al menos 5 caracteres. Tema inválido: '" + tema + "'";
                }
                if (tema.length() > 30) {
                    return "Cada tema de interés no puede exceder 30 caracteres. Tema inválido: '" + tema + "'";
                }
            }
        }

        return null; // Todos los campos son válidos
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Estudiante getEstudiante() {
        if (!confirmado) return null;

        String nombre = nombreField.getText().trim();
        String apellido1 = apellido1Field.getText().trim();
        String apellido2 = apellido2Field.getText().trim();
        String id = idField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String correo = correoField.getText().trim().toLowerCase(); // Normalizar
        String direccion = direccionField.getText().trim();
        String contrasena = new String(contrasenaField.getPassword());
        String orgLaboral = orgLaboralField.getText().trim();

        List<String> temas = new ArrayList<>();
        String temasTexto = temasArea.getText().trim();
        if (!temasTexto.isEmpty()) {
            String[] temasArray = temasTexto.split("\\s*,\\s*");
            for (String tema : temasArray) {
                if (!tema.isEmpty()) {
                    temas.add(tema);
                }
            }
        }

        return new Estudiante(nombre, apellido1, apellido2, id, telefono, correo, direccion, contrasena, orgLaboral, temas);
    }
}