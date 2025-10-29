package poo.proyecto1.evaluacion;

import java.util.List;
import java.util.ArrayList;

public class Pareo extends Pregunta {
    private List<String> listaOpciones; // Pares: ["A", "1", "B", "2", ...]

    public Pareo(String pDescripcionPregunta, int pCantidadPuntos, List<String> pListaOpciones) {
        super(pDescripcionPregunta, pCantidadPuntos);
        this.listaOpciones = pListaOpciones != null ? new ArrayList<>(pListaOpciones) : new ArrayList<>();
    }

    public List<String> getListaOpciones() {
        return new ArrayList<>(listaOpciones);
    }

    public void setListaOpciones(List<String> listaOpciones) {
        this.listaOpciones = listaOpciones != null ? new ArrayList<>(listaOpciones) : new ArrayList<>();
    }

    @Override
    public String obtenerTipo() {
        return "Pareo";
    }

    @Override
    public String toString() {
        return "Pareo{" +
                "parejas=" + listaOpciones +
                "} " + super.toString();
    }
}