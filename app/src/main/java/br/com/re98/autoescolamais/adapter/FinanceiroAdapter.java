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
import br.com.re98.autoescolamais.modelo.Financeiro;


public class FinanceiroAdapter extends BaseAdapter{

    private final List<Financeiro> financeiros;
    private final Context context;

    public FinanceiroAdapter(Context context, List<Financeiro> financeiros) {
        this.financeiros = financeiros;
        this.context = context;
    }


    @Override
    public int getCount() {
        return financeiros.size();
    }

    @Override
    public Object getItem(int position) {
        return financeiros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Financeiro financeiro = financeiros.get(position);



        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.list_financeiro, parent, false);
        }

        if (position % 2 == 1) {
            view.setBackgroundColor(Color.WHITE);
        } else {
            view.setBackgroundColor(Color.parseColor("#f5f1f2"));
        }


        TextView numero = (TextView) view.findViewById(R.id.item_numero);
        numero.setText(financeiro.getNumero());

        TextView descricao = (TextView) view.findViewById(R.id.item_descricao);
        descricao.setText(financeiro.getDescricao());

        TextView valor = (TextView) view.findViewById(R.id.item_valor);
        valor.setText(financeiro.getValor());

        TextView vencimento = (TextView) view.findViewById(R.id.item_vencimento);
        vencimento.setText(financeiro.getVencimento());

        TextView pagto = (TextView) view.findViewById(R.id.item_pagto);
        pagto.setText(financeiro.getPagto());

        return view;
    }
}
