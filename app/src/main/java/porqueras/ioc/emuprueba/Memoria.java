package porqueras.ioc.emuprueba;

/**
 * @author Esteban Porqueras
 */

public class Memoria {
    public static int[] romRam = new int[65536];

    public synchronized static void escribe(int posMemoria, int dato) {
        if (posMemoria > 0x3FFF) {//Se está escribiendo por encima de la ROM
            romRam[posMemoria] = dato;
        }
    }

    public synchronized static int lee(int posMemoria) {
        return romRam[posMemoria];
    }

    public static int leePantalla(int posMemoria) {
        return romRam[posMemoria];
    }
}
