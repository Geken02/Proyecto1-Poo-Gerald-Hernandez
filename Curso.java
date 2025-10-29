package poo.proyecto1.curso;

import java.util.Objects;

public class Curso {
    // Atributos privados
    private String idCurso;
    private String nombreCurso;
    private String descripcionCurso;
    private int cantHorasDia;
    private String modalidad;
    private int cantMinEstudiantes;
    private int cantMaxEstudiantes;
    private int calificacionMinAprobar;
    private String tipoCurso;

    // Constructor
    public Curso(String idCurso, String nombreCurso, String descripcionCurso,
                 int cantHorasDia, int cantHorasSemana, String modalidad,
                 int cantMinEstudiantes, int cantMaxEstudiantes,
                 int calificacionMinAprobar, String tipoCurso) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.descripcionCurso = descripcionCurso;
        this.cantHorasDia = cantHorasDia;
        this.modalidad = modalidad;
        this.cantMinEstudiantes = cantMinEstudiantes;
        this.cantMaxEstudiantes = cantMaxEstudiantes;
        this.calificacionMinAprobar = calificacionMinAprobar;
        this.tipoCurso = tipoCurso;
    }

    // Getters
    public String getIdCurso() {
        return idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    public int getCantHorasDia() {
        return cantHorasDia;
    }

    public String getModalidad() {
        return modalidad;
    }

    public int getCantMinEstudiantes() {
        return cantMinEstudiantes;
    }

    public int getCantMaxEstudiantes() {
        return cantMaxEstudiantes;
    }

    public int getCalificacionMinAprobar() {
        return calificacionMinAprobar;
    }

    public String getTipoCurso() {
        return tipoCurso;
    }

    // Setters
    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = descripcionCurso;
    }

    public void setCantHorasDia(int cantHorasDia) {
        this.cantHorasDia = cantHorasDia;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setCantMinEstudiantes(int cantMinEstudiantes) {
        this.cantMinEstudiantes = cantMinEstudiantes;
    }

    public void setCantMaxEstudiantes(int cantMaxEstudiantes) {
        this.cantMaxEstudiantes = cantMaxEstudiantes;
    }

    public void setCalificacionMinAprobar(int calificacionMinAprobar) {
        this.calificacionMinAprobar = calificacionMinAprobar;
    }

    public void setTipoCurso(String tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    // toString()
    @Override
    public String toString() {
        return "Curso{" +
                "idCurso='" + idCurso + '\'' +
                ", nombreCurso='" + nombreCurso + '\'' +
                ", descripcionCurso='" + descripcionCurso + '\'' +
                ", cantHorasDia=" + cantHorasDia +
                ", modalidad='" + modalidad + '\'' +
                ", cantMinEstudiantes=" + cantMinEstudiantes +
                ", cantMaxEstudiantes=" + cantMaxEstudiantes +
                ", calificacionMinAprobar=" + calificacionMinAprobar +
                ", tipoCurso='" + tipoCurso + '\'' +
                '}';
    }

    // equals y hashCode (opcional, útil si vas a usar en colecciones)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return Objects.equals(idCurso, curso.idCurso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCurso);
    }
}