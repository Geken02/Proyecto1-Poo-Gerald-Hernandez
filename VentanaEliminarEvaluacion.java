
package poo.proyecto1.vistas;

import poo.proyecto1.evaluacion.Evaluacion;
import poo.proyecto1.util.RegistroEvaluaciones;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaEliminarEvaluacion extends JDialog {
    private boolean eliminadoExitosamente = false;

    public VentanaEliminarEvaluacion(Frame parent) {
        super(parent, "Eliminar Evaluación", true);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instrucciones
        JLabel instrucciones = new JLabel("Seleccione la evaluación que desea eliminar:");
        instrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(instrucciones, BorderLayout.NORTH);

        // Cargar evaluaciones
        RegistroEvaluaciones registro = new RegistroEvaluaciones();
        List<Evaluacion> evaluaciones = registro.getListaEvaluaciones();

        if (evaluaciones.isEmpty()) {
            JLabel mensaje = new JLabel("No hay evaluaciones disponibles para eliminar.");
            mensaje.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(mensaje, BorderLayout.CENTER);
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton cerrarBtn = new JButton("Cerrar");
            cerrarBtn.addActionListener(e -> dispose());
            buttonPanel.add(cerrarBtn);
            panel.add(buttonPanel, BorderLayout.SOUTH);
        } else {
            // Lista de evaluaciones
            DefaultComboBoxModel<Evaluacion> comboModel = new DefaultComboBoxModel<>();
            for (Evaluacion eval : evaluaciones) {
                comboModel.addElement(eval);
            }
            
            JComboBox<Evaluacion> evaluacionesCombo = new JComboBox<>(comboModel);
            
            JPanel formPanel = new JPanel(new FlowLayout());
            formPanel.add(new JLabel("Evaluación:"));
            formPanel.add(evaluacionesCombo);
            panel.add(formPanel, BorderLayout.CENTER);
            
            // Botones
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton eliminarBtn = new JButton("Eliminar");
            JButton cancelarBtn = new JButton("Cancelar");
            
            eliminarBtn.addActionListener(e -> {
                Evaluacion seleccionada = (Evaluacion) evaluacionesCombo.getSelectedItem();
                if (seleccionada != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar la evaluación '" + seleccionada.getNombreEvaluacion() + "'?\n" +
                        "Esta acción no se puede deshacer.",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        boolean exito = registro.eliminarEvaluacion(seleccionada.getIdEvaluacion());
                        if (exito) {
                            JOptionPane.showMessageDialog(
                                this,
                                "Evaluación eliminada exitosamente.",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            eliminadoExitosamente = true;
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(
                                this,
                                "Error al eliminar la evaluación.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
            });
            
            cancelarBtn.addActionListener(e -> dispose());
            
            buttonPanel.add(eliminarBtn);
            buttonPanel.add(cancelarBtn);
            panel.add(buttonPanel, BorderLayout.SOUTH);
        }

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
    
    public boolean isEliminadoExitosamente() {
        return eliminadoExitosamente;
    }
}