package poo.proyecto1.admin;

import poo.proyecto1.persona.Persona;
import poo.proyecto1.persona.estudiante.Estudiante;
import poo.proyecto1.persona.profesor.Profesor;
import poo.proyecto1.util.JsonUtils;
import poo.proyecto1.curso.Curso;
import poo.proyecto1.grupo.Grupo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.swing.SwingUtilities;

import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import java.time.LocalDate;

public class Administrador {
    // Constantes para autenticación (credenciales fijas del admin)
    public static final String ID_ADMIN = "admin";
    public static final String CONTRASENA_ADMIN = "12345";

    // Atributos privados (si necesitas almacenar datos temporales, pero en este caso no se requieren según el UML)
    private String idAdmin;  // Podría ser útil si permites cambiar el ID, pero por ahora usamos constante
    private String contrasenaAdmin; // Idem

  
    private static final String RUTA_USUARIOS = "MC_usuarios.json";
    private List<Persona> listaUsuarios;

    
    // Carga la lista al iniciar
   private List<Persona> cargarUsuarios() {
    try {
        return JsonUtils.cargarListaPersonas(RUTA_USUARIOS);
    } catch (Exception e) {
        System.err.println("Error al cargar usuarios. Se iniciará con lista vacía.");
        e.printStackTrace();
        return new ArrayList<>();
    }
}

