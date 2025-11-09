// poo/proyecto1/evaluacion/realizacionEvaluacion/RealizacionEvaluacionGUI.java
package poo.proyecto1.evaluacion.realizarEvaluacion;

import poo.proyecto1.persona.Persona;
import poo.proyecto1.evaluacion.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class RealizacionEvaluacionGUI extends JFrame {
    // Constantes para estilos
    private static final Color COLOR_PRIMARIO = new Color(70, 130, 180);
    private static final Color COLOR_SECUNDARIO = new Color(240, 248, 255);
    private static final Color COLOR_EXITO = new Color(46, 125, 50);
    private static final Color COLOR_PELIGRO = new Color(198, 40, 40);
    private static final Color COLOR_ADVERTENCIA = new Color(255, 152, 0);
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 16);
    private static final Font FUENTE_SUBTITULO = new Font("Arial", Font.BOLD, 14);
    private static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 12);

    private Evaluacion evaluacion;
    private List<ComponenteRespuesta> componentesRespuesta;
    private JLabel labelCronometro;
    private javax.swing.Timer timer;
    private int segundosRestantes;
    private boolean modoPrevisualizacion;
    private Persona usuario;

    public RealizacionEvaluacionGUI(Evaluacion evaluacion, Persona usuario) {
        this.modoPrevisualizacion = (usuario instanceof poo.proyecto1.persona.profesor.Profesor);
        List<Pregunta> preguntasParaMostrar = new ArrayList<>(evaluacion.getListaPreguntas());
        if (evaluacion.isPreguntasEnOrdenAleatorio()) {
            Collections.shuffle(preguntasParaMostrar);
        }
        this.evaluacion = evaluacion;
        this.componentesRespuesta = new ArrayList<>();
        this.usuario = usuario;
        initUI(preguntasParaMostrar);
    }

    private void initUI(List<Pregunta> preguntasParaMostrar) {
        setTitle("Resolviendo: " + evaluacion.getNombreEvaluacion());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // === PANEL SUPERIOR (Instrucciones + Cronómetro) ===
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 0));
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Panel de instrucciones
        JPanel panelInstrucciones = new JPanel(new BorderLayout());
        panelInstrucciones.setBackground(Color.WHITE);
        panelInstrucciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(COLOR_PRIMARIO, 2),
                        "Instrucciones de la Evaluación"),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JTextArea txtInstrucciones = new JTextArea(evaluacion.getInstruccionesGenerales());
        txtInstrucciones.setEditable(false);
        txtInstrucciones.setWrapStyleWord(true);
        txtInstrucciones.setLineWrap(true);
        txtInstrucciones.setBackground(new Color(250, 250, 250));
        txtInstrucciones.setFont(FUENTE_NORMAL);
        txtInstrucciones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane instruccionesScroll = new JScrollPane(txtInstrucciones);
        instruccionesScroll.setPreferredSize(new Dimension(600, 80));
        instruccionesScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panelInstrucciones.add(instruccionesScroll, BorderLayout.CENTER);

        // Panel del cronómetro
        JPanel panelCronometro = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelCronometro.setBackground(Color.WHITE);
        panelCronometro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PELIGRO, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));

        labelCronometro = new JLabel("Tiempo restante: --:--");
        labelCronometro.setFont(FUENTE_SUBTITULO);
        labelCronometro.setForeground(COLOR_PELIGRO);
        panelCronometro.add(labelCronometro);

        panelSuperior.add(panelInstrucciones, BorderLayout.CENTER);
        panelSuperior.add(panelCronometro, BorderLayout.EAST);
        mainPanel.add(panelSuperior, BorderLayout.NORTH);

        // === PANEL CENTRAL (Preguntas) ===
        JPanel panelPreguntas = new JPanel();
        panelPreguntas.setLayout(new BoxLayout(panelPreguntas, BoxLayout.Y_AXIS));
        panelPreguntas.setBackground(Color.WHITE);
        panelPreguntas.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        JScrollPane scrollPreguntas = new JScrollPane(panelPreguntas);
        scrollPreguntas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPreguntas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPreguntas.setBorder(BorderFactory.createEmptyBorder());
        scrollPreguntas.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPreguntas, BorderLayout.CENTER);

        // Mostrar todas las preguntas
        for (Pregunta pregunta : preguntasParaMostrar) {
            if (pregunta instanceof PreguntaSeleccionUnica ||
                    pregunta instanceof PreguntaSeleccionMultiple ||
                    pregunta instanceof PreguntaFalsoVerdadero ||
                    pregunta instanceof PreguntaPareo ||
                    pregunta instanceof PreguntaSopaLetras) {
                ComponenteRespuesta comp = new ComponenteRespuesta(pregunta, evaluacion.isOpcionesEnOrdenAleatorio());
                comp.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelPreguntas.add(comp);
                panelPreguntas.add(Box.createRigidArea(new Dimension(0, 20)));
                componentesRespuesta.add(comp);
            }
        }

        // === PANEL INFERIOR (Botones) ===
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        JButton btnFinalizar = new JButton("Finalizar y Calificar");
        aplicarEstiloBotonPrimario(btnFinalizar);
        btnFinalizar.addActionListener(e -> calificarEvaluacion());

        JButton btnVolver = new JButton("Volver al Selector");
        aplicarEstiloBotonSecundario(btnVolver);
        btnVolver.addActionListener(e -> volverAlSelector());

        panelBoton.add(btnFinalizar);
        panelBoton.add(btnVolver);
        mainPanel.add(panelBoton, BorderLayout.SOUTH);

        add(mainPanel);

        // === CONFIGURACIÓN DEL CRONÓMETRO ===
        if (evaluacion.getDuracionEvaluacion() > 0) {
            segundosRestantes = evaluacion.getDuracionEvaluacion() * 60;
            actualizarEtiquetaCronometro();
            timer = new javax.swing.Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    segundosRestantes--;
                    actualizarEtiquetaCronometro();
                    if (segundosRestantes <= 0) {
                        timer.stop();
                        tiempoExpirado();
                    } else if (segundosRestantes <= 300) { // 5 minutos
                        labelCronometro.setForeground(COLOR_ADVERTENCIA);
                    }
                }
            });
            timer.start();
        }

        revalidate();
        repaint();
    }

    private void aplicarEstiloBotonPrimario(JButton boton) {
        boton.setFont(FUENTE_SUBTITULO);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARIO.darker()),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_PRIMARIO.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_PRIMARIO);
            }
        });
    }

    private void aplicarEstiloBotonSecundario(JButton boton) {
        boton.setFont(FUENTE_NORMAL);
        boton.setBackground(new Color(220, 220, 220));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(200, 200, 200));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(220, 220, 220));
            }
        });
    }

    private void actualizarEtiquetaCronometro() {
        int minutos = segundosRestantes / 60;
        int segundos = segundosRestantes % 60;
        labelCronometro.setText(String.format("Tiempo restante: %02d:%02d", minutos, segundos));
    }

    private void tiempoExpirado() {
        JOptionPane.showMessageDialog(
                this,
                "<html><div style='text-align: center;'>" +
                        "<h3 style='color: #c62828;'>¡Tiempo Agotado!</h3>" +
                        "<p>Se ha agotado el tiempo de la evaluación.</p>" +
                        "<p>Se procederá a calificar automáticamente.</p>" +
                        "</div></html>",
                "Tiempo Expirado",
                JOptionPane.WARNING_MESSAGE);
        calificarEvaluacion();
    }

    private void calificarEvaluacion() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

         if (modoPrevisualizacion) {
        // Solo mostrar resultados, no guardar nada
        return;
    }

        double puntajeTotal = 0;
        double puntajeObtenido = 0;
        int preguntasContestadas = 0;
        int totalPreguntas = componentesRespuesta.size();

        List<String> preguntasSinResponder = new ArrayList<>();
        for (int i = 0; i < componentesRespuesta.size(); i++) {
            ComponenteRespuesta comp = componentesRespuesta.get(i);
            Pregunta pregunta = comp.getPregunta();
            if (pregunta instanceof PreguntaPareo) {
                double puntajePareo = calcularPuntajePareo(comp);
                puntajeObtenido += puntajePareo;
                puntajeTotal += pregunta.getCantidadPuntos();
                if (puntajePareo > 0) {
                    preguntasContestadas++;
                }
            } else if (pregunta instanceof PreguntaSopaLetras) {
                double puntajeSopa = calcularPuntajeSopaLetras(comp);
                puntajeObtenido += puntajeSopa;
                puntajeTotal += pregunta.getCantidadPuntos();
                if (puntajeSopa > 0) {
                    preguntasContestadas++;
                }
            } else {
                List<Integer> respuestas = comp.getRespuestasUsuario();
                if (respuestas.isEmpty()) {
                    preguntasSinResponder.add("Pregunta " + (i + 1) + ": " + pregunta.getDescripcionPregunta());
                } else {
                    preguntasContestadas++;
                    if (pregunta.corregirRespuesta(respuestas)) {
                        puntajeObtenido += pregunta.getCantidadPuntos();
                    }
                }
                puntajeTotal += pregunta.getCantidadPuntos();
            }
        }

        if (!preguntasSinResponder.isEmpty()) {
            String mensajePreguntasSinResponder = "<html><div style='max-width: 400px;'>" +
                    "<h3 style='color: #ff9800; margin-bottom: 10px;'>Preguntas Sin Responder</h3>" +
                    "<p>Tienes <b>" + preguntasSinResponder.size() + "</b> pregunta(s) sin responder:</p>" +
                    "<ul style='margin: 10px 0;'>";

            for (int i = 0; i < Math.min(3, preguntasSinResponder.size()); i++) {
                mensajePreguntasSinResponder += "<li>" + preguntasSinResponder.get(i) + "</li>";
            }

            if (preguntasSinResponder.size() > 3) {
                mensajePreguntasSinResponder += "<li>... y " + (preguntasSinResponder.size() - 3) + " más</li>";
            }

            mensajePreguntasSinResponder += "</ul><p>¿Desea finalizar de todas formas?</p></div></html>";

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    mensajePreguntasSinResponder,
                    "Preguntas Sin Responder",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (respuesta != JOptionPane.YES_OPTION) {
                if (timer != null && segundosRestantes > 0) {
                    timer.start();
                }
                return;
            }
        }

        double porcentaje = puntajeTotal > 0 ? (puntajeObtenido / puntajeTotal) * 100 : 0;
        String colorPorcentaje = porcentaje >= 80 ? "#2e7d32" : (porcentaje >= 60 ? "#ff9800" : "#c62828");
        String emoji = porcentaje >= 80 ? "🎉" : (porcentaje >= 60 ? "👍" : "😔");

        String mensaje = String.format(
                "<html><div style='text-align: center; max-width: 450px;'>" +
                        "<h2 style='color: #4682b4; margin-bottom: 20px;'>%s Resultados de la Evaluación</h2>" +
                        "<div style='background: #f8f9fa; padding: 20px; border-radius: 8px; margin: 15px 0;'>" +
                        "<p style='margin: 8px 0;'><b>Preguntas contestadas:</b> %d/%d</p>" +
                        "<p style='margin: 8px 0;'><b>Puntaje obtenido:</b> <span style='color: %s;'>%.2f/%.2f</span></p>"
                        +
                        "<p style='margin: 8px 0; font-size: 18px;'><b>Porcentaje:</b> <span style='color: %s; font-weight: bold;'>%.2f%%</span></p>"
                        +
                        "</div>" +
                        "<p style='color: #666; font-style: italic;'>%s</p>" +
                        "</div></html>",
                emoji,
                preguntasContestadas, totalPreguntas,
                colorPorcentaje, puntajeObtenido, puntajeTotal,
                colorPorcentaje, porcentaje,
                getMensajeMotivacional(porcentaje));

        JOptionPane.showMessageDialog(this, mensaje, "Evaluación Finalizada", JOptionPane.INFORMATION_MESSAGE);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "<html><div style='text-align: center;'>" +
                        "<p>¿Desea realizar otra evaluación?</p>" +
                        "</div></html>",
                "Continuar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            volverAlSelector();
        } else {
            this.dispose();
        }
    }

    private String getMensajeMotivacional(double porcentaje) {
        if (porcentaje >= 90)
            return "¡Excelente trabajo! Dominas completamente el tema.";
        if (porcentaje >= 80)
            return "¡Muy bien hecho! Tienes un buen dominio del tema.";
        if (porcentaje >= 70)
            return "Buen trabajo. Sigue practicando para mejorar.";
        if (porcentaje >= 60)
            return "Aprobado. Sigue esforzándote para mejorar tu puntaje.";
        return "No te rindas. Revisa el material y vuelve a intentarlo.";
    }

    private double calcularPuntajePareo(ComponenteRespuesta comp) {
        PreguntaPareo preguntaPareo = (PreguntaPareo) comp.getPregunta();
        Map<String, String> paresCorrectos = preguntaPareo.getParesCorrectos();
        List<Integer> respuestasUsuario = comp.getRespuestasUsuario();
        List<String> izquierdaOriginal = preguntaPareo.getColumnaIzquierda();
        List<String> derechaOriginal = preguntaPareo.getColumnaDerecha();

        List<String> elementosMostrados = new ArrayList<>();
        for (String item : izquierdaOriginal) {
            if (!item.matches("Distrator.*")) {
                elementosMostrados.add(item);
            }
        }

        int totalPreguntasCorrectas = elementosMostrados.size();
        if (totalPreguntasCorrectas == 0)
            return 0;

        int respuestasCorrectasPareo = 0;
        for (int i = 0; i < respuestasUsuario.size(); i++) {
            int respuestaUsuario = respuestasUsuario.get(i);
            if (respuestaUsuario != -1 && respuestaUsuario < derechaOriginal.size()) {
                String elementoIzquierda = izquierdaOriginal.get(i);
                String respuestaCorrecta = paresCorrectos.get(elementoIzquierda);
                String respuestaUsuarioStr = derechaOriginal.get(respuestaUsuario);
                if (respuestaUsuarioStr != null && respuestaUsuarioStr.equals(respuestaCorrecta)) {
                    respuestasCorrectasPareo++;
                }
            }
        }

        double puntajePorRespuesta = (double) preguntaPareo.getCantidadPuntos() / totalPreguntasCorrectas;
        return puntajePorRespuesta * respuestasCorrectasPareo;
    }

    private double calcularPuntajeSopaLetras(ComponenteRespuesta comp) {
        PreguntaSopaLetras preguntaSopa = (PreguntaSopaLetras) comp.getPregunta();
        Map<String, String> palabrasEnunciados = preguntaSopa.getPalabrasYEnunciados();
        List<String> palabrasCorrectas = new ArrayList<>(palabrasEnunciados.keySet());

        // Obtener palabras seleccionadas gráficamente
        List<String> palabrasSeleccionadas = comp.getPalabrasSeleccionadasSopaLetras();
        Set<String> palabrasEncontradas = new HashSet<>();
        for (String palabra : palabrasSeleccionadas) {
            palabrasEncontradas.add(palabra.toUpperCase());
        }

        int palabrasCorrectasEncontradas = 0;
        for (String palabraCorrecta : palabrasCorrectas) {
            if (palabrasEncontradas.contains(palabraCorrecta.toUpperCase())) {
                palabrasCorrectasEncontradas++;
            }
        }

        if (palabrasCorrectas.isEmpty())
            return 0;

        // Calcular puntaje proporcional
        double puntajePorPalabra = (double) preguntaSopa.getCantidadPuntos() / palabrasCorrectas.size();
        double puntajeTotal = puntajePorPalabra * palabrasCorrectasEncontradas;

        // Asegurar que no se exceda el puntaje máximo
        return Math.min(puntajeTotal, preguntaSopa.getCantidadPuntos());
    }

    private void volverAlSelector() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new SelectorEvaluacionGUI(usuario).setVisible(true);
        });
    }
}