package poo.proyecto1;

import poo.proyecto1.admin.Administrador;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
    Administrador admin = new Administrador();

    // Registrar (ya lo tienes)
    admin.registrarEstudiante("Ana", "López", "García", "123", "...", "...", "...", "...", "Empresa", Arrays.asList("Java"));

    // Consultar
    admin.consultarUsuarioPorId("123");

    // Modificar datos comunes
    admin.modificarDatosComunes("123", "Ana María", "López", "García", "555-9999", "nuevo@email.com", "Nueva Dirección");

    // Modificar datos específicos de estudiante
    admin.modificarEstudiante("123", "Nueva Empresa", Arrays.asList("Java", "Python", "Matemáticas"));

    // Listar todo

    // Eliminar
    // admin.eliminarUsuario("123");
}
}