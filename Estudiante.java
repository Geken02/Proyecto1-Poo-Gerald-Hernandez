// poo/proyecto1/usuarios/estudiante/Estudiante.java
package poo.proyecto1.persona.estudiante;

import poo.proyecto1.persona.Persona;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import poo.proyecto1.util.JsonUtils;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.grupo.Grupo;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * Clase que representa a un estudiante en el sistema de gestión académica.
 * Extiende de Persona y añade atributos específicos de estudiantes.
 * 
 * @author Ana
 * @since 1.0
 */
public class Estudiante extends Persona {
    private String orgLabora;
    private List<String> temasInteres;

    /**
     * Constructor por defecto necesario para Gson.
     */
    public Estudiante() {
        super();
        this.orgLabora = "";
        this.temasInteres = new ArrayList<>();
    }

    /**
     * Constructor principal que valida los atributos específicos del estudiante.
     * 
     * @param nombre Nombre del estudiante
     * @param pApellido1 Primer apellido
     * @param pApellido2 Segundo apellido
     * @param idPersonal Identificación personal
     * @param pTelefono Número de teléfono
     * @param pCorreoElectronico Correo electrónico
     * @param pDireccionFisica Dirección física
     * @param pContrasena Contraseña
     * @param pOrgLabora Organización donde labora (máximo 40 caracteres)
     * @param pTemasInteres Lista de temas de interés (cada tema 5-30 caracteres)
     * @throws IllegalArgumentException si algún parámetro no cumple con las validaciones
     */
    public Estudiante(String nombre, String pApellido1, String pApellido2,
                       String idPersonal, String pTelefono, String pCorreoElectronico,
                       String pDireccionFisica, String pContrasena,
                       String pOrgLabora, List<String> pTemasInteres) {
        super(nombre, pApellido1, pApellido2, idPersonal, pTelefono, pCorreoElectronico,
              pDireccionFisica, pContrasena);
        setOrgLabora(pOrgLabora);
        setTemasInteres(pTemasInteres);
    }

    // ========== MÉTODO DE VALIDACIÓN PRIVADO ==========

    /**
     * Valida un tema de interés.
     * 
     * @param tema Tema a validar
     * @return tema validado
     * @throws IllegalArgumentException si no cumple con las reglas
     */
    private String validarTemaInteres(String tema) {
        if (tema == null || tema.trim().isEmpty()) {
            throw new IllegalArgumentException("El tema de interés no puede estar vacío.");
        }
        String trimTema = tema.trim();
        if (trimTema.length() < 5) {
            throw new IllegalArgumentException("El tema de interés debe tener al menos 5 caracteres.");
        }
        if (trimTema.length() > 30) {
            throw new IllegalArgumentException("El tema de interés no puede exceder 30 caracteres.");
        }
        return trimTema;
    }

    // ========== GETTERS ==========

    /**
     * Obtiene la organización donde labora el estudiante.
     * 
     * @return organización laboral
     */
    public String getOrgLabora() {
        return orgLabora;
    }

    /**
     * Obtiene los temas de interés del estudiante.
     * 
     * @return copia de la lista de temas de interés
     */
    public List<String> getTemasInteres() {
        return new ArrayList<>(temasInteres);
    }

    // ========== SETTERS CON VALIDACIÓN ==========

    /**
     * Establece la organización donde labora el estudiante.
     * 
     * @param orgLabora Organización laboral (máximo 40 caracteres)
     */
    public void setOrgLabora(String orgLabora) {
        if (orgLabora == null) {
            this.orgLabora = "";
        } else {
            String trimOrg = orgLabora.trim();
            if (trimOrg.length() > 40) {
                throw new IllegalArgumentException("La organización laboral no puede exceder 40 caracteres.");
            }
            this.orgLabora = trimOrg;
        }
    }

    /**
     * Establece los temas de interés del estudiante.
     * 
     * @param temasInteres Lista de temas de interés (cada tema 5-30 caracteres)
     * @throws IllegalArgumentException si algún tema no cumple con las reglas
     */
    public void setTemasInteres(List<String> temasInteres) {
        if (temasInteres == null || temasInteres.isEmpty()) {
            this.temasInteres = new ArrayList<>();
        } else {
            this.temasInteres = new ArrayList<>();
            for (String tema : temasInteres) {
                this.temasInteres.add(validarTemaInteres(tema));
            }
        }
    }

    // ========== MÉTODOS ADICIONALES HEREDADOS ==========

