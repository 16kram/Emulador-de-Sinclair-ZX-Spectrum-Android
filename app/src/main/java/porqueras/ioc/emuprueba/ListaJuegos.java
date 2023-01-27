package porqueras.ioc.emuprueba;

/**
 * @author Esteban Porqueras
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ListaJuegos extends AppCompatActivity implements WordListAdapter.ListItemClick {

    private final LinkedList<String> listaJuegos = new LinkedList<>();
    private final LinkedList<Integer> idJuego = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_juegos);

        //Lista de juegos en la carpeta raw
        ArrayList<String> rawFiles = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();
        for (int count = 0; count < fields.length; count++) {
            rawFiles.add(fields[count].getName());

        }

        //Ordenamos alfabeticamente los juegos
        Collections.sort(rawFiles);

        //Añadimos los juegos a la lista
        for (String juego : rawFiles) {
            if (juego != "spectrum") {
                listaJuegos.add(juego);
            }
        }

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, listaJuegos, this);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //Recibe la posición del elemento clicado de la clase WordListAdapter
    @Override
    public void onListItemClick(String clickedItem) {
        Intent intent = new Intent();
        intent.putExtra("resultado", getResources().getIdentifier(clickedItem, "raw", getPackageName()));
        setResult(RESULT_OK, intent);
        finish();
    }
}