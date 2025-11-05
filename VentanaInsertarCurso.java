// poo/proyecto1/vistas/InsertarCursoDialog.java
package poo.proyecto1.vistas;

import poo.proyecto1.curso.Curso;
import javax.swing.*;
import java.awt.*;

public class VentanaInsertarCurso extends JDialog {
    private JTextField idField, nombreField, descripcionField;
    private JTextField horasDiaField, minEstField, maxEstField, califMinField;
    private JComboBox<String> modalidadCombo, tipoCursoCombo;
    private boolean confirmado = false;

    public VentanaInsertarCurso(Frame parent) {
        super(parent, "Registrar Nuevo Curso", true);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID del Curso (único):"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Nombre del Curso:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        formPanel.add(descripcionField);

        formPanel.add(new JLabel("Horas por día:"));
        horasDiaField = new JTextField();
        formPanel.add(horasDiaField);

        formPanel.add(new JLabel("Modalidad:"));
        modalidadCombo = new JComboBox<>(new String[]{"Presencial", "Virtual Sincronico", "Virtual asincrónico", "Virtual híbrido", "Semipresencial"
});
        formPanel.add(modalidadCombo);

        formPanel.add(new JLabel("Mín. estudiantes:"));
        minEstField = new JTextField();
        formPanel.add(minEstField);

        formPanel.add(new JLabel("Máx. estudiantes:"));
        maxEstField = new JTextField();
        formPanel.add(maxEstField);

        formPanel.add(new JLabel("Calificación mín. para aprobar (0-100):"));
        califMinField = new JTextField();
        formPanel.add(califMinField);

        formPanel.add(new JLabel("Tipo de curso:"));
        tipoCursoCombo = new JComboBox<>(new String[]{"Teorico", "Practico", "Taller", "Seminario"});
        formPanel.add(tipoCursoCombo);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Aceptar");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            if (validarCampos()) {
                confirmado = true;
                setVisible(false);
            }
        });

        cancelarBtn.addActionListener(e -> setVisible(false));

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private boolean validarCampos() {
        try {
            if (idField.getText().trim().isEmpty() || nombreField.getText().trim().isEmpty()) {
                throw new Exception("ID y nombre son obligatorios.");
            }
            Integer.parseInt(horasDiaField.getText().trim());
            Integer.parseInt(minEstField.getText().trim());
            Integer.parseInt(maxEstField.getText().trim());
            int calif = Integer.parseInt(califMinField.getText().trim());
            if (calif < 0 || calif > 100) {
                throw new Exception("Calificación debe estar entre 0 y 100.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Curso getCurso() {
        if (!confirmado) return null;

        String id = idField.getText().trim();
        String nombre = nombreField.getText().trim();
        String descripcion = descripcionField.getText().trim();
        int horasDia = Integer.parseInt(horasDiaField.getText().trim());
        String modalidad = (String) modalidadCombo.getSelectedItem();
        int minEst = Integer.parseInt(minEstField.getText().trim());
        int maxEst = Integer.parseInt(maxEstField.getText().trim());
        int califMin = Integer.parseInt(califMinField.getText().trim());
        String tipo = (String) tipoCursoCombo.getSelectedItem();
    
        return new Curso(id, nombre, descripcion, horasDia, 0, modalidad, minEst, maxEst, califMin, tipo);
    }
}