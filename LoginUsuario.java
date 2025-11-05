package poo.proyecto1.persona.personalogin;

import java.util.List;
import java.util.Optional;

import poo.proyecto1.persona.Persona;
import poo.proyecto1.util.Encriptador;

public class LoginUsuario 
{
    public static Optional<Persona> autenticar(String identificacion, String contrasena, List<Persona> usuarios) {
        String contrasenaEncriptada = Encriptador.encriptarSHA256(contrasena);

        for (Persona usuario : usuarios) {
            if (usuario.getIdentificacionPersonal().equals(identificacion)) {
                if (usuario.getContrasena().equals(contrasenaEncriptada)) {
                    return Optional.of(usuario);
                } else {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }
}
