package poo.proyecto1.evaluacion.creacionEvaluacion;

import javax.swing.*;

import poo.proyecto1.evaluacion.Pregunta;
import poo.proyecto1.evaluacion.PreguntaFalsoVerdadero;
import poo.proyecto1.evaluacion.PreguntaPareo;
import poo.proyecto1.evaluacion.PreguntaSeleccionMultiple;
import poo.proyecto1.evaluacion.PreguntaSeleccionUnica;
import poo.proyecto1.evaluacion.PreguntaSopaLetras;

import java.awt.*;
import java.util.Map;

// Un JPanel personalizado para mostrar los datos de una pregunta.
public class PanelPregunta extends JPanel {
    public PanelPregunta(Pregunta pregunta) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEtchedBorder());

        // Panel superior para el enunciado y los puntos
        JPanel panelHeader = new JPanel(new BorderLayout());
        JLabel lblEnunciado = new JLabel("<html><b>" + pregunta.getDescripcionPregunta() + "</b></html>");
        JLabel lblPuntos = new JLabel(String.valueOf(pregunta.getCantidadPuntos()) + " pts");
        lblPuntos.setHorizontalAlignment(SwingConstants.RIGHT);

        panelHeader.add(lblEnunciado, BorderLayout.CENTER);
        panelHeader.add(lblPuntos, BorderLayout.EAST);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        add(panelHeader, BorderLayout.NORTH);

        // Panel central para las opciones
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 10));

        // Usamos polimorfismo para mostrar las opciones según el tipo de pregunta
        if (pregunta instanceof PreguntaSeleccionUnica) {
            PreguntaSeleccionUnica psu = (PreguntaSeleccionUnica) pregunta;
            for (String opcion : psu.getOpciones()) {
                panelOpciones.add(new JLabel("• " + opcion));
            }
        } else if (pregunta instanceof PreguntaSeleccionMultiple) {
            PreguntaSeleccionMultiple psm = (PreguntaSeleccionMultiple) pregunta;
            for (String opcion : psm.getOpciones()) {
                panelOpciones.add(new JLabel("• " + opcion));
            }
        } else if (pregunta instanceof PreguntaFalsoVerdadero) {
            PreguntaFalsoVerdadero pfv = (PreguntaFalsoVerdadero) pregunta;
            String verdaderoLabel = "• Verdadero";
            String falsoLabel = "• Falso";

            if (pfv.isRespuestaCorrecta()) {
                verdaderoLabel += " (Correcta)";
            } else {
                falsoLabel += " (Correcta)";
            }
            panelOpciones.add(new JLabel(verdaderoLabel));
            panelOpciones.add(new JLabel(falsoLabel));
        } else if (pregunta instanceof PreguntaPareo) {
            PreguntaPareo pp = (PreguntaPareo) pregunta;
            for (Map.Entry<String, String> par : pp.getParesCorrectos().entrySet()) {
                panelOpciones.add(new JLabel(par.getKey() + "  <---->  " + par.getValue()));
            }
        } else if (pregunta instanceof PreguntaSopaLetras) { // LÓGICA PARA SOPA DE LETRAS
            PreguntaSopaLetras psl = (PreguntaSopaLetras) pregunta;
            int contador = 1;
            for (Map.Entry<String, String> par : psl.getPalabrasYEnunciados().entrySet()) {
                // Usamos HTML para el formato para que el texto largo se ajuste y se vea bien
                String textoPar = String.format("<html><b>%d.</b> Palabra: <i>%s</i><br>Enunciado: %s</html>", contador,
                        par.getKey(), par.getValue());
                panelOpciones.add(new JLabel(textoPar));
                panelOpciones.add(Box.createVerticalStrut(5)); // Añade un pequeño espacio entre pares
                contador++;
            }
        }

        add(panelOpciones, BorderLayout.CENTER);
    }
}