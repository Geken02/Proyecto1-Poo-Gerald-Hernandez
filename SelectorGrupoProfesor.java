// poo/proyecto1/vistas/SelectorGrupoProfesor.java
package poo.proyecto1.vistas;

import poo.proyecto1.persona.profesor.Profesor;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.grupo.Grupo;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import poo.proyecto1.vistas.SelectorEvaluacionesporGrupo;

public class SelectorGrupoProfesor extends JDialog {
    private Profesor profesor;

    public SelectorGrupoProfesor(Frame parent, Profesor profesor) {
        super(parent, "Seleccionar Grupo para Realizar Evaluación", true);
        this.profesor = profesor;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(450, 200);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ComboBox de IDs de cursos
        formPanel.add(new JLabel("Curso (ID):"));
        JComboBox<String> cursoCombo = new JComboBox<>();
        cargarCursosProfesor(cursoCombo);
        formPanel.add(cursoCombo);

        // ComboBox de IDs de grupos
        formPanel.add(new JLabel("Grupo (ID):"));
        JComboBox<Integer> grupoCombo = new JComboBox<>();
        cursoCombo.addActionListener(e -> {
            String idCurso = (String) cursoCombo.getSelectedItem();
            cargarGruposCurso(grupoCombo, idCurso);
        });
        // Cargar grupos del primer curso si existe
        if (cursoCombo.getItemCount() > 0) {
            String primerIdCurso = (String) cursoCombo.getItemAt(0);
            cargarGruposCurso(grupoCombo, primerIdCurso);
        }
        formPanel.add(grupoCombo);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton seleccionarBtn = new JButton("Seleccionar");
        JButton cancelarBtn = new JButton("Cancelar");

        seleccionarBtn.addActionListener(e -> {
            String idCurso = (String) cursoCombo.getSelectedItem();
            Integer idGrupo = (Integer) grupoCombo.getSelectedItem();
            
            if (idCurso == null || idGrupo == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un curso y grupo válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            dispose();
            new SelectorEvaluacionesporGrupo(profesor, idCurso, idGrupo).setVisible(true);
        });

        cancelarBtn.addActionListener(e -> dispose());

        buttonPanel.add(seleccionarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void cargarCursosProfesor(JComboBox<String> combo) {
        combo.removeAllItems();
        List<Curso> cursos = profesor.obtenerCursosDesdeJSON();
        boolean profesorEncontrado = false;
        
        for (Curso curso : cursos) {
            // Verificar si el profesor está asignado a algún grupo de este curso
            for (Grupo grupo : curso.getGrupos()) {
                if (profesor.getIdentificacionPersonal().equals(grupo.getProfesorAsignado())) {
                    combo.addItem(curso.getIdCurso()); // Solo el ID (String)
                    profesorEncontrado = true;
                    break; // Evitar duplicados
                }
            }
        }
        
        if (!profesorEncontrado) {
            combo.setEnabled(false);
            JOptionPane.showMessageDialog(this, 
                "No está asignado a ningún grupo en ningún curso.", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cargarGruposCurso(JComboBox<Integer> combo, String idCurso) {
        combo.removeAllItems();
        if (idCurso != null) {
            List<Curso> cursos = profesor.obtenerCursosDesdeJSON();
            for (Curso curso : cursos) {
                if (curso.getIdCurso().equals(idCurso)) {
                    for (Grupo grupo : curso.getGrupos()) {
                        if (profesor.getIdentificacionPersonal().equals(grupo.getProfesorAsignado())) {
                            combo.addItem(grupo.getIdGrupo()); // Solo el ID (Integer)
                        }
                    }
                    break;
                }
            }
            if (combo.getItemCount() == 0) {
                combo.setEnabled(false);
            }
        }
    }
}