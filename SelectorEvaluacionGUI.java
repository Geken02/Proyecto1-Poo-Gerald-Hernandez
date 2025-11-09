// poo/proyecto1/evaluacion/realizacionEvaluacion/SelectorEvaluacionGUI.java
package poo.proyecto1.evaluacion.realizarEvaluacion;

import poo.proyecto1.evaluacion.Evaluacion;
import poo.proyecto1.util.RegistroEvaluaciones;
import poo.proyecto1.persona.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SelectorEvaluacionGUI extends JFrame {
    private JComboBox<Evaluacion> comboEvaluaciones;
    private Persona usuario;

    public SelectorEvaluacionGUI(Persona usuario) {
        setTitle("Seleccionar Evaluación para Resolver");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);
        this.usuario = usuario;
        initUI();
        cargarEvaluaciones();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblInstruccion = new JLabel("Seleccione la evaluación que desea resolver:", JLabel.CENTER);
        add(lblInstruccion, BorderLayout.NORTH);

        comboEvaluaciones = new JComboBox<>();
        add(comboEvaluaciones, BorderLayout.CENTER);

        JButton btnIniciar = new JButton("Iniciar Evaluación");
        btnIniciar.addActionListener(e -> iniciarEvaluacion());
        add(btnIniciar, BorderLayout.SOUTH);
    }

    private void cargarEvaluaciones() {
        RegistroEvaluaciones registro = new RegistroEvaluaciones();
        List<Evaluacion> evaluaciones = registro.getListaEvaluaciones();

        comboEvaluaciones.removeAllItems();
        if (evaluaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay evaluaciones disponibles para resolver.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            comboEvaluaciones.setEnabled(false);
        } else {
            for (Evaluacion eval : evaluaciones) {
                comboEvaluaciones.addItem(eval);
            }
        }
    }

    private void iniciarEvaluacion() {
        Evaluacion seleccionada = (Evaluacion) comboEvaluaciones.getSelectedItem();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación válida.", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ocultar esta ventana y abrir la de realización
        this.setVisible(false);
        SwingUtilities.invokeLater(() -> {
            new RealizacionEvaluacionGUI(seleccionada, usuario).setVisible(true);
        });
    }
}