// poo/proyecto1/evaluacion/realizacionEvaluacion/ComponenteRespuesta.java
package poo.proyecto1.evaluacion.realizarEvaluacion;

import poo.proyecto1.evaluacion.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class ComponenteRespuesta extends JPanel {
    // Constantes para estilos
    private static final Color COLOR_PRIMARIO = new Color(70, 130, 180);
    private static final Color COLOR_SECUNDARIO = new Color(240, 248, 255);
    private static final Color COLOR_EXITO = new Color(46, 125, 50);
    private static final Color COLOR_PELIGRO = new Color(198, 40, 40);
    private static final Color COLOR_FONDO = new Color(248, 248, 248);
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 14);
    private static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 12);
    private static final Font FUENTE_ENUNCIADO = new Font("Arial", Font.BOLD, 13);

    private Pregunta pregunta;
    private List<JComponent> controlesRespuesta;
    private List<Integer> mapeoIndiceMostradoAOriginal;
    private List<JComboBox<String>> combosPareo;
    private List<Integer> indicesNoDistractores;
    private SopaLetrasPanelInteractivo sopaLetrasPanel;
    private boolean opcionesAleatorias;

    public ComponenteRespuesta(Pregunta pregunta, boolean opcionesAleatorias) {
        this.pregunta = pregunta;
        this.opcionesAleatorias = opcionesAleatorias;
        this.controlesRespuesta = new ArrayList<>();
        this.mapeoIndiceMostradoAOriginal = new ArrayList<>();
        this.combosPareo = new ArrayList<>();
        this.indicesNoDistractores = new ArrayList<>();

        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Enunciado con mejor estilo
        JLabel lblEnunciado = new JLabel(
                "<html><div style='text-align: left; padding: 12px; background: #f0f8ff; " +
                        "border-left: 4px solid #4682b4; border-radius: 4px;'>" +
                        "<b style='color: #2c3e50; font-size: 14px;'>" + pregunta.getDescripcionPregunta() + "</b>" +
                        "<br><span style='color: #666; font-size: 11px; margin-top: 5px; display: block;'>" +
                        "Puntos: " + pregunta.getCantidadPuntos() + " • Tipo: " + getTipoPregunta(pregunta) +
                        "</span></div></html>");
        lblEnunciado.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(lblEnunciado, BorderLayout.NORTH);

        // Panel de opciones
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setBackground(Color.WHITE);
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        if (pregunta instanceof PreguntaSeleccionUnica) {
            configurarSeleccionUnica((PreguntaSeleccionUnica) pregunta, panelOpciones);
        } else if (pregunta instanceof PreguntaSeleccionMultiple) {
            configurarSeleccionMultiple((PreguntaSeleccionMultiple) pregunta, panelOpciones);
        } else if (pregunta instanceof PreguntaFalsoVerdadero) {
            configurarFalsoVerdadero(panelOpciones);
        } else if (pregunta instanceof PreguntaPareo) {
            configurarPareoConCombos((PreguntaPareo) pregunta, panelOpciones);
        } else if (pregunta instanceof PreguntaSopaLetras) {
            configurarSopaLetrasInteractiva((PreguntaSopaLetras) pregunta, panelOpciones);
        }

        add(panelOpciones, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private String getTipoPregunta(Pregunta pregunta) {
        if (pregunta instanceof PreguntaSeleccionUnica)
            return "Selección Única";
        if (pregunta instanceof PreguntaSeleccionMultiple)
            return "Selección Múltiple";
        if (pregunta instanceof PreguntaFalsoVerdadero)
            return "Verdadero/Falso";
        if (pregunta instanceof PreguntaPareo)
            return "Pareo";
        if (pregunta instanceof PreguntaSopaLetras)
            return "Sopa de Letras";
        return "Desconocido";
    }

    private void configurarSeleccionUnica(PreguntaSeleccionUnica p, JPanel panelOpciones) {
        List<String> opcionesOriginales = p.getOpciones();
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < opcionesOriginales.size(); i++) {
            indices.add(i);
        }
        if (opcionesAleatorias) {
            Collections.shuffle(indices);
        }
        mapeoIndiceMostradoAOriginal = new ArrayList<>(indices);

        ButtonGroup grupo = new ButtonGroup();
        for (int idxMostrado = 0; idxMostrado < indices.size(); idxMostrado++) {
            int idxOriginal = indices.get(idxMostrado);
            JRadioButton radio = new JRadioButton(opcionesOriginales.get(idxOriginal));
            aplicarEstiloOpcion(radio);
            grupo.add(radio);
            controlesRespuesta.add(radio);

            JPanel panelOpcion = new JPanel(new BorderLayout());
            panelOpcion.setBackground(Color.WHITE);
            panelOpcion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            panelOpcion.add(radio, BorderLayout.CENTER);

            panelOpciones.add(panelOpcion);
            panelOpciones.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    private void configurarSeleccionMultiple(PreguntaSeleccionMultiple p, JPanel panelOpciones) {
        List<String> opcionesOriginales = p.getOpciones();
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < opcionesOriginales.size(); i++) {
            indices.add(i);
        }
        if (opcionesAleatorias) {
            Collections.shuffle(indices);
        }
        mapeoIndiceMostradoAOriginal = new ArrayList<>(indices);

        for (int idxMostrado = 0; idxMostrado < indices.size(); idxMostrado++) {
            int idxOriginal = indices.get(idxMostrado);
            JCheckBox check = new JCheckBox(opcionesOriginales.get(idxOriginal));
            aplicarEstiloOpcion(check);
            controlesRespuesta.add(check);

            JPanel panelOpcion = new JPanel(new BorderLayout());
            panelOpcion.setBackground(Color.WHITE);
            panelOpcion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            panelOpcion.add(check, BorderLayout.CENTER);

            panelOpciones.add(panelOpcion);
            panelOpciones.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    private void aplicarEstiloOpcion(AbstractButton boton) {
        boton.setFont(FUENTE_NORMAL);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        boton.setOpaque(true);

        // Efecto hover básico
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(245, 245, 245));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
            }
        });
    }

    private void configurarFalsoVerdadero(JPanel panelOpciones) {
        JPanel panelFalsoVerdadero = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelFalsoVerdadero.setBackground(Color.WHITE);
        panelFalsoVerdadero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JRadioButton rbVerdadero = crearBotonEstilizado("Verdadero", COLOR_EXITO);
        JRadioButton rbFalso = crearBotonEstilizado("Falso", COLOR_PELIGRO);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbVerdadero);
        grupo.add(rbFalso);

        panelFalsoVerdadero.add(rbVerdadero);
        panelFalsoVerdadero.add(Box.createRigidArea(new Dimension(30, 0)));
        panelFalsoVerdadero.add(rbFalso);

        controlesRespuesta.add(rbVerdadero);
        controlesRespuesta.add(rbFalso);

        panelOpciones.add(panelFalsoVerdadero);
    }

    private JRadioButton crearBotonEstilizado(String texto, Color color) {
        JRadioButton boton = new JRadioButton(texto);
        boton.setFont(FUENTE_NORMAL);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        boton.setOpaque(true);

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
            }
        });

        return boton;
    }

    private void configurarPareoConCombos(PreguntaPareo p, JPanel panelOpciones) {
        List<String> izquierdaOriginal = p.getColumnaIzquierda();
        List<String> derecha = p.getColumnaDerecha();

        List<String> izquierdaFiltrada = new ArrayList<>();
        indicesNoDistractores.clear();

        for (int i = 0; i < izquierdaOriginal.size(); i++) {
            String item = izquierdaOriginal.get(i);
            if (!item.matches("Distrator.*")) {
                izquierdaFiltrada.add(item);
                indicesNoDistractores.add(i);
            }
        }

        if (izquierdaFiltrada.isEmpty() || derecha.isEmpty()) {
            JLabel lblError = new JLabel("No hay elementos para parear");
            lblError.setFont(FUENTE_NORMAL);
            lblError.setForeground(Color.GRAY);
            lblError.setHorizontalAlignment(SwingConstants.CENTER);
            panelOpciones.add(lblError);
            return;
        }

        List<String> derechaBarajada = new ArrayList<>(derecha);
        Collections.shuffle(derechaBarajada);

        JPanel panelPareo = new JPanel();
        panelPareo.setLayout(new BoxLayout(panelPareo, BoxLayout.Y_AXIS));
        panelPareo.setBackground(Color.WHITE);
        panelPareo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        for (int i = 0; i < izquierdaFiltrada.size(); i++) {
            String itemIzq = izquierdaFiltrada.get(i);

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
            fila.setAlignmentX(Component.LEFT_ALIGNMENT);
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            fila.setBackground(Color.WHITE);

            JLabel label = new JLabel(itemIzq);
            label.setFont(FUENTE_ENUNCIADO);
            label.setForeground(new Color(60, 60, 60));
            label.setPreferredSize(new Dimension(180, 30));

            JLabel flecha = new JLabel("→");
            flecha.setFont(new Font("Arial", Font.BOLD, 16));
            flecha.setForeground(COLOR_PRIMARIO);
            flecha.setPreferredSize(new Dimension(40, 30));
            flecha.setHorizontalAlignment(SwingConstants.CENTER);

            JComboBox<String> combo = new JComboBox<>();
            combo.addItem("-- Seleccione --");
            for (String itemDer : derechaBarajada) {
                combo.addItem(itemDer);
            }
            combo.setPreferredSize(new Dimension(220, 35));
            combo.setBackground(Color.WHITE);
            combo.setFont(FUENTE_NORMAL);

            combo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (index >= 0) {
                        if (isSelected) {
                            setBackground(new Color(184, 207, 229));
                            setForeground(Color.BLACK);
                        } else {
                            setBackground(Color.WHITE);
                            setForeground(Color.BLACK);
                        }
                    } else {
                        setBackground(Color.WHITE);
                        setForeground(Color.GRAY);
                    }
                    return c;
                }
            });

            combosPareo.add(combo);
            controlesRespuesta.add(combo);

            fila.add(label);
            fila.add(flecha);
            fila.add(combo);

            panelPareo.add(fila);

            if (i < izquierdaFiltrada.size() - 1) {
                panelPareo.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        panelOpciones.add(panelPareo);

        JLabel lblInstrucciones = new JLabel(
                "<html><div style='text-align: center; color: #666; font-style: italic; margin-top: 10px;'>" +
                        "Seleccione la opción correcta para cada elemento. Todas las opciones están disponibles en cada lista desplegable."
                        +
                        "</div></html>");
        lblInstrucciones.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        lblInstrucciones.setFont(new Font("Arial", Font.ITALIC, 12));
        panelOpciones.add(lblInstrucciones);
    }

    private void configurarSopaLetrasInteractiva(PreguntaSopaLetras p, JPanel panelOpciones) {
        Map<String, String> palabrasEnunciados = p.getPalabrasYEnunciados();

        if (palabrasEnunciados.isEmpty()) {
            JLabel lblError = new JLabel("No hay palabras para buscar en la sopa de letras");
            lblError.setFont(FUENTE_NORMAL);
            lblError.setForeground(Color.GRAY);
            lblError.setHorizontalAlignment(SwingConstants.CENTER);
            panelOpciones.add(lblError);
            return;
        }

        int tamaño = calcularTamañoSopa(palabrasEnunciados.keySet());

        sopaLetrasPanel = new SopaLetrasPanelInteractivo(palabrasEnunciados, tamaño);

        JPanel contenedorSopa = new JPanel(new BorderLayout());
        contenedorSopa.setBackground(Color.WHITE);
        contenedorSopa.add(sopaLetrasPanel, BorderLayout.CENTER);

        JScrollPane scrollSopa = new JScrollPane(contenedorSopa);
        scrollSopa.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollSopa.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollSopa.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(COLOR_PRIMARIO, 2),
                        "Sopa de Letras - Selecciona las palabras"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        scrollSopa.getVerticalScrollBar().setUnitIncrement(16);
        scrollSopa.getHorizontalScrollBar().setUnitIncrement(16);

        int anchoPreferido = Math.min(800, tamaño * 35 + 50);
        int altoPreferido = Math.min(600, tamaño * 35 + 50);
        scrollSopa.setPreferredSize(new Dimension(anchoPreferido, altoPreferido));
        scrollSopa.setMaximumSize(new Dimension(anchoPreferido, altoPreferido));

        panelOpciones.add(scrollSopa);
        panelOpciones.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botón para limpiar selecciones con mejor estilo
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);

        JButton btnLimpiar = new JButton("Limpiar Selecciones");
        aplicarEstiloBoton(btnLimpiar, new Color(200, 200, 200), Color.BLACK);
        btnLimpiar.addActionListener(e -> {
            sopaLetrasPanel.limpiarSelecciones();
        });

        panelBotones.add(btnLimpiar);
        panelOpciones.add(panelBotones);

        JLabel lblInstrucciones = new JLabel(
                "<html><div style='text-align: center; color: #666; margin-top: 15px; padding: 15px; background: #f8f9fa; border-radius: 5px;'>"
                        +
                        "<b style='color: #2c3e50;'>Instrucciones:</b> Haz clic en la primera letra de una palabra y arrastra el mouse sobre las letras contiguas "
                        +
                        "para seleccionar palabras en <b>horizontal, vertical y diagonal</b>. Las palabras seleccionadas se mantendrán marcadas. "
                        +
                        "Tu puntaje se basará en las palabras que selecciones correctamente." +
                        "</div></html>");
        lblInstrucciones.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 12));
        panelOpciones.add(lblInstrucciones);
    }

    private void aplicarEstiloBoton(JButton boton, Color fondo, Color texto) {
        boton.setFont(FUENTE_NORMAL);
        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fondo.darker()),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(fondo.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(fondo);
            }
        });
    }

    private int calcularTamañoSopa(Set<String> palabras) {
        int maxLongitud = 0;
        for (String palabra : palabras) {
            if (palabra.length() > maxLongitud) {
                maxLongitud = palabra.length();
            }
        }

        int tamañoBase = Math.max(15, maxLongitud + 5);
        int tamañoPorCantidad = (int) Math.sqrt(palabras.size() * 50);

        return Math.min(25, Math.max(tamañoBase, tamañoPorCantidad));
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public List<Integer> getRespuestasUsuario() {
        List<Integer> respuestasOriginales = new ArrayList<>();

        if (pregunta instanceof PreguntaSeleccionUnica || pregunta instanceof PreguntaFalsoVerdadero) {
            for (int i = 0; i < controlesRespuesta.size(); i++) {
                if (controlesRespuesta.get(i) instanceof JRadioButton) {
                    JRadioButton radio = (JRadioButton) controlesRespuesta.get(i);
                    if (radio.isSelected()) {
                        if (!mapeoIndiceMostradoAOriginal.isEmpty()) {
                            respuestasOriginales.add(mapeoIndiceMostradoAOriginal.get(i));
                        } else {
                            respuestasOriginales.add(i);
                        }
                        break;
                    }
                }
            }
        } else if (pregunta instanceof PreguntaSeleccionMultiple) {
            for (int i = 0; i < controlesRespuesta.size(); i++) {
                if (controlesRespuesta.get(i) instanceof JCheckBox) {
                    JCheckBox check = (JCheckBox) controlesRespuesta.get(i);
                    if (check.isSelected()) {
                        respuestasOriginales.add(mapeoIndiceMostradoAOriginal.get(i));
                    }
                }
            }
        } else if (pregunta instanceof PreguntaPareo) {
            PreguntaPareo p = (PreguntaPareo) pregunta;
            List<String> derechaOriginal = p.getColumnaDerecha();
            List<String> izquierdaOriginal = p.getColumnaIzquierda();

            List<Integer> respuestasCompletas = new ArrayList<>();
            for (int i = 0; i < izquierdaOriginal.size(); i++) {
                respuestasCompletas.add(-1);
            }

            for (int i = 0; i < combosPareo.size(); i++) {
                JComboBox<String> combo = combosPareo.get(i);
                String seleccion = (String) combo.getSelectedItem();

                if (seleccion != null && !seleccion.equals("-- Seleccione --")) {
                    int indiceDerecha = derechaOriginal.indexOf(seleccion);
                    int indiceOriginal = indicesNoDistractores.get(i);
                    respuestasCompletas.set(indiceOriginal, indiceDerecha);
                }
            }

            respuestasOriginales = respuestasCompletas;
        } else if (pregunta instanceof PreguntaSopaLetras) {
            return new ArrayList<>();
        }
        return respuestasOriginales;
    }

    public List<String> getPalabrasSeleccionadasSopaLetras() {
        if (sopaLetrasPanel != null) {
            return sopaLetrasPanel.getPalabrasSeleccionadas();
        }
        return new ArrayList<>();
    }

    public List<JComboBox<String>> getCombosPareo() {
        return combosPareo;
    }

    private class SopaLetrasPanelInteractivo extends JPanel {
        private char[][] matriz;
        private int tamaño;
        private Map<String, String> palabrasEnunciados;
        private List<String> palabras;
        private JLabel[][] letrasLabels;
        private boolean[][] seleccionado;
        private List<Point> seleccionActual;
        private Color colorSeleccion = new Color(100, 180, 255);
        private Color colorFondoCelda = new Color(248, 248, 248);
        private Point inicioSeleccion;
        private List<List<Point>> todasLasSelecciones;
        private Set<String> palabrasSeleccionadas;

        private final int[][] DIRECCIONES = {
                { 0, 1 }, // Horizontal izquierda a derecha
                { 0, -1 }, // Horizontal derecha a izquierda
                { 1, 0 }, // Vertical arriba a abajo
                { -1, 0 }, // Vertical abajo a arriba
                { 1, 1 }, // Diagonal arriba-abajo derecha
                { 1, -1 }, // Diagonal arriba-abajo izquierda
                { -1, 1 }, // Diagonal abajo-arriba derecha
                { -1, -1 } // Diagonal abajo-arriba izquierda
        };

        public SopaLetrasPanelInteractivo(Map<String, String> palabrasEnunciados, int tamaño) {
            this.palabrasEnunciados = palabrasEnunciados;
            this.tamaño = tamaño;
            this.palabras = new ArrayList<>(palabrasEnunciados.keySet());
            this.letrasLabels = new JLabel[tamaño][tamaño];
            this.seleccionado = new boolean[tamaño][tamaño];
            this.seleccionActual = new ArrayList<>();
            this.todasLasSelecciones = new ArrayList<>();
            this.palabrasSeleccionadas = new HashSet<>();

            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            inicializarSopa();
            initUI();
        }

        private void inicializarSopa() {
            matriz = new char[tamaño][tamaño];

            for (int i = 0; i < tamaño; i++) {
                for (int j = 0; j < tamaño; j++) {
                    matriz[i][j] = ' ';
                }
            }

            palabras.sort((a, b) -> Integer.compare(b.length(), a.length()));

            for (int i = 0; i < palabras.size(); i++) {
                String palabra = palabras.get(i).toUpperCase();
                boolean colocada = false;
                int intentos = 0;
                int maxIntentos = 100;

                while (!colocada && intentos < maxIntentos) {
                    int direccion;

                    if (i < 8) {
                        direccion = i;
                    } else {
                        direccion = (int) (Math.random() * DIRECCIONES.length);
                    }

                    int dx = DIRECCIONES[direccion][0];
                    int dy = DIRECCIONES[direccion][1];

                    int x = (int) (Math.random() * tamaño);
                    int y = (int) (Math.random() * tamaño);

                    if (puedeColocarPalabra(palabra, x, y, dx, dy)) {
                        colocarPalabra(palabra, x, y, dx, dy);
                        colocada = true;
                    }
                    intentos++;
                }

                if (!colocada) {
                    System.err.println("No se pudo colocar la palabra: " + palabra);
                }
            }

            rellenarEspaciosVacios();
        }

        private boolean puedeColocarPalabra(String palabra, int x, int y, int dx, int dy) {
            int len = palabra.length();

            int finalX = x + (len - 1) * dx;
            int finalY = y + (len - 1) * dy;

            if (finalX < 0 || finalX >= tamaño || finalY < 0 || finalY >= tamaño) {
                return false;
            }

            for (int i = 0; i < len; i++) {
                int currentX = x + i * dx;
                int currentY = y + i * dy;

                if (matriz[currentX][currentY] != ' ' &&
                        matriz[currentX][currentY] != palabra.charAt(i)) {
                    return false;
                }
            }

            return true;
        }

        private void colocarPalabra(String palabra, int x, int y, int dx, int dy) {
            for (int i = 0; i < palabra.length(); i++) {
                int currentX = x + i * dx;
                int currentY = y + i * dy;
                matriz[currentX][currentY] = palabra.charAt(i);
            }
        }

        private void rellenarEspaciosVacios() {
            String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Random random = new Random();

            for (int i = 0; i < tamaño; i++) {
                for (int j = 0; j < tamaño; j++) {
                    if (matriz[i][j] == ' ') {
                        matriz[i][j] = letras.charAt(random.nextInt(letras.length()));
                    }
                }
            }
        }

        private void initUI() {
            JPanel panelSopa = new JPanel(new GridLayout(tamaño, tamaño, 2, 2));
            panelSopa.setBackground(Color.WHITE);
            panelSopa.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            int tamañoCelda = calcularTamañoCelda();

            for (int i = 0; i < tamaño; i++) {
                for (int j = 0; j < tamaño; j++) {
                    JLabel label = new JLabel(String.valueOf(matriz[i][j]), SwingConstants.CENTER);
                    label.setPreferredSize(new Dimension(tamañoCelda, tamañoCelda));
                    label.setFont(new Font("Monospaced", Font.BOLD, calcularTamañoFuente(tamañoCelda)));
                    label.setOpaque(true);
                    label.setBackground(colorFondoCelda);
                    label.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(180, 180, 180)),
                            BorderFactory.createEmptyBorder(2, 2, 2, 2)));
                    label.setForeground(new Color(60, 60, 60));

                    final int fila = i;
                    final int columna = j;

                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (inicioSeleccion != null) {
                                finalizarSeleccion();
                            }
                            iniciarSeleccion(fila, columna);
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            if (inicioSeleccion != null) {
                                extenderSeleccion(fila, columna);
                            }
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            finalizarSeleccion();
                        }
                    });

                    letrasLabels[i][j] = label;
                    panelSopa.add(label);
                }
            }

            int anchoTotal = tamaño * (tamañoCelda + 2) + 20;
            int altoTotal = tamaño * (tamañoCelda + 2) + 20;
            panelSopa.setPreferredSize(new Dimension(anchoTotal, altoTotal));

            JPanel panelLateral = crearPanelLateral();

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelSopa, panelLateral);
            splitPane.setResizeWeight(0.7);
            splitPane.setDividerLocation(0.7);
            splitPane.setOneTouchExpandable(true);
            splitPane.setBorder(BorderFactory.createEmptyBorder());

            add(splitPane, BorderLayout.CENTER);
        }

        private void iniciarSeleccion(int fila, int columna) {
            inicioSeleccion = new Point(fila, columna);
            seleccionActual = new ArrayList<>();
            seleccionActual.add(new Point(fila, columna));

            letrasLabels[fila][columna].setBackground(colorSeleccion);
            letrasLabels[fila][columna].setForeground(Color.WHITE);
        }

        private void extenderSeleccion(int fila, int columna) {
            if (inicioSeleccion == null)
                return;

            Point ultimo = seleccionActual.get(seleccionActual.size() - 1);

            int diffFila = fila - ultimo.x;
            int diffColumna = columna - ultimo.y;

            if (Math.abs(diffFila) <= 1 && Math.abs(diffColumna) <= 1) {
                if (diffFila != 0 || diffColumna != 0) {
                    if (seleccionActual.size() == 1 || esMismaDireccion(diffFila, diffColumna)) {
                        if (!seleccionActual.contains(new Point(fila, columna))) {
                            seleccionActual.add(new Point(fila, columna));
                            letrasLabels[fila][columna].setBackground(colorSeleccion);
                            letrasLabels[fila][columna].setForeground(Color.WHITE);
                        }
                    }
                }
            }
        }

        private boolean esMismaDireccion(int diffFila, int diffColumna) {
            if (seleccionActual.size() < 2)
                return true;

            Point primero = seleccionActual.get(0);
            Point segundo = seleccionActual.get(1);

            int direccionFila = segundo.x - primero.x;
            int direccionColumna = segundo.y - primero.y;

            if (direccionFila != 0)
                direccionFila = direccionFila / Math.abs(direccionFila);
            if (direccionColumna != 0)
                direccionColumna = direccionColumna / Math.abs(direccionColumna);

            int diffFilaNorm = (diffFila != 0) ? diffFila / Math.abs(diffFila) : 0;
            int diffColumnaNorm = (diffColumna != 0) ? diffColumna / Math.abs(diffColumna) : 0;

            return direccionFila == diffFilaNorm && direccionColumna == diffColumnaNorm;
        }

        private void finalizarSeleccion() {
            if (inicioSeleccion != null && seleccionActual.size() > 1) {
                todasLasSelecciones.add(new ArrayList<>(seleccionActual));

                for (Point p : seleccionActual) {
                    seleccionado[p.x][p.y] = true;
                }

                String palabraSeleccionada = obtenerPalabraDeSeleccion();
                if (palabraSeleccionada != null) {
                    // Verificar si la palabra seleccionada está en la lista de palabras correctas
                    boolean palabraValida = false;
                    for (String palabraCorrecta : palabras) {
                        if (palabraCorrecta.equalsIgnoreCase(palabraSeleccionada)) {
                            palabrasSeleccionadas.add(palabraSeleccionada.toUpperCase());
                            palabraValida = true;
                            System.out.println("Palabra válida encontrada: " + palabraSeleccionada);
                            break;
                        }
                    }
                    if (!palabraValida) {
                        System.out.println("Palabra no válida: " + palabraSeleccionada);
                    }
                }
            }
            inicioSeleccion = null;
            seleccionActual = new ArrayList<>();
        }

        private String obtenerPalabraDeSeleccion() {
            if (seleccionActual.size() < 2)
                return null;

            StringBuilder palabra = new StringBuilder();
            for (Point p : seleccionActual) {
                palabra.append(matriz[p.x][p.y]);
            }
            return palabra.toString();
        }

        public void limpiarSelecciones() {
            for (int i = 0; i < tamaño; i++) {
                for (int j = 0; j < tamaño; j++) {
                    seleccionado[i][j] = false;
                }
            }
            todasLasSelecciones.clear();
            seleccionActual.clear();
            inicioSeleccion = null;
            palabrasSeleccionadas.clear();

            for (int i = 0; i < tamaño; i++) {
                for (int j = 0; j < tamaño; j++) {
                    letrasLabels[i][j].setBackground(colorFondoCelda);
                    letrasLabels[i][j].setForeground(new Color(60, 60, 60));
                }
            }
        }

        private int calcularTamañoCelda() {
            if (tamaño <= 15) {
                return 35;
            } else if (tamaño <= 20) {
                return 30;
            } else {
                return 25;
            }
        }

        private int calcularTamañoFuente(int tamañoCelda) {
            if (tamañoCelda >= 35) {
                return 16;
            } else if (tamañoCelda >= 30) {
                return 14;
            } else {
                return 12;
            }
        }

        private JPanel crearPanelLateral() {
            JPanel panelLateral = new JPanel(new BorderLayout(10, 10));
            panelLateral.setBackground(Color.WHITE);
            panelLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel panelEnunciados = new JPanel(new BorderLayout());
            panelEnunciados.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(COLOR_PRIMARIO),
                            "Palabras a buscar"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            panelEnunciados.setBackground(Color.WHITE);

            JTextArea areaEnunciados = new JTextArea();
            areaEnunciados.setEditable(false);
            areaEnunciados.setWrapStyleWord(true);
            areaEnunciados.setLineWrap(true);
            areaEnunciados.setBackground(new Color(248, 248, 248));
            areaEnunciados.setFont(new Font("Arial", Font.PLAIN, 12));
            areaEnunciados.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            StringBuilder enunciados = new StringBuilder();
            int contador = 1;
            for (Map.Entry<String, String> entry : palabrasEnunciados.entrySet()) {
                enunciados.append(contador).append(". ").append(entry.getValue()).append("\n\n");
                contador++;
            }
            areaEnunciados.setText(enunciados.toString());

            JScrollPane scrollEnunciados = new JScrollPane(areaEnunciados);
            scrollEnunciados.setPreferredSize(new Dimension(250, 300));
            scrollEnunciados.getVerticalScrollBar().setUnitIncrement(16);
            panelEnunciados.add(scrollEnunciados, BorderLayout.CENTER);

            JPanel panelInstrucciones = new JPanel(new BorderLayout());
            panelInstrucciones.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(new Color(150, 150, 150)),
                            "Instrucciones"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            panelInstrucciones.setBackground(Color.WHITE);

            JTextArea areaInstrucciones = new JTextArea(
                    "• Haz clic en la primera letra\n" +
                            "• Arrastra en cualquier dirección\n" +
                            "• Suelta para completar\n" +
                            "• Las selecciones se mantienen\n" +
                            "• Tu puntaje se basará en las\n" +
                            "  palabras que selecciones\n" +
                            "• Usa 'Limpiar Selecciones' para reiniciar");
            areaInstrucciones.setEditable(false);
            areaInstrucciones.setBackground(new Color(240, 248, 255));
            areaInstrucciones.setFont(new Font("Arial", Font.PLAIN, 11));
            areaInstrucciones.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            panelInstrucciones.add(areaInstrucciones, BorderLayout.CENTER);

            panelLateral.add(panelEnunciados, BorderLayout.CENTER);
            panelLateral.add(panelInstrucciones, BorderLayout.SOUTH);

            return panelLateral;
        }

        public List<String> getPalabrasSeleccionadas() {
            return new ArrayList<>(palabrasSeleccionadas);
        }

        public char[][] getMatriz() {
            return matriz;
        }
    }
}