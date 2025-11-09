package poo.proyecto1.evaluacion.creacionEvaluacion;

import javax.swing.*;

import poo.proyecto1.evaluacion.PreguntaPareo;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogoCrearPareo extends JDialog {
    private JTextField txtEnunciado;
    private JTextField txtPuntos;
    private JTextField txtIzquierda;
    private JTextField txtDerecha;
    private JPanel panelPares;
    private List<JLabel> listaParesVisuales;
    private Map<String, String> paresCreados;
    private PreguntaPareo preguntaCreada;

    // --- NUEVO ATRIBUTO PARA EL CONTADOR ---
    private int contadorDistrator = 0;

    public DialogoCrearPareo(Frame owner) {
        super(owner, "Crear Pregunta de Pareo", true);
        this.listaParesVisuales = new ArrayList<>();
        this.paresCreados = new HashMap<>();
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

        // Creación de pares
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelForm.add(new JLabel("Crear Pares:"), gbc);
        gbc.gridy = 3;
        JPanel panelCreacionPares = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCreacionPares.add(new JLabel("Izquierda:"));
        txtIzquierda = new JTextField(15);
        panelCreacionPares.add(txtIzquierda);
        panelCreacionPares.add(new JLabel("Derecha:"));
        txtDerecha = new JTextField(15);
        panelCreacionPares.add(txtDerecha);
        JButton btnAddPar = new JButton("Añadir Par");
        btnAddPar.addActionListener(e -> addPar());
        panelCreacionPares.add(btnAddPar);
        panelForm.add(panelCreacionPares, gbc);

        // Panel para mostrar los pares creados
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panelPares = new JPanel();
        panelPares.setLayout(new BoxLayout(panelPares, BoxLayout.Y_AXIS));
        panelPares.setBorder(BorderFactory.createTitledBorder("Pares Creados"));
        JScrollPane scrollPares = new JScrollPane(panelPares);
        scrollPares.setPreferredSize(new Dimension(400, 150));
        panelForm.add(scrollPares, gbc);

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

    // --- MÉTODO MODIFICADO ---
    private void addPar() {
        String derecha = txtDerecha.getText().trim();
        String izquierda = txtIzquierda.getText().trim();

        // La columna derecha (la respuesta) es obligatoria.
        if (derecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Derecha' no puede estar vacío.", "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si la columna izquierda está vacía, generamos "Distrator" + contador.
        if (izquierda.isEmpty()) {
            contadorDistrator++; // 1. Incrementar el contador
            izquierda = "Distrator" + contadorDistrator; // 2. Crear el nuevo nombre único
        }

        // Validación para evitar sobrescribir un par si la clave (izquierda) ya existe.
        if (paresCreados.containsKey(izquierda)) {
            JOptionPane.showMessageDialog(this,
                    "El elemento de la izquierda '" + izquierda + "' ya ha sido añadido.\nUse un texto diferente.",
                    "Error de Duplicado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si todo es correcto, añadimos el par.
        paresCreados.put(izquierda, derecha);
        JLabel lblPar = new JLabel(izquierda + "  <---->  " + derecha);
        panelPares.add(lblPar);
        panelPares.revalidate();
        panelPares.repaint();

        // Limpiar campos de texto
        txtIzquierda.setText("");
        txtDerecha.setText("");
        txtIzquierda.requestFocus();
    }

    private void crearPregunta() {
        try {
            String enunciado = txtEnunciado.getText().trim();
            int puntos = Integer.parseInt(txtPuntos.getText().trim());

            if (enunciado.isEmpty()) {
                throw new IllegalArgumentException("El enunciado no puede estar vacío.");
            }
            if (paresCreados.isEmpty()) {
                throw new IllegalArgumentException("Debe añadir al menos un par.");
            }

            List<String> columnaIzquierda = new ArrayList<>(paresCreados.keySet());
            List<String> columnaDerecha = new ArrayList<>(paresCreados.values());

            preguntaCreada = new PreguntaPareo(enunciado, puntos, columnaIzquierda, columnaDerecha, paresCreados);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los puntos deben ser un número válido.", "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public PreguntaPareo getPreguntaCreada() {
        return preguntaCreada;
    }
}