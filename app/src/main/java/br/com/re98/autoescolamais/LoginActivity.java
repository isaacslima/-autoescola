package br.com.re98.autoescolamais;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.re98.autoescolamais.dao.LoginDAO;
import br.com.re98.autoescolamais.dto.LoginSync;
import br.com.re98.autoescolamais.modelo.Login;
import br.com.re98.autoescolamais.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {


    private EditText login;
    private EditText senha;
    private Button loginEntrar;
    private String[] user = {""};
    private String[] pass = {""};
    private String[] idProcesso = {""};
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        LoginDAO dao = new LoginDAO(LoginActivity.this);
        Login usuario = dao.buscaLogin();
        dao.close();

        if(usuario.getLogin() != null){
            logiing();
        }

        login = (EditText) findViewById(R.id.login_login);
        senha = (EditText) findViewById(R.id.login_senha);

        loginEntrar = (Button) findViewById(R.id.login_entrar);
        loginEntrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = login.getText().toString();
                String str2 = senha.getText().toString();
                if(str.equalsIgnoreCase(""))
                {
                    login.setHint("Favor inserir o login");//it gives user to hint
                    login.setError("Digite o login");//it gives user to info message //use any one //
                }
                else
                if(str2.equalsIgnoreCase(""))
                {
                    senha.setHint("Favor inserir a senha");//it gives user to hint
                    senha.setError("Digite a senha");//it gives user to info message //use any one //
                }
                else{
                    Toast.makeText(LoginActivity.this, "Realizando login aguarde...", Toast.LENGTH_SHORT).show();
                    logiing();
                }

            }
        });


    }

    private void logiing() {

        String log = null;

        LoginDAO dao = new LoginDAO(LoginActivity.this);
        Login usuario = dao.buscaLogin();
        dao.close();

        if(usuario.getLogin() != null){
           log = usuario.getLogin().toString();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("login",usuario);
            startActivity(intent);

        }else {
        log = login.getText().toString();


            Call<LoginSync> call = new RetrofitInicializador().getLoginService().lista(log);

            call.enqueue(new Callback<LoginSync>() {
                @Override
                public void onResponse(Call<LoginSync> call, Response<LoginSync> response) {

                    LoginSync loginSync = response.body();
                    if(tentaLogar(loginSync.getLogin())){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Login lg = new Login();
                        lg.setLogin(user[0]);
                        lg.setSenha(pass[0]);
                        lg.setIdProcesso(idProcesso[0]);
                        intent.putExtra("login",lg);

                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login ou Senha Incorreto", Toast.LENGTH_SHORT).show();
                    }

                }

                private Boolean tentaLogar(List<Login> logins) {

                    for (Login login:
                            logins ) {

                        user[0] = login.getLogin().toString();
                        pass[0] = login.getSenha().toString();
                        idProcesso[0] = login.getIdProcesso().toString();

                    }
                    if(senha.getText().toString().equals(pass[0]) && login.getText().toString().equals(user[0])){
                        Toast.makeText(LoginActivity.this, "Login Efetuado com Sucesso", Toast.LENGTH_SHORT).show();

                    }
                    return true;

                }

                @Override
                public void onFailure(Call<LoginSync> call, Throwable t) {
                    Log.e("onFailure chamado", t.getMessage());
                }
            });
        }



    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}

