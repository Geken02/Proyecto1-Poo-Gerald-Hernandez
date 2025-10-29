package poo.proyecto1.evaluacion;

import poo.proyecto1.evaluacion.Pregunta;
import java.util.List;
import java.util.ArrayList;

public class SeleccionUnica extends Pregunta {
    private List<String> listaOpciones;

    public SeleccionUnica(String pDescripcionPregunta, int pCantidadPuntos, List<String> pListaOpciones) {
        super(pDescripcionPregunta, pCantidadPuntos);
        this.listaOpciones = pListaOpciones != null ? new ArrayList<>(pListaOpciones) : new ArrayList<>();
    }

    // Getter y Setter
    public List<String> getListaOpciones() {
        return new ArrayList<>(listaOpciones);
    }

    public void setListaOpciones(List<String> listaOpciones) {
        this.listaOpciones = listaOpciones != null ? new ArrayList<>(listaOpciones) : new ArrayList<>();
    }

    @Override
    public String obtenerTipo() {
        return "Selección Única";
    }

    @Override
    public String toString() {
        return "SeleccionUnica{" +
                "opciones=" + listaOpciones +
                "} " + super.toString();
    }
}