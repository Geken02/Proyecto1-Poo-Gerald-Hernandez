// poo/proyecto1/curso/Curso.java
package poo.proyecto1.curso;

import poo.proyecto1.grupo.Grupo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un curso en el sistema de gestión académica.
 * Contiene los atributos y métodos para gestionar la información de un curso.
 * 
 * @author Gerald
 * @since 1.0
 */
public class Curso {
    // Constantes para validaciones
    private static final int MIN_HORAS_DIA = 1;
    private static final int MAX_HORAS_DIA = 8;
    private static final int MIN_ESTUDIANTES = 1;
    private static final int MAX_ESTUDIANTES = 20;
    private static final int MIN_CALIFICACION = 0;
    private static final int MAX_CALIFICACION = 100;

    // Modalidades válidas
    private static final String[] MODALIDADES_VALIDAS = {
        "Presencial", 
        "Virtual sincrónico", 
        "Virtual asincrónico", 
        "Virtual híbrido", 
        "Semipresencial"
    };

    // Tipos de curso válidos
    private static final String[] TIPOS_CURSO_VALIDOS = {
        "Teórico", 
        "Práctico", 
        "Taller", 
        "Seminario"
    };

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

    private List<Grupo> grupos = new ArrayList<>();

    /**
     * Constructor por defecto necesario para Gson.
     */
    public Curso() {
        // Inicialización predeterminada
    }

    /**
     * Constructor principal que valida todos los parámetros según las reglas del sistema.
     * 
     * @param idCurso Identificador único del curso
     * @param nombreCurso Nombre del curso (5-40 caracteres)
     * @param descripcionCurso Descripción del curso (5-400 caracteres)
     * @param cantHorasDia Cantidad de horas por día (1-8)
     * @param cantHorasSemana Parámetro no utilizado (mantenido por compatibilidad)
     * @param modalidad Modalidad del curso (valores predefinidos)
     * @param cantMinEstudiantes Cantidad mínima de estudiantes (1-5)
     * @param cantMaxEstudiantes Cantidad máxima de estudiantes (mínimo ≤ máximo ≤ 20)
     * @param calificacionMinAprobar Calificación mínima para aprobar (0-100)
     * @param tipoCurso Tipo de curso (valores predefinidos)
     * @throws IllegalArgumentException si algún parámetro no cumple con las validaciones
     */
    public Curso(String idCurso, String nombreCurso, String descripcionCurso,
                 int cantHorasDia, String modalidad,
                 int cantMinEstudiantes, int cantMaxEstudiantes,
                 int calificacionMinAprobar, String tipoCurso) {
        setIdCurso(idCurso);
        setNombreCurso(nombreCurso);
        setDescripcionCurso(descripcionCurso);
        setCantHorasDia(cantHorasDia);
        setModalidad(modalidad);
        setCantMinEstudiantes(cantMinEstudiantes);
        setCantMaxEstudiantes(cantMaxEstudiantes);
        setCalificacionMinAprobar(calificacionMinAprobar);
        setTipoCurso(tipoCurso);
        this.grupos = new ArrayList<>();
    }

    // ========== MÉTODOS DE VALIDACIÓN PRIVADOS ==========

