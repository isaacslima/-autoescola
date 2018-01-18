package br.com.re98.autoescolamais.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.re98.autoescolamais.modelo.Perfil;



public class PerfilDAO extends SQLiteOpenHelper {


    public PerfilDAO(Context context){
        super(context, "Autoescola", null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


    public void insere(Perfil perfil){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosPerfil(perfil);
        db.insert("Perfil", null, dados);
    }

    private boolean existe(Perfil perfil) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT codProcesso FROM Perfil WHERE codProcesso=? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{perfil.getCodProcesso()});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    public void altera(Perfil perfil) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosPerfil(perfil);

        String[] params = {perfil.getCodProcesso().toString()};
        db.update("Perfil", dados,"codProcesso = ?", params);

    }

    public ContentValues pegaDadosPerfil(Perfil perfil){
        ContentValues dados = new ContentValues();
        dados.put("codProcesso",perfil.getCodProcesso());
        dados.put("validadeProcesso",perfil.getValidadeProcesso());
        dados.put("nome",perfil.getNome());
        dados.put("rg",perfil.getRg());
        dados.put("cpf",perfil.getCpf());
        dados.put("dataAprovacaoExameMedico",perfil.getDataAprovacaoExameMedico());
        dados.put("dataAprovacaoExameTeorico",perfil.getDataAprovacaoExameTeorico());
        dados.put("dataAprovacaoMoto",perfil.getDataAprovacaoMoto());
        dados.put("dataAprovacaoCarro",perfil.getDataAprovacaoCarro());
        return dados;
    }

    public void sincroniza(List<Perfil> perfils) {

        for (Perfil perfil:
                perfils ) {
            if(existe(perfil)){
                Log.i("onExiste","Passou no existe");
                altera(perfil);
            } else {
                Log.i("onExiste","Não existe");
                insere(perfil);
            }

        }
    }


    public List<Perfil> buscaPerfil() {
        String sql = "SELECT * FROM Perfil;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Perfil> perfil = new ArrayList<Perfil>();
        perfil = populaPerfil(c);
        c.close();
        return perfil;
    }


    private List<Perfil> populaPerfil(Cursor c) {
        List<Perfil> perfils = new ArrayList<Perfil>();
        while(c.moveToNext()){
            Perfil perfil = new Perfil();
            perfil.setCodProcesso(c.getString((c.getColumnIndex("codProcesso"))));
            perfil.setNome(c.getString((c.getColumnIndex("nome"))));
            perfil.setCpf(c.getString((c.getColumnIndex("cpf"))));
            perfil.setRg(c.getString((c.getColumnIndex("rg"))));
            perfil.setValidadeProcesso(c.getString((c.getColumnIndex("validadeProcesso"))));
            perfil.setDataAprovacaoExameMedico(c.getString((c.getColumnIndex("dataAprovacaoExameMedico"))));
            perfil.setDataAprovacaoExameTeorico(c.getString((c.getColumnIndex("dataAprovacaoExameTeorico"))));
            perfil.setDataAprovacaoCarro(c.getString((c.getColumnIndex("dataAprovacaoExameCarro"))));
            perfil.setDataAprovacaoMoto(c.getString((c.getColumnIndex("dataAprovacaoExameMoto"))));
            perfils.add(perfil);
        }
        return perfils;
    }

}
