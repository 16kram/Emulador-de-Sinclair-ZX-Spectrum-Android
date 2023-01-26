package porqueras.ioc.emuprueba;

/**
 * @author Esteban Porqueras
 */

import static java.lang.System.in;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Cargar {
    Context context;
    Z80 z;
    private int numBloque;
    private final int[] buffer = new int[0xfffff];
    private final int[] tamBloque = new int[1024];//Longitud de los bloques de datos
    private final ArrayList<BloqueDatos> bloque = new ArrayList();

    public Cargar(Context context) {
        this.context = context;
    }

    //Carga la ROM del Spectrum
    public void loadRom() {
        int contador = 0;//Inicio memoria Spectrum
        try {
            InputStream in = context.getResources().openRawResource(R.raw.spectrum);
            int c;
            while ((c = in.read()) != -1) {
                Memoria.romRam[contador] = c;
                contador++;
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Log.d("Error", "Error en la carga");
        }
    }

    //Carga una pantalla de presentación
    public void cargarPantalla(int idPantalla) {
        int contador = 16384;//Inicio pantalla Spectrum
        InputStream in = context.getResources().openRawResource(idPantalla);
        try {
            int c;
            while ((c = in.read()) != -1) {
                Memoria.romRam[contador] = c;
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Error", "Error al leer el archivo");
        }
    }

    public void setZ80(Z80 z) {
        this.z = z;
    }

    //Carga un archivo SnapShot SNA
    public synchronized void cargarSnapShot(int idJuego) {
        try {
            InputStream in = context.getResources().openRawResource(idJuego);
            z.setI(in.read());
            z.escribeL_(in.read());
            z.escribeH_(in.read());
            z.escribeE_(in.read());
            z.escribeD_(in.read());
            z.escribeC_(in.read());
            z.escribeB_(in.read());
            z.escribeF_(in.read());
            z.escribeA_(in.read());
            z.escribeL(in.read());
            z.escribeH(in.read());
            z.escribeE(in.read());
            z.escribeD(in.read());
            z.escribeC(in.read());
            z.escribeB(in.read());
            z.escribeIyL(in.read());
            z.escribeIyH(in.read());
            z.escribeIxL(in.read());
            z.escribeIxH(in.read());
            int interrupt = in.read();
            z.setIFF();//Habilita las interrupciones
            z.escribeR(in.read());
            z.escribeF(in.read());
            z.escribeA(in.read());
            z.escribeP(in.read());
            z.escribeS(in.read());
            int intMode = in.read();//Modo de interupción
            switch (intMode) {
                case 0:
                    z.setIM0();
                    break;
                case 1:
                    z.setIM1();
                    break;
                case 2:
                    z.setIM2();
            }
            int border = in.read();
            for (int n = 16384; n < 65536; n++) {
                Memoria.escribe(n, in.read());
            }
            Principal.llenaBorder(border);
            /*p.repaint();
            p.repaint();
            p.repaint();*/
            z.retN();
        } catch (Exception e) {
            //Error
            Log.d("Error", "Error de carga " + e);
        }
    }

    //Carga un archivo TAP
    public void cargaCinta(int idJuego) {
        numBloque = 0;
        bloque.clear();
        BloqueDatos bloqueDatos;
        int contador = 0;
        try {
            InputStream in = context.getResources().openRawResource(idJuego);
            int c;
            while ((c = in.read()) != -1) {
                buffer[contador] = c;
                contador++;
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int posDato;
        int posBuffer = 0;
        int ptTam = 0;
        while ((buffer[posBuffer] + (buffer[posBuffer + 1] * 256)) != 0) {//Si hay bloques de datos sigue añadiéndolos
            bloqueDatos = new BloqueDatos();
            int longitudDatos = buffer[posBuffer] + (buffer[posBuffer + 1] * 256);//Longitud del bloque de datos a cargar
            tamBloque[ptTam++] = longitudDatos;//Guarda la longitud del tamaño del bloque de datos
            posBuffer += 2;//Saltamos la longitud del bloque para no añadir estos datos al bloque de datos
            int finalDatos = posBuffer + longitudDatos;
            posDato = 0;
            do {
                bloqueDatos.setDato(posDato, buffer[posBuffer]);
                posDato++;
                posBuffer++;
            } while (posBuffer < finalDatos);
            bloque.add(bloqueDatos);
            System.out.println("longitud datos=" + longitudDatos);
        }
    }

    //Devuelve el dato indicado del bloque actual
    public int getDatoBloqueActual(int numDato) {
        return bloque.get(this.numBloque).getDato(numDato);
    }

    //Incrementa el número de bloque actual
    public void IncNumBloqueActual() {
        numBloque++;
    }

    //Retorna el número de bloque actual
    public int getNumBloqueActual() {
        return numBloque;
    }

    //Añade el número de bloque pasado como parámetro
    public void setNumBloque(int pos) {
        numBloque = pos;
    }

    //Retorna el tamaño del número de bloques de datos que hay amacenados en el ArrayList bloque
    public int getNumBloques() {
        return bloque.size();
    }

    //Devuelve la longitud del bloque de datos en curso
    public int getTamBloque() {
        return tamBloque[numBloque] - 2;
    }

}
