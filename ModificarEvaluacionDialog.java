// poo/proyecto1/evaluacion/modificarEvaluacion/ModificarEvaluacionDialog.java
package poo.proyecto1.evaluacion.modificarEvaluacion;

import javax.swing.*;

public class ModificarEvaluacionDialog extends JDialog {
    public ModificarEvaluacionDialog(JFrame owner) {
        super(owner, "Modificar Evaluación", true); // true = modal
        ModificarEvaluacionGUI contenido = new ModificarEvaluacionGUI();
        setContentPane(contenido.getContentPane());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(owner);
    }
}