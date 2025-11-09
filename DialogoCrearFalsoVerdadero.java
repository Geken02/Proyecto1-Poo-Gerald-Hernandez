package poo.proyecto1.evaluacion.creacionEvaluacion;

import javax.swing.*;

import poo.proyecto1.evaluacion.PreguntaFalsoVerdadero;

import java.awt.*;

public class DialogoCrearFalsoVerdadero extends JDialog {
    private JTextField txtEnunciado;
    private JTextField txtPuntos;
    private JRadioButton rbVerdadero;
    private JRadioButton rbFalso;
    private PreguntaFalsoVerdadero preguntaCreada;

    public DialogoCrearFalsoVerdadero(Frame owner) {
        super(owner, "Crear Pregunta de Falso/Verdadero", true);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Enunciado
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("Enunciado:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEnunciado = new JTextField(30);
        panelForm.add(txtEnunciado, gbc);

        // Puntos
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelForm.add(new JLabel("Puntos:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPuntos = new JTextField(10);
        panelForm.add(txtPuntos, gbc);

        // Respuesta Correcta
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelForm.add(new JLabel("Respuesta Correcta:"), gbc);
        gbc.gridy = 3;
        JPanel panelRespuesta = new JPanel();
        rbVerdadero = new JRadioButton("Verdadero");
        rbFalso = new JRadioButton("Falso");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbVerdadero);
        grupo.add(rbFalso);
        rbVerdadero.setSelected(true); // Seleccionar Verdadero por defecto
        panelRespuesta.add(rbVerdadero);
        panelRespuesta.add(rbFalso);
        panelForm.add(panelRespuesta, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> crearPregunta());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void crearPregunta() {
        try {
            String enunciado = txtEnunciado.getText().trim();
            int puntos = Integer.parseInt(txtPuntos.getText().trim());

            if (enunciado.isEmpty()) {
                throw new IllegalArgumentException("El enunciado no puede estar vacío.");
            }

            boolean respuestaCorrecta = rbVerdadero.isSelected();
            preguntaCreada = new PreguntaFalsoVerdadero(enunciado, puntos, respuestaCorrecta);
            dispose(); // Cierra el diálogo

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los puntos deben ser un número válido.", "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public PreguntaFalsoVerdadero getPreguntaCreada() {
        return preguntaCreada;
    }
}