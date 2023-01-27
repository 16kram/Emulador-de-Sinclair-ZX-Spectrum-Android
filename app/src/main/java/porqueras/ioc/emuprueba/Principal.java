package porqueras.ioc.emuprueba;

/**
 * @author Esteban Porqueras
 */

import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class Principal implements Runnable {
    private Z80 z;
    public static int astable = 0;
    public static double tiempoTotal = 0;//Tiempo en ms para calcular la velocidad del emulador
    private int tiempoFrame = 20;//Número de frames por segundo
    private int frames;
    private long inicioTemp;
    private int velocidadEmu = 0;
    public static int[] border = new int[312];
    public static int ptrBorde = 0;
    private static int[] borderBuffer = new int[312];
    private static int ptrBorderBuffer = 0;
    private static int borde = 7;//Color del borde
    int muestraBorder = 0;
    public static boolean ponBorde = false;
    public static boolean dibujaPantalla=false;

    public Principal(Z80 z) {
        frames = 0;
        inicioTemp = System.currentTimeMillis();//Guarda el tiempo inicial
        this.z = z;
        z.resetZ80();
    }

    //Guarda el color del borde de la pantalla que envía el Z-80
    public static void setBorde(int border) {
        borde = border;
    }


    //Actualiza el borde de la pantalla en el buffer
    public static void actualizaBorde() {
        borderBuffer[ptrBorderBuffer] = borde;
        ptrBorderBuffer++;
    }

    //Rellena el borde, se utiliza para poner el color del borde en las cargas SNA y cuando se hace un Reset
    public static void llenaBorder(int color) {
        //Arrays.fill(border,color);
        for (int n = 0; n < 312; n++) {
            border[n] = color;
        }
    }


    @Override
    public synchronized void run() {

        while (true) {
            z.clock();

            if ((z.gettStates() - muestraBorder) > 224) {//Cada final de línea actualiza el borde de la pantalla
                muestraBorder = z.gettStates();
                actualizaBorde();
            }

            if (z.gettStates() > 69888) {//A los 69888 T-States se completa un cuadro de TV
                /*if (sonidoActivado) {
                    sonido.play();
                }*/
                frames++;//Incrementa el número de cuadros
                if (frames > 16) {
                    astable = (astable ^ 1);//Realiza el parpadeo de la pantalla (FLASH)
                    frames = 0;
                    if (tiempoTotal != 0) {//El tiempoTotal ha de ser !=0 para que no haya error de división entre cero
                        velocidadEmu = (int) (2000 / tiempoTotal);
                        //Log.d("velocidad","velocidad emulador="+velocidadEmu+"%");
                    }
                }
                border = Arrays.copyOf(borderBuffer, 312);//Copia el buffer del borde dentro del array del pantalla del borde
                ponBorde = true;
                z.settStates(0);
                muestraBorder = 0;
                ptrBorderBuffer = 0;
                z.Int();
                while ((System.currentTimeMillis() - inicioTemp) < tiempoFrame) {
                }
                tiempoTotal = System.currentTimeMillis() - inicioTemp;
                //System.out.println(tiempoTotal);
                inicioTemp = System.currentTimeMillis();//Guarda el tiempo inicial
            }
        }
    }
}
