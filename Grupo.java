package poo.proyecto1.grupo;

import poo.proyecto1.persona.profesor.Profesor;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.persona.estudiante.Estudiante;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Grupo {
    private int idGrupo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Relaciones (referencias)
    String profesorAsignado;
    private List<Estudiante> estudiantesMatriculados;

    // Constructor
    public Grupo(int idGrupo, LocalDate fechaInicio, LocalDate fechaFin,
                 String profesor) {
        this.idGrupo = idGrupo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.profesorAsignado = profesor;
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

    public String getProfesorAsignado() {
        return profesorAsignado;
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

    public void setProfesorAsignado(String profesorAsignado) {
        this.profesorAsignado = profesorAsignado;
    }


    // Métodos de gestión

    /**
     * Matricula un estudiante al grupo.
     * Verifica que no supere el límite máximo de estudiantes.
     */
  

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
   
    // toString()
    @Override
    public String toString() {
        return "Grupo{" +
                "idGrupo=" + idGrupo +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
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