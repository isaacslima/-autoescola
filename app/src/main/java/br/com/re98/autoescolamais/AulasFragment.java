package br.com.re98.autoescolamais;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.re98.autoescolamais.adapter.AulaAdapter;
import br.com.re98.autoescolamais.dao.AulaDAO;
import br.com.re98.autoescolamais.dao.LoginDAO;
import br.com.re98.autoescolamais.dto.AulaSync;
import br.com.re98.autoescolamais.modelo.Aula;
import br.com.re98.autoescolamais.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AulasFragment extends Fragment {

    private View myView;
    private ListView listaAula;
    private String idProcesso = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.aulas, container, false);

        listaAula = (ListView) myView.findViewById(R.id.lv_aulas);

        Button button_agendar = (Button) myView.findViewById(R.id.bt_agendar_aulas);

        button_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View myView) {

                //Intent intent = new Intent(getActivity(), AgendamentoActivity.class);
                //intent.putExtra("Agendamento", "Instrutor");
                //startActivity(intent);


            }
        });

        return myView;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        LoginDAO dao = new LoginDAO(getContext());
        idProcesso = dao.buscaIdProcesso();
        dao.close();

        Call<AulaSync> call = new RetrofitInicializador().getAulaService().lista(idProcesso);

        call.enqueue(new Callback<AulaSync>() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<AulaSync> call, Response<AulaSync> response) {
                AulaSync aulaSync = response.body();
                AulaDAO dao = new AulaDAO(getContext());
                dao.sincroniza(aulaSync.getAulas());
                carregaLista();
                dao.close();
            }

            @Override
            public void onFailure(Call<AulaSync> call, Throwable t) {

            }
        });


        carregaLista();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void carregaLista()  {
        AulaDAO dao = new AulaDAO(getContext());
        List<Aula> aulas = dao.buscaAula();
        dao.close();

        AulaAdapter adapter = new AulaAdapter(getContext(), aulas);
        listaAula.setAdapter(adapter);

    }
}
