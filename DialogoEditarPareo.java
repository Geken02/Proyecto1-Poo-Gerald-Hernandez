// poo/proyecto1/evaluacion/modificarEvaluacion/DialogoEditarPareo.java
package poo.proyecto1.evaluacion.modificarEvaluacion;

import poo.proyecto1.evaluacion.PreguntaPareo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogoEditarPareo extends JDialog {
    private JTextField txtEnunciado;
    private JTextField txtPuntos;
    private JTextField txtIzquierda;
    private JTextField txtDerecha;
    private JPanel panelPares;
    private Map<String, String> paresCreados;
    private PreguntaPareo preguntaEditada;

    public DialogoEditarPareo(Frame owner, PreguntaPareo pregunta) {
        super(owner, "Editar Pregunta de Pareo", true);
        this.paresCreados = new HashMap<>(pregunta.getParesCorrectos());
        precargarDatos(pregunta);
        initComponents();
        cargarParesEditables();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(owner);
    }

    private void precargarDatos(PreguntaPareo pregunta) {
        txtEnunciado = new JTextField(pregunta.getDescripcionPregunta(), 30);
        txtPuntos = new JTextField(String.valueOf(pregunta.getCantidadPuntos()), 10);
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

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panelPares = new JPanel();
        panelPares.setLayout(new BoxLayout(panelPares, BoxLayout.Y_AXIS));
        panelPares.setBorder(BorderFactory.createTitledBorder("Pares Editables"));
        JScrollPane scrollPares = new JScrollPane(panelPares);
        scrollPares.setPreferredSize(new Dimension(400, 150));
        panelForm.add(scrollPares, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.addActionListener(e -> crearPregunta());
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarParesEditables() {
        // Limpiar en caso de reutilización
        panelPares.removeAll();
        for (Map.Entry<String, String> par : paresCreados.entrySet()) {
            addFilaEditable(par.getKey(), par.getValue());
        }
        panelPares.revalidate();
        panelPares.repaint();
    }

    private void addFilaEditable(String izquierda, String derecha) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtIzq = new JTextField(izquierda, 12);
        JTextField txtDer = new JTextField(derecha, 12);
        JButton btnEliminar = new JButton("Eliminar");

        btnEliminar.addActionListener(e -> {
            // Eliminar del mapa usando la clave original
            paresCreados.remove(izquierda);
            panelPares.remove(fila);
            panelPares.revalidate();
            panelPares.repaint();
        });

        // Actualizar el mapa cuando se edita
        txtIzq.addActionListener(e -> {
            paresCreados.remove(izquierda); // Eliminar la clave antigua
            paresCreados.put(txtIzq.getText(), derecha); // Añadir con nueva clave
        });
        txtDer.addActionListener(e -> {
            paresCreados.put(izquierda, txtDer.getText()); // Actualizar valor
        });

        fila.add(new JLabel("Izq:"));
        fila.add(txtIzq);
        fila.add(new JLabel("Der:"));
        fila.add(txtDer);
        fila.add(btnEliminar);
        panelPares.add(fila);
    }

    private void addPar() {
        String izquierda = txtIzquierda.getText().trim();
        String derecha = txtDerecha.getText().trim();
        if (izquierda.isEmpty() || derecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ambos campos son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (paresCreados.containsKey(izquierda)) {
            JOptionPane.showMessageDialog(this, "La clave ya existe.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        paresCreados.put(izquierda, derecha);
        addFilaEditable(izquierda, derecha);
        txtIzquierda.setText("");
        txtDerecha.setText("");
        panelPares.revalidate();
        panelPares.repaint();
    }

    private void crearPregunta() {
        try {
            String enunciado = txtEnunciado.getText().trim();
            int puntos = Integer.parseInt(txtPuntos.getText().trim());
            if (enunciado.isEmpty())
                throw new IllegalArgumentException("El enunciado no puede estar vacío.");
            if (paresCreados.isEmpty())
                throw new IllegalArgumentException("Debe haber al menos un par.");

            List<String> columnaIzquierda = new ArrayList<>(paresCreados.keySet());
            List<String> columnaDerecha = new ArrayList<>(paresCreados.values());
            preguntaEditada = new PreguntaPareo(enunciado, puntos, columnaIzquierda, columnaDerecha,
                    new HashMap<>(paresCreados));
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los puntos deben ser un número válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public PreguntaPareo getPreguntaEditada() {
        return preguntaEditada;
    }
}