    /**
     * Valida que un string no sea nulo ni vacío y esté dentro del rango de longitud especificado.
     * 
     * @param valor El string a validar
     * @param nombreCampo Nombre del campo para mensajes de error
     * @param minLongitud Longitud mínima permitida
     * @param maxLongitud Longitud máxima permitida
     * @return el string validado
     * @throws IllegalArgumentException si la validación falla
     */
    private String validarString(String valor, String nombreCampo, int minLongitud, int maxLongitud) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío.");
        }
        String trimValor = valor.trim();
        if (trimValor.length() < minLongitud) {
            throw new IllegalArgumentException(nombreCampo + " debe tener al menos " + minLongitud + " caracteres.");
        }
        if (trimValor.length() > maxLongitud) {
            throw new IllegalArgumentException(nombreCampo + " no puede exceder " + maxLongitud + " caracteres.");
        }
        return trimValor;
    }

    /**
     * Verifica si un valor está en un array de valores permitidos.
     * 
     * @param valor Valor a verificar
     * @param valoresPermitidos Array de valores permitidos
     * @return true si el valor está permitido, false en caso contrario
     */
    private boolean esValorPermitido(String valor, String[] valoresPermitidos) {
        if (valor == null) return false;
        for (String permitido : valoresPermitidos) {
            if (permitido.equals(valor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene una cadena con los valores permitidos para mostrar en mensajes de error.
     * 
     * @param valores Array de valores permitidos
     * @return cadena con los valores separados por comas
     */
    private String obtenerValoresPermitidos(String[] valores) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valores.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append("'").append(valores[i]).append("'");
        }
        return sb.toString();
    }

    // ========== GETTERS ==========

    /**
     * Obtiene el identificador único del curso.
     * 
     * @return ID del curso
     */
    public String getIdCurso() {
        return idCurso;
    }

    /**
     * Obtiene el nombre del curso.
     * 
     * @return nombre del curso
     */
    public String getNombreCurso() {
        return nombreCurso;
    }

    /**
     * Obtiene la descripción del curso.
     * 
     * @return descripción del curso
     */
    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    /**
     * Obtiene la cantidad de horas por día del curso.
     * 
     * @return cantidad de horas por día
     */
    public int getCantHorasDia() {
        return cantHorasDia;
    }

    /**
     * Obtiene la modalidad del curso.
     * 
     * @return modalidad del curso
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * Obtiene la cantidad mínima de estudiantes para abrir un grupo.
     * 
     * @return cantidad mínima de estudiantes
     */
    public int getCantMinEstudiantes() {
        return cantMinEstudiantes;
    }

    /**
     * Obtiene la cantidad máxima de estudiantes permitida.
     * 
     * @return cantidad máxima de estudiantes
     */
    public int getCantMaxEstudiantes() {
        return cantMaxEstudiantes;
    }

    /**
     * Obtiene la calificación mínima para aprobar el curso.
     * 
     * @return calificación mínima para aprobar
     */
    public int getCalificacionMinAprobar() {
        return calificacionMinAprobar;
    }

    /**
     * Obtiene el tipo de curso.
     * 
     * @return tipo de curso
     */
    public String getTipoCurso() {
        return tipoCurso;
    }

    /**
     * Obtiene los grupos asociados al curso.
     * 
     * @return copia de la lista de grupos
     */
    public List<Grupo> getGrupos() {
        return new ArrayList<>(grupos);
    }

    // ========== SETTERS CON VALIDACIÓN ==========

    /**
     * Establece el identificador único del curso.
     * 
     * @param idCurso Identificador único del curso
     * @throws IllegalArgumentException si el ID está vacío
     */
    public void setIdCurso(String idCurso) {
        if (idCurso == null || idCurso.trim().isEmpty()) {
            throw new IllegalArgumentException("ID del curso no puede estar vacío.");
        }
        this.idCurso = idCurso.trim();
    }

    /**
     * Establece el nombre del curso.
     * 
     * @param nombreCurso Nombre del curso (5-40 caracteres)
     * @throws IllegalArgumentException si no cumple con las reglas
     */
    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = validarString(nombreCurso, "Nombre del curso", 5, 40);
    }

    /**
     * Establece la descripción del curso.
     * 
     * @param descripcionCurso Descripción del curso (5-400 caracteres)
     * @throws IllegalArgumentException si no cumple con las reglas
     */
    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = validarString(descripcionCurso, "Descripción del curso", 5, 400);
    }

    /**
     * Establece la cantidad de horas por día del curso.
     * 
     * @param cantHorasDia Cantidad de horas por día (1-8)
     * @throws IllegalArgumentException si no está en el rango permitido
     */
    public void setCantHorasDia(int cantHorasDia) {
        if (cantHorasDia < MIN_HORAS_DIA || cantHorasDia > MAX_HORAS_DIA) {
            throw new IllegalArgumentException("La cantidad de horas por día debe estar entre " + 
                MIN_HORAS_DIA + " y " + MAX_HORAS_DIA + ".");
        }
        this.cantHorasDia = cantHorasDia;
    }

    /**
     * Establece la modalidad del curso.
     * 
     * @param modalidad Modalidad del curso (valores predefinidos)
     * @throws IllegalArgumentException si no es un valor permitido
     */
    public void setModalidad(String modalidad) {
        if (!esValorPermitido(modalidad, MODALIDADES_VALIDAS)) {
            throw new IllegalArgumentException("Modalidad inválida. Valores permitidos: " + 
                obtenerValoresPermitidos(MODALIDADES_VALIDAS));
        }
        this.modalidad = modalidad;
    }

    /**
     * Establece la cantidad mínima de estudiantes para abrir un grupo.
     * 
     * @param cantMinEstudiantes Cantidad mínima de estudiantes (1-5)
     * @throws IllegalArgumentException si no está en el rango permitido
     */
    public void setCantMinEstudiantes(int cantMinEstudiantes) {
        if (cantMinEstudiantes < MIN_ESTUDIANTES || cantMinEstudiantes > 5) {
            throw new IllegalArgumentException("La cantidad mínima de estudiantes debe estar entre " + 
                MIN_ESTUDIANTES + " y 5.");
        }
        // Validar que no exceda la cantidad máxima actual
        if (cantMinEstudiantes > this.cantMaxEstudiantes) {
            throw new IllegalArgumentException("La cantidad mínima de estudiantes no puede ser mayor que la cantidad máxima (" + 
                this.cantMaxEstudiantes + ").");
        }
        this.cantMinEstudiantes = cantMinEstudiantes;
    }

    /**
     * Establece la cantidad máxima de estudiantes permitida.
     * 
     * @param cantMaxEstudiantes Cantidad máxima de estudiantes (mínimo ≤ máximo ≤ 20)
     * @throws IllegalArgumentException si no cumple con las reglas
     */
    public void setCantMaxEstudiantes(int cantMaxEstudiantes) {
        if (cantMaxEstudiantes < this.cantMinEstudiantes || cantMaxEstudiantes > MAX_ESTUDIANTES) {
            throw new IllegalArgumentException("La cantidad máxima de estudiantes debe estar entre " + 
                this.cantMinEstudiantes + " y " + MAX_ESTUDIANTES + ".");
        }
        this.cantMaxEstudiantes = cantMaxEstudiantes;
    }

    /**
     * Establece la calificación mínima para aprobar el curso.
     * 
     * @param calificacionMinAprobar Calificación mínima para aprobar (0-100)
     * @throws IllegalArgumentException si no está en el rango permitido
     */
    public void setCalificacionMinAprobar(int calificacionMinAprobar) {
        if (calificacionMinAprobar < MIN_CALIFICACION || calificacionMinAprobar > MAX_CALIFICACION) {
            throw new IllegalArgumentException("La calificación mínima para aprobar debe estar entre " + 
                MIN_CALIFICACION + " y " + MAX_CALIFICACION + ".");
        }
        this.calificacionMinAprobar = calificacionMinAprobar;
    }

    /**
     * Establece el tipo de curso.
     * 
     * @param tipoCurso Tipo de curso (valores predefinidos)
     * @throws IllegalArgumentException si no es un valor permitido
     */
    public void setTipoCurso(String tipoCurso) {
        if (!esValorPermitido(tipoCurso, TIPOS_CURSO_VALIDOS)) {
            throw new IllegalArgumentException("Tipo de curso inválido. Valores permitidos: " + 
                obtenerValoresPermitidos(TIPOS_CURSO_VALIDOS));
        }
        this.tipoCurso = tipoCurso;
    }

    // ========== MÉTODOS DE GESTIÓN ==========

    /**
     * Agrega un grupo al curso.
     * 
     * @param grupo Grupo a agregar
     */
    public void agregarGrupo(Grupo grupo) {
        if (grupo != null) {
            grupos.add(grupo);
        }
    }

    /**
     * Obtiene un grupo por su ID.
     * 
     * @param idGrupo ID del grupo a buscar
     * @return Grupo encontrado o null si no existe
     */
    public Grupo obtenerGrupo(int idGrupo) {
        return grupos.stream()
            .filter(g -> g.getIdGrupo() == idGrupo)
            .findFirst()
            .orElse(null);
    }

    // ========== MÉTODOS OBJECT ==========

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
                ", grupos=" + grupos.size() + " grupos" +
                '}';
    }
}