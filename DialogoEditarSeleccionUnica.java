// poo/proyecto1/evaluacion/modificarEvaluacion/DialogoEditarSeleccionUnica.java
package poo.proyecto1.evaluacion.modificarEvaluacion;

import poo.proyecto1.evaluacion.PreguntaSeleccionUnica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogoEditarSeleccionUnica extends JDialog {
    private JTextField txtEnunciado;
    private JTextField txtPuntos;
    private JPanel panelOpciones;
    private List<JRadioButton> listaRadios;
    private ButtonGroup grupoRadios;
    private PreguntaSeleccionUnica preguntaEditada;

    public DialogoEditarSeleccionUnica(Frame owner, PreguntaSeleccionUnica pregunta) {
        super(owner, "Editar Selección Única", true);
        this.listaRadios = new ArrayList<>();
        this.grupoRadios = new ButtonGroup();
        precargarDatos(pregunta);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(owner);
    }

    private void precargarDatos(PreguntaSeleccionUnica pregunta) {
        // Precargamos los datos para usarlos en initComponents
        txtEnunciado = new JTextField(pregunta.getDescripcionPregunta(), 30);
        txtPuntos = new JTextField(String.valueOf(pregunta.getCantidadPuntos()), 10);

        // Opciones y respuesta correcta
        List<String> opciones = pregunta.getOpciones();
        int indiceCorrecto = pregunta.getIndiceRespuestaCorrecta();

        // Creamos los componentes de las opciones
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        for (int i = 0; i < opciones.size(); i++) {
            JTextField txtOpcion = new JTextField(opciones.get(i), 25);
            JRadioButton radio = new JRadioButton();
            if (i == indiceCorrecto) {
                radio.setSelected(true);
            }
            grupoRadios.add(radio);
            listaRadios.add(radio);

            JPanel panelOpcion = new JPanel(new BorderLayout());
            panelOpcion.add(radio, BorderLayout.WEST);
            panelOpcion.add(txtOpcion, BorderLayout.CENTER);
            panelOpciones.add(panelOpcion);
        }
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
        panelForm.add(txtEnunciado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelForm.add(new JLabel("Puntos:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelForm.add(txtPuntos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panelForm.add(panelOpciones, gbc);

        JPanel panelBotones = new JPanel();
        JButton btnAddOpcion = new JButton("Añadir Opción");
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAddOpcion.addActionListener(e -> addOptionComponent());
        btnAceptar.addActionListener(e -> crearPreguntaEditada());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAddOpcion);
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void addOptionComponent() {
        JTextField txtOpcion = new JTextField(25);
        JRadioButton radio = new JRadioButton();
        grupoRadios.add(radio);
        listaRadios.add(radio);

        JPanel panelOpcion = new JPanel(new BorderLayout());
        panelOpcion.add(radio, BorderLayout.WEST);
        panelOpcion.add(txtOpcion, BorderLayout.CENTER);
        panelOpciones.add(panelOpcion);
        panelOpciones.revalidate();
        panelOpciones.repaint();
        pack();
    }

    private void crearPreguntaEditada() {
        try {
            String enunciado = txtEnunciado.getText().trim();
            int puntos = Integer.parseInt(txtPuntos.getText().trim());
            List<String> opciones = new ArrayList<>();
            int indiceCorrecto = -1;

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

                JRadioButton radio = (JRadioButton) panel.getComponent(0);
                if (radio.isSelected()) {
                    indiceCorrecto = i;
                }
            }

            if (opciones.isEmpty())
                throw new IllegalArgumentException("Las opciones no pueden estar vacías.");
            if (indiceCorrecto == -1)
                throw new IllegalArgumentException("Debe seleccionar una respuesta correcta.");

            preguntaEditada = new PreguntaSeleccionUnica(enunciado, puntos, opciones, indiceCorrecto);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los puntos deben ser un número válido.", "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public PreguntaSeleccionUnica getPreguntaEditada() {
        return preguntaEditada;
    }
}