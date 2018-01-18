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

public class AulaAdapter extends BaseAdapter{

    private final List<Aula> aulas;
    private final Context context;

    public AulaAdapter(Context context, List<Aula> aulas) {
        this.aulas = aulas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return aulas.size();
    }

    @Override
    public Object getItem(int position) {
        return aulas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aula aula = aulas.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.list_aulas, parent, false);
        }

        if (position % 2 == 1) {
            view.setBackgroundColor(Color.WHITE);
        } else {
            view.setBackgroundColor(Color.parseColor("#f5f1f2"));
        }

        TextView numero = (TextView) view.findViewById(R.id.item_au_numero);
        numero.setText(aula.getNumero());

        TextView data = (TextView) view.findViewById(R.id.item_au_data);
        data.setText(aula.getDataAula());

        TextView dia = (TextView) view.findViewById(R.id.item_au_dia);
        dia.setText(aula.getDia());

        TextView hora = (TextView) view.findViewById(R.id.item_au_hora);
        hora.setText(aula.getHora());

        TextView instrutor = (TextView) view.findViewById(R.id.item_au_instrutor);
        instrutor.setText(aula.getInstrutor());

        TextView modelo = (TextView) view.findViewById(R.id.item_au_modelo);
        modelo.setText(aula.getModelo());

        return view;
    }
}
