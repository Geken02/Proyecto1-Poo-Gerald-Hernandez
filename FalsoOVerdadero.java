package poo.proyecto1.evaluacion;

import java.util.List;
import java.util.ArrayList;

public class FalsoOVerdadero extends Pregunta {
    private List<String> listaOpciones; // Normalmente ["Verdadero", "Falso"]

    public FalsoOVerdadero(String pDescripcionPregunta, int pCantidadPuntos, List<String> pListaOpciones) {
        super(pDescripcionPregunta, pCantidadPuntos);
        // Si no se pasa lista, usar valores por defecto
        if (pListaOpciones == null || pListaOpciones.isEmpty()) {
            this.listaOpciones = new ArrayList<>();
            this.listaOpciones.add("Verdadero");
            this.listaOpciones.add("Falso");
        } else {
            this.listaOpciones = new ArrayList<>(pListaOpciones);
        }
    }

    public List<String> getListaOpciones() {
        return new ArrayList<>(listaOpciones);
    }

    public void setListaOpciones(List<String> listaOpciones) {
        this.listaOpciones = listaOpciones != null ? new ArrayList<>(listaOpciones) : new ArrayList<>();
    }

    @Override
    public String obtenerTipo() {
        return "Falso o Verdadero";
    }

    @Override
    public String toString() {
        return "FalsoOVerdadero{" +
                "opciones=" + listaOpciones +
                "} " + super.toString();
    }
}