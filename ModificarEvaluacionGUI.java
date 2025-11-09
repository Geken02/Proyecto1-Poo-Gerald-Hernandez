// poo/proyecto1/evaluacion/modificarEvaluacion/ModificarEvaluacionGUI.java
package poo.proyecto1.evaluacion.modificarEvaluacion;

import poo.proyecto1.evaluacion.*;
import poo.proyecto1.evaluacion.creacionEvaluacion.PanelPregunta;
import poo.proyecto1.util.RegistroEvaluaciones;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ModificarEvaluacionGUI extends JFrame {
    private JComboBox<Evaluacion> comboEvaluaciones;
    private JTextField txtNombre;
    private JTextArea txtInstrucciones;
    private JTextField txtDuracion;
    private JCheckBox chkPreguntasAleatorias;
    private JCheckBox chkOpcionesAleatorias;
    private JPanel panelPreguntas;
    private List<Evaluacion> listaEvaluaciones;
    private RegistroEvaluaciones registro;

    public ModificarEvaluacionGUI() {
        registro = new RegistroEvaluaciones();
        initUI();
        cargarEvaluaciones();
    }

    private void initUI() {
        setTitle("Modificar Evaluación");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout(10, 10));

        // Panel superior: selección de evaluación
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSeleccion.add(new JLabel("Seleccionar evaluación:"));
        comboEvaluaciones = new JComboBox<>();
        comboEvaluaciones.addActionListener(e -> cargarEvaluacionSeleccionada());
        panelSeleccion.add(comboEvaluaciones);
        add(panelSeleccion, BorderLayout.NORTH);

        // Panel central: datos editables
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Evaluación"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        panelDatos.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelDatos.add(new JLabel("Instrucciones:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtInstrucciones = new JTextArea(3, 20);
        JScrollPane scrollInstrucciones = new JScrollPane(txtInstrucciones);
        panelDatos.add(scrollInstrucciones, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelDatos.add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDuracion = new JTextField(5);
        panelDatos.add(txtDuracion, gbc);

        // Checkbox: Preguntas en orden aleatorio
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        chkPreguntasAleatorias = new JCheckBox("Mezclar orden de preguntas");
        panelDatos.add(chkPreguntasAleatorias, gbc);

        // Checkbox: Opciones en orden aleatorio
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        chkOpcionesAleatorias = new JCheckBox("Mezclar orden de opciones");
        panelDatos.add(chkOpcionesAleatorias, gbc);

        add(panelDatos, BorderLayout.CENTER);

        // Panel de preguntas
        panelPreguntas = new JPanel();
        panelPreguntas.setLayout(new BoxLayout(panelPreguntas, BoxLayout.Y_AXIS));
        panelPreguntas.setBorder(BorderFactory.createTitledBorder("Preguntas"));
        JScrollPane scrollPreguntas = new JScrollPane(panelPreguntas);
        add(scrollPreguntas, BorderLayout.SOUTH);

        // Botón de guardar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ImageIcon iconoGuardar = new ImageIcon(getClass().getResource("/imagenes/Guardar.gif"));
        JButton btnGuardar = new JButton(iconoGuardar);
        btnGuardar.setToolTipText("Guardar cambios");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setFocusable(false);
        btnGuardar.addActionListener(e -> guardarCambios());
        panelBoton.add(btnGuardar);
        add(panelBoton, BorderLayout.EAST);
    }

    private void cargarEvaluaciones() {
        listaEvaluaciones = registro.getListaEvaluaciones();
        comboEvaluaciones.removeAllItems();

        if (listaEvaluaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay evaluaciones guardadas.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Evaluacion eval : listaEvaluaciones) {
            comboEvaluaciones.addItem(eval);
        }
    }

    private void cargarEvaluacionSeleccionada() {
        Evaluacion seleccionada = (Evaluacion) comboEvaluaciones.getSelectedItem();
        if (seleccionada == null)
            return;

        txtNombre.setText(seleccionada.getNombreEvaluacion());
        txtInstrucciones.setText(seleccionada.getInstruccionesGenerales());
        txtDuracion.setText(String.valueOf(seleccionada.getDuracionEvaluacion()));
        chkPreguntasAleatorias.setSelected(seleccionada.isPreguntasEnOrdenAleatorio());
        chkOpcionesAleatorias.setSelected(seleccionada.isOpcionesEnOrdenAleatorio());

        panelPreguntas.removeAll();
        for (Pregunta pregunta : seleccionada.getListaPreguntas()) {
            agregarPreguntaAlPanel(seleccionada, pregunta);
        }
        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    }

    private void agregarPreguntaAlPanel(Evaluacion evaluacion, Pregunta pregunta) {
        JPanel contenedorPregunta = new JPanel(new BorderLayout());
        contenedorPregunta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        PanelPregunta vistaPregunta = new PanelPregunta(pregunta);
        contenedorPregunta.add(vistaPregunta, BorderLayout.CENTER);

        // Panel para los botones (Editar y Eliminar)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        // Botón Editar
        ImageIcon iconoEditar = new ImageIcon(getClass().getResource("/imagenes/Editar.gif"));
        JButton btnEditar = new JButton(iconoEditar);
        btnEditar.setToolTipText("Editar pregunta");
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setFocusable(false);
        btnEditar.addActionListener(e -> editarPregunta(evaluacion, pregunta));
        panelBotones.add(btnEditar);

        // Botón Eliminar
        ImageIcon iconoPapelera = new ImageIcon(getClass().getResource("/imagenes/papelera.gif"));
        JButton btnEliminar = new JButton(iconoPapelera);
        btnEliminar.setToolTipText("Eliminar pregunta");
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusable(false);
        btnEliminar.setPreferredSize(new Dimension(24, 24));
        btnEliminar.addActionListener(e -> {
            panelPreguntas.remove(contenedorPregunta);
            evaluacion.getListaPreguntas().remove(pregunta);
            panelPreguntas.revalidate();
            panelPreguntas.repaint();
        });
        panelBotones.add(btnEliminar);

        contenedorPregunta.add(panelBotones, BorderLayout.EAST);
        panelPreguntas.add(contenedorPregunta);
    }

    private void editarPregunta(Evaluacion evaluacion, Pregunta pregunta) {
        if (pregunta instanceof PreguntaSeleccionUnica) {
            editarPreguntaSeleccionUnica(evaluacion, (PreguntaSeleccionUnica) pregunta);
        } else if (pregunta instanceof PreguntaSeleccionMultiple) {
            editarPreguntaSeleccionMultiple(evaluacion, (PreguntaSeleccionMultiple) pregunta);
        } else if (pregunta instanceof PreguntaFalsoVerdadero) {
            editarPreguntaFalsoVerdadero(evaluacion, (PreguntaFalsoVerdadero) pregunta);
        } else if (pregunta instanceof PreguntaPareo) {
            editarPreguntaPareo(evaluacion, (PreguntaPareo) pregunta);
        } else if (pregunta instanceof PreguntaSopaLetras) {
            editarPreguntaSopaLetras(evaluacion, (PreguntaSopaLetras) pregunta);
        } else {
            JOptionPane.showMessageDialog(this, "Tipo de pregunta no soportado para edición.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editarPreguntaSeleccionUnica(Evaluacion evaluacion, PreguntaSeleccionUnica preguntaActual) {
        DialogoEditarSeleccionUnica dialogo = new DialogoEditarSeleccionUnica(this, preguntaActual);
        dialogo.setVisible(true);
        PreguntaSeleccionUnica preguntaEditada = dialogo.getPreguntaEditada();
        if (preguntaEditada != null) {
            reemplazarPregunta(evaluacion, preguntaActual, preguntaEditada);
        }
    }

    private void editarPreguntaSeleccionMultiple(Evaluacion evaluacion, PreguntaSeleccionMultiple preguntaActual) {
        DialogoEditarSeleccionMultiple dialogo = new DialogoEditarSeleccionMultiple(this, preguntaActual);
        dialogo.setVisible(true);
        PreguntaSeleccionMultiple preguntaEditada = dialogo.getPreguntaEditada();
        if (preguntaEditada != null) {
            reemplazarPregunta(evaluacion, preguntaActual, preguntaEditada);
        }
    }

    private void editarPreguntaFalsoVerdadero(Evaluacion evaluacion, PreguntaFalsoVerdadero preguntaActual) {
        DialogoEditarFalsoVerdadero dialogo = new DialogoEditarFalsoVerdadero(this, preguntaActual);
        dialogo.setVisible(true);
        PreguntaFalsoVerdadero preguntaEditada = dialogo.getPreguntaEditada();
        if (preguntaEditada != null) {
            reemplazarPregunta(evaluacion, preguntaActual, preguntaEditada);
        }
    }

    private void editarPreguntaPareo(Evaluacion evaluacion, PreguntaPareo preguntaActual) {
        DialogoEditarPareo dialogo = new DialogoEditarPareo(this, preguntaActual);
        dialogo.setVisible(true);
        PreguntaPareo preguntaEditada = dialogo.getPreguntaEditada();
        if (preguntaEditada != null) {
            reemplazarPregunta(evaluacion, preguntaActual, preguntaEditada);
        }
    }

    private void editarPreguntaSopaLetras(Evaluacion evaluacion, PreguntaSopaLetras preguntaActual) {
        DialogoEditarSopaLetras dialogo = new DialogoEditarSopaLetras(this, preguntaActual);
        dialogo.setVisible(true);
        PreguntaSopaLetras preguntaEditada = dialogo.getPreguntaEditada();
        if (preguntaEditada != null) {
            reemplazarPregunta(evaluacion, preguntaActual, preguntaEditada);
        }
    }

    private void reemplazarPregunta(Evaluacion evaluacion, Pregunta preguntaAntigua, Pregunta preguntaNueva) {
        List<Pregunta> preguntas = evaluacion.getListaPreguntas();
        int indice = preguntas.indexOf(preguntaAntigua);
        if (indice >= 0) {
            preguntas.set(indice, preguntaNueva);
            panelPreguntas.removeAll();
            for (Pregunta p : evaluacion.getListaPreguntas()) {
                agregarPreguntaAlPanel(evaluacion, p);
            }
            panelPreguntas.revalidate();
            panelPreguntas.repaint();
        }
    }

    private void guardarCambios() {
        Evaluacion seleccionada = (Evaluacion) comboEvaluaciones.getSelectedItem();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "No hay evaluación seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();
            String instrucciones = txtInstrucciones.getText().trim();
            int duracion = Integer.parseInt(txtDuracion.getText().trim());

            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }

            Evaluacion evaluacionActualizada = new Evaluacion(
                    seleccionada.getIdEvaluacion(),
                    nombre,
                    instrucciones,
                    duracion,
                    chkPreguntasAleatorias.isSelected(),
                    chkOpcionesAleatorias.isSelected());

            // Copiar preguntas actuales
            for (Pregunta p : seleccionada.getListaPreguntas()) {
                evaluacionActualizada.agregarPregunta(p);
            }

            // Reemplazar en la lista
            for (int i = 0; i < listaEvaluaciones.size(); i++) {
                if (listaEvaluaciones.get(i).getIdEvaluacion() == seleccionada.getIdEvaluacion()) {
                    listaEvaluaciones.set(i, evaluacionActualizada);
                    break;
                }
            }

            // Guardar en archivo
            try (FileWriter writer = new FileWriter(registro.getRutaArchivo())) {
                registro.getGson().toJson(listaEvaluaciones, writer);
            }

            JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Recargar desde archivo para sincronizar
            RegistroEvaluaciones registroFresco = new RegistroEvaluaciones();
            this.listaEvaluaciones = registroFresco.getListaEvaluaciones();

            comboEvaluaciones.removeAllItems();
            Evaluacion seleccionar = null;
            for (Evaluacion e : listaEvaluaciones) {
                comboEvaluaciones.addItem(e);
                if (e.getIdEvaluacion() == seleccionada.getIdEvaluacion()) {
                    seleccionar = e;
                }
            }
            if (seleccionar != null) {
                comboEvaluaciones.setSelectedItem(seleccionar);
                cargarEvaluacionSeleccionada();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar los cambios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModificarEvaluacionGUI().setVisible(true);
        });
    }
}