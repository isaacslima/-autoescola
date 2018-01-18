package br.com.re98.autoescolamais.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.re98.autoescolamais.converter.ConverteData;
import br.com.re98.autoescolamais.modelo.Financeiro;


public class FinanceiroDAO  extends SQLiteOpenHelper {


    public FinanceiroDAO(Context context){
        super(context, "Autoescola", null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


    public List<Financeiro> buscaFinanceiro() {
        String sql = "SELECT * FROM Financeiro;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Financeiro> financeiros = new ArrayList<Financeiro>();

        financeiros = populaFinanceiro(c);
        c.close();

        return financeiros;

    }

    private List<Financeiro> populaFinanceiro(Cursor c) {
        List<Financeiro> financeiros = new ArrayList<Financeiro>();


        ConverteData dt = new ConverteData();

        while(c.moveToNext()){
            Financeiro financeiro = new Financeiro();
            financeiro.setId(c.getString(c.getColumnIndex("id")));
            financeiro.setNumero(c.getString(c.getColumnIndex("numero")));
            financeiro.setDescricao(c.getString(c.getColumnIndex("descricao")));
            financeiro.setValor(c.getString(c.getColumnIndex("valor")));
            financeiro.setValor("R$ " + financeiro.getValor() );
            financeiro.setVencimento(c.getString(c.getColumnIndex("vencimento")));
            String pagto = c.getString(c.getColumnIndex("pagto"));
            if(pagto == null || pagto == ""){
                financeiro.setPagto("");
            } else{
                financeiro.setPagto(pagto);
            }

            financeiros.add(financeiro);

        }
        return financeiros;
    }

    public void sincroniza(List<Financeiro> financeiros) {
        for (Financeiro financeiro:
                financeiros ) {
            if(existe(financeiro)){
                altera(financeiro);
            } else {
                insere(financeiro);
            }
        }
    }

    private boolean existe(Financeiro financeiro) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT id FROM Financeiro WHERE id=? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{financeiro.getId()});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }


    public void altera(Financeiro financeiro) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosFinanceiro(financeiro);
        String[] params = {financeiro.getId().toString()};
        db.update("Financeiro", dados,"id = ?", params);

    }



    public void insere(Financeiro financeiro) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosFinanceiro(financeiro);
        db.insert("Financeiro", null, dados);
    }

    private ContentValues pegaDadosFinanceiro(Financeiro financeiro) {
        ContentValues dados = new ContentValues();
        dados.put("id", financeiro.getId());
        dados.put("descricao", financeiro.getDescricao());
        dados.put("numero", financeiro.getNumero());
        dados.put("pagto", financeiro.getPagto());
        dados.put("restante", financeiro.getRestante());
        dados.put("valor", financeiro.getValor());
        dados.put("vencimento", financeiro.getVencimento());
        Log.i("pegaDadosFinanceiro", ""+financeiro.getVencimento());
        return dados;
    }

}
