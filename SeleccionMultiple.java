package poo.proyecto1.evaluacion;

import java.util.List;
import java.util.ArrayList;

public class SeleccionMultiple extends Pregunta {
    private List<String> listaOpciones;

    public SeleccionMultiple(String pDescripcionPregunta, int pCantidadPuntos, List<String> pListaOpciones) {
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
        return "Selección Múltiple";
    }

    @Override
    public String toString() {
        return "SeleccionMultiple{" +
                "opciones=" + listaOpciones +
                "} " + super.toString();
    }
}