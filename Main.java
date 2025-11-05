package poo.proyecto1;

import poo.proyecto1.admin.Administrador;
import poo.proyecto1.persona.personalogin.LoginFrame;
import poo.proyecto1.util.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.google.gson.reflect.TypeToken;

import poo.proyecto1.persona.Persona;;

public class Main {

    // Ruta del archivo JSON en el sistema de archivos (no en resources)
    private static final String RUTA_USUARIOS = "MC_usuarios.json";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Persona> usuarios = new ArrayList<>();

            try {
                // Cargar directamente desde el sistema de archivos
                usuarios = JsonUtils.cargarListaPersonas(RUTA_USUARIOS);
                System.out.println("Usuarios cargados: " + usuarios.size());
            } catch (Exception e) {
                System.err.println("No se pudo cargar el archivo de usuarios. Se iniciará con lista vacía.");
                e.printStackTrace();
                // usuarios ya es una lista vacía
            }

            LoginFrame loginFrame = new LoginFrame(usuarios);
            loginFrame.setVisible(true);
        });
    }
}
