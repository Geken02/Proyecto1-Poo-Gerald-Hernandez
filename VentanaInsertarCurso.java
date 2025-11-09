// poo/proyecto1/vistas/VentanaInsertarCurso.java

package poo.proyecto1.vistas;

import poo.proyecto1.curso.Curso;
import javax.swing.*;
import java.awt.*;

public class VentanaInsertarCurso extends JDialog {
    private JTextField idField, nombreField, descripcionField;
    private JTextField horasDiaField, minEstField, maxEstField, califMinField;
    private JComboBox<String> modalidadCombo, tipoCursoCombo;
    private boolean confirmado = false;

    // Valores exactos que usa la clase Curso (deben coincidir)
    private static final String[] MODALIDADES_VALIDAS = {
        "Presencial", 
        "Virtual sincrónico", 
        "Virtual asincrónico", 
        "Virtual híbrido", 
        "Semipresencial"
    };
    
    private static final String[] TIPOS_CURSO_VALIDOS = {
        "Teórico", 
        "Práctico", 
        "Taller", 
        "Seminario"
    };

    public VentanaInsertarCurso(Frame parent) {
        super(parent, "Registrar Nuevo Curso", true);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID del Curso (único):"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Nombre del Curso (5-40 caracteres):"));
        nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Descripción (5-400 caracteres):"));
        descripcionField = new JTextField();
        formPanel.add(descripcionField);

        formPanel.add(new JLabel("Horas por día (1-8):"));
        horasDiaField = new JTextField();
        formPanel.add(horasDiaField);

        formPanel.add(new JLabel("Modalidad:"));
        modalidadCombo = new JComboBox<>(MODALIDADES_VALIDAS);
        formPanel.add(modalidadCombo);

        formPanel.add(new JLabel("Mín. estudiantes (1-5):"));
        minEstField = new JTextField();
        formPanel.add(minEstField);

        formPanel.add(new JLabel("Máx. estudiantes (mín ≤ máx ≤ 20):"));
        maxEstField = new JTextField();
        formPanel.add(maxEstField);

        formPanel.add(new JLabel("Calificación mín. para aprobar (0-100):"));
        califMinField = new JTextField();
        formPanel.add(califMinField);

        formPanel.add(new JLabel("Tipo de curso:"));
        tipoCursoCombo = new JComboBox<>(TIPOS_CURSO_VALIDOS);
        formPanel.add(tipoCursoCombo);

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
        // Validar ID
        String id = idField.getText().trim();
        if (id.isEmpty()) return "ID del curso no puede estar vacío.";

        // Validar nombre
        String nombre = nombreField.getText().trim();
        if (nombre.isEmpty()) return "Nombre del curso no puede estar vacío.";
        if (nombre.length() < 5) return "Nombre del curso debe tener al menos 5 caracteres.";
        if (nombre.length() > 40) return "Nombre del curso no puede exceder 40 caracteres.";

        // Validar descripción
        String descripcion = descripcionField.getText().trim();
        if (descripcion.isEmpty()) return "Descripción del curso no puede estar vacía.";
        if (descripcion.length() < 5) return "Descripción del curso debe tener al menos 5 caracteres.";
        if (descripcion.length() > 400) return "Descripción del curso no puede exceder 400 caracteres.";

        // Validar horas por día
        String horasDiaStr = horasDiaField.getText().trim();
        if (horasDiaStr.isEmpty()) return "Horas por día no puede estar vacío.";
        try {
            int horasDia = Integer.parseInt(horasDiaStr);
            if (horasDia < 1 || horasDia > 8) {
                return "Horas por día debe estar entre 1 y 8.";
            }
        } catch (NumberFormatException e) {
            return "Horas por día debe ser un número entero.";
        }

        // Validar cantidad mínima de estudiantes
        String minEstStr = minEstField.getText().trim();
        if (minEstStr.isEmpty()) return "Cantidad mínima de estudiantes no puede estar vacía.";
        int minEst;
        try {
            minEst = Integer.parseInt(minEstStr);
            if (minEst < 1 || minEst > 5) {
                return "Cantidad mínima de estudiantes debe estar entre 1 y 5.";
            }
        } catch (NumberFormatException e) {
            return "Cantidad mínima de estudiantes debe ser un número entero.";
        }

        // Validar cantidad máxima de estudiantes
        String maxEstStr = maxEstField.getText().trim();
        if (maxEstStr.isEmpty()) return "Cantidad máxima de estudiantes no puede estar vacía.";
        try {
            int maxEst = Integer.parseInt(maxEstStr);
            if (maxEst < minEst) {
                return "Cantidad máxima de estudiantes no puede ser menor que la mínima (" + minEst + ").";
            }
            if (maxEst > 20) {
                return "Cantidad máxima de estudiantes no puede exceder 20.";
            }
        } catch (NumberFormatException e) {
            return "Cantidad máxima de estudiantes debe ser un número entero.";
        }

        // Validar calificación mínima
        String califMinStr = califMinField.getText().trim();
        if (califMinStr.isEmpty()) return "Calificación mínima no puede estar vacía.";
        try {
            int califMin = Integer.parseInt(califMinStr);
            if (califMin < 0 || califMin > 100) {
                return "Calificación mínima debe estar entre 0 y 100.";
            }
        } catch (NumberFormatException e) {
            return "Calificación mínima debe ser un número entero.";
        }

        // Validar modalidad y tipo (deberían estar en los combos, pero verificamos)
        String modalidad = (String) modalidadCombo.getSelectedItem();
        if (modalidad == null || modalidad.trim().isEmpty()) {
            return "Debe seleccionar una modalidad válida.";
        }

        String tipoCurso = (String) tipoCursoCombo.getSelectedItem();
        if (tipoCurso == null || tipoCurso.trim().isEmpty()) {
            return "Debe seleccionar un tipo de curso válido.";
        }

        return null; // Todos los campos son válidos
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Curso getCurso() {
        if (!confirmado) return null;

        String id = idField.getText().trim();
        String nombre = nombreField.getText().trim();
        String descripcion = descripcionField.getText().trim();
        int horasDia = Integer.parseInt(horasDiaField.getText().trim());
        String modalidad = (String) modalidadCombo.getSelectedItem();
        int minEst = Integer.parseInt(minEstField.getText().trim());
        int maxEst = Integer.parseInt(maxEstField.getText().trim());
        int califMin = Integer.parseInt(califMinField.getText().trim());
        String tipo = (String) tipoCursoCombo.getSelectedItem();

        // Nota: el parámetro cantHorasSemana se pasa como 0 (no se usa)
        return new Curso(id, nombre, descripcion, horasDia, modalidad, minEst, maxEst, califMin, tipo);
    }
}