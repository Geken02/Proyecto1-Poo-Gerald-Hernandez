// poo/proyecto1/vistas/SelectorEvaluacionesPorGrupo.java
package poo.proyecto1.vistas;

import poo.proyecto1.persona.Persona;
import poo.proyecto1.evaluacion.Evaluacion;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.grupo.Grupo;
import poo.proyecto1.util.JsonUtils;
import poo.proyecto1.util.RegistroEvaluaciones;
import poo.proyecto1.evaluacion.realizarEvaluacion.RealizacionEvaluacionGUI;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

public class SelectorEvaluacionesporGrupo extends JFrame {
    private Persona usuario;
    private String idCurso;
    private int idGrupo;

    public SelectorEvaluacionesporGrupo(Persona usuario, String idCurso, int idGrupo) {
        this.usuario = usuario;
        this.idCurso = idCurso;
        this.idGrupo = idGrupo;
        setTitle("Evaluaciones del Grupo " + idGrupo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 250);
        setLocationRelativeTo(null);
        initUI();
        cargarEvaluacionesDisponibles();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        
        JLabel lblInstruccion = new JLabel(
            "Seleccione una evaluación para " + 
            (usuario instanceof poo.proyecto1.persona.profesor.Profesor ? "previsualizar" : "realizar") + ":",
            JLabel.CENTER
        );
        add(lblInstruccion, BorderLayout.NORTH);

        JComboBox<Integer> comboEvaluaciones = new JComboBox<>();
        add(comboEvaluaciones, BorderLayout.CENTER);

        JButton btnAccion = new JButton(
            usuario instanceof poo.proyecto1.persona.profesor.Profesor ? "Previsualizar" : "Realizar Evaluación"
        );
        btnAccion.addActionListener(e -> {
            Integer idSeleccionado = (Integer) comboEvaluaciones.getSelectedItem();
            if (idSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una evaluación válida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            RegistroEvaluaciones registro = new RegistroEvaluaciones();
            List<Evaluacion> todasEvaluaciones = registro.getListaEvaluaciones();
            
            for (Evaluacion eval : todasEvaluaciones) {
                if (eval.getIdEvaluacion() == idSeleccionado) {
                    this.setVisible(false);
                    SwingUtilities.invokeLater(() -> {
                        new RealizacionEvaluacionGUI(eval, usuario).setVisible(true);
                    });
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(this, "Evaluación no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        });
        add(btnAccion, BorderLayout.SOUTH);
    }

    private void cargarEvaluacionesDisponibles() {
        try {
            java.lang.reflect.Type tipoCursos = new TypeToken<List<Curso>>(){}.getType();
            List<Curso> cursos = JsonUtils.cargarLista("MC_cursos.json", tipoCursos);
            
            for (Curso curso : cursos) {
                if (curso.getIdCurso().equals(idCurso)) {
                    Grupo grupo = curso.obtenerGrupo(idGrupo);
                    if (grupo != null) {
                        Map<Integer, java.time.LocalDateTime> evaluacionesAsociadas = grupo.getEvaluacionesAsociadas();
                        if (evaluacionesAsociadas.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "No hay evaluaciones asociadas a este grupo.", "Información", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        
                        JComboBox<Integer> combo = (JComboBox<Integer>) getContentPane().getComponent(1);
                        combo.removeAllItems();
                        

                        for (Integer idEvaluacion : evaluacionesAsociadas.keySet()) {
                            combo.addItem(idEvaluacion);
                        }
                        return;
                    }
                }
            }
            
            JOptionPane.showMessageDialog(this, "No se encontró el grupo especificado.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Error al cargar evaluaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}