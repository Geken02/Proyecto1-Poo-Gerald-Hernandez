package poo.proyecto1.evaluacion;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Pregunta {
    // Atributos comunes
    protected String descripcionPregunta;
    protected int cantidadPuntos;
    

    // Constructor
    public Pregunta(String pDescripcionPregunta, int pCantidadPuntos) {
        this.descripcionPregunta = pDescripcionPregunta;
        this.cantidadPuntos = pCantidadPuntos;
    }

    // Getters y Setters
    public String getDescripcionPregunta() {
        return descripcionPregunta;
    }

    public void setDescripcionPregunta(String descripcionPregunta) {
        this.descripcionPregunta = descripcionPregunta;
    }

    public int getCantidadPuntos() {
        return cantidadPuntos;
    }

    public void setCantidadPuntos(int cantidadPuntos) {
        this.cantidadPuntos = cantidadPuntos;
    }

    // Método abstracto que cada tipo de pregunta debe implementar
    public abstract String obtenerTipo();

    // toString() básico
    @Override
    public String toString() {
        return "Pregunta{" +
                "descripcion='" + descripcionPregunta + '\'' +
                ", puntos=" + cantidadPuntos +
                ", tipo='" + obtenerTipo() + '\'' +
                '}';
    }

    // equals y hashCode (por descripción)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pregunta pregunta = (Pregunta) o;
        return Objects.equals(descripcionPregunta, pregunta.descripcionPregunta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcionPregunta);
    }
}