package poo.proyecto1.evaluacion.creacionEvaluacion;

import javax.swing.*;

import poo.proyecto1.evaluacion.Pregunta;
import poo.proyecto1.evaluacion.PreguntaSeleccionMultiple;
import poo.proyecto1.evaluacion.PreguntaSeleccionUnica;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogoCrearPregunta extends JDialog {
    private JTextField txtEnunciado;
    private JTextField txtPuntos;
    private JPanel panelOpciones;
    private List<JCheckBox> listaChecks;
    private List<JRadioButton> listaRadios;
    private ButtonGroup grupoRadios;
    private Pregunta preguntaCreada;
    private final String tipoPregunta;

    public DialogoCrearPregunta(Frame owner, String tipoPregunta) {
        super(owner, "Crear Pregunta de " + tipoPregunta.replace("_", " "), true);
        this.tipoPregunta = tipoPregunta;
        this.listaChecks = new ArrayList<>();
        this.listaRadios = new ArrayList<>();
        this.grupoRadios = new ButtonGroup();

        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("Enunciado:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEnunciado = new JTextField(30);
        panelForm.add(txtEnunciado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelForm.add(new JLabel("Puntos:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPuntos = new JTextField(10);
        panelForm.add(txtPuntos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Opciones"));
        panelForm.add(panelOpciones, gbc);

        JPanel panelBotones = new JPanel();
        JButton btnAddOpcion = new JButton("Añadir Opción");
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAddOpcion.addActionListener(e -> addOptionComponent());
        btnAceptar.addActionListener(e -> crearPregunta());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAddOpcion);
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void addOptionComponent() {
        JTextField txtOpcion = new JTextField(25);
        JComponent selector;
        if ("SELECCION_UNICA".equals(tipoPregunta)) {
            selector = new JRadioButton();
            grupoRadios.add((JRadioButton) selector);
            listaRadios.add((JRadioButton) selector);
        } else {
            selector = new JCheckBox();
            listaChecks.add((JCheckBox) selector);
        }

        JPanel panelOpcion = new JPanel(new BorderLayout());
        panelOpcion.add(selector, BorderLayout.WEST);
        panelOpcion.add(txtOpcion, BorderLayout.CENTER);

        panelOpciones.add(panelOpcion);
        panelOpciones.revalidate();
        panelOpciones.repaint();
        pack();
    }

    private void crearPregunta() {
        try {
            String enunciado = txtEnunciado.getText().trim();
            int puntos = Integer.parseInt(txtPuntos.getText().trim());
            List<String> opciones = new ArrayList<>();
            List<Integer> correctasIndices = new ArrayList<>();

            if (enunciado.isEmpty())
                throw new IllegalArgumentException("El enunciado no puede estar vacío.");

            Component[] components = panelOpciones.getComponents();
            if (components.length == 0)
                throw new IllegalArgumentException("Debe añadir al menos una opción.");

            for (int i = 0; i < components.length; i++) {
                JPanel panel = (JPanel) components[i];
                JTextField txtOpcion = (JTextField) panel.getComponent(1);
                String opcionText = txtOpcion.getText().trim();
                if (opcionText.isEmpty())
                    continue;
                opciones.add(opcionText);

                JComponent selector = (JComponent) panel.getComponent(0);
                if (selector instanceof JCheckBox && ((JCheckBox) selector).isSelected()) {
                    correctasIndices.add(i);
                } else if (selector instanceof JRadioButton && ((JRadioButton) selector).isSelected()) {
                    correctasIndices.add(i);
                }
            }

            if (opciones.isEmpty())
                throw new IllegalArgumentException("Las opciones no pueden estar vacías.");
            if (correctasIndices.isEmpty())
                throw new IllegalArgumentException("Debe seleccionar al menos una respuesta correcta.");

            if ("SELECCION_UNICA".equals(tipoPregunta)) {
                preguntaCreada = new PreguntaSeleccionUnica(enunciado, puntos, opciones, correctasIndices.get(0));
            } else {
                preguntaCreada = new PreguntaSeleccionMultiple(enunciado, puntos, opciones, correctasIndices);
            }

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los puntos deben ser un número válido.", "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Pregunta getPreguntaCreada() {
        return preguntaCreada;
    }
}