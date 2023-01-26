package porqueras.ioc.emuprueba;

/**
 * @author Esteban Porqueras
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;
    final private ListItemClick mOnClickListener;

    public interface ListItemClick {
        void onListItemClick(String clickedItem);
    }

    //Constructor, obtiene el inflador del contexto y sus datos
    public WordListAdapter(Context context, LinkedList<String> mWordList, ListItemClick listener) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = mWordList;
        mOnClickListener = listener;
    }

    //Crea un View y lo devuelve
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mIemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mIemView, this);
    }

    //Asocia los datos con el ViewHolder para una posición dada en el RecyclerView
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.palabra.setText(mCurrent);
    }

    //Retorna el número de elementos de datos disponibles para mostrar
    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    //Clase interna
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView palabra;
        public WordListAdapter mAdapter;
        Toast toast;

        public WordViewHolder(@NonNull View itemView, WordListAdapter wordListAdapter) {
            super(itemView);
            palabra = itemView.findViewById(R.id.word);
            this.mAdapter = wordListAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Coge la posición del objeto pulsado
            int posicion = getLayoutPosition();
            String elemento = mWordList.get(posicion);

            //SE muestra el juego que se está cargando
            Context context = v.getContext();
            CharSequence text = elemento + " is loading...";
            if (toast != null) {//Si hay más de un Toast se cancelan los demás y sólo se muestra un mensaje
                toast.cancel();
            }
            int duration = Toast.LENGTH_LONG;
            toast = Toast.makeText(context, text, duration);
            toast.show();

            //Enviamos al interface el nombre del juego
            mOnClickListener.onListItemClick(elemento);
        }
    }

}
