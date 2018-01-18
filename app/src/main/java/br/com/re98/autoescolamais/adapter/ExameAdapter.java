package br.com.re98.autoescolamais.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import br.com.re98.autoescolamais.R;
import br.com.re98.autoescolamais.modelo.Aula;
import br.com.re98.autoescolamais.modelo.Exame;

public class ExameAdapter extends BaseAdapter{

    private final List<Exame> exames;
    private final Context context;

    public ExameAdapter(Context context, List<Exame> exames) {
        this.exames = exames;
        this.context = context;
    }

    @Override
    public int getCount() {
        return exames.size();
    }

    @Override
    public Object getItem(int position) {
        return exames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exame exame = exames.get(position);



        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.list_exames, parent, false);
        }

        if (position % 2 == 1) {
            view.setBackgroundColor(Color.WHITE);
        } else {
            view.setBackgroundColor(Color.parseColor("#f5f1f2"));
        }

        TextView numero = (TextView) view.findViewById(R.id.item_ex_numero);
        numero.setText(exame.getNumero());

        TextView tipo = (TextView) view.findViewById(R.id.item_ex_tipo);
        tipo.setText(exame.getTipo());

        TextView categoria = (TextView) view.findViewById(R.id.item_ex_categoria);
        categoria.setText(exame.getCategoria());

        TextView dataexame = (TextView) view.findViewById(R.id.item_ex_data);
        dataexame.setText(exame.getDataexames());

        TextView resultado = (TextView) view.findViewById(R.id.item_ex_resultado);
        resultado.setText(exame.getResultado());

        return view;
    }
}
