// poo/proyecto1/evaluacion/realizacionEvaluacion/SopaLetrasPanel.java
package poo.proyecto1.evaluacion.realizarEvaluacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class SopaLetrasPanel extends JPanel {
    private char[][] matriz;
    private int tamaño;
    private Map<String, String> palabrasEnunciados;
    private List<String> palabras;
    private JTextArea areaEnunciados;
    private JLabel[][] letrasLabels;
    private boolean[][] seleccionado;
    private List<Point> seleccionActual;
    private Color colorSeleccion = new Color(173, 216, 230); // Azul claro

    // Direcciones
    private static final int[][] DIRECCIONES = {
            { 0, 1 }, // Horizontal izquierda a derecha
            { 0, -1 }, // Horizontal derecha a izquierda
            { 1, 0 }, // Vertical arriba a abajo
            { -1, 0 }, // Vertical abajo a arriba
            { 1, 1 }, // Diagonal arriba-abajo derecha
            { 1, -1 }, // Diagonal arriba-abajo izquierda
            { -1, 1 }, // Diagonal abajo-arriba derecha
            { -1, -1 } // Diagonal abajo-arriba izquierda
    };

    public SopaLetrasPanel(Map<String, String> palabrasEnunciados, int tamaño) {
        this.palabrasEnunciados = palabrasEnunciados;
        this.tamaño = tamaño;
        this.palabras = new ArrayList<>(palabrasEnunciados.keySet());
        this.letrasLabels = new JLabel[tamaño][tamaño];
        this.seleccionado = new boolean[tamaño][tamaño];
        this.seleccionActual = new ArrayList<>();

        setLayout(new BorderLayout());
        inicializarSopa();
        initUI();
    }

    private void inicializarSopa() {
        matriz = new char[tamaño][tamaño];

        // Inicializar matriz con espacios
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j] = ' ';
            }
        }

        // Ordenar palabras por longitud (las más largas primero)
        palabras.sort((a, b) -> Integer.compare(b.length(), a.length()));

        // Colocar palabras en las direcciones específicas para las primeras 8
        for (int i = 0; i < palabras.size(); i++) {
            String palabra = palabras.get(i).toUpperCase();
            boolean colocada = false;
            int intentos = 0;
            int maxIntentos = 100;

            while (!colocada && intentos < maxIntentos) {
                int direccion;

                // Asignar direcciones específicas para las primeras 8 palabras
                if (i < 8) {
                    direccion = i;
                } else {
                    // Dirección aleatoria para las restantes
                    direccion = (int) (Math.random() * DIRECCIONES.length);
                }

                int dx = DIRECCIONES[direccion][0];
                int dy = DIRECCIONES[direccion][1];

                // Calcular posición inicial posible
                int x = (int) (Math.random() * tamaño);
                int y = (int) (Math.random() * tamaño);

                // Verificar si cabe en la dirección elegida
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

        // Rellenar espacios vacíos con letras aleatorias
        rellenarEspaciosVacios();
    }

    private boolean puedeColocarPalabra(String palabra, int x, int y, int dx, int dy) {
        int len = palabra.length();

        // Verificar límites
        int finalX = x + (len - 1) * dx;
        int finalY = y + (len - 1) * dy;

        if (finalX < 0 || finalX >= tamaño || finalY < 0 || finalY >= tamaño) {
            return false;
        }

        // Verificar superposición
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
        // Panel para la sopa de letras
        JPanel panelSopa = new JPanel(new GridLayout(tamaño, tamaño, 2, 2));
        panelSopa.setBorder(BorderFactory.createTitledBorder("Sopa de Letras - Selecciona las palabras"));
        panelSopa.setBackground(Color.WHITE);

        // Crear labels para cada celda con interactividad
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                JLabel label = new JLabel(String.valueOf(matriz[i][j]), SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(35, 35));
                label.setFont(new Font("Monospaced", Font.BOLD, 16));
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                final int fila = i;
                final int columna = j;

                // Agregar listener para selección
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        iniciarSeleccion(fila, columna);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (seleccionActual.size() > 0) {
                            extenderSeleccion(fila, columna);
                        }
                    }
                });

                letrasLabels[i][j] = label;
                panelSopa.add(label);
            }
        }

        // Panel para enunciados
        JPanel panelEnunciados = new JPanel(new BorderLayout());
        panelEnunciados.setBorder(BorderFactory.createTitledBorder("Enunciados - Busque las siguientes palabras:"));

        areaEnunciados = new JTextArea(10, 30);
        areaEnunciados.setEditable(false);
        areaEnunciados.setWrapStyleWord(true);
        areaEnunciados.setLineWrap(true);
        areaEnunciados.setBackground(new Color(248, 248, 248));

        // Construir texto de enunciados
        StringBuilder enunciados = new StringBuilder();
        int contador = 1;
        for (Map.Entry<String, String> entry : palabrasEnunciados.entrySet()) {
            enunciados.append(contador).append(". ").append(entry.getValue()).append("\n");
            contador++;
        }
        areaEnunciados.setText(enunciados.toString());

        JScrollPane scrollEnunciados = new JScrollPane(areaEnunciados);
        panelEnunciados.add(scrollEnunciados, BorderLayout.CENTER);

        // Panel de instrucciones
        JPanel panelInstrucciones = new JPanel(new BorderLayout());
        panelInstrucciones.setBorder(BorderFactory.createTitledBorder("Instrucciones"));

        JTextArea areaInstrucciones = new JTextArea(
                "1. Haz clic en la primera letra de la palabra\n" +
                        "2. Arrastra el mouse sobre las letras contiguas\n" +
                        "3. Suelta el mouse para completar la selección\n" +
                        "4. Las palabras seleccionadas se marcarán en azul\n" +
                        "5. Escribe las palabras encontradas en el área de texto inferior");
        areaInstrucciones.setEditable(false);
        areaInstrucciones.setBackground(new Color(240, 248, 255));
        areaInstrucciones.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInstrucciones.add(areaInstrucciones, BorderLayout.CENTER);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.add(panelSopa, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.add(panelEnunciados, BorderLayout.CENTER);
        panelDerecho.add(panelInstrucciones, BorderLayout.SOUTH);

        panelPrincipal.add(panelDerecho, BorderLayout.EAST);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void iniciarSeleccion(int fila, int columna) {
        // Limpiar selección anterior
        limpiarSeleccion();

        seleccionActual.clear();
        seleccionActual.add(new Point(fila, columna));
        seleccionado[fila][columna] = true;
        letrasLabels[fila][columna].setBackground(colorSeleccion);
    }

    private void extenderSeleccion(int fila, int columna) {
        Point ultimo = seleccionActual.get(seleccionActual.size() - 1);

        // Verificar si es adyacente a la última selección
        if (Math.abs(fila - ultimo.x) <= 1 && Math.abs(columna - ultimo.y) <= 1) {
            seleccionActual.add(new Point(fila, columna));
            seleccionado[fila][columna] = true;
            letrasLabels[fila][columna].setBackground(colorSeleccion);
        }
    }

    private void limpiarSeleccion() {
        for (Point p : seleccionActual) {
            seleccionado[p.x][p.y] = false;
            letrasLabels[p.x][p.y].setBackground(Color.WHITE);
        }
    }

    // Método para obtener la palabra seleccionada
    public String getPalabraSeleccionada() {
        if (seleccionActual.size() < 2) {
            return "";
        }

        StringBuilder palabra = new StringBuilder();
        for (Point p : seleccionActual) {
            palabra.append(matriz[p.x][p.y]);
        }
        return palabra.toString();
    }

    // Método para obtener todas las palabras seleccionadas por el usuario
    public List<String> getPalabrasSeleccionadas() {
        // En esta implementación simple, el usuario escribirá las palabras
        // En una implementación más avanzada, podrías rastrear las selecciones
        return new ArrayList<>();
    }

    public List<String> getPalabras() {
        return new ArrayList<>(palabras);
    }

    public char[][] getMatriz() {
        return matriz;
    }
}