package porqueras.ioc.emuprueba;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.util.Arrays;

/**
 * @author Esteban Porqueras
 */

public class Sonido {
    AudioTrack audioTrack;
    private int SAMPLE_RATE = 48000;//Número de muestras por segundo
    private byte muestras[] = new byte[40000];
    public static int posMuestra = 0;
    public static int valor = 0;
    private int audioTstates = 0;
    private int tiempo = 0;

    public Sonido() {
        //Crea la salida de sonido
        int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize,
                AudioTrack.MODE_STREAM);
    }

    // Reproduce el sonido
    public void play() {
        //ajustaMuestra();
        audioTrack.write(muestras, 0, posMuestra);
        audioTrack.play();
        posMuestra = 0;
    }

    //Guarda la muestras de sonido enviadas por el Z-80
    //En el array 'muestras' para luego reproducirlo
    public void guardaMuestra() {
        //Canal monofásico
        muestras[posMuestra + 0] = (byte) (valor & 0xFF); //byte bajo
        muestras[posMuestra + 1] = (byte) ((valor >> 8) & 0xFF); //byte alto
        posMuestra += 2;
    }

    //Añade el valor que envía el z-80 del buzzer y del microfono en una variable
    public void setValor(int buz, int mic) {
        int nivel = 0;
        nivel = (buz == 1) ? (int) 16384 : 0;
        nivel += (mic == 1) ? (int) 8192 : 0;
        //valor = valor - (valor / 16);
        //valor = valor + (nivel / 16);
        valor = nivel;
        //System.out.println(valor);
    }

    //Actualiza la muestra de sonido
    public void actualizaMuestra(int tStates) {
        tStates = tStates - audioTstates;
        audioTstates = audioTstates + tStates;
        int dif = tStates - 72;
        tiempo = tiempo + dif;
        if (tiempo > 71) {
            guardaMuestra();
            tiempo = tiempo - 72;
            //System.out.println(tiempo);
        }
        guardaMuestra();
    }

    //Ajusta la muestra de sonido
    public void ajustaMuestra() {
        while (posMuestra < 3840) {
            guardaMuestra();
        }
    }

    //Pone a cero el array de muestras de sonido
    public void reset() {
        Arrays.fill(muestras, (byte) 0);
        audioTstates = 0;
        posMuestra = 0;
    }

}
