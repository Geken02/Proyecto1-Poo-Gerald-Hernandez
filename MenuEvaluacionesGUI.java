// poo/proyecto1/evaluacion/MenuEvaluacionesGUI.java

package poo.proyecto1.evaluacion.creacionEvaluacion;

import poo.proyecto1.evaluacion.Evaluacion;
import poo.proyecto1.evaluacion.Pregunta;
import poo.proyecto1.evaluacion.PreguntaFalsoVerdadero;
import poo.proyecto1.evaluacion.PreguntaPareo;
import poo.proyecto1.evaluacion.PreguntaSopaLetras;
import poo.proyecto1.util.FileIOService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuEvaluacionesGUI extends JFrame {
    private JTextField txtNombre;
    private JTextArea txtInstrucciones;
    private JTextField txtDuracion;
    private JCheckBox chkPreguntasAleatorias; // ← NUEVO
    private JCheckBox chkOpcionesAleatorias; // ← NUEVO
    private JPanel panelPreguntas;
    private Evaluacion evaluacionActual;
    private final FileIOService fileIOService;

    public MenuEvaluacionesGUI() {
        super("Gestor de Evaluaciones");
        evaluacionActual = null;
        this.fileIOService = new FileIOService(this);
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panelMain = new JPanel(new BorderLayout(10, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de datos de la evaluación
        JPanel panelDatosEvaluacion = new JPanel(new GridBagLayout());
        panelDatosEvaluacion.setBorder(BorderFactory.createTitledBorder("Datos de la Evaluación"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatosEvaluacion.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        panelDatosEvaluacion.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelDatosEvaluacion.add(new JLabel("Instrucciones:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtInstrucciones = new JTextArea(3, 20);
        JScrollPane scrollInstrucciones = new JScrollPane(txtInstrucciones);
        panelDatosEvaluacion.add(scrollInstrucciones, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelDatosEvaluacion.add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDuracion = new JTextField(5);
        panelDatosEvaluacion.add(txtDuracion, gbc);

        // ← NUEVOS CAMPOS: justo después de duración
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        chkPreguntasAleatorias = new JCheckBox("Mostrar preguntas en orden aleatorio");
        panelDatosEvaluacion.add(chkPreguntasAleatorias, gbc);

        gbc.gridy = 4;
        chkOpcionesAleatorias = new JCheckBox("Mostrar opciones en orden aleatorio");
        panelDatosEvaluacion.add(chkOpcionesAleatorias, gbc);

        panelMain.add(panelDatosEvaluacion, BorderLayout.NORTH);

        // Panel de preguntas
        panelPreguntas = new JPanel();
        panelPreguntas.setLayout(new BoxLayout(panelPreguntas, BoxLayout.Y_AXIS));
        panelPreguntas.setBorder(BorderFactory.createTitledBorder("Preguntas de la Evaluación"));
        JScrollPane scrollPreguntas = new JScrollPane(panelPreguntas);
        panelMain.add(scrollPreguntas, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddUnica = new JButton("Añadir Selección Única");
        JButton btnAddMultiple = new JButton("Añadir Selección Múltiple");
        JButton btnAddFalsoVerdadero = new JButton("Añadir Falso/Verdadero");
        JButton btnAddPareo = new JButton("Añadir Pareo");
        JButton btnAddSopaLetras = new JButton("Añadir Sopa de Letras");
        JButton btnGuardar = new JButton("Guardar Evaluación");
        // JButton btnNuevaEval = new JButton("Nueva Evaluación");

        btnAddUnica.addActionListener(e -> addPregunta("SELECCION_UNICA"));
        btnAddMultiple.addActionListener(e -> addPregunta("SELECCION_MULTIPLE"));
        btnAddFalsoVerdadero.addActionListener(e -> addPreguntaFalsoVerdadero());
        btnAddPareo.addActionListener(e -> addPreguntaPareo());
        btnAddSopaLetras.addActionListener(e -> addPreguntaSopaLetras());
        // btnNuevaEval.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardarEvaluacion());

        panelBotones.add(btnAddUnica);
        panelBotones.add(btnAddMultiple);
        panelBotones.add(btnAddFalsoVerdadero);
        panelBotones.add(btnAddPareo);
        panelBotones.add(btnAddSopaLetras);
        panelBotones.add(new JSeparator(SwingConstants.VERTICAL));
        panelBotones.add(btnGuardar);
        // panelBotones.add(btnNuevaEval);

        panelMain.add(panelBotones, BorderLayout.SOUTH);
        add(panelMain);
    }

    private void agregarPreguntaAlPanel(Pregunta pregunta) {
        JPanel contenedorPregunta = new JPanel(new BorderLayout());
        contenedorPregunta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        PanelPregunta vistaPregunta = new PanelPregunta(pregunta);
        contenedorPregunta.add(vistaPregunta, BorderLayout.CENTER);

        ImageIcon iconoPapelera = new ImageIcon(
                getClass().getResource("/imagenes/papelera.gif"));

        JButton btnEliminar = new JButton(iconoPapelera);
        btnEliminar.setToolTipText("Eliminar pregunta");
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusable(false);
        btnEliminar.setPreferredSize(new Dimension(24, 24));

        btnEliminar.addActionListener(e -> {
            panelPreguntas.remove(contenedorPregunta);
            panelPreguntas.revalidate();
            panelPreguntas.repaint();

            if (evaluacionActual != null) {
                evaluacionActual.getListaPreguntas().remove(pregunta);
            }
        });

        contenedorPregunta.add(btnEliminar, BorderLayout.EAST);
        panelPreguntas.add(contenedorPregunta);
        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    }

    // === MÉTODOS DE AGREGAR PREGUNTAS ===
    private void addPregunta(String tipo) {
        if (evaluacionActual == null && !crearEvaluacionDesdeFormulario())
            return;

        DialogoCrearPregunta dialogo = new DialogoCrearPregunta(this, tipo);
        dialogo.setVisible(true);

        Pregunta nuevaPregunta = dialogo.getPreguntaCreada();
        if (nuevaPregunta != null) {
            evaluacionActual.agregarPregunta(nuevaPregunta);
            agregarPreguntaAlPanel(nuevaPregunta);
        }
    }

    private void addPreguntaFalsoVerdadero() {
        if (evaluacionActual == null && !crearEvaluacionDesdeFormulario())
            return;

        DialogoCrearFalsoVerdadero dialogo = new DialogoCrearFalsoVerdadero(this);
        dialogo.setVisible(true);

        PreguntaFalsoVerdadero nuevaPregunta = dialogo.getPreguntaCreada();
        if (nuevaPregunta != null) {
            evaluacionActual.agregarPregunta(nuevaPregunta);
            agregarPreguntaAlPanel(nuevaPregunta);
        }
    }

    private void addPreguntaPareo() {
        if (evaluacionActual == null && !crearEvaluacionDesdeFormulario())
            return;

        DialogoCrearPareo dialogo = new DialogoCrearPareo(this);
        dialogo.setVisible(true);

        PreguntaPareo nuevaPregunta = dialogo.getPreguntaCreada();
        if (nuevaPregunta != null) {
            evaluacionActual.agregarPregunta(nuevaPregunta);
            agregarPreguntaAlPanel(nuevaPregunta);
        }
    }

    private void addPreguntaSopaLetras() {
        if (evaluacionActual == null && !crearEvaluacionDesdeFormulario())
            return;

        DialogoCrearSopaLetras dialogo = new DialogoCrearSopaLetras(this);
        dialogo.setVisible(true);

        PreguntaSopaLetras nuevaPregunta = dialogo.getPreguntaCreada();
        if (nuevaPregunta != null) {
            evaluacionActual.agregarPregunta(nuevaPregunta);
            agregarPreguntaAlPanel(nuevaPregunta);
        }
    }

    // === UTILIDADES ===
    private boolean crearEvaluacionDesdeFormulario() {
        try {
            String nombre = txtNombre.getText().trim();
            String instrucciones = txtInstrucciones.getText().trim();
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            boolean preguntasAleatorias = chkPreguntasAleatorias.isSelected(); // ← NUEVO
            boolean opcionesAleatorias = chkOpcionesAleatorias.isSelected(); // ← NUEVO

            if (nombre.isEmpty())
                throw new IllegalArgumentException("El nombre de la evaluación es obligatorio.");

            int nuevoId = fileIOService.obtenerProximoId();
            // ← USAR EL NUEVO CONSTRUCTOR CON 6 PARÁMETROS
            evaluacionActual = new Evaluacion(nuevoId, nombre, instrucciones, duracion,
                    preguntasAleatorias, opcionesAleatorias);
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void limpiarFormulario() {
        evaluacionActual = null;
        txtNombre.setText("");
        txtInstrucciones.setText("");
        txtDuracion.setText("");
        chkPreguntasAleatorias.setSelected(false); // ← NUEVO
        chkOpcionesAleatorias.setSelected(false); // ← NUEVO
        panelPreguntas.removeAll();
        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    }

    private void guardarEvaluacion() {
        if (evaluacionActual == null && !crearEvaluacionDesdeFormulario())
            return;
        fileIOService.guardarNuevaEvaluacion(evaluacionActual); // ← ¡ESTO GUARDA EN JSON!
    }

}