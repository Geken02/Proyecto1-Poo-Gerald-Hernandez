// poo/proyecto1/usuarios/profesor/Profesor.java
package poo.proyecto1.persona.profesor;

import poo.proyecto1.persona.Persona;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import poo.proyecto1.admin.Administrador;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.grupo.Grupo;
import java.time.LocalDateTime;
import poo.proyecto1.util.JsonUtils;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


/**
 * Clase que representa a un profesor en el sistema de gestión académica.
 * Extiende de Persona y añade atributos específicos de profesores.
 * 
 * @author Ana
 * @since 1.0
 */
public class Profesor extends Persona {
    // Atributos adicionales
    private List<String> titulosObtenidos;
    private List<String> certificacionesEstudios;

    /**
     * Constructor por defecto necesario para Gson.
     */
    public Profesor() {
        super();
        this.titulosObtenidos = new ArrayList<>();
        this.certificacionesEstudios = new ArrayList<>();
    }

    /**
     * Constructor principal que valida los atributos específicos del profesor.
     * 
     * @param nombre Nombre del profesor
     * @param pApellido1 Primer apellido
     * @param pApellido2 Segundo apellido
     * @param idPersonal Identificación personal
     * @param pTelefono Número de teléfono
     * @param pCorreoElectronico Correo electrónico
     * @param pDireccionFisica Dirección física
     * @param pContrasena Contraseña
     * @param pTitulosObtenidos Lista de títulos (cada título 5-40 caracteres)
     * @param pCertificacionesEstudios Lista de certificaciones (cada certificación 5-40 caracteres)
     * @throws IllegalArgumentException si algún parámetro no cumple con las validaciones
     */
    public Profesor(String nombre, String pApellido1, String pApellido2,
                    String idPersonal, String pTelefono, String pCorreoElectronico,
                    String pDireccionFisica, String pContrasena,
                    List<String> pTitulosObtenidos, List<String> pCertificacionesEstudios) {
        super(nombre, pApellido1, pApellido2, idPersonal, pTelefono, pCorreoElectronico,
              pDireccionFisica, pContrasena);
        setTitulosObtenidos(pTitulosObtenidos);
        setCertificacionesEstudios(pCertificacionesEstudios);
    }

    // ========== MÉTODO DE VALIDACIÓN PRIVADO ==========

