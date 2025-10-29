package poo.proyecto1.usuarios.profesor;

import poo.proyecto1.persona.Persona;
import java.util.List;
import java.util.ArrayList;

public class Profesor extends Persona {
    // Atributos adicionales
    private List<String> titulosObtenidos;
    private List<String> certificacionesEstudios;

    // Constructor
    public Profesor(String nombre, String pApellido1, String pApellido2,
                    String idPersonal, String pTelefono, String pCorreoElectronico,
                    String pDireccionFisica, String pContrasena,
                    List<String> pTitulosObtenidos, List<String> pCertificacionesEstudios) {
        super(nombre, pApellido1, pApellido2, idPersonal, pTelefono, pCorreoElectronico,
              pDireccionFisica, pContrasena);
        this.titulosObtenidos = pTitulosObtenidos != null ? new ArrayList<>(pTitulosObtenidos) : new ArrayList<>();
        this.certificacionesEstudios = pCertificacionesEstudios != null ? new ArrayList<>(pCertificacionesEstudios) : new ArrayList<>();
    }

    // Getters y Setters
    public List<String> getTitulosObtenidos() {
        return new ArrayList<>(titulosObtenidos); // Copia para inmutabilidad
    }

    public void setTitulosObtenidos(List<String> titulosObtenidos) {
        this.titulosObtenidos = titulosObtenidos != null ? new ArrayList<>(titulosObtenidos) : new ArrayList<>();
    }

    public List<String> getCertificacionesEstudios() {
        return new ArrayList<>(certificacionesEstudios); // Copia para inmutabilidad
    }

    public void setCertificacionesEstudios(List<String> certificacionesEstudios) {
        this.certificacionesEstudios = certificacionesEstudios != null ? new ArrayList<>(certificacionesEstudios) : new ArrayList<>();
    }

    // Método toString()
    @Override
    public String toString() {
        return "Profesor{" +
                "titulosObtenidos=" + titulosObtenidos +
                ", certificacionesEstudios=" + certificacionesEstudios +
                "} " + super.toString();
    }

    // Métodos adicionales

    public void agregarEvaluacion() {
        System.out.println("Profesor " + getNombre() + " ha agregado una nueva evaluación.");
    }

    public void verEvaluacion() {
        System.out.println("Profesor " + getNombre() + " está revisando evaluaciones.");
    }

    public void modificarEvaluacion() {
        System.out.println("Profesor " + getNombre() + " ha modificado una evaluación.");
    }

    public void eliminarEvaluacion() {
        System.out.println("Profesor " + getNombre() + " ha eliminado una evaluación.");
    }

    public void previsualizarEvaluacion() {
        System.out.println("Profesor " + getNombre() + " está previsualizando una evaluación.");
    }

    public void reporteEvaluacion() {
        System.out.println("Generando reporte de evaluaciones del profesor " + getNombre());
    }

    public void asociarEvaluacion() {
        System.out.println("Profesor " + getNombre() + " ha asociado una evaluación a un curso.");
    }

    public void desasociarEvaluacion() {
        System.out.println("Profesor " + getNombre() + " ha desasociado una evaluación de un curso.");
    }

    public void revisarEvaluacionesAsignadas() {
        System.out.println("Profesor " + getNombre() + " está revisando evaluaciones asignadas a sus estudiantes.");
    }

    public void revisarEvaluacionesRealizadas() {
        System.out.println("Profesor " + getNombre() + " está revisando evaluaciones ya realizadas.");
    }

    // Sobrescribimos consultarInfo para incluir datos específicos
    @Override
    public void consultarInfo() {
        super.consultarInfo();
        System.out.println("Títulos Obtenidos: " + titulosObtenidos);
        System.out.println("Certificaciones: " + certificacionesEstudios);
        System.out.println("===============================");
    }
}