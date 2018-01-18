package br.com.re98.autoescolamais;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import br.com.re98.autoescolamais.dao.LoginDAO;
import br.com.re98.autoescolamais.modelo.Login;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;

    private AlertDialog alerta;

    ListView listPerfil;
    List<String> perfil;
    ArrayAdapter<String> adaptador;
    Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //recuperando dados do login
        Intent intent = getIntent();
        login = (Login) intent.getSerializableExtra("login");


        LoginDAO dao = new LoginDAO(this);
        dao.salvaLogin(login);
        dao.close();



        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new PerfilFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.moveTaskToBack(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        fragmentManager = getFragmentManager();


        if (id == R.id.nav_perfil) {



            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new PerfilFragment()).commit();

        } else if (id == R.id.nav_financeiro) {

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FinanceiroFragment()).commit();


        } else if (id == R.id.nav_aulas) {

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new AulasFragment()).commit();


        }  else if (id == R.id.nav_exames) {


            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ExameFragment()).commit();

        }else if (id == R.id.nav_aulas) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FaleconoscoFragment()).commit();

        }
        else if (id == R.id.nav_sair) {

            confirmacaoSaida();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void confirmacaoSaida() {
        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.alerta, null);


        //inflamos o layout alerta.xml na view

        //definimos para o botão do layout um clickListener
        view.findViewById(R.id.alerta_sair).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //exibe um Toast informativo.
                LoginDAO dao = new LoginDAO(MainActivity.this);
                login = dao.buscaLogin();
                dao.apagaLogin(login);
                dao.close();
                finish();
            }
        });

        view.findViewById(R.id.alerta_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //desfaz o alerta.
                alerta.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deseja realmente sair?");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();


    }


}
