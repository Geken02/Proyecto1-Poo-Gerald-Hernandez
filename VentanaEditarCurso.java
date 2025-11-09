// poo/proyecto1/vistas/VentanaEditarCurso.java

package poo.proyecto1.vistas;

import poo.proyecto1.curso.Curso;
import javax.swing.*;
import java.awt.*;

public class VentanaEditarCurso extends JDialog {
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

    public VentanaEditarCurso(Frame parent, Curso curso) {
        super(parent, "Editar Curso", true);
        setLayout(new BorderLayout());

        // Precargar con datos actuales
        idField = new JTextField(curso.getIdCurso());
        idField.setEditable(false);
        nombreField = new JTextField(curso.getNombreCurso());
        descripcionField = new JTextField(curso.getDescripcionCurso());
        horasDiaField = new JTextField(String.valueOf(curso.getCantHorasDia()));
        minEstField = new JTextField(String.valueOf(curso.getCantMinEstudiantes()));
        maxEstField = new JTextField(String.valueOf(curso.getCantMaxEstudiantes()));
        califMinField = new JTextField(String.valueOf(curso.getCalificacionMinAprobar()));

        // Modalidad: encontrar el valor actual en el combo
        modalidadCombo = new JComboBox<>(MODALIDADES_VALIDAS);
        modalidadCombo.setSelectedItem(curso.getModalidad());

        // Tipo de curso: encontrar el valor actual en el combo
        tipoCursoCombo = new JComboBox<>(TIPOS_CURSO_VALIDOS);
        tipoCursoCombo.setSelectedItem(curso.getTipoCurso());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID (no editable):"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Nombre del Curso (5-40 caracteres):"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Descripción (5-400 caracteres):"));
        formPanel.add(descripcionField);
        formPanel.add(new JLabel("Horas por día (1-8):"));
        formPanel.add(horasDiaField);
        formPanel.add(new JLabel("Modalidad:"));
        formPanel.add(modalidadCombo);
        formPanel.add(new JLabel("Mín. estudiantes (1-5):"));
        formPanel.add(minEstField);
        formPanel.add(new JLabel("Máx. estudiantes (mín ≤ máx ≤ 20):"));
        formPanel.add(maxEstField);
        formPanel.add(new JLabel("Calif. mín. aprobatoria (0-100):"));
        formPanel.add(califMinField);
        formPanel.add(new JLabel("Tipo de curso:"));
        formPanel.add(tipoCursoCombo);

        add(formPanel, BorderLayout.CENTER);

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
        // Validar ID (aunque sea no editable, lo verificamos)
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

    public boolean isConfirmado() { return confirmado; }
    public String getIdCurso() { return idField.getText().trim(); }
    public String getNombreCurso() { return nombreField.getText().trim(); }
    public String getDescripcionCurso() { return descripcionField.getText().trim(); }
    public int getCantHorasDia() { return Integer.parseInt(horasDiaField.getText().trim()); }
    public String getModalidad() { return (String) modalidadCombo.getSelectedItem(); }
    public int getCantMinEstudiantes() { return Integer.parseInt(minEstField.getText().trim()); }
    public int getCantMaxEstudiantes() { return Integer.parseInt(maxEstField.getText().trim()); }
    public int getCalificacionMinAprobar() { return Integer.parseInt(califMinField.getText().trim()); }
    public String getTipoCurso() { return (String) tipoCursoCombo.getSelectedItem(); }
}