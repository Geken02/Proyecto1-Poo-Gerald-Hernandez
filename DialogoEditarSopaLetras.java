// poo/proyecto1/evaluacion/modificarEvaluacion/DialogoEditarSopaLetras.java
package poo.proyecto1.evaluacion.modificarEvaluacion;

import poo.proyecto1.evaluacion.PreguntaSopaLetras;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogoEditarSopaLetras extends JDialog {
    private JTextField txtEnunciado;
    private JTextField txtPuntos;
    private JPanel panelPares;
    private List<JPanel> filas;
    private PreguntaSopaLetras preguntaEditada;

    public DialogoEditarSopaLetras(Frame owner, PreguntaSopaLetras pregunta) {
        super(owner, "Editar Pregunta de Sopa de Letras", true);
        filas = new ArrayList<>();
        txtEnunciado = new JTextField(pregunta.getDescripcionPregunta(), 30);
        txtPuntos = new JTextField(String.valueOf(pregunta.getCantidadPuntos()), 10);
        initComponents();
        // Cargar pares existentes
        Map<String, String> pares = pregunta.getPalabrasYEnunciados();
        for (Map.Entry<String, String> par : pares.entrySet()) {
            addFilaConValores(par.getKey(), par.getValue());
        }
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("Descripción de la pregunta:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelForm.add(txtEnunciado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelForm.add(new JLabel("Puntos que vale la pregunta:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelForm.add(txtPuntos, gbc);

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

        JPanel panelBotonesInferiores = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAddPar = new JButton("Añadir Par");
        btnAddPar.addActionListener(e -> addFila());
        panelBotonesInferiores.add(btnAddPar);
        panelForm.add(panelBotonesInferiores, new GridBagConstraints(0, 3, 2, 1, 0, 0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.addActionListener(e -> crearPregunta());
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void addFila() {
        addFilaConValores("", "");
    }

    private void addFilaConValores(String palabra, String enunciado) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtPalabra = new JTextField(palabra, 15);
        JTextField txtEnunciado = new JTextField(enunciado, 30);
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
            if (enunciado.isEmpty())
                throw new IllegalArgumentException("La descripción no puede estar vacía.");
            if (puntos < 1)
                throw new IllegalArgumentException("Los puntos deben ser >= 1.");

            for (JPanel fila : filas) {
                JTextField txtPalabra = (JTextField) fila.getComponent(1);
                JTextField txtEnunciado = (JTextField) fila.getComponent(3);
                String palabra = txtPalabra.getText().trim();
                String enun = txtEnunciado.getText().trim();
                if (!palabra.isEmpty() && !enun.isEmpty()) {
                    paresCreados.put(palabra, enun);
                }
            }

            if (paresCreados.size() < 10) {
                throw new IllegalArgumentException("Debe haber al menos 10 pares válidos.");
            }

            preguntaEditada = new PreguntaSopaLetras(enunciado, puntos, paresCreados);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los puntos deben ser un número válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public PreguntaSopaLetras getPreguntaEditada() {
        return preguntaEditada;
    }
}