package poo.proyecto1.vistas;

import poo.proyecto1.curso.Curso;
import javax.swing.*;
import java.awt.*;

public class VentanaEditarCurso extends JDialog {
    private JTextField idField, nombreField, descripcionField;
    private JTextField horasDiaField, minEstField, maxEstField, califMinField;
    private JTextField tipoCursoField;
    private JComboBox<String> modalidadCombo;
    private boolean confirmado = false;

    public VentanaEditarCurso(Frame parent, Curso curso) {
        super(parent, "Editar Curso", true);
        setLayout(new BorderLayout());

        idField = new JTextField(curso.getIdCurso());
        idField.setEditable(false);
        nombreField = new JTextField(curso.getNombreCurso());
        descripcionField = new JTextField(curso.getDescripcionCurso());
        horasDiaField = new JTextField(String.valueOf(curso.getCantHorasDia()));
        modalidadCombo = new JComboBox<>(new String[]{"Presencial", "Virtual", "Híbrido"});
        modalidadCombo.setSelectedItem(curso.getModalidad());
        minEstField = new JTextField(String.valueOf(curso.getCantMinEstudiantes()));
        maxEstField = new JTextField(String.valueOf(curso.getCantMaxEstudiantes()));
        califMinField = new JTextField(String.valueOf(curso.getCalificacionMinAprobar()));
        tipoCursoField = new JTextField(curso.getTipoCurso());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID (no editable):"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Descripción:"));
        formPanel.add(descripcionField);
        formPanel.add(new JLabel("Horas por día:"));
        formPanel.add(horasDiaField);
        formPanel.add(new JLabel("Modalidad:"));
        formPanel.add(modalidadCombo);
        formPanel.add(new JLabel("Mín. estudiantes:"));
        formPanel.add(minEstField);
        formPanel.add(new JLabel("Máx. estudiantes:"));
        formPanel.add(maxEstField);
        formPanel.add(new JLabel("Calif. mín. aprobatoria:"));
        formPanel.add(califMinField);
        formPanel.add(new JLabel("Tipo de curso:"));
        formPanel.add(tipoCursoField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton aceptarBtn = new JButton("Guardar Cambios");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            try {
                Integer.parseInt(horasDiaField.getText());
                Integer.parseInt(minEstField.getText());
                Integer.parseInt(maxEstField.getText());
                int calif = Integer.parseInt(califMinField.getText());
                if (calif < 0 || calif > 100) {
                    throw new NumberFormatException();
                }
                confirmado = true;
                setVisible(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelarBtn.addActionListener(e -> setVisible(false));

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmado() { return confirmado; }
    public String getIdCurso() { return idField.getText(); }
    public String getNombreCurso() { return nombreField.getText().trim(); }
    public String getDescripcionCurso() { return descripcionField.getText().trim(); }
    public int getCantHorasDia() { return Integer.parseInt(horasDiaField.getText()); }
    public String getModalidad() { return (String) modalidadCombo.getSelectedItem(); }
    public int getCantMinEstudiantes() { return Integer.parseInt(minEstField.getText()); }
    public int getCantMaxEstudiantes() { return Integer.parseInt(maxEstField.getText()); }
    public int getCalificacionMinAprobar() { return Integer.parseInt(califMinField.getText()); }
    public String getTipoCurso() { return tipoCursoField.getText().trim(); }
}