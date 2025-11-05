package poo.proyecto1.grupo;

import poo.proyecto1.persona.profesor.Profesor;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.persona.estudiante.Estudiante;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Grupo {
    // Atributos privados
    private int idGrupo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Relaciones (referencias)
    private Profesor profesorAsignado;
    private Curso cursoAsociado;
    private List<Estudiante> estudiantesMatriculados;

    // Constructor
    public Grupo(int idGrupo, LocalDate fechaInicio, LocalDate fechaFin,
                 Profesor profesor, Curso curso) {
        this.idGrupo = idGrupo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.profesorAsignado = profesor;
        this.cursoAsociado = curso;
        this.estudiantesMatriculados = new ArrayList<>();
    }

    // Getters
    public int getIdGrupo() {
        return idGrupo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Profesor getProfesorAsignado() {
        return profesorAsignado;
    }

    public Curso getCursoAsociado() {
        return cursoAsociado;
    }

    public List<Estudiante> getEstudiantesMatriculados() {
        return new ArrayList<>(estudiantesMatriculados); // Devuelve copia para inmutabilidad
    }

    // Setters
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setProfesorAsignado(Profesor profesorAsignado) {
        this.profesorAsignado = profesorAsignado;
    }

    public void setCursoAsociado(Curso cursoAsociado) {
        this.cursoAsociado = cursoAsociado;
    }

    // Métodos de gestión

    /**
     * Matricula un estudiante al grupo.
     * Verifica que no supere el límite máximo de estudiantes.
     */
    public boolean matricularEstudiante(Estudiante estudiante) {
        if (estudiantesMatriculados.size() < cursoAsociado.getCantMaxEstudiantes()) {
            estudiantesMatriculados.add(estudiante);
            System.out.println("Estudiante " + estudiante.getNombre() + " matriculado en el grupo " + idGrupo);
            return true;
        } else {
            System.out.println("No se puede matricular: grupo lleno (máximo " + cursoAsociado.getCantMaxEstudiantes() + " estudiantes).");
            return false;
        }
    }

    /**
     * Desmatricula un estudiante del grupo.
     */
    public boolean desmatricularEstudiante(Estudiante estudiante) {
        if (estudiantesMatriculados.remove(estudiante)) {
            System.out.println("Estudiante " + estudiante.getNombre() + " desmatriculado del grupo " + idGrupo);
            return true;
        } else {
            System.out.println("Estudiante no encontrado en este grupo.");
            return false;
        }
    }

    /**
     * Muestra información del grupo.
     */
    public void mostrarInfoGrupo() {
        System.out.println("=== Grupo " + idGrupo + " ===");
        System.out.println("Curso: " + cursoAsociado.getNombreCurso());
        System.out.println("Profesor: " + profesorAsignado.getNombre() + " " + profesorAsignado.getApellido1());
        System.out.println("Fechas: " + fechaInicio + " a " + fechaFin);
        System.out.println("Estudiantes matriculados: " + estudiantesMatriculados.size() + "/" + cursoAsociado.getCantMaxEstudiantes());
        System.out.println("------------------------------");
    }

    // toString()
    @Override
    public String toString() {
        return "Grupo{" +
                "idGrupo=" + idGrupo +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", profesorAsignado=" + (profesorAsignado != null ? profesorAsignado.getNombre() : "Sin asignar") +
                ", cursoAsociado=" + (cursoAsociado != null ? cursoAsociado.getNombreCurso() : "Sin asignar") +
                ", estudiantesMatriculados=" + estudiantesMatriculados.size() +
                '}';
    }

    // equals y hashCode (por ID)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grupo grupo = (Grupo) o;
        return idGrupo == grupo.idGrupo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGrupo);
    }
}