package poo.proyecto1.admin;

import poo.proyecto1.persona.Persona;
import poo.proyecto1.usuarios.estudiante.Estudiante;
import poo.proyecto1.usuarios.profesor.Profesor;
import poo.proyecto1.util.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;

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
    Type tipoLista = new TypeToken<List<Persona>>(){}.getType();
    try {
        return JsonUtils.cargarListaDesdeJson(RUTA_USUARIOS, tipoLista);
    } catch (Exception e) {
        System.err.println("Error al cargar usuarios. Se iniciará con lista vacía.");
        e.printStackTrace();
        return new ArrayList<>();
    }
}

    // Guarda la lista actual en el archivo
    private void guardarUsuarios() {
        try {
            JsonUtils.guardarListaEnJson(listaUsuarios, RUTA_USUARIOS);
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

    public void eliminarEstudiante() {
        System.out.println("Eliminando un estudiante...");
    }

    public void eliminarProfesor() {
        System.out.println("Eliminando un profesor...");
    }

    public void agregarCurso() {
        System.out.println("Agregando un nuevo curso...");
    }

    public void consultarCurso() {
        System.out.println("Consultando información de un curso...");
    }

    public void modificarCurso() {
        System.out.println("Modificando datos de un curso...");
    }

    public void eliminarCurso() {
        System.out.println("Eliminando un curso...");
    }

    public void asociarGrupoACurso() {
        System.out.println("Asociando un grupo a un curso...");
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
}