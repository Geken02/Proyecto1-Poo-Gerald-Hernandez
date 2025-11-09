package poo.proyecto1.util;

import poo.proyecto1.evaluacion.Evaluacion;

import javax.swing.*;
import java.util.List;

/**
 * Servicio que maneja la lógica de alto nivel para guardar y cargar
 * evaluaciones,
 * interactuando con el usuario y utilizando el RegistroEvaluaciones.
 */
public class FileIOService {

    private final RegistroEvaluaciones registro;
    private final JFrame parentFrame;

    public FileIOService(JFrame parentFrame) {
        this.registro = new RegistroEvaluaciones();
        this.parentFrame = parentFrame;
    }

    /**
     * Obtiene el próximo ID único disponible para una nueva evaluación.
     * 
     * @return El nuevo ID.
     */
    public int obtenerProximoId() {
        return registro.generarNuevoId();
    }

    /**
     * Guarda la evaluación actual en el registro centralizado.
     * 
     * @param evaluacion La evaluación a guardar.
     */
    public void guardarNuevaEvaluacion(Evaluacion evaluacion) {
        if (evaluacion == null) {
            JOptionPane.showMessageDialog(parentFrame, "No hay ninguna evaluación para guardar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        registro.addYGuardarEvaluacion(evaluacion);
        JOptionPane.showMessageDialog(parentFrame, "Evaluación '" + evaluacion.getNombreEvaluacion()
                + "' guardada con éxito (ID: " + evaluacion.getIdEvaluacion() + ").", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Carga la última evaluación guardada en el registro.
     * Por simplicidad, carga la más reciente. Una versión más avanzada
     * podría mostrar un diálogo para seleccionar cuál cargar.
     * 
     * @return La última evaluación cargada, o null si no hay ninguna.
     */
    public Evaluacion cargarUltimaEvaluacion() {
        List<Evaluacion> lista = registro.getListaEvaluaciones();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No hay ninguna evaluación guardada para cargar.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        // Por simplicidad, devolvemos la última de la lista.
        // Una implementación mejor podría ordenar por fecha de modificación.
        Evaluacion ultimaEvaluacion = lista.get(lista.size() - 1);
        JOptionPane.showMessageDialog(parentFrame,
                "Cargando la última evaluación guardada: '" + ultimaEvaluacion.getNombreEvaluacion() + "'",
                "Carga Exitosa", JOptionPane.INFORMATION_MESSAGE);
        return ultimaEvaluacion;
    }
}