    /**
     * Registra al estudiante en un curso específico.
     * 
     * @param idCurso ID del curso
     * @param idGrupo ID del grupo dentro del curso
     * @return true si la matrícula fue exitosa, false en caso contrario
     */
    public boolean matricularEnGrupo(String idCurso, int idGrupo) {
        // 1. Cargar todos los cursos desde JSON
        java.lang.reflect.Type tipoCursos = new com.google.gson.reflect.TypeToken<java.util.List<poo.proyecto1.curso.Curso>>(){}.getType();
        java.util.List<poo.proyecto1.curso.Curso> cursos;
        try {
            cursos = poo.proyecto1.util.JsonUtils.cargarLista("data/MC_cursos.json", tipoCursos);
        } catch (Exception e) {
            System.err.println("Error al cargar cursos.");
            return false;
        }

        // 2. Buscar el curso y el grupo
        poo.proyecto1.curso.Curso cursoDestino = null;
        poo.proyecto1.grupo.Grupo grupoDestino = null;
        for (poo.proyecto1.curso.Curso c : cursos) {
            if (c.getIdCurso().equals(idCurso)) {
                cursoDestino = c;
                grupoDestino = c.obtenerGrupo(idGrupo);
                break;
            }
        }

        if (cursoDestino == null || grupoDestino == null) {
            System.out.println("Curso o grupo no encontrado.");
            return false;
        }

        // 3. Validar capacidad
        if (grupoDestino.getEstudiantesMatriculados().size() >= cursoDestino.getCantMaxEstudiantes()) {
            System.out.println("Grupo lleno.");
            return false;
        }

        // 4. Validar que no esté ya matriculado
        if (grupoDestino.getEstudiantesMatriculados().contains(this.getIdentificacionPersonal())) {
            System.out.println("Ya estás matriculado en este grupo.");
            return false;
        }

        // 5. Matricular
        grupoDestino.matricularEstudiante(this.getIdentificacionPersonal());

        // 6. Guardar todos los cursos actualizados
        try {
            poo.proyecto1.util.JsonUtils.guardarLista(cursos, tipoCursos, "data/MC_cursos.json");
            System.out.println("Matrícula exitosa.");
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar los cursos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Muestra las evaluaciones asignadas al estudiante.
     */
    public void verEvaluacionesAsignadas() {
        System.out.println("Mostrando evaluaciones asignadas al estudiante " + getNombre());
        // Aquí iría lógica real de consulta de evaluaciones
    }

    /**
     * Muestra el desempeño personal del estudiante.
     */
    public void verDesempeñoPersonal() {
        System.out.println("Mostrando desempeño personal del estudiante " + getNombre());
        // Aquí podrías mostrar promedio, asistencia, etc.
    }

    // En poo.proyecto1/usuarios/estudiante/Estudiante.java



    /**
     * Obtiene el único grupo al que está matriculado el estudiante en un curso específico.
     * 
     * @param idCurso ID del curso
     * @return ID del grupo (int) o -1 si no está matriculado o hay múltiples grupos
     */
    public int obtenerGrupoUnicoDeCurso(String idCurso) {
        try {
            Type tipoCursos = new TypeToken<List<Curso>>(){}.getType();
            List<Curso> cursos = JsonUtils.cargarLista("MC_Cursos.json", tipoCursos);
            
            for (Curso curso : cursos) {
                if (curso.getIdCurso().equals(idCurso)) {
                    int grupoEncontrado = -1;
                    int contador = 0;
                    
                    for (Grupo grupo : curso.getGrupos()) {
                        if (grupo.getEstudiantesMatriculados().contains(this.getIdentificacionPersonal())) {
                            grupoEncontrado = grupo.getIdGrupo();
                            contador++;
                            if (contador > 1) {
                                // Más de un grupo encontrado
                                return -1;
                            }
                        }
                    }
                    return grupoEncontrado; // -1 si no está matriculado
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar grupo único: " + e.getMessage());
        }
        return -1; // Curso no encontrado
    }

    // ========== MÉTODOS SOBREESCRITOS ==========

    /**
     * Sobrescribe el método consultarInfo para incluir datos específicos del estudiante.
     */
    @Override
    public void consultarInfo() {
        super.consultarInfo();
        System.out.println("Organización Laboral: " + orgLabora);
        System.out.println("Temas de Interés: " + temasInteres);
        System.out.println("===============================");
    }

    /**
     * Sobrescribe el método mostrarMenu para mostrar el menú específico de estudiante.
     */
    @Override
    public void mostrarMenu(List<Persona> usuarios) {
        javax.swing.SwingUtilities.invokeLater(() -> 
            new poo.proyecto1.persona.estudiante.MenuEstudianteFrame(this, usuarios));
    }

    // ========== MÉTODOS OBJECT ==========

    @Override
    public String toString() {
        return "Estudiante{" +
                "orgLabora='" + orgLabora + '\'' +
                ", temasInteres=" + temasInteres +
                "} " + super.toString();
    }
}