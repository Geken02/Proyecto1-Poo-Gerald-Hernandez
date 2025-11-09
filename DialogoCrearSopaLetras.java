package poo.proyecto1.evaluacion.creacionEvaluacion;

import javax.swing.*;

import poo.proyecto1.evaluacion.PreguntaSopaLetras;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogoCrearSopaLetras extends JDialog {
    private JTextField txtEnunciado;
    private JTextField txtPuntos;
    private JPanel panelPares;
    private List<JPanel> filas;
    private PreguntaSopaLetras preguntaCreada;

    public DialogoCrearSopaLetras(Frame owner) {
        super(owner, "Crear Pregunta de Sopa de Letras", true);
        this.filas = new ArrayList<>();
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Descripción de la pregunta
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("Descripción de la pregunta:"), gbc); // <-- ETIQUETA MODIFICADA
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEnunciado = new JTextField(30);
        panelForm.add(txtEnunciado, gbc);

        // Puntos que vale la pregunta
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelForm.add(new JLabel("Puntos que vale la pregunta:"), gbc); // <-- ETIQUETA MODIFICADA
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPuntos = new JTextField(10);
        panelForm.add(txtPuntos, gbc);

        // Panel para los pares
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panelPares = new JPanel();
        panelPares.setLayout(new BoxLayout(panelPares, BoxLayout.Y_AXIS));
        panelPares.setBorder(BorderFactory.createTitledBorder("Pares de Palabra y Enunciado (Mínimo 10)"));

        JScrollPane scrollPares = new JScrollPane(panelPares);
        scrollPares.setPreferredSize(new Dimension(550, 300));
        panelForm.add(scrollPares, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotonesInferiores = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAddPar = new JButton("Añadir Par");
        btnAddPar.addActionListener(e -> addFila());
        panelBotonesInferiores.add(btnAddPar);
        panelForm.add(panelBotonesInferiores, new GridBagConstraints(0, 3, 2, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        // Botones de aceptar/cancelar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> crearPregunta());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Añadir las primeras filas por defecto
        for (int i = 0; i < 3; i++) {
            addFila();
        }
    }

    private void addFila() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtPalabra = new JTextField(15);
        JTextField txtEnunciado = new JTextField(30);
        JButton btnEliminar = new JButton("Eliminar");

        btnEliminar.addActionListener(e -> {
            panelPares.remove(fila);
            filas.remove(fila);
            panelPares.revalidate();
            panelPares.repaint();
        });

        fila.add(new JLabel("Palabra:"));
        fila.add(txtPalabra);
        fila.add(new JLabel("Enunciado:"));
        fila.add(txtEnunciado);
        fila.add(btnEliminar);

        filas.add(fila);
        panelPares.add(fila);
        panelPares.revalidate();
        panelPares.repaint();
    }

    private void crearPregunta() {
        try {
            String enunciado = txtEnunciado.getText().trim();
            int puntos = Integer.parseInt(txtPuntos.getText().trim());
            Map<String, String> paresCreados = new HashMap<>();

            if (enunciado.isEmpty()) {
                throw new IllegalArgumentException("La descripción de la pregunta no puede estar vacía.");
            }

            // --- NUEVA VALIDACIÓN PARA LOS PUNTOS ---
            if (puntos < 1) {
                throw new IllegalArgumentException(
                        "El valor de los puntos debe ser un número entero mayor o igual a 1.");
            }

            for (JPanel fila : filas) {
                JTextField txtPalabra = (JTextField) fila.getComponent(1);
                JTextField txtEnunciadoFila = (JTextField) fila.getComponent(3);
                String palabra = txtPalabra.getText().trim();
                String enunciadoFila = txtEnunciadoFila.getText().trim();

                if (!palabra.isEmpty() && !enunciadoFila.isEmpty()) {
                    paresCreados.put(palabra, enunciadoFila);
                }
            }

            if (paresCreados.size() < 10) {
                throw new IllegalArgumentException("Debe añadir al menos 10 pares de palabra y enunciado válidos.");
            }

            preguntaCreada = new PreguntaSopaLetras(enunciado, puntos, paresCreados);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los puntos deben ser un número válido.", "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public PreguntaSopaLetras getPreguntaCreada() {
        return preguntaCreada;
    }
}