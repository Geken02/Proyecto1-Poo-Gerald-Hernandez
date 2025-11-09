// poo/proyecto1/evaluacion/consultar/ConsultarEvaluacionesDialog.java
package poo.proyecto1.vistas;

import poo.proyecto1.evaluacion.Evaluacion;
import poo.proyecto1.util.RegistroEvaluaciones;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaConsultarEvaluacion extends JDialog {
    private Evaluacion evaluacionSeleccionada = null;
    private boolean confirmado = false;

    public VentanaConsultarEvaluacion(Frame parent) {
        super(parent, "Consultar Evaluaciones", true);
        setLayout(new BorderLayout());

        // Panel de lista de evaluaciones
        JPanel listaPanel = new JPanel(new BorderLayout());
        listaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel instrucciones = new JLabel("Seleccione una evaluación para ver sus detalles:");
        listaPanel.add(instrucciones, BorderLayout.NORTH);

        // Cargar evaluaciones
        RegistroEvaluaciones registro = new RegistroEvaluaciones();
        List<Evaluacion> evaluaciones = registro.getListaEvaluaciones();

        DefaultListModel<Evaluacion> listModel = new DefaultListModel<>();
        for (Evaluacion eval : evaluaciones) {
            listModel.addElement(eval);
        }

        JList<Evaluacion> evaluacionesList = new JList<>(listModel);
        evaluacionesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (!evaluaciones.isEmpty()) {
            evaluacionesList.setSelectedIndex(0);
        }

        JScrollPane scrollPane = new JScrollPane(evaluacionesList);
        listaPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton verDetallesBtn = new JButton("Ver Detalles");
        JButton cancelarBtn = new JButton("Cancelar");

        verDetallesBtn.addActionListener(e -> {
            Evaluacion seleccionada = evaluacionesList.getSelectedValue();
            if (seleccionada != null) {
                evaluacionSeleccionada = seleccionada;
                confirmado = true;
                mostrarDetallesEvaluacion(seleccionada);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor seleccione una evaluación.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelarBtn.addActionListener(e -> dispose());

        buttonPanel.add(verDetallesBtn);
        buttonPanel.add(cancelarBtn);
        listaPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(listaPanel);
        pack();
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(parent);
    }

    private void mostrarDetallesEvaluacion(Evaluacion evaluacion) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("=== DETALLES DE LA EVALUACIÓN ===\n\n");
        detalles.append("ID: ").append(evaluacion.getIdEvaluacion()).append("\n");
        detalles.append("Nombre: ").append(evaluacion.getNombreEvaluacion()).append("\n");
        detalles.append("Instrucciones: ").append(evaluacion.getInstruccionesGenerales()).append("\n");
        detalles.append("Duración: ").append(evaluacion.getDuracionEvaluacion()).append(" minutos\n");
        detalles.append("Preguntas aleatorias: ").append(evaluacion.isPreguntasEnOrdenAleatorio() ? "Sí" : "No").append("\n");
        detalles.append("Opciones aleatorias: ").append(evaluacion.isOpcionesEnOrdenAleatorio() ? "Sí" : "No").append("\n");
        detalles.append("Número de preguntas: ").append(evaluacion.getListaPreguntas().size()).append("\n");
        detalles.append("\n=== OBJETIVOS ===\n");
        detalles.append("\n=== PREGUNTAS ===\n");
        for (int i = 0; i < evaluacion.getListaPreguntas().size(); i++) {
            var pregunta = evaluacion.getListaPreguntas().get(i);
            detalles.append("\nPregunta ").append(i + 1).append(" (").append(pregunta.getTipo()).append("):\n");
            detalles.append("Descripción: ").append(pregunta.getDescripcionPregunta()).append("\n");
            detalles.append("Puntos: ").append(pregunta.getCantidadPuntos()).append("\n");
            
        }

        JTextArea textArea = new JTextArea(detalles.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JOptionPane.showMessageDialog(this, 
            new JScrollPane(textArea), 
            "Detalles de la Evaluación", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Evaluacion getEvaluacionSeleccionada() {
        return evaluacionSeleccionada;
    }
}