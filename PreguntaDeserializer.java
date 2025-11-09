// poo/proyecto1/utils/PreguntaDeserializer.java
package poo.proyecto1.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import poo.proyecto1.evaluacion.*;

import poo.proyecto1.evaluacion.Pregunta;
import poo.proyecto1.evaluacion.PreguntaFalsoVerdadero;
import poo.proyecto1.evaluacion.PreguntaPareo;
import poo.proyecto1.evaluacion.PreguntaSeleccionMultiple;
import poo.proyecto1.evaluacion.PreguntaSeleccionUnica;
import poo.proyecto1.evaluacion.PreguntaSopaLetras;

import java.io.IOException;
import java.lang.reflect.Type;

public class PreguntaDeserializer implements JsonDeserializer<Pregunta> {
    @Override
    public Pregunta deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String tipo = jsonObject.has("tipo")
                ? jsonObject.get("tipo").getAsString()
                : "SELECCION_UNICA";

        switch (tipo) {
            case "FALSO_VERDADERO":
                return context.deserialize(json, PreguntaFalsoVerdadero.class);
            case "PAREO":
                return context.deserialize(json, PreguntaPareo.class);
            case "SOPA_LETRAS":
                return context.deserialize(json, PreguntaSopaLetras.class);
            case "SELECCION_MULTIPLE":
                return context.deserialize(json, PreguntaSeleccionMultiple.class);
            case "SELECCION_UNICA":
            default:
                return context.deserialize(json, PreguntaSeleccionUnica.class);
        }
    }
}