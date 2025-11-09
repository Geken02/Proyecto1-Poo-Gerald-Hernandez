// poo/proyecto1/vistas/VentanaAsociarEvaluacion.java

package poo.proyecto1.vistas;

import poo.proyecto1.evaluacion.Evaluacion;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.grupo.Grupo;
import poo.proyecto1.util.RegistroEvaluaciones;
import poo.proyecto1.persona.profesor.Profesor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class VentanaAsociarEvaluacion extends JDialog {
    private Profesor profesor;
    private boolean confirmado = false;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public VentanaAsociarEvaluacion(Frame parent, Profesor profesor) {
        super(parent, "Asociar Evaluación a Grupo", true);
        this.profesor = profesor;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Cargar datos
        RegistroEvaluaciones registroEval = new RegistroEvaluaciones();
        List<Evaluacion> evaluaciones = registroEval.getListaEvaluaciones();
        
        List<Curso> cursos = profesor.obtenerCursosDesdeJSON();

        // ComboBox de evaluaciones - solo muestra ID
        formPanel.add(new JLabel("Evaluación (ID):"));
        JComboBox<Evaluacion> evaluacionCombo = new JComboBox<>();
        for (Evaluacion eval : evaluaciones) {
            evaluacionCombo.addItem(eval);
        }
        // Personalizar renderer para mostrar solo el ID
        evaluacionCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Evaluacion) {
                    Evaluacion eval = (Evaluacion) value;
                    setText(String.valueOf(eval.getIdEvaluacion()));
                }
                return this;
            }
        });
        formPanel.add(evaluacionCombo);

        // ComboBox de cursos - solo muestra ID del curso
        formPanel.add(new JLabel("Curso (ID):"));
        JComboBox<Curso> cursoCombo = new JComboBox<>();
        for (Curso curso : cursos) {
            cursoCombo.addItem(curso);
        }
        cursoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Curso) {
                    Curso curso = (Curso) value;
                    setText(curso.getIdCurso());
                }
                return this;
            }
        });
        formPanel.add(cursoCombo);

        // ComboBox de grupos - solo muestra ID del grupo
        formPanel.add(new JLabel("Grupo (ID):"));
        JComboBox<Grupo> grupoCombo = new JComboBox<>();
        actualizarGruposCombo(grupoCombo, cursos.isEmpty() ? null : (Curso) cursoCombo.getSelectedItem());
        grupoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Grupo) {
                    Grupo grupo = (Grupo) value;
                    setText(String.valueOf(grupo.getIdGrupo()));
                }
                return this;
            }
        });
        formPanel.add(grupoCombo);

        cursoCombo.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) cursoCombo.getSelectedItem();
            actualizarGruposCombo(grupoCombo, cursoSeleccionado);
        });

        // Campo de hora de inicio
        formPanel.add(new JLabel("Hora de inicio (YYYY-MM-DD HH:mm):"));
        JTextField horaInicioField = new JTextField("2025-12-01 09:00");
        formPanel.add(horaInicioField);

        add(new JScrollPane(formPanel), BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton asociarBtn = new JButton("Asociar");
        JButton cancelarBtn = new JButton("Cancelar");

        asociarBtn.addActionListener(e -> {
            try {
                Evaluacion eval = (Evaluacion) evaluacionCombo.getSelectedItem();
                Curso curso = (Curso) cursoCombo.getSelectedItem();
                Grupo grupo = (Grupo) grupoCombo.getSelectedItem();
                LocalDateTime horaInicio = LocalDateTime.parse(horaInicioField.getText().trim(), INPUT_FORMATTER);

                if (eval == null || curso == null || grupo == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                profesor.asociarEvaluacion(curso.getIdCurso(), grupo.getIdGrupo(), 
                                         eval.getIdEvaluacion(), horaInicio);
                confirmado = true;
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de hora: YYYY-MM-DD HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarBtn.addActionListener(e -> dispose());

        buttonPanel.add(asociarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private void actualizarGruposCombo(JComboBox<Grupo> grupoCombo, Curso curso) {
        grupoCombo.removeAllItems();
        if (curso != null) {
            for (Grupo grupo : curso.getGrupos()) {
                grupoCombo.addItem(grupo);
            }
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}