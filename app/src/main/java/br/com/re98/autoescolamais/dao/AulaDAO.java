package br.com.re98.autoescolamais.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import br.com.re98.autoescolamais.modelo.Aula;

public class AulaDAO extends SQLiteOpenHelper{


    public AulaDAO(Context context){
        super(context, "Autoescola", null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public List<Aula> buscaAula() {
        String sql = "SELECT * FROM Aulas;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Aula> aulas = new ArrayList<Aula>();
        aulas = populaAulas(c);
        c.close();
        return aulas;
    }

    private List<Aula> populaAulas(Cursor c) {
        List<Aula> aulas = new ArrayList<Aula>();

        while(c.moveToNext()){
            Aula aula = new Aula();
            aula.setNumero(c.getString(c.getColumnIndex("numero")));
            aula.setId(c.getString(c.getColumnIndex("id")));
            aula.setDataAula(c.getString(c.getColumnIndex("dataaula")));
            aula.setDia(c.getString(c.getColumnIndex("dia")));
            aula.setHora(c.getString(c.getColumnIndex("hora")));
            aula.setInstrutor(c.getString(c.getColumnIndex("instrutor")));
            aula.setModelo(c.getString(c.getColumnIndex("modelo")));
            aulas.add(aula);
        }
        return aulas;
    }

    public void sincroniza(List<Aula> aulas) {
        for (Aula aula:
                aulas ) {
            if(existe(aula)){
                altera(aula);
            } else {
                insere(aula);
            }
        }
    }

    private boolean existe(Aula aula) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT id FROM Aulas WHERE id=? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{aula.getId()});
        int quantidade = cursor.getCount();
        cursor.close();
        return quantidade > 0;
    }


    public void altera(Aula aula) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosExames(aula);
        String[] params = {aula.getId().toString()};
        db.update("Aulas", dados,"id = ?", params);
    }



    public void insere(Aula aula) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosExames(aula);
        db.insert("Aulas", null, dados);
    }

    private ContentValues pegaDadosExames(Aula aula) {
        ContentValues dados = new ContentValues();
        dados.put("id", aula.getId());
        dados.put("numero", aula.getNumero());
        dados.put("dataaula",aula.getDataAula());
        dados.put("dia", aula.getDia());
        dados.put("hora",aula.getHora());
        dados.put("instrutor",aula.getInstrutor());
        dados.put("modelo", aula.getModelo());

        return dados;
    }
}
