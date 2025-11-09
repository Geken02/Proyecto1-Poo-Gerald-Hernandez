// poo/proyecto1/vistas/SelectorCursoEstudiante.java
package poo.proyecto1.vistas;

import poo.proyecto1.persona.estudiante.Estudiante;
import javax.swing.*;
import java.awt.*;
import poo.proyecto1.vistas.SelectorEvaluacionesporGrupo;

public class SelectorCursoEstudiante extends JDialog {
    private Estudiante estudiante;
    private String idCursoSeleccionado = null;

    public SelectorCursoEstudiante(Frame parent, Estudiante estudiante) {
        super(parent, "Seleccionar Curso para Realizar Evaluación", true);
        this.estudiante = estudiante;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(400, 180);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("ID del Curso:"));
        JTextField campoIdCurso = new JTextField();
        formPanel.add(campoIdCurso);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Aceptar");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            String idCurso = campoIdCurso.getText().trim();
            if (idCurso.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID de curso válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar que el estudiante esté en un único grupo del curso
            int idGrupo = estudiante.obtenerGrupoUnicoDeCurso(idCurso);
            if (idGrupo == -1) {
                JOptionPane.showMessageDialog(this, 
                    "No está matriculado en exactamente un grupo de este curso.\n" +
                    "Verifique su matrícula o contacte al administrador.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            idCursoSeleccionado = idCurso;
            dispose();
            
            // Abrir directamente el selector de evaluaciones
            new SelectorEvaluacionesporGrupo(estudiante, idCurso, idGrupo).setVisible(true);
        });

        cancelarBtn.addActionListener(e -> dispose());

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getIdCursoSeleccionado() {
        return idCursoSeleccionado;
    }
}