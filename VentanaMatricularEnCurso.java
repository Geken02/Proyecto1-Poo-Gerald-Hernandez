
package poo.proyecto1.vistas;

import poo.proyecto1.persona.estudiante.Estudiante;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.grupo.Grupo;
import poo.proyecto1.util.JsonUtils;
import com.google.gson.reflect.TypeToken;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Type;
import java.util.List;

public class VentanaMatricularEnCurso extends JDialog {
    private JTextField cursoIdField;
    private JComboBox<Grupo> grupoCombo;
    private JButton buscarBtn, matricularBtn;
    private Estudiante estudiante;

    public VentanaMatricularEnCurso(JFrame parent, Estudiante estudiante) {
        super(parent, "Matricular en Curso", true);
        this.estudiante = estudiante;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Panel de entrada
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("ID del Curso:"));
        cursoIdField = new JTextField(15);
        inputPanel.add(cursoIdField);
        buscarBtn = new JButton("Buscar Curso");
        inputPanel.add(buscarBtn);
        add(inputPanel, BorderLayout.NORTH);

        // Panel de grupo
        JPanel grupoPanel = new JPanel(new FlowLayout());
        grupoPanel.add(new JLabel("Seleccione un grupo:"));
        grupoCombo = new JComboBox<>();
        grupoCombo.setEnabled(false);
        grupoPanel.add(grupoCombo);
        matricularBtn = new JButton("Matricular");
        matricularBtn.setEnabled(false);
        grupoPanel.add(matricularBtn);
        add(grupoPanel, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose());
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Acciones
        buscarBtn.addActionListener(e -> buscarCurso());
        matricularBtn.addActionListener(e -> matricular());

        pack();
        setMinimumSize(new Dimension(450, 180));
        setLocationRelativeTo(getParent());
    }

    private void buscarCurso() {
        String idCurso = cursoIdField.getText().trim();
        if (idCurso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID del curso.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            java.lang.reflect.Type tipo = new TypeToken<List<Curso>>(){}.getType();
            List<Curso> cursos = JsonUtils.cargarLista("MC_Cursos.json", tipo);
            Curso curso = cursos.stream()
                .filter(c -> c.getIdCurso().equals(idCurso))
                .findFirst()
                .orElse(null);

            if (curso == null) {
                JOptionPane.showMessageDialog(this, "Curso no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Grupo> gruposDisponibles = curso.getGrupos().stream()
                .filter(g -> g.getEstudiantesMatriculados().size() < curso.getCantMaxEstudiantes())
                .filter(g -> !g.getEstudiantesMatriculados().contains(estudiante.getIdentificacionPersonal()))
                .toList();

            if (gruposDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay grupos disponibles con cupo.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            grupoCombo.removeAllItems();
            gruposDisponibles.forEach(grupoCombo::addItem);
            grupoCombo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Grupo g) {
                        setText("Grupo " + g.getIdGrupo() + " (" + g.getFechaInicio() + " a " + g.getFechaFin() + ")");
                    }
                    return this;
                }
            });
            grupoCombo.setEnabled(true);
            matricularBtn.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cursos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void matricular() {
        Grupo grupo = (Grupo) grupoCombo.getSelectedItem();
        if (grupo == null) return;

        boolean exito = estudiante.matricularEnGrupo(cursoIdField.getText().trim(), grupo.getIdGrupo());
        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Matrícula exitosa!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}