    /**
     * Valida un título o certificación.
     * 
     * @param texto Texto a validar (título o certificación)
     * @param tipo Tipo de texto ("Título" o "Certificación")
     * @return texto validado
     * @throws IllegalArgumentException si no cumple con las reglas
     */
    private String validarTituloOCertificacion(String texto, String tipo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(tipo + " no puede estar vacío.");
        }
        String trimTexto = texto.trim();
        if (trimTexto.length() < 5) {
            throw new IllegalArgumentException(tipo + " debe tener al menos 5 caracteres.");
        }
        if (trimTexto.length() > 40) {
            throw new IllegalArgumentException(tipo + " no puede exceder 40 caracteres.");
        }
        return trimTexto;
    }


    /**
     * Obtiene todos los cursos desde el archivo JSON.
     * 
     * @return Lista de cursos o lista vacía si hay error
     */
    public List<Curso> obtenerCursosDesdeJSON() {
        try {
            Type tipoCursos = new TypeToken<List<Curso>>(){}.getType();
            List<Curso> cursos = JsonUtils.cargarLista("MC_cursos.json", tipoCursos);
            return cursos != null ? cursos : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar cursos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista de cursos en el archivo JSON.
     * 
     * @param cursos Lista de cursos a guardar
     */
    private void guardarCursosEnJSON(List<Curso> cursos) {
        try {
            Type tipoCursos = new TypeToken<List<Curso>>(){}.getType();
            JsonUtils.guardarLista(cursos, tipoCursos, "MC_cursos.json");
        } catch (Exception e) {
            System.err.println("Error al guardar cursos: " + e.getMessage());
        }
    }

    /**
     * Obtiene un curso específico por su ID.
     * 
     * @param idCurso ID del curso
     * @return Curso encontrado o null si no existe
     */
    private Curso obtenerCursoPorId(String idCurso) {
        List<Curso> cursos = obtenerCursosDesdeJSON();
        for (Curso curso : cursos) {
            if (curso.getIdCurso().equals(idCurso)) {
                return curso;
            }
        }
        return null;
    }

    /**
     * Obtiene un grupo específico de un curso.
     * 
     * @param idCurso ID del curso
     * @param idGrupo ID del grupo
     * @return Grupo encontrado o null si no existe
     */
    private Grupo obtenerGrupoDeCurso(String idCurso, int idGrupo) {
        Curso curso = obtenerCursoPorId(idCurso);
        return curso != null ? curso.obtenerGrupo(idGrupo) : null;
    }

    // ========== GETTERS ==========

    /**
     * Obtiene los títulos obtenidos por el profesor.
     * 
     * @return copia de la lista de títulos
     */
    public List<String> getTitulosObtenidos() {
        return new ArrayList<>(titulosObtenidos);
    }

    /**
     * Obtiene las certificaciones de estudios del profesor.
     * 
     * @return copia de la lista de certificaciones
     */
    public List<String> getCertificacionesEstudios() {
        return new ArrayList<>(certificacionesEstudios);
    }

    // ========== SETTERS CON VALIDACIÓN ==========

    /**
     * Establece los títulos obtenidos por el profesor.
     * 
     * @param titulosObtenidos Lista de títulos (cada título 5-40 caracteres)
     * @throws IllegalArgumentException si algún título no cumple con las reglas
     */
    public void setTitulosObtenidos(List<String> titulosObtenidos) {
        if (titulosObtenidos == null || titulosObtenidos.isEmpty()) {
            this.titulosObtenidos = new ArrayList<>();
        } else {
            this.titulosObtenidos = new ArrayList<>();
            for (int i = 0; i < titulosObtenidos.size(); i++) {
                String titulo = titulosObtenidos.get(i);
                this.titulosObtenidos.add(validarTituloOCertificacion(titulo, "Título " + (i + 1)));
            }
        }
    }

    /**
     * Establece las certificaciones de estudios del profesor.
     * 
     * @param certificacionesEstudios Lista de certificaciones (cada certificación 5-40 caracteres)
     * @throws IllegalArgumentException si alguna certificación no cumple con las reglas
     */
    public void setCertificacionesEstudios(List<String> certificacionesEstudios) {
        if (certificacionesEstudios == null || certificacionesEstudios.isEmpty()) {
            this.certificacionesEstudios = new ArrayList<>();
        } else {
            this.certificacionesEstudios = new ArrayList<>();
            for (int i = 0; i < certificacionesEstudios.size(); i++) {
                String certificacion = certificacionesEstudios.get(i);
                this.certificacionesEstudios.add(validarTituloOCertificacion(certificacion, "Certificación " + (i + 1)));
            }
        }
    }


    /**
     * Previsualiza una evaluación.
     */
    public void previsualizarEvaluacion() {
        System.out.println("Profesor " + getNombre() + " está previsualizando una evaluación.");
    }

    /**
     * Genera un reporte de evaluaciones.
     */
    public void reporteEvaluacion() {
        System.out.println("Generando reporte de evaluaciones del profesor " + getNombre());
    }

    /**
     * Asocia una evaluación a un grupo específico.
     */
    public void asociarEvaluacion(String idCurso, int idGrupo, int idEvaluacion, LocalDateTime horaInicio) {
        // Validar entrada
        if (idEvaluacion <= 0 || horaInicio == null) {
            System.out.println("Datos inválidos para asociar evaluación.");
            return;
        }

        // Obtener cursos actuales
        List<Curso> cursos = obtenerCursosDesdeJSON();
        boolean cursoEncontrado = false;
        boolean grupoEncontrado = false;

        // Buscar y actualizar el grupo específico
        for (Curso curso : cursos) {
            if (curso.getIdCurso().equals(idCurso)) {
                cursoEncontrado = true;
                Grupo grupo = curso.obtenerGrupo(idGrupo);
                if (grupo != null) {
                    grupoEncontrado = true;
                    if (grupo.asociarEvaluacion(idEvaluacion, horaInicio)) {
                        // Guardar los cambios
                        guardarCursosEnJSON(cursos);
                        System.out.println("Profesor " + getNombre() + " ha asociado la evaluación " + 
                                        idEvaluacion + " al grupo " + idGrupo + " del curso " + idCurso);
                        return;
                    } else {
                        System.out.println("Error al asociar la evaluación al grupo.");
                        return;
                    }
                }
                break;
            }
        }

        if (!cursoEncontrado) {
            System.out.println("Curso con ID " + idCurso + " no encontrado.");
        } else if (!grupoEncontrado) {
            System.out.println("Grupo con ID " + idGrupo + " no encontrado en el curso " + idCurso + ".");
        }
    }

    /**
     * Desasocia una evaluación de un grupo específico.
     */
    public void desasociarEvaluacion(String idCurso, int idGrupo, int idEvaluacion) {
        // Obtener cursos actuales
        List<Curso> cursos = obtenerCursosDesdeJSON();
        boolean cursoEncontrado = false;
        boolean grupoEncontrado = false;

        for (Curso curso : cursos) {
            if (curso.getIdCurso().equals(idCurso)) {
                cursoEncontrado = true;
                Grupo grupo = curso.obtenerGrupo(idGrupo);
                if (grupo != null) {
                    grupoEncontrado = true;
                    if (grupo.desasociarEvaluacion(idEvaluacion)) {
                        guardarCursosEnJSON(cursos);
                        System.out.println("Profesor " + getNombre() + " ha desasociado la evaluación " + 
                                        idEvaluacion + " del grupo " + idGrupo + " del curso " + idCurso);
                        return;
                    } else {
                        System.out.println("No se puede desasociar la evaluación: aún no ha comenzado o no existe.");
                        return;
                    }
                }
                break;
            }
        }

        if (!cursoEncontrado) {
            System.out.println("Curso con ID " + idCurso + " no encontrado.");
        } else if (!grupoEncontrado) {
            System.out.println("Grupo con ID " + idGrupo + " no encontrado en el curso " + idCurso + ".");
        }
    }
    /**
     * Revisa evaluaciones asignadas a sus estudiantes.
     */
    public void revisarEvaluacionesAsignadas() {
        System.out.println("Profesor " + getNombre() + " está revisando evaluaciones asignadas a sus estudiantes.");
    }

    /**
     * Revisa evaluaciones ya realizadas.
     */
    public void revisarEvaluacionesRealizadas() {
        System.out.println("Profesor " + getNombre() + " está revisando evaluaciones ya realizadas.");
    }

    /**
     * Abre la interfaz gráfica para crear evaluaciones.
     * 
     * @param parentFrame Frame padre para la ventana modal
     */
    public void mostrarMenuEvaluaciones(JFrame parentFrame) {
        SwingUtilities.invokeLater(() -> {
            new poo.proyecto1.evaluacion.creacionEvaluacion.MenuEvaluacionesGUI().setVisible(true);
        });
    }

    /**
     * Obtiene las evaluaciones asociadas a un grupo específico.
     * 
     * @param idCurso ID del curso
     * @param idGrupo ID del grupo
     * @return Map con evaluaciones (ID -> hora de inicio) o null si no existe el grupo
     */
    public java.util.Map<Integer, LocalDateTime> obtenerEvaluacionesDelGrupo(String idCurso, int idGrupo) {
        Grupo grupo = obtenerGrupoDeCurso(idCurso, idGrupo);
        return grupo != null ? grupo.getEvaluacionesAsociadas() : null;
    }

    /**
     * Sobrescribe el método consultarInfo para incluir datos específicos del profesor.
     */
    @Override
    public void consultarInfo() {
        super.consultarInfo();
        System.out.println("Títulos Obtenidos: " + titulosObtenidos);
        System.out.println("Certificaciones: " + certificacionesEstudios);
        System.out.println("===============================");
    }

    /**
     * Sobrescribe el método mostrarMenu para mostrar el menú específico de profesor.
     */
    @Override
    public void mostrarMenu(List<Persona> usuarios) {
        javax.swing.SwingUtilities.invokeLater(() -> 
            new poo.proyecto1.persona.profesor.MenuProfesorFrame(this, usuarios));
    }

    // ========== MÉTODOS OBJECT ==========

    @Override
    public String toString() {
        return "Profesor{" +
                "titulosObtenidos=" + titulosObtenidos +
                ", certificacionesEstudios=" + certificacionesEstudios +
                "} " + super.toString();
    }
}