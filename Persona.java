package poo.proyecto1.persona;

import java.util.List;
import java.util.Objects;

public abstract class Persona {
    // Atributos privados
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String identificacionPersonal;
    private String telefono;
    private String correoElectronico;
    private String direccionFisica;
    private String contrasena;

    public Persona(){};

    // Constructor
    public Persona(String nombre, String pApellido1, String pApellido2,
                   String idPersonal, String pTelefono, String pCorreoElectronico,
                   String pDireccionFisica, String pContrasena) {
        this.nombre = nombre;
        this.apellido1 = pApellido1;
        this.apellido2 = pApellido2;
        this.identificacionPersonal = idPersonal;
        this.telefono = pTelefono;
        this.correoElectronico = pCorreoElectronico;
        this.direccionFisica = pDireccionFisica;
        this.contrasena = pContrasena;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getIdentificacionPersonal() {
        return identificacionPersonal;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getDireccionFisica() {
        return direccionFisica;
    }

    public String getContrasena() {
        return contrasena;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setIdentificacionPersonal(String identificacionPersonal) {
        this.identificacionPersonal = identificacionPersonal;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setDireccionFisica(String direccionFisica) {
        this.direccionFisica = direccionFisica;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", identificacionPersonal='" + identificacionPersonal + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", direccionFisica='" + direccionFisica + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }

    // Métodos comunes (heredados por Estudiante y Profesor)
    public void cambiarContrasena(String nuevaContrasena) {
        this.contrasena = nuevaContrasena;
        System.out.println("Contraseña actualizada correctamente.");
    }

    public void iniciarSesion() {
        System.out.println("Sesión iniciada para: " + this.nombre + " " + this.apellido1);
    }

    public void consultarInfo() {
        System.out.println("=== Información de la Persona ===");
        System.out.println("Nombre completo: " + nombre + " " + apellido1 + " " + apellido2);
        System.out.println("Identificación: " + identificacionPersonal);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Correo: " + correoElectronico);
        System.out.println("Dirección: " + direccionFisica);
        System.out.println("==================================");
    }

    public abstract void mostrarMenu(List<Persona> usuarios);
}