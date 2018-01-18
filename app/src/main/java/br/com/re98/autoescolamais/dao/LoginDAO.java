package br.com.re98.autoescolamais.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import br.com.re98.autoescolamais.modelo.Login;


public class LoginDAO extends SQLiteOpenHelper{

    public LoginDAO(Context context) {
        super(context, "Autoescola", null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Perfil (codProcesso TEXT, "+
                "nome TEXT, " +
                "rg TEXT, " +
                "cpf TEXT, " +
                "validadeProcesso TEXT, " +
                "dataAprovacaoExameMedico TEXT, " +
                "dataAprovacaoExameTeorico TEXT," +
                "dataAprovacaoMoto TEXT," +
                "dataAprovacaoCarro TEXT);";

        db.execSQL(sql);
        String sql2 = "CREATE TABLE Financeiro (numero TEXT, "+
                "id TEXT, " +
                "descricao TEXT, " +
                "valor TEXT, " +
                "vencimento TEXT, " +
                "pagto TEXT, " +
                "restante TEXT);";
        db.execSQL(sql2);

        String sql3 = "CREATE TABLE Aulas (numero TEXT, "+
                "id TEXT, " +
                "dataaula TEXT, " +
                "dia TEXT, " +
                "hora TEXT, " +
                "instrutor TEXT, " +
                "modelo TEXT);";
        db.execSQL(sql3);


        String sql4 = "CREATE TABLE Exames ( id TEXT, " +
                "numero TEXT, "+
                "tipo TEXT, " +
                "dataexame TEXT, " +
                "resultado TEXT, " +
                "categoria TEXT);";
        db.execSQL(sql4);

        String sql5 = "CREATE TABLE Login (login TEXT, " +
                "senha TEXT, " +
                "idProcesso);";
        db.execSQL(sql5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "";
        switch (oldVersion){
            case 3:
                sql = "CREATE TABLE Financeiro (numero TEXT, "+
                        "id TEXT, " +
                        "descricao TEXT, " +
                        "valor TEXT, " +
                        "vencimento TEXT, " +
                        "pagto TEXT, " +
                        "restante TEXT);";
                db.execSQL(sql);

                sql = "CREATE TABLE Aulas (numero TEXT, "+
                        "id TEXT, " +
                        "dataaula TEXT, " +
                        "dia TEXT, " +
                        "hora TEXT, " +
                        "instrutor TEXT, " +
                        "modelo TEXT);";

                db.execSQL(sql);

                sql = "CREATE TABLE Exames ( id TEXT, "+
                        "tipo TEXT, " +
                        "dataexame TEXT, " +
                        "numero TEXT, " +
                        "resultado TEXT, " +
                        "categoria TEXT );";
                db.execSQL(sql);

            case 6:

                String sql5 = "CREATE TABLE Login (login TEXT, " +
                        "senha TEXT, " +
                        "idProcesso TEXT);";
                db.execSQL(sql5);

        }


    }

    public Login buscaLogin(){

        Login login = new Login();

        String sql = "SELECT * FROM Login;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()){
            login.setLogin(c.getString(c.getColumnIndex("login")));
            login.setIdProcesso(c.getString(c.getColumnIndex("idProcesso")));
            login.setSenha(c.getString(c.getColumnIndex("senha")));
        }

        return login;
    }

    public String buscaIdProcesso() {
        String idProcesso = null;
        String sql = "SELECT * FROM Login;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()){
            idProcesso = c.getString((c.getColumnIndex("idProcesso")));
        }
        return idProcesso;
    }

    public void salvaLogin(Login login) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosLogin(login);

        if(existe(login)){

        } else {
            db.insert("Login", null, dados);
        }


    }

    private boolean existe(Login login) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT login FROM Login WHERE login =? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{login.getLogin()});
        int quantidade = cursor.getCount();
        cursor.close();
        return quantidade > 0;
    }

    private ContentValues pegaDadosLogin(Login login) {
        ContentValues dados = new ContentValues();
        dados.put("idProcesso",login.getIdProcesso());
        dados.put("login",login.getLogin());
        dados.put("senha",login.getSenha());

        return dados;
    }


    public void apagaLogin(Login login) {

        SQLiteDatabase db = getReadableDatabase();
        Log.i("apaga_login", "Login apagar "+ login.getIdProcesso());
        db.delete("Login", "idProcesso" + "='" + login.getIdProcesso()+ "'", null);
        db.delete("Financeiro", "id >'1'",null );
        db.delete("Perfil"," codProcesso = '" +login.getIdProcesso()+"'", null);
        db.delete("Exames","id > '1'",null);
        db.delete("Aulas","id > '1'",null);

    }

}
