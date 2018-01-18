package br.com.re98.autoescolamais.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import br.com.re98.autoescolamais.modelo.Exame;

public class ExameDAO extends SQLiteOpenHelper {

    public ExameDAO(Context context){
        super(context, "Autoescola", null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public List<Exame> buscaExame() {
        String sql = "SELECT * FROM Exames;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Exame> exames = new ArrayList<Exame>();
        exames = populaExames(c);
        c.close();
        return exames;
    }

    private List<Exame> populaExames(Cursor c) {
        List<Exame> exames = new ArrayList<Exame>();

        while(c.moveToNext()){
            Exame exame = new Exame();
            Log.i("populaExame", "numero: " + c.getString(c.getColumnIndex("numero")) + " - data: " + c.getString(c.getColumnIndex("dataexame")));
            exame.setNumero(c.getString(c.getColumnIndex("numero")));
            exame.setId(c.getString(c.getColumnIndex("id")));
            exame.setTipo(c.getString(c.getColumnIndex("tipo")));
            exame.setDataexames(c.getString(c.getColumnIndex("dataexame")));
            exame.setResultado(c.getString(c.getColumnIndex("resultado")));
            String categoria = c.getString(c.getColumnIndex("categoria"));
            if(categoria == null || categoria == ""){
                exame.setCategoria("");
            } else{
                exame.setCategoria(categoria);
            }
            exames.add(exame);

        }
        return exames;
    }



    public void sincroniza(List<Exame> exames) {
        for (Exame exame:
                exames ) {
            if(existe(exame)){
                altera(exame);
                Log.i("Exame", ""+exame.getId() + " - data: " + exame.getDataexames());
            } else {
                insere(exame);
            }
        }
    }

    private boolean existe(Exame exame) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT id FROM Exames WHERE id=? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{exame.getId()});
        int quantidade = cursor.getCount();
        cursor.close();
        return quantidade > 0;
    }


    public void altera(Exame exame) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosExames(exame);
        String[] params = {exame.getId().toString()};
        db.update("Exames", dados,"id = ?", params);
    }



    public void insere(Exame exame) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosExames(exame);
        db.insert("Exames", null, dados);
    }

    private ContentValues pegaDadosExames(Exame exame) {
        ContentValues dados = new ContentValues();
        dados.put("id", exame.getId());
        dados.put("numero", exame.getNumero());
        dados.put("tipo", exame.getTipo());
        dados.put("dataexame", exame.getDataexames());
        Log.i("pegaDadosExames", ""+exame.getDataexames());
        dados.put("resultado", exame.getResultado());
        dados.put("categoria", exame.getCategoria());
        return dados;
    }
}
