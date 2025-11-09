// poo/proyecto1/vistas/VentanaEditarProfesor.java

package poo.proyecto1.vistas;

import poo.proyecto1.persona.profesor.Profesor;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VentanaEditarProfesor extends JDialog {
    private JTextField nombreField, apellido1Field, apellido2Field, idField;
    private JTextField telefonoField, correoField, direccionField;
    private JPasswordField contrasenaField;
    private JTextArea titulosArea, certificacionesArea;
    private boolean confirmado = false;

    // Patrón para correo: parte1@parte2 (mínimo 3 caracteres cada parte, sin espacios)
    private static final Pattern CORREO_PATTERN = Pattern.compile("^[^\\s@]{3,}@[^\\s@]{3,}$");

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

        formPanel.add(new JLabel("Nombre (2-20 caracteres):"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Apellido 1 (2-20 caracteres):"));
        formPanel.add(apellido1Field);
        formPanel.add(new JLabel("Apellido 2 (2-20 caracteres):"));
        formPanel.add(apellido2Field);
        formPanel.add(new JLabel("ID (no editable, ≥9 caracteres):"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Teléfono (≥8 caracteres):"));
        formPanel.add(telefonoField);
        formPanel.add(new JLabel("Correo (parte1@parte2):"));
        formPanel.add(correoField);
        formPanel.add(new JLabel("Dirección (5-60 caracteres):"));
        formPanel.add(direccionField);
        formPanel.add(new JLabel("Contraseña (≥8 caracteres, con mayúsculas, minúsculas y números):"));
        formPanel.add(contrasenaField);
        formPanel.add(new JLabel("Títulos obtenidos (5-40 caracteres cada uno, separados por comas):"));
        formPanel.add(new JScrollPane(titulosArea));
        formPanel.add(new JLabel("Certificaciones de estudios (5-40 caracteres cada una, separadas por comas):"));
        formPanel.add(new JScrollPane(certificacionesArea));

        add(formPanel, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Guardar Cambios");
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

        // Validar títulos
        String titulosTexto = titulosArea.getText().trim();
        if (!titulosTexto.isEmpty()) {
            String[] titulosArray = titulosTexto.split("\\s*,\\s*");
            for (String titulo : titulosArray) {
                if (titulo.isEmpty()) continue;
                if (titulo.length() < 5) {
                    return "Cada título debe tener al menos 5 caracteres. Título inválido: '" + titulo + "'";
                }
                if (titulo.length() > 40) {
                    return "Cada título no puede exceder 40 caracteres. Título inválido: '" + titulo + "'";
                }
            }
        }

        // Validar certificaciones
        String certificacionesTexto = certificacionesArea.getText().trim();
        if (!certificacionesTexto.isEmpty()) {
            String[] certificacionesArray = certificacionesTexto.split("\\s*,\\s*");
            for (String certificacion : certificacionesArray) {
                if (certificacion.isEmpty()) continue;
                if (certificacion.length() < 5) {
                    return "Cada certificación debe tener al menos 5 caracteres. Certificación inválida: '" + certificacion + "'";
                }
                if (certificacion.length() > 40) {
                    return "Cada certificación no puede exceder 40 caracteres. Certificación inválida: '" + certificacion + "'";
                }
            }
        }

        return null; // Todos los campos son válidos
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