// poo/proyecto1/vistas/VentanaDesasociarEvaluacion.java

package poo.proyecto1.vistas;

import poo.proyecto1.curso.Curso;
import poo.proyecto1.grupo.Grupo;
import poo.proyecto1.persona.profesor.Profesor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaDesasociarEvaluacion extends JDialog {
    private Profesor profesor;
    private boolean confirmado = false;

    public VentanaDesasociarEvaluacion(Frame parent, Profesor profesor) {
        super(parent, "Desasociar Evaluación", true);
        this.profesor = profesor;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Obtener cursos del profesor
        List<Curso> cursos = profesor.obtenerCursosDesdeJSON();

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

        // Actualizar grupos cuando cambia el curso
        cursoCombo.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) cursoCombo.getSelectedItem();
            actualizarGruposCombo(grupoCombo, cursoSeleccionado);
        });

        // ComboBox de evaluaciones asociadas al grupo seleccionado
        formPanel.add(new JLabel("Evaluación (ID):"));
        JComboBox<Integer> evaluacionCombo = new JComboBox<>();
        grupoCombo.addActionListener(e -> {
            Grupo grupoSeleccionado = (Grupo) grupoCombo.getSelectedItem();
            if (grupoSeleccionado != null) {
                actualizarEvaluacionesCombo(evaluacionCombo, grupoSeleccionado);
            } else {
                evaluacionCombo.removeAllItems();
            }
        });
        // Inicializar el combo de evaluaciones si hay un grupo seleccionado inicialmente
        if (!grupoCombo.getModel().getSelectedItem().equals(null)) {
            Grupo grupoInicial = (Grupo) grupoCombo.getModel().getSelectedItem();
            actualizarEvaluacionesCombo(evaluacionCombo, grupoInicial);
        }
        formPanel.add(evaluacionCombo);

        add(formPanel, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton desasociarBtn = new JButton("Desasociar");
        JButton cancelarBtn = new JButton("Cancelar");

        desasociarBtn.addActionListener(e -> {
            try {
                Curso curso = (Curso) cursoCombo.getSelectedItem();
                Grupo grupo = (Grupo) grupoCombo.getSelectedItem();
                Integer idEvaluacion = (Integer) evaluacionCombo.getSelectedItem();

                if (curso == null || grupo == null || idEvaluacion == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                profesor.desasociarEvaluacion(curso.getIdCurso(), grupo.getIdGrupo(), idEvaluacion);
                confirmado = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al desasociar la evaluación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarBtn.addActionListener(e -> dispose());

        buttonPanel.add(desasociarBtn);
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

    private void actualizarEvaluacionesCombo(JComboBox<Integer> evaluacionCombo, Grupo grupo) {
        evaluacionCombo.removeAllItems();
        java.util.Map<Integer, java.time.LocalDateTime> evaluaciones = grupo.getEvaluacionesAsociadas();
        if (evaluaciones != null && !evaluaciones.isEmpty()) {
            for (Integer idEvaluacion : evaluaciones.keySet()) {
                evaluacionCombo.addItem(idEvaluacion);
            }
        } else {
            // Opción para cuando no hay evaluaciones asociadas
            evaluacionCombo.setEnabled(false);
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}