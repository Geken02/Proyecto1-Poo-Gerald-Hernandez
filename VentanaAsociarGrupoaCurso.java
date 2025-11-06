package poo.proyecto1.vistas;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public class VentanaAsociarGrupoaCurso extends JDialog {

    private JTextField cursoIdField, profesorIdField;
    private JTextField fechaInicioField, fechaFinField;
    private boolean confirmado = false;
    private LocalDate fechaInicio, fechaFin;

    public VentanaAsociarGrupoaCurso(Frame parent) {
        super(parent, "Crear Nuevo Grupo", true);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID del Curso:"));
        cursoIdField = new JTextField();
        formPanel.add(cursoIdField);

        formPanel.add(new JLabel("ID del Profesor:"));
        profesorIdField = new JTextField();
        formPanel.add(profesorIdField);

        formPanel.add(new JLabel("Fecha de Inicio (YYYY-MM-DD):"));
        fechaInicioField = new JTextField("2025-11-06");
        formPanel.add(fechaInicioField);

        formPanel.add(new JLabel("Fecha de Fin (YYYY-MM-DD):"));
        fechaFinField = new JTextField("2026-02-06");
        formPanel.add(fechaFinField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Crear Grupo");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            if (validarYProcesar()) {
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

        private boolean validarYProcesar() {
        String idCurso = cursoIdField.getText().trim();
        String idProfesor = profesorIdField.getText().trim(); // ← puede estar vacío
        String fechaInicioStr = fechaInicioField.getText().trim();
        String fechaFinStr = fechaFinField.getText().trim();

        if (idCurso.isEmpty()) { // ← Solo curso es obligatorio
            JOptionPane.showMessageDialog(this, "ID de curso es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            fechaInicio = LocalDate.parse(fechaInicioStr);
            fechaFin = LocalDate.parse(fechaFinStr);
            if (!fechaFin.isAfter(fechaInicio)) {
                JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean isConfirmado() { return confirmado; }
    public String getCursoId() { return cursoIdField.getText().trim(); }
    public String getProfesorId() { return profesorIdField.getText().trim(); }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
}