package poo.proyecto1.persona.estudiante;

import poo.proyecto1.persona.Persona;
import java.util.List;

import javax.swing.SwingUtilities;

import java.util.ArrayList;

public class Estudiante extends Persona {
    // Atributos adicionales
    private String orgLabora;
    private List<String> temasInteres;

    public Estudiante() {
        super(); // Llama al constructor por defecto de Persona (implícito si no existe)
        this.orgLabora = "";
        this.temasInteres = new ArrayList<>();
    }

    // Constructor
    public Estudiante(String nombre, String pApellido1, String pApellido2,
                       String idPersonal, String pTelefono, String pCorreoElectronico,
                       String pDireccionFisica, String pContrasena,
                       String pOrgLabora, List<String> pTemasInteres) {
        super(nombre, pApellido1, pApellido2, idPersonal, pTelefono, pCorreoElectronico,
              pDireccionFisica, pContrasena);
        this.orgLabora = pOrgLabora;
        this.temasInteres = pTemasInteres != null ? new ArrayList<>(pTemasInteres) : new ArrayList<>();
    }

    // Getter y Setter para orgLabora
    public String getOrgLabora() {
        return orgLabora;
    }

    public void setOrgLabora(String orgLabora) {
        this.orgLabora = orgLabora;
    }

    // Getter y Setter para temasInteres
    public List<String> getTemasInteres() {
        return new ArrayList<>(temasInteres); // Devuelve copia para evitar modificaciones externas
    }

    public void setTemasInteres(List<String> temasInteres) {
        this.temasInteres = temasInteres != null ? new ArrayList<>(temasInteres) : new ArrayList<>();
    }

    // Método toString()
    @Override
    public String toString() {
        return "Estudiante{" +
                "orgLabora='" + orgLabora + '\'' +
                ", temasInteres=" + temasInteres +
                "} " + super.toString();
    }

    // Métodos adicionales

    public void matricularCurso() {
        System.out.println("Estudiante " + getNombre() + " se ha matriculado en un curso.");
    }

    public void verEvaluacionesAsignadas() {
        System.out.println("Mostrando evaluaciones asignadas al estudiante " + getNombre());
        // Aquí iría lógica real de consulta de evaluaciones
    }

    public void verDesempeñoPersonal() {
        System.out.println("Mostrando desempeño personal del estudiante " + getNombre());
        // Aquí podrías mostrar promedio, asistencia, etc.
    }

    // Sobrescribimos métodos heredados si queremos personalizarlos
    @Override
    public void consultarInfo() {
        super.consultarInfo();
        System.out.println("Organización Laboral: " + orgLabora);
        System.out.println("Temas de Interés: " + temasInteres);
        System.out.println("===============================");
    }

    @Override
    public void mostrarMenu(List<Persona> usuarios) {
        SwingUtilities.invokeLater(() -> new MenuEstudianteFrame(this, usuarios));
    }
}