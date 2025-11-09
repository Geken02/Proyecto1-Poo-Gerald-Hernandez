// poo/proyecto1/utils/RegistroEvaluaciones.java
package poo.proyecto1.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import poo.proyecto1.evaluacion.Evaluacion;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Gestiona el registro de todas las evaluaciones.
 * Se encarga de cargarlas desde un archivo JSON, generar IDs únicos
 * y guardar el estado completo del registro en `data/Evaluaciones.json`.
 */
public class RegistroEvaluaciones {

    private static final String NOMBRE_ARCHIVO = "MC_Evaluaciones.json";
    private final String rutaArchivo;
    private final Gson gson;
    private List<Evaluacion> evaluaciones;

    public RegistroEvaluaciones() {
        // La carpeta 'data' estará al mismo nivel que 'src'

        this.rutaArchivo = NOMBRE_ARCHIVO;

        // Configuración de Gson SIN registerTypeAdapterFactory
        // El manejo polimórfico se hace mediante @JsonAdapter en la clase Pregunta
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        this.evaluaciones = new ArrayList<>();
        cargarEvaluaciones();
    }

    /**
     * Carga la lista de evaluaciones desde el archivo JSON.
     * Si el archivo no existe, no hace nada y la lista permanecerá vacía.
     */
    private void cargarEvaluaciones() {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type listType = new TypeToken<ArrayList<Evaluacion>>() {
            }.getType();
            evaluaciones = gson.fromJson(reader, listType);
            if (evaluaciones == null) {
                evaluaciones = new ArrayList<>();
            }
            System.out.println("Se cargaron " + evaluaciones.size() + " evaluaciones desde " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de evaluaciones: " + e.getMessage());
            evaluaciones = new ArrayList<>();
        }
    }

    /**
     * Genera un nuevo ID único para una evaluación.
     * Encuentra el ID más alto en la lista actual y le suma 1.
     * 
     * @return El nuevo ID único.
     */
    public int generarNuevoId() {
        if (evaluaciones.isEmpty()) {
            return 1;
        }
        return evaluaciones.stream()
                .max(Comparator.comparingInt(Evaluacion::getIdEvaluacion))
                .map(Evaluacion::getIdEvaluacion)
                .orElse(0) + 1;
    }

    /**
     * Añade una nueva evaluación al registro y guarda el archivo completo.
     * 
     * @param evaluacion La evaluación a añadir.
     */
    public void addYGuardarEvaluacion(Evaluacion evaluacion) {
        evaluaciones.add(evaluacion);
        guardarEvaluaciones();
    }

    /**
     * Guarda toda la lista de evaluaciones en el archivo JSON.
     */
    private void guardarEvaluaciones() {

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(evaluaciones, writer);
            System.out.println("Evaluaciones guardadas en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de evaluaciones: " + e.getMessage());
        }
    }

    /**
     * Devuelve la lista de todas las evaluaciones cargadas.
     * 
     * @return Una lista de evaluaciones.
     */
    public List<Evaluacion> getListaEvaluaciones() {
        // Devuelve una copia para evitar modificación externa accidental de la lista
        // interna.
        return new ArrayList<>(evaluaciones);
    }

    /**
     * Elimina una evaluación por su ID y guarda los cambios.
     * 
     * @param idEvaluacion ID de la evaluación a eliminar
     * @return true si se eliminó exitosamente, false si no se encontró
     */
    public boolean eliminarEvaluacion(int idEvaluacion) {
        boolean eliminado = evaluaciones.removeIf(evaluacion -> evaluacion.getIdEvaluacion() == idEvaluacion);
        if (eliminado) {
            guardarEvaluaciones();
            System.out.println("Evaluación con ID " + idEvaluacion + " eliminada correctamente.");
        } else {
            System.err.println("No se encontró evaluación con ID " + idEvaluacion);
        }
        return eliminado;
    }

    public Gson getGson() {
        return gson;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }
}