    // Guarda la lista actual en el archivo
    private void guardarUsuarios() {
        try {
            JsonUtils.guardarListaPersonas(listaUsuarios, RUTA_USUARIOS);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    // ===== MÉTODOS DE REGISTRO =====
    public void registrarEstudiante(
        String nombre, String apellido1, String apellido2,
        String idPersonal, String telefono, String correo,
        String direccion, String contrasena,
        String orgLaboral, List<String> temasInteres) {

        // Verificar que no exista otro con la misma identificación
        if (existeUsuarioConId(idPersonal)) {
            System.out.println("Error: Ya existe un usuario con ID " + idPersonal);
            return;
        }

        Estudiante nuevo = new Estudiante(
            nombre, apellido1, apellido2, idPersonal,
            telefono, correo, direccion, contrasena,
            orgLaboral, temasInteres
        );

        listaUsuarios.add(nuevo);
        guardarUsuarios();
        System.out.println("Estudiante registrado exitosamente: " + nombre);
    }

    public void registrarProfesor(
        String nombre, String apellido1, String apellido2,
        String idPersonal, String telefono, String correo,
        String direccion, String contrasena,
        List<String> titulos, List<String> certificaciones) {

        if (existeUsuarioConId(idPersonal)) {
            System.out.println("Error: Ya existe un usuario con ID " + idPersonal);
            return;
        }

        Profesor nuevo = new Profesor(
            nombre, apellido1, apellido2, idPersonal,
            telefono, correo, direccion, contrasena,
            titulos, certificaciones
        );

        listaUsuarios.add(nuevo);
        guardarUsuarios();
        System.out.println("Profesor registrado exitosamente: " + nombre);
    }

    //Método auxiliar para evitar duplicados
    private boolean existeUsuarioConId(String id) {
        return listaUsuarios.stream()
            .anyMatch(p -> p.getIdentificacionPersonal().equals(id));
    }

    public List<Persona> getListaUsuarios() {
        return new ArrayList<>(listaUsuarios); 
    }


    public Administrador() {
        this.idAdmin = ID_ADMIN;
        this.contrasenaAdmin = CONTRASENA_ADMIN;
        this.listaUsuarios = cargarUsuarios();
        this.listaCursos = cargarCursos();
        if (this.listaUsuarios == null) {
        this.listaUsuarios = new ArrayList<>();
    
        }
    }

    // Método para iniciar sesión como administrador (valida contra las constantes)
    public boolean iniciarSesion(String usuario, String contrasena) {
        if (ID_ADMIN.equals(usuario) && CONTRASENA_ADMIN.equals(contrasena)) {
            System.out.println("Sesión iniciada como Administrador.");
            return true;
        } else {
            System.out.println("Credenciales incorrectas. Acceso denegado.");
            return false;
        }
    }

    public void consultarUsuarioPorId(String id) {
        Persona usuario = buscarUsuarioPorId(id);
        if (usuario != null) {
            usuario.consultarInfo(); // Usa el método polimórfico ya definido
            System.out.println("Tipo: " +  (usuario instanceof Estudiante ? "Estudiante" : "Profesor"));
        } else {
            System.out.println("Usuario con ID '" + id + "' no encontrado.");
        }
    }

    private Persona buscarUsuarioPorId(String id) {
        return listaUsuarios.stream().filter(p -> p.getIdentificacionPersonal().equals(id)).findFirst().orElse(null);
    }

    public void modificarDatosComunes(String id, String nombre, String apellido1, String apellido2, String telefono, String correo, String direccion) {
        Persona usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        usuario.setNombre(nombre);
        usuario.setApellido1(apellido1);
        usuario.setApellido2(apellido2);
        usuario.setTelefono(telefono);
        usuario.setCorreoElectronico(correo);
        usuario.setDireccionFisica(direccion);

        guardarUsuarios();
        System.out.println("Datos comunes actualizados correctamente.");
    }

    public void modificarEstudiante(String id, String orgLaboral, List<String> temasInteres) {
        Persona persona = buscarUsuarioPorId(id);
        if (persona == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        if (!(persona instanceof Estudiante)) {
            System.out.println("El usuario con ID " + id + " no es un estudiante.");
            return;
        }

        Estudiante estudiante = (Estudiante) persona;
        estudiante.setOrgLabora(orgLaboral);
        estudiante.setTemasInteres(temasInteres);

        guardarUsuarios();
        System.out.println("Datos del estudiante actualizados.");
    }

    public void modificarProfesor(String id, List<String> titulos, List<String> certificaciones) {
        Persona persona = buscarUsuarioPorId(id);
        if (persona == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        if (!(persona instanceof Profesor)) {
            System.out.println("El usuario con ID " + id + " no es un profesor.");
            return;
        }

        Profesor profesor = (Profesor) persona;
        profesor.setTitulosObtenidos(titulos);
        profesor.setCertificacionesEstudios(certificaciones);

        guardarUsuarios();
        System.out.println("Datos del profesor actualizados.");
    }

    public void eliminarUsuario(String id) {
        boolean eliminado = listaUsuarios.removeIf(p -> p.getIdentificacionPersonal().equals(id));
        if (eliminado) {
            guardarUsuarios();
        System.out.println("Usuario eliminado correctamente.");
        } else {
        System.out.println("Usuario con ID '" + id + "' no encontrado.");
        }
    }

    // ... (tus atributos y métodos existentes)

    private static final String RUTA_CURSOS = "MC_Cursos.json";
    private List<Curso> listaCursos;

    // ===== MÉTODOS PARA CURSOS =====

    private List<Curso> cargarCursos() {
        Type tipoLista = new TypeToken<List<Curso>>(){}.getType();
        try {
            List<Curso> lista = JsonUtils.cargarLista(RUTA_CURSOS, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar cursos. Se iniciará con lista vacía.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void guardarCursos() {
        Type tipoLista = new TypeToken<List<Curso>>(){}.getType();
        try {
            JsonUtils.guardarLista(listaCursos, tipoLista, RUTA_CURSOS);
        } catch (Exception e) {
            System.err.println("Error al guardar cursos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Optional<Curso> buscarCursoPorId(String idCurso) {
        return listaCursos.stream()
            .filter(c -> c.getIdCurso().equals(idCurso))
            .findFirst();
    }

    // --- Implementaciones reales ---

    public void agregarCurso(
        String idCurso, String nombre, String descripcion,
        int horasDia, String modalidad,
        int minEst, int maxEst,
        int califMinAprobar, String tipo) {

        if (buscarCursoPorId(idCurso).isPresent()) {
            System.out.println("Error: Ya existe un curso con ID " + idCurso);
            return;
        }

        Curso nuevo = new Curso(
            idCurso, nombre, descripcion,
            horasDia, 0, modalidad, 
            minEst, maxEst,
            califMinAprobar, tipo
        );

        listaCursos.add(nuevo);
        guardarCursos();
        System.out.println("Curso agregado exitosamente: " + nombre);
    }

    public void consultarCurso(String idCurso) {
        Optional<Curso> cursoOpt = buscarCursoPorId(idCurso);
        if (cursoOpt.isPresent()) {
            System.out.println(cursoOpt.get());
        } else {
            System.out.println("Curso con ID '" + idCurso + "' no encontrado.");
        }
    }

    public void modificarCurso(
        String idCurso,
        String nuevoNombre, String nuevaDescripcion,
        int nuevasHorasDia, String nuevaModalidad,
        int nuevoMinEst, int nuevoMaxEst,
        int nuevaCalifMin, String nuevoTipo) {

        Optional<Curso> cursoOpt = buscarCursoPorId(idCurso);
        if (cursoOpt.isEmpty()) {
            System.out.println("Curso no encontrado.");
            return;
        }

        Curso curso = cursoOpt.get();
        curso.setNombreCurso(nuevoNombre);
        curso.setDescripcionCurso(nuevaDescripcion);
        curso.setCantHorasDia(nuevasHorasDia);
        curso.setModalidad(nuevaModalidad);
        curso.setCantMinEstudiantes(nuevoMinEst);
        curso.setCantMaxEstudiantes(nuevoMaxEst);
        curso.setCalificacionMinAprobar(nuevaCalifMin);
        curso.setTipoCurso(nuevoTipo);

        guardarCursos();
        System.out.println("Curso actualizado correctamente.");
    }

    public void eliminarCurso(String idCurso) {
        boolean eliminado = listaCursos.removeIf(c -> c.getIdCurso().equals(idCurso));
        if (eliminado) {
            guardarCursos();
            System.out.println("Curso eliminado correctamente.");
        } else {
            System.out.println("Curso con ID '" + idCurso + "' no encontrado.");
        }
    }

    public void listarCursos() {
        if (listaCursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }
        System.out.println("\n=== LISTA DE CURSOS ===");
        for (Curso c : listaCursos) {
            System.out.println("- " + c.getNombreCurso() + " (ID: " + c.getIdCurso() + ")");
        }
        System.out.println("========================\n");
    }

    public Estudiante obtenerEstudiantePorId(String id) {
        return listaUsuarios.stream()
            .filter(p -> p.getIdentificacionPersonal().equals(id))
            .filter(p -> p instanceof Estudiante)
            .map(p -> (Estudiante) p)
            .findFirst()
            .orElse(null);
    }

    // En Administrador.java
    public Curso obtenerCursoPorId(String idCurso) {
        return listaCursos.stream()
            .filter(c -> c.getIdCurso().equals(idCurso))
            .findFirst()
            .orElse(null);
    }

    // Busca un profesor por ID y devuelve una copia (o null si no existe)
    public Profesor obtenerProfesorPorId(String id) {
        return listaUsuarios.stream()
            .filter(p -> p.getIdentificacionPersonal().equals(id))
            .filter(p -> p instanceof Profesor)
            .map(p -> (Profesor) p)
            .findFirst()
            .orElse(null);
    }

    // Busca cualquier persona por ID (útil para validaciones)
    public Persona obtenerPersonaPorId(String id) {
        return listaUsuarios.stream()
            .filter(p -> p.getIdentificacionPersonal().equals(id))
            .findFirst()
            .orElse(null);
    }

    public boolean crearGrupoParaCurso(String idCurso, String idProfesor, LocalDate fechaInicio, LocalDate fechaFin) {
        Curso curso = obtenerCursoPorId(idCurso);
        if (curso == null) {
            System.out.println("Curso no encontrado.");
            return false;
        }

        String idProf = idProfesor != null && !idProfesor.trim().isEmpty() ? idProfesor.trim() : null;

        // Validar profesor solo si se proporcionó
        if (idProf != null) {
            Profesor profesor = obtenerProfesorPorId(idProf);
            if (profesor == null) {
                System.out.println("Profesor no encontrado.");
                return false;
            }
        }

        int nuevoIdGrupo = curso.getGrupos().size() + 1;
        Grupo nuevoGrupo = new Grupo(nuevoIdGrupo, fechaInicio, fechaFin, idProf );
        curso.agregarGrupo(nuevoGrupo);
        guardarCursos();
        System.out.println("Grupo " + nuevoIdGrupo + " creado para el curso " + idCurso);
        return true;
    }

    public boolean asignarProfesorAGrupo(String idCurso, int idGrupo, String idProfesor) {
        Curso curso = obtenerCursoPorId(idCurso);
        if (curso == null) {
            System.out.println("Curso no encontrado.");
            return false;
        }

        Grupo grupo = curso.obtenerGrupo(idGrupo);
        if (grupo == null) {
            System.out.println("Grupo no encontrado en el curso.");
            return false;
        }

        Profesor profesor = obtenerProfesorPorId(idProfesor);
        if (profesor == null) {
            System.out.println("Profesor no encontrado.");
            return false;
        }

        grupo.setProfesorAsignado(idProfesor);
        guardarCursos();
        System.out.println("Profesor " + idProfesor + " asignado al grupo " + idGrupo + " del curso " + idCurso);
        return true;
    }

    public void asociarGrupoAProfesor() {
        System.out.println("Asociando un grupo a un profesor...");
    }

    public void reporteListaDeEstudiantes() {
        System.out.println("Generando reporte de lista de estudiantes...");
    }

    public void reporteEstadisticaDeMatricula() {
        System.out.println("Generando reporte estadístico de matrícula...");
    }

    // Método toString() opcional
    @Override
    public String toString() {
        return "Administrador{ID=" + ID_ADMIN + ", contrasena=***}";
    }

    public void mostrarMenu(List<Persona> usuarios) {
        SwingUtilities.invokeLater(() -> new MenuAdministradorFrame(usuarios));
    } 
}