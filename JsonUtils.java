package poo.proyecto1.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import poo.proyecto1.persona.Persona;
import poo.proyecto1.usuarios.estudiante.Estudiante;
import poo.proyecto1.usuarios.profesor.Profesor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(
            RuntimeTypeAdapterFactory.of(Persona.class, "tipo")
                .registerSubtype(Estudiante.class, "estudiante")
                .registerSubtype(Profesor.class, "profesor")
        )
        .setPrettyPrinting()
        .create();

    public static void guardarListaEnJson(List<Persona> lista, String rutaArchivo) throws IOException {
    Type tipoLista = new TypeToken<List<Persona>>(){}.getType();
    try (FileWriter writer = new FileWriter(rutaArchivo)) {
        gson.toJson(lista, tipoLista, writer); 
    }
    System.out.println("Lista guardada exitosamente en: " + rutaArchivo);
}

    public static <T> List<T> cargarListaDesdeJson(String rutaArchivo, Type tipoLista) throws IOException {
    try (FileReader reader = new FileReader(rutaArchivo)) {
        List<T> lista = gson.fromJson(reader, tipoLista);
        return lista != null ? lista : new ArrayList<>();
    } catch (IOException e) {
        System.err.println("No se pudo encontrar o leer el archivo: " + rutaArchivo + ". Se devolverá una lista vacía.");
        return new ArrayList<>();
    }
}
}