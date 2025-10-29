package poo.proyecto1.evaluacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Evaluacion {
    // Atributos privados
    private int idEvaluacion;
    private String nombreEvaluacion;
    private String instruccionesGenerales;
    private List<String> objetivosEvaluacion;
    private int duracionEvaluacion; // en minutos
    private boolean ordenAleatorioPreguntas; // mejor usar boolean que int
    private boolean ordenAleatorioRespuestas; // mejor usar boolean que int
    private List<Pregunta> listaPreguntas;

    // Constructor
    public Evaluacion(int idEvaluacion, String nombreEvaluacion,
                      String instruccionesGenerales, List<String> objetivosEvaluacion,
                      int duracionEvaluacion, boolean ordenAleatorioPreguntas,
                      boolean ordenAleatorioRespuestas, List<Pregunta> listaPreguntas) {
        this.idEvaluacion = idEvaluacion;
        this.nombreEvaluacion = nombreEvaluacion;
        this.instruccionesGenerales = instruccionesGenerales;
        this.objetivosEvaluacion = objetivosEvaluacion != null ? new ArrayList<>(objetivosEvaluacion) : new ArrayList<>();
        this.duracionEvaluacion = duracionEvaluacion;
        this.ordenAleatorioPreguntas = ordenAleatorioPreguntas;
        this.ordenAleatorioRespuestas = ordenAleatorioRespuestas;
        this.listaPreguntas = listaPreguntas != null ? new ArrayList<>(listaPreguntas) : new ArrayList<>();
    }

    // Getters
    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public String getNombreEvaluacion() {
        return nombreEvaluacion;
    }

    public String getInstruccionesGenerales() {
        return instruccionesGenerales;
    }

    public List<String> getObjetivosEvaluacion() {
        return new ArrayList<>(objetivosEvaluacion);
    }

    public int getDuracionEvaluacion() {
        return duracionEvaluacion;
    }

    public boolean isOrdenAleatorioPreguntas() {
        return ordenAleatorioPreguntas;
    }

    public boolean isOrdenAleatorioRespuestas() {
        return ordenAleatorioRespuestas;
    }

    public List<Pregunta> getListaPreguntas() {
        return new ArrayList<>(listaPreguntas);
    }

    // Setters
    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public void setNombreEvaluacion(String nombreEvaluacion) {
        this.nombreEvaluacion = nombreEvaluacion;
    }

    public void setInstruccionesGenerales(String instruccionesGenerales) {
        this.instruccionesGenerales = instruccionesGenerales;
    }

    public void setObjetivosEvaluacion(List<String> objetivosEvaluacion) {
        this.objetivosEvaluacion = objetivosEvaluacion != null ? new ArrayList<>(objetivosEvaluacion) : new ArrayList<>();
    }

    public void setDuracionEvaluacion(int duracionEvaluacion) {
        this.duracionEvaluacion = duracionEvaluacion;
    }

    public void setOrdenAleatorioPreguntas(boolean ordenAleatorioPreguntas) {
        this.ordenAleatorioPreguntas = ordenAleatorioPreguntas;
    }

    public void setOrdenAleatorioRespuestas(boolean ordenAleatorioRespuestas) {
        this.ordenAleatorioRespuestas = ordenAleatorioRespuestas;
    }

    // Métodos adicionales

    /**
     * Agrega una pregunta a la evaluación.
     */
    public void agregarPregunta(Pregunta pregunta) {
        if (pregunta != null) {
            listaPreguntas.add(pregunta);
            System.out.println("✅ Pregunta agregada a la evaluación: " + nombreEvaluacion);
        } else {
            System.out.println("❌ No se puede agregar una pregunta nula.");
        }
    }

    /**
     * Muestra información básica de la evaluación.
     */
    public void mostrarInfo() {
        System.out.println("=== Evaluación: " + nombreEvaluacion + " ===");
        System.out.println("ID: " + idEvaluacion);
        System.out.println("Duración: " + duracionEvaluacion + " minutos");
        System.out.println("Instrucciones: " + instruccionesGenerales);
        System.out.println("Objetivos: " + objetivosEvaluacion);
        System.out.println("Preguntas: " + listaPreguntas.size());
        System.out.println("Orden aleatorio preguntas: " + ordenAleatorioPreguntas);
        System.out.println("Orden aleatorio respuestas: " + ordenAleatorioRespuestas);
        System.out.println("------------------------------");
    }

    // toString()
    @Override
    public String toString() {
        return "Evaluacion{" +
                "idEvaluacion=" + idEvaluacion +
                ", nombreEvaluacion='" + nombreEvaluacion + '\'' +
                ", instruccionesGenerales='" + instruccionesGenerales + '\'' +
                ", objetivosEvaluacion=" + objetivosEvaluacion +
                ", duracionEvaluacion=" + duracionEvaluacion +
                ", ordenAleatorioPreguntas=" + ordenAleatorioPreguntas +
                ", ordenAleatorioRespuestas=" + ordenAleatorioRespuestas +
                ", listaPreguntas=" + listaPreguntas.size() +
                '}';
    }

    // equals y hashCode (por ID)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evaluacion that = (Evaluacion) o;
        return idEvaluacion == that.idEvaluacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvaluacion);
    }
}