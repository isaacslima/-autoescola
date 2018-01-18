package br.com.re98.autoescolamais;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import br.com.re98.autoescolamais.adapter.ExameAdapter;
import br.com.re98.autoescolamais.dao.ExameDAO;
import br.com.re98.autoescolamais.dao.LoginDAO;
import br.com.re98.autoescolamais.dto.ExameSync;
import br.com.re98.autoescolamais.modelo.Exame;
import br.com.re98.autoescolamais.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExameFragment extends Fragment {

    private View myView;
    private ListView listaExame;
    private String idProcesso = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.exames, container, false);

        listaExame = (ListView) myView.findViewById(R.id.lv_exames);


        return myView;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void carregaLista()  {
        ExameDAO dao = new ExameDAO(getContext());
        List<Exame> exames = dao.buscaExame();
        dao.close();

        ExameAdapter adapter = new ExameAdapter(getContext(), exames);
        listaExame.setAdapter(adapter);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        LoginDAO dao = new LoginDAO(getContext());
        idProcesso = dao.buscaIdProcesso();
        dao.close();

        Call<ExameSync> call = new RetrofitInicializador().getExameService().lista(idProcesso);

        call.enqueue(new Callback<ExameSync>() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<ExameSync> call, Response<ExameSync> response) {
                ExameSync exameSync = response.body();
                ExameDAO dao = new ExameDAO(getContext());
                dao.sincroniza(exameSync.getExames());
                carregaLista();
                dao.close();
            }

            @Override
            public void onFailure(Call<ExameSync> call, Throwable t) {

            }
        });


        carregaLista();

    }
}
