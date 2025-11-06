// poo/proyecto1/vistas/AsignarProfesorDialog.java
package poo.proyecto1.vistas;

import javax.swing.*;
import java.awt.*;

public class VentanaAsignarProfesor extends JDialog {
    private JTextField cursoIdField, grupoIdField, profesorIdField;
    private boolean confirmado = false;

    public VentanaAsignarProfesor(Frame parent) {
        super(parent, "Asignar Profesor a Grupo", true);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID del Curso:"));
        cursoIdField = new JTextField();
        formPanel.add(cursoIdField);

        formPanel.add(new JLabel("ID del Grupo:"));
        grupoIdField = new JTextField();
        formPanel.add(grupoIdField);

        formPanel.add(new JLabel("ID del Profesor:"));
        profesorIdField = new JTextField();
        formPanel.add(profesorIdField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Asignar");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            if (validar()) {
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

    private boolean validar() {
        if (cursoIdField.getText().trim().isEmpty() ||
            grupoIdField.getText().trim().isEmpty() ||
            profesorIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmado() { return confirmado; }
    public String getCursoId() { return cursoIdField.getText().trim(); }
    public int getGrupoId() { return Integer.parseInt(grupoIdField.getText().trim()); }
    public String getProfesorId() { return profesorIdField.getText().trim(); }
}