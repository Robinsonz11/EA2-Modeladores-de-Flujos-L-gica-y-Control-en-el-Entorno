import java.util.Scanner;

/**
 * Control de inventario simulado para una tienda de barrio.
 * Los datos de stock los digita el usuario (datos simulados, sin base de datos).
 */
public class InventarioTienda {

    static final int STOCK_MAXIMO = 500;   // supuesto: capacidad máxima por producto
    static final int UMBRAL_BAJO = 10;     // supuesto: stock bajo si es <= 10 unidades

    // Decisión: cuántas unidades pedir según el stock actual
    static int unidadesAPedir(int stock) {
        if (stock == 0) {
            return 30;              // agotado: pedido grande
        } else if (stock <= UMBRAL_BAJO) {
            return 20;              // stock bajo: pedido normal
        } else {
            return 0;               // stock suficiente: no pedir
        }
    }

    static String estado(int stock) {
        if (stock == 0) {
            return "AGOTADO";
        } else if (stock <= UMBRAL_BAJO) {
            return "BAJO";
        }
        return "SUFICIENTE";
    }

    // Si la entrada se cierra (fin de datos), termina el programa de forma controlada
    static void verificarEntrada(Scanner sc) {
        if (!sc.hasNextLine()) {
            System.out.println("\nEntrada finalizada. Se cierra el programa.");
            System.exit(0);
        }
    }

    // Lee un entero; repite hasta que sea un número válido dentro de [min, max]
    static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            verificarEntrada(sc);
            String linea = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Error: el valor debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero.");
            }
        }
    }

    // Lee un texto; repite hasta que no esté vacío
    static String leerTexto(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            verificarEntrada(sc);
            String texto = sc.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Error: el nombre no puede estar vacío.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Inventario de tienda de barrio (datos simulados) ===");

        int cantidad = leerEntero(sc, "Número de productos a revisar (1-10): ", 1, 10);
        int totalAPedir = 0;
        int agotados = 0;

        for (int i = 1; i <= cantidad; i++) {
            String nombre = leerTexto(sc, "Producto " + i + " - nombre: ");
            int stock = leerEntero(sc, "  Stock actual de " + nombre + " (0-" + STOCK_MAXIMO + "): ", 0, STOCK_MAXIMO);
            int pedir = unidadesAPedir(stock);
            totalAPedir += pedir;
            if (stock == 0) {
                agotados++;
            }
            System.out.println("  -> Estado: " + estado(stock) + " | Pedir: " + pedir + " unidades");
        }

        System.out.println("---------------------------------------------");
        System.out.println("Productos revisados: " + cantidad);
        System.out.println("Productos agotados: " + agotados);
        System.out.println("Total de unidades a pedir: " + totalAPedir);
        sc.close();
    }
}
