package porqueras.ioc.emuprueba;

/**
 * @author Esteban Porqueras
 */

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    Z80 z;
    Principal principal;
    Thread hiloPrincipal;
    Button boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9, boton0;
    Button botonq, botonw, botone, botonr, botont, botony, botonu, botoni, botono, botonp;
    Button botona, botons, botond, botonf, botong, botonh, botonj, botonk, botonl, botonenter;
    Button botoncapsshift, botonz, botonx, botonc, botonv, botonb, botonn, botonm, botonsymbolshift, botonspace;
    SharedPreferences preferencias;
    View decorView;
    private float x1, x2;
    private float y1, y2;
    private boolean kempstonActivado = false;


    //Paleta de colores del Spectrum de 48K
    private static final int[] COLORES48k = {
            0xff060800, /* negro */
            0xff0d13a7, /* azul */
            0xffbd0707, /* rojo */
            0xffc312af, /* magenta */
            0xff07ba0c, /* verde */
            0xff0dc6b4, /* cyan */
            0xffbcb914, /* amarillo */
            0xffc2c4bc, /* blanco */
            0xff060800, /* negro "brillante" */
            0xff161cb0, /* azul brillante */
            0xffce1818, /* rojo brillante	*/
            0xffdc2cc8, /* magenta brillante */
            0xff28dc2d, /* verde brillante */
            0xff36efde, /* cyan brillante */
            0xffeeeb46, /* amarillo brillante */
            0xfffdfff7  /* blanco brillante */
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard_spectrum);

        //Habilitamos la aceleración por hardware
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        //Mantenemos la pantalla en orientación vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Mantenemos la pantalla encendida mientras la actividad esté encendida
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Objeto para el menú de preferencias
        preferencias = getSharedPreferences("porqueras.ioc.emuprueba_preferences", MODE_PRIVATE);

        //Habilita el joystick Kempston si está selccionado en las preferencias
        kempstonActivado = preferencias.getBoolean("kempston", false);

        //SharedPreferences.Editor editor = preferencias.edit();
        //editor.putBoolean("carga",true);
        //editor.commit();

        /*decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/

        //Añadimos el screen del Spectrum
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layoutSpectrum);
        Pantalla pantalla = new Pantalla(this);
        layout.addView(pantalla);

        //Añadimos el listener de la pantalla para que se pueda usar como joystick táctil
        layout.setOnTouchListener(this);

        //Añadimos los listeners del teclado
        botoncapsshift = (Button) findViewById(R.id.buttoncapsshift);
        botoncapsshift.setOnTouchListener(this);
        botonz = (Button) findViewById(R.id.buttonz);
        botonz.setOnTouchListener(this);
        botonx = (Button) findViewById(R.id.buttonx);
        botonx.setOnTouchListener(this);
        botonc = (Button) findViewById(R.id.buttonc);
        botonc.setOnTouchListener(this);
        botonv = (Button) findViewById(R.id.buttonv);
        botonv.setOnTouchListener(this);
        botonb = (Button) findViewById(R.id.buttonb);
        botonb.setOnTouchListener(this);
        botonn = (Button) findViewById(R.id.buttonn);
        botonn.setOnTouchListener(this);
        botonm = (Button) findViewById(R.id.buttonm);
        botonm.setOnTouchListener(this);
        botonsymbolshift = (Button) findViewById(R.id.buttonsymbolshift);
        botonsymbolshift.setOnTouchListener(this);
        botonspace = (Button) findViewById(R.id.buttonspace);
        botonspace.setOnTouchListener(this);

        botona = (Button) findViewById(R.id.buttona);
        botona.setOnTouchListener(this);
        botons = (Button) findViewById(R.id.buttons);
        botons.setOnTouchListener(this);
        botond = (Button) findViewById(R.id.buttond);
        botond.setOnTouchListener(this);
        botonf = (Button) findViewById(R.id.buttonf);
        botonf.setOnTouchListener(this);
        botong = (Button) findViewById(R.id.buttong);
        botong.setOnTouchListener(this);
        botonh = (Button) findViewById(R.id.buttonh);
        botonh.setOnTouchListener(this);
        botonj = (Button) findViewById(R.id.buttonj);
        botonj.setOnTouchListener(this);
        botonk = (Button) findViewById(R.id.buttonk);
        botonk.setOnTouchListener(this);
        botonl = (Button) findViewById(R.id.buttonl);
        botonl.setOnTouchListener(this);
        botonenter = (Button) findViewById(R.id.buttonenter);
        botonenter.setOnTouchListener(this);

        botonq = (Button) findViewById(R.id.buttonq);
        botonq.setOnTouchListener(this);
        botonw = (Button) findViewById(R.id.buttonw);
        botonw.setOnTouchListener(this);
        botone = (Button) findViewById(R.id.buttone);
        botone.setOnTouchListener(this);
        botonr = (Button) findViewById(R.id.buttonr);
        botonr.setOnTouchListener(this);
        botont = (Button) findViewById(R.id.buttont);
        botont.setOnTouchListener(this);
        botony = (Button) findViewById(R.id.buttony);
        botony.setOnTouchListener(this);
        botonu = (Button) findViewById(R.id.buttonu);
        botonu.setOnTouchListener(this);
        botoni = (Button) findViewById(R.id.buttoni);
        botoni.setOnTouchListener(this);
        botono = (Button) findViewById(R.id.buttono);
        botono.setOnTouchListener(this);
        botonp = (Button) findViewById(R.id.buttonp);
        botonp.setOnTouchListener(this);

        boton1 = (Button) findViewById(R.id.button1);
        boton1.setOnTouchListener(this);
        boton2 = (Button) findViewById(R.id.button2);
        boton2.setOnTouchListener(this);
        boton3 = (Button) findViewById(R.id.button3);
        boton3.setOnTouchListener(this);
        boton4 = (Button) findViewById(R.id.button4);
        boton4.setOnTouchListener(this);
        boton5 = (Button) findViewById(R.id.button5);
        boton5.setOnTouchListener(this);
        boton6 = (Button) findViewById(R.id.button6);
        boton6.setOnTouchListener(this);
        boton7 = (Button) findViewById(R.id.button7);
        boton7.setOnTouchListener(this);
        boton8 = (Button) findViewById(R.id.button8);
        boton8.setOnTouchListener(this);
        boton9 = (Button) findViewById(R.id.button9);
        boton9.setOnTouchListener(this);
        boton0 = (Button) findViewById(R.id.button0);
        boton0.setOnTouchListener(this);

        //Inicializa el teclado
        LeeTeclas leeTeclas = new LeeTeclas();

        //Inicializa el Z80;
        z = new Z80();

        //Carga la ROM del Spectrum
        Cargar cargar = new Cargar(this);
        cargar.loadRom();


        //Hilo principal
        principal = new Principal(z);
        hiloPrincipal = new Thread(principal);
        hiloPrincipal.setPriority(Thread.NORM_PRIORITY);//Establece la prioridad del hilo
        hiloPrincipal.start();

    }

    //Inflamos el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //Acciones del menú
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sna:
                Intent intentLista = new Intent(this, ListaJuegos.class);
                startActivityForResult(intentLista, 1234);
                break;
            case R.id.preferencias:
                Intent i = new Intent(this, Preferencias.class);
                startActivity(i);
                break;
            case R.id.reset:
                z.resetZ80();
                break;
            case R.id.acercade:
                Intent intentAcercaDe = new Intent(this, AcercaDe.class);
                startActivity(intentAcercaDe);
                break;
            case R.id.salir:
                hiloPrincipal.destroy();
                //finishAndRemoveTask();
                finishActivity(0);
        }
        return false;
    }

    //Cuando se regresa a la actividad principal desde Preferencias
    //A la vuelta pasa por el método onRestart y recogemos las preferencias del joystick
    @Override
    protected void onRestart() {
        super.onRestart();
        kempstonActivado = preferencias.getBoolean("kempston", false);
    }

    //Devuelve del ReciclerView el programa que se tiene que cargar
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Carga los juegos
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            int res = data.getExtras().getInt("resultado");
            z.resetZ80();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Cargar cargar = new Cargar(this);
            z.setCargar(cargar);
            z.halt();
            cargar.setZ80(z);
            //cargar.cargarSnapShot(res);
            cargar.cargaCinta(res);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            z.noHalt();
            cargaAutomatica();
        }
    }

    //Escribe automaticamente LOAD "" para cargar el programa seleccionado
    public void cargaAutomatica() {
        boolean carga = preferencias.getBoolean("carga", true);
        if (carga) {
            //Carga automática
            LeeTeclas.KROW6 &= (byte) 183; //J
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
            LeeTeclas.KROW6 = -65;
            LeeTeclas.KROW7 &= 253;
            LeeTeclas.KROW5 &= (byte) 190; //P
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
            LeeTeclas.KROW7 = -65;
            LeeTeclas.KROW5 = -65;
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {

            }
            LeeTeclas.KROW7 &= 253;
            LeeTeclas.KROW5 &= (byte) 190; //P
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
            LeeTeclas.KROW5 = -65;
            LeeTeclas.KROW7 = -65;
            LeeTeclas.KROW6 &= (byte) 190; //ENTER
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {

            }
            LeeTeclas.KROW6 = -65;
        }
    }


    //Revisa si se ha pulsado una tecla o si se ha hecho una acción con el joystick
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //Si se pulsa la tecla
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.buttoncapsshift:
                    LeeTeclas.KROW0 &= 254;//Caps Shift (SHIFT)
                    break;
                case R.id.buttonz:
                    LeeTeclas.KROW0 &= (byte) 189; //Z
                    break;
                case R.id.buttonx:
                    LeeTeclas.KROW0 &= (byte) 187; //X
                    break;
                case R.id.buttonc:
                    LeeTeclas.KROW0 &= (byte) 183; //C
                    break;
                case R.id.buttonv:
                    LeeTeclas.KROW0 &= (byte) 175; //V
                    break;
                case R.id.buttona:
                    LeeTeclas.KROW1 &= (byte) 190; //A
                    break;
                case R.id.buttons:
                    LeeTeclas.KROW1 &= (byte) 189; //S
                    break;
                case R.id.buttond:
                    LeeTeclas.KROW1 &= (byte) 187; //D
                    break;
                case R.id.buttonf:
                    LeeTeclas.KROW1 &= (byte) 183; //F
                    break;
                case R.id.buttong:
                    LeeTeclas.KROW1 &= (byte) 175; //G
                    break;
                case R.id.buttonq:
                    LeeTeclas.KROW2 &= (byte) 190; //Q
                    break;
                case R.id.buttonw:
                    LeeTeclas.KROW2 &= (byte) 189; //W
                    break;
                case R.id.buttone:
                    LeeTeclas.KROW2 &= (byte) 187; //E
                    break;
                case R.id.buttonr:
                    LeeTeclas.KROW2 &= (byte) 183; //R
                    break;
                case R.id.buttont:
                    LeeTeclas.KROW2 &= (byte) 175; //T
                    break;
                case R.id.button1:
                    LeeTeclas.KROW3 &= (byte) 190; //1
                    break;
                case R.id.button2:
                    LeeTeclas.KROW3 &= (byte) 189; //2
                    break;
                case R.id.button3:
                    LeeTeclas.KROW3 &= (byte) 187; //3
                    break;
                case R.id.button4:
                    LeeTeclas.KROW3 &= (byte) 183; //4
                    break;
                case R.id.button5:
                    LeeTeclas.KROW3 &= (byte) 175; //5
                    break;
                case R.id.button0:
                    LeeTeclas.KROW4 &= (byte) 190; //0
                    break;
                case R.id.button9:
                    LeeTeclas.KROW4 &= (byte) 189; //9
                    break;
                case R.id.button8:
                    LeeTeclas.KROW4 &= (byte) 187; //8
                    break;
                case R.id.button7:
                    LeeTeclas.KROW4 &= (byte) 183; //7
                    break;
                case R.id.button6:
                    LeeTeclas.KROW4 &= (byte) 175; //6
                    break;
                case R.id.buttonp:
                    LeeTeclas.KROW5 &= (byte) 190; //P
                    break;
                case R.id.buttono:
                    LeeTeclas.KROW5 &= (byte) 189; //O
                    break;
                case R.id.buttoni:
                    LeeTeclas.KROW5 &= (byte) 187; //I
                    break;
                case R.id.buttonu:
                    LeeTeclas.KROW5 &= (byte) 183; //U
                    break;
                case R.id.buttony:
                    LeeTeclas.KROW5 &= (byte) 175; //Y
                    break;
                case R.id.buttonenter:
                    LeeTeclas.KROW6 &= (byte) 190; //ENTER
                    break;
                case R.id.buttonl:
                    LeeTeclas.KROW6 &= (byte) 189; //L
                    break;
                case R.id.buttonk:
                    LeeTeclas.KROW6 &= (byte) 187; //K
                    break;
                case R.id.buttonj:
                    LeeTeclas.KROW6 &= (byte) 183; //J
                    break;
                case R.id.buttonh:
                    LeeTeclas.KROW6 &= (byte) 175; //H
                    break;
                case R.id.buttonspace:
                    LeeTeclas.KROW7 &= (byte) 190; //SPACE
                    break;
                case R.id.buttonm:
                    LeeTeclas.KROW7 &= (byte) 187; //M
                    break;
                case R.id.buttonn:
                    LeeTeclas.KROW7 &= (byte) 183; //N
                    break;
                case R.id.buttonb:
                    LeeTeclas.KROW7 &= (byte) 175; //B
                    break;
                case R.id.buttonsymbolshift:
                    LeeTeclas.KROW7 &= 253;//Symbol Shift (CTRL)
                    break;
            }
            Log.d("tecla", "Tecla pulsada");
            x1 = event.getX();
            y1 = event.getY();
        }

        //Si se libera la tecla
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //Pausa para que se mantengan un tiempo los valores de las teclas pulsadas y las pueda leer el Z-80
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch ((v.getId())) {
                case R.id.buttoncapsshift:
                    LeeTeclas.KROW0 |= 1;//Caps Shift (SHIFT)
                    break;
                case R.id.buttonz:
                    LeeTeclas.KROW0 |= (byte) (0xff - 189); //Z
                    break;
                case R.id.buttonx:
                    LeeTeclas.KROW0 |= (byte) (0xff - 187); //X
                    break;
                case R.id.buttonc:
                    LeeTeclas.KROW0 |= (byte) (0xff - 183); //C
                    break;
                case R.id.buttonv:
                    LeeTeclas.KROW0 |= (byte) (0xff - 175); //V
                    break;
                case R.id.buttona:
                    LeeTeclas.KROW1 |= (byte) (0xff - 190); //A
                    break;
                case R.id.buttons:
                    LeeTeclas.KROW1 |= (byte) (0xff - 189); //S
                    break;
                case R.id.buttond:
                    LeeTeclas.KROW1 |= (byte) (0xff - 187); //D
                    break;
                case R.id.buttonf:
                    LeeTeclas.KROW1 |= (byte) (0xff - 183); //F
                    break;
                case R.id.buttong:
                    LeeTeclas.KROW1 |= (byte) (0xff - 175); //G
                    break;
                case R.id.buttonq:
                    LeeTeclas.KROW2 |= (byte) (0xff - 190); //Q
                    break;
                case R.id.buttonw:
                    LeeTeclas.KROW2 |= (byte) (0xff - 189); //W
                    break;
                case R.id.buttone:
                    LeeTeclas.KROW2 |= (byte) (0xff - 187); //E
                    break;
                case R.id.buttonr:
                    LeeTeclas.KROW2 |= (byte) (0xff - 183); //R
                    break;
                case R.id.buttont:
                    LeeTeclas.KROW2 |= (byte) (0xff - 175); //T
                    break;
                case R.id.button1:
                    LeeTeclas.KROW3 |= (byte) (0xff - 190); //1
                    break;
                case R.id.button2:
                    LeeTeclas.KROW3 |= (byte) (0xff - 189); //2
                    break;
                case R.id.button3:
                    LeeTeclas.KROW3 |= (byte) (0xff - 187); //3
                    break;
                case R.id.button4:
                    LeeTeclas.KROW3 |= (byte) (0xff - 183); //4
                    break;
                case R.id.button5:
                    LeeTeclas.KROW3 |= (byte) (0xff - 175); //5
                    break;
                case R.id.button0:
                    LeeTeclas.KROW4 |= (byte) (0xff - 190); //0
                    break;
                case R.id.button9:
                    LeeTeclas.KROW4 |= (byte) (0xff - 189); //9
                    break;
                case R.id.button8:
                    LeeTeclas.KROW4 |= (byte) (0xff - 187); //8
                    break;
                case R.id.button7:
                    LeeTeclas.KROW4 |= (byte) (0xff - 183); //7
                    break;
                case R.id.button6:
                    LeeTeclas.KROW4 |= (byte) (0xff - 175); //6
                    break;
                case R.id.buttonp:
                    LeeTeclas.KROW5 |= (byte) (0xff - 190); //P
                    break;
                case R.id.buttono:
                    LeeTeclas.KROW5 |= (byte) (0xff - 189); //O
                    break;
                case R.id.buttoni:
                    LeeTeclas.KROW5 |= (byte) (0xff - 187); //I
                    break;
                case R.id.buttonu:
                    LeeTeclas.KROW5 |= (byte) (0xff - 183); //U
                    break;
                case R.id.buttony:
                    LeeTeclas.KROW5 |= (byte) (0xff - 175); //Y
                    break;
                case R.id.buttonenter:
                    LeeTeclas.KROW6 |= (byte) (0xff - 190); //ENTER
                    break;
                case R.id.buttonl:
                    LeeTeclas.KROW6 |= (byte) (0xff - 189); //L
                    break;
                case R.id.buttonk:
                    LeeTeclas.KROW6 |= (byte) (0xff - 187); //K
                    break;
                case R.id.buttonj:
                    LeeTeclas.KROW6 |= (byte) (0xff - 183); //J
                    break;
                case R.id.buttonh:
                    LeeTeclas.KROW6 |= (byte) (0xff - 175); //H
                    break;
                case R.id.buttonspace:
                    LeeTeclas.KROW7 |= (byte) (0xff - 190); //SPACE
                    break;
                case R.id.buttonm:
                    LeeTeclas.KROW7 |= (byte) (0xff - 187); //M
                    break;
                case R.id.buttonn:
                    LeeTeclas.KROW7 |= (byte) (0xff - 183); //N
                    break;
                case R.id.buttonb:
                    LeeTeclas.KROW7 |= (byte) (0xff - 175); //B
                    break;
                case R.id.buttonsymbolshift:
                    LeeTeclas.KROW7 |= 2;//Symbol Shift (CTRL)
                    break;
            }
            Log.d("tecla", "Tecla liberada");
            LeeTeclas.kempston &= (0xff - 1);//Derecha cursor
            LeeTeclas.kempston &= (0xff - 2);//Izquierda cursor
            LeeTeclas.kempston &= (0xff - 8);//Arriba cursor
            LeeTeclas.kempston &= (0xff - 4);//Abajo cursor
            LeeTeclas.kempston &= (0xff - 16);////Disparo tecla <>
        }

        //JOYSTICK KEMPSTON
        //Si arrastramos el dedo
        if (event.getAction() == MotionEvent.ACTION_MOVE && kempstonActivado) {
            final int TR = 7;
            x2 = event.getX();
            y2 = event.getY();
            //Derecha y abajo
            if (x2 > (x1 + TR) && y2 > (y1 + TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 1;//Derecha cursor
                LeeTeclas.kempston |= 4;//Abajo cursor
                LeeTeclas.kempston &= (0xff - 2);//Izquierda cursor
                LeeTeclas.kempston &= (0xff - 8);//Arriba cursor
                //Log.d("mov", "DERECHA Y ABAJO--------------------------");
            }
            //Izquierda y abajo
            else if (x2 < (x1 - TR) && y2 > (y1 + TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 2;//Izquierda cursor
                LeeTeclas.kempston &= (0xff - 1);//Derecha cursor
                LeeTeclas.kempston |= 4;//Abajo cursor
                LeeTeclas.kempston &= (0xff - 8);//Arriba cursor
                //Log.d("mov", "IZQUIERDA Y ABAJO--------------------------");
            }
            //Derecha y arriba
            else if (x2 > (x1 + TR) && y2 < (y1 - TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 1;//Derecha cursor
                LeeTeclas.kempston &= (0xff - 2);//Izquierda cursor
                LeeTeclas.kempston |= 8;//Arriba cursor
                LeeTeclas.kempston &= (0xff - 4);//Abajo cursor
                //Log.d("mov", "DERECHA Y ARRIBA--------------------------");
            }
            //Izquierda y arriba
            else if (x2 < (x1 - TR) && y2 < (y1 - TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 2;//Izquierda cursor
                LeeTeclas.kempston &= (0xff - 1);//Derecha cursor
                LeeTeclas.kempston |= 8;//Arriba cursor
                LeeTeclas.kempston &= (0xff - 4);//Abajo cursor
                //Log.d("mov", "IZQUIERDA Y ARRIBA--------------------------");
            }
            //Derecha
            else if (x2 > (x1 + TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 1;//Derecha cursor
                LeeTeclas.kempston &= (0xff - 2);//Izquierda cursor
                LeeTeclas.kempston &= (0xff - 8);//Arriba cursor
                LeeTeclas.kempston &= (0xff - 4);//Abajo cursor
                //Log.d("mov", "DERECHA");
            }
            //Izquierda
            else if (x2 < (x1 - TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 2;//Izquierda cursor
                LeeTeclas.kempston &= (0xff - 1);//Derecha cursor
                LeeTeclas.kempston &= (0xff - 8);//Arriba cursor
                LeeTeclas.kempston &= (0xff - 4);//Abajo cursor
                //Log.d("mov", "IZQUIERDA");
            }
            //Abajo
            else if (y2 > (y1 + TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 4;//Abajo cursor
                LeeTeclas.kempston &= (0xff - 8);//Arriba cursor
                LeeTeclas.kempston &= (0xff - 2);//Izquierda cursor
                LeeTeclas.kempston &= (0xff - 1);//Derecha cursor
                //Log.d("mov", "ABAJO");
            }
            //Arriba
            else if (y2 < (y1 - TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 8;//Arriba cursor
                LeeTeclas.kempston &= (0xff - 4);//Abajo cursor
                LeeTeclas.kempston &= (0xff - 2);//Izquierda cursor
                LeeTeclas.kempston &= (0xff - 1);//Derecha cursor
                //Log.d("mov", "ARRIBA");
            }
            //Derecha
            else if (x2 > (x1 + TR)) {
                x1 = x2;
                y1 = y2;
                LeeTeclas.kempston |= 1;//Derecha cursor
                LeeTeclas.kempston &= (0xff - 2);//Izquierda cursor
                LeeTeclas.kempston &= (0xff - 8);//Arriba cursor
                LeeTeclas.kempston &= (0xff - 4);//Abajo cursor
                //Log.d("mov", "DERECHA");
            }
        }
        //Disparo
        if (event.getPointerCount() > 1 && kempstonActivado) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {//Si ponemos el segundo dedo dispara
                LeeTeclas.kempston |= 16;//Disparo tecla
            } else if (event.getAction() != MotionEvent.ACTION_MOVE) {//Si quitamos el segundo dedo deja de disparar
                LeeTeclas.kempston &= (0xff - 16);////Disparo tecla
            }
        }
        return true;
    }


    //Pantalla
    public class Pantalla extends View {
        private static final int DIR_MEMORIA_VIDEO = 16384;
        private static final int DIR_MEMORIA_ATRIBUTOS = 6144;
        private static final int TAM = 4, Y = 192 * TAM, X = 256 * TAM;
        private int astable = 0;//Parpadeo del Flash
        private int borde = 7;//Color del borde

        public Pantalla(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            Paint pincel = new Paint();

            //Muestra el borde de la pantalla
            int anchoVentanaBorde = canvas.getWidth();//Ancho de la ventana actual
            int altoVentanaBorde = 208 * TAM;
            int posXBorde = 0;
            int posYBorde = 0;
            int ptrBorde = 0;
            while (posYBorde < altoVentanaBorde) {
                borde = Principal.border[ptrBorde];
                pincel.setColor(COLORES48k[borde & 7]);
                canvas.drawRect(posXBorde, posYBorde, anchoVentanaBorde, posYBorde + TAM, pincel);
                posYBorde = posYBorde + TAM;
                ptrBorde++;
            }

            //Muestra el contenido de la pantalla
            int anchoVentana = canvas.getWidth();//Ancho de la ventana actual
            int altoVentana = canvas.getHeight();//Alto de la ventana actual
            //Log.d("ventana", "ancho=" + anchoVentana + " alto=" + altoVentana);
            int offsetX = (anchoVentana - X) / 2;
            //int offsetY = (altoVentana - Y) / 2;
            int offsetY = 16;
            int posX = 0 + offsetX;
            int posY = 0 + offsetY;
            int dato;//Dato de la memoria de pantalla
            int dirMemoria = DIR_MEMORIA_VIDEO;//Dirección memoria de video
            int dirAtributos = dirMemoria + DIR_MEMORIA_ATRIBUTOS;//Direcció memoria atributos color
            int scan = 0;
            int posMemX = 0;
            int posMemY = 0;
            int color, paper, ink, bright, flash;
            int posMaxX = X + offsetX;
            int posMaxY = Y + offsetY;
            while (posY < (posMaxY)) {
                while (posX < posMaxX) {
                    dato = Memoria.leePantalla(dirMemoria + (256 * scan) + posMemX);
                    color = Memoria.leePantalla(dirAtributos + posMemX + ((posMemY) * 32));
                    ink = color & 0b00000111;
                    paper = color & 0b00111000;
                    paper = paper >> 3;
                    bright = color & 0b01000000;
                    flash = color & 0b10000000;
                    if (flash == 128 && Principal.astable == 1) {
                        int aux = paper;
                        paper = ink;
                        ink = aux;
                    }
                    for (int z = 0; z < 8; z++) {
                        if ((dato & 0x80) == 0x80) {
                            if (bright == 0x40) {
                                pincel.setColor(COLORES48k[8 + ink]);
                            } else {
                                pincel.setColor(COLORES48k[ink]);
                            }
                        } else {
                            if (bright == 0x40) {
                                pincel.setColor(COLORES48k[8 + paper]);
                            } else {
                                pincel.setColor(COLORES48k[paper]);
                            }
                        }
                        dato = dato << 1;
                        canvas.drawRect(posX, posY, posX + TAM, posY + TAM, pincel);
                        posX = posX + TAM;
                    }
                    posMemX++;
                }
                posY = posY + TAM;
                posMemX = 0;
                posX = 0 + offsetX;
                scan++;
                if (scan > 7) {
                    scan = 0;
                    dirMemoria = dirMemoria + 32;
                    posMemY++;
                }
                if (posY == ((64 * TAM) + offsetY)) {
                    dirMemoria = DIR_MEMORIA_VIDEO + 2048;
                    scan = 0;
                }
                if (posY == ((128 * TAM) + offsetY)) {
                    dirMemoria = DIR_MEMORIA_VIDEO + 4096;
                    scan = 0;
                }
            }
            invalidate();//Actualiza la pantalla tan pronto como sea posible
        }
    }
}