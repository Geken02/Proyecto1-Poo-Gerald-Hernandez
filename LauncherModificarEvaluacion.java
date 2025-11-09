// poo/proyecto1/evaluacion/modificarEvaluacion/LauncherModificarEvaluacion.java
package poo.proyecto1.evaluacion.modificarEvaluacion;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada para la interfaz de modificación de evaluaciones.
 */
public class LauncherModificarEvaluacion {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModificarEvaluacionGUI().setVisible(true);
        });
    }
}