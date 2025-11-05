package poo.proyecto1.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import poo.proyecto1.persona.Persona;
import poo.proyecto1.persona.estudiante.Estudiante;
import poo.proyecto1.persona.profesor.Profesor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Gson especializado para Persona (con soporte polimórfico)
    private static final Gson gsonPersonas = new GsonBuilder()
        .registerTypeAdapterFactory(
            RuntimeTypeAdapterFactory.of(Persona.class, "tipo")
                .registerSubtype(Estudiante.class, "estudiante")
                .registerSubtype(Profesor.class, "profesor")
        )
        .setPrettyPrinting()
        .create();

    // Gson genérico para otros tipos (como Curso, Grupo, etc.)
    private static final Gson gsonGenerico = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    // === MÉTODOS PARA PERSONAS ===
    public static void guardarListaPersonas(List<Persona> lista, String rutaArchivo) throws IOException {
        Type tipo = new TypeToken<List<Persona>>(){}.getType();
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gsonPersonas.toJson(lista, tipo, writer);
        }
        System.out.println("Usuarios guardados en: " + rutaArchivo);
    }

    public static List<Persona> cargarListaPersonas(String rutaArchivo) throws IOException {
        Type tipo = new TypeToken<List<Persona>>(){}.getType();
        try (FileReader reader = new FileReader(rutaArchivo)) {
            List<Persona> lista = gsonPersonas.fromJson(reader, tipo);
            return lista != null ? lista : new ArrayList<>();
        }
    }

    // === MÉTODOS GENÉRICOS PARA OTROS TIPOS (como Curso) ===
    public static <T> void guardarLista(List<T> lista, Type tipoLista, String rutaArchivo) throws IOException {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gsonGenerico.toJson(lista, tipoLista, writer);
        }
        System.out.println("Lista guardada en: " + rutaArchivo);
    }

    public static <T> List<T> cargarLista(String rutaArchivo, Type tipoLista) throws IOException {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            List<T> lista = gsonGenerico.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        }
    }
}