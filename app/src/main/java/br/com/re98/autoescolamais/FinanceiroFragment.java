package br.com.re98.autoescolamais;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.re98.autoescolamais.adapter.FinanceiroAdapter;
import br.com.re98.autoescolamais.dao.FinanceiroDAO;
import br.com.re98.autoescolamais.dao.LoginDAO;
import br.com.re98.autoescolamais.dto.FinanceiroSync;
import br.com.re98.autoescolamais.modelo.Financeiro;
import br.com.re98.autoescolamais.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinanceiroFragment extends Fragment {

    private View myView;
    private ListView listaFinanceiro;
    private String idProcesso = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.financeiro, container, false);

        listaFinanceiro = (ListView) myView.findViewById(R.id.lv_financeiro);


        return myView;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void carregaLista()  {
        FinanceiroDAO dao = new FinanceiroDAO(getContext());
        List<Financeiro> financeiros = dao.buscaFinanceiro();
        dao.close();

        FinanceiroAdapter adapter = new FinanceiroAdapter(getContext(), financeiros);
        listaFinanceiro.setAdapter(adapter);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        LoginDAO dao = new LoginDAO(getContext());
        idProcesso = dao.buscaIdProcesso();
        dao.close();

        Call<FinanceiroSync> call = new RetrofitInicializador().getFinanceiroService().lista(idProcesso);

        call.enqueue(new Callback<FinanceiroSync>() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<FinanceiroSync> call, Response<FinanceiroSync> response) {
                FinanceiroSync financeiroSync = response.body();
                FinanceiroDAO dao = new FinanceiroDAO(getContext());
                dao.sincroniza(financeiroSync.getFincanceiros());
                carregaLista();
                dao.close();


            }

            @Override
            public void onFailure(Call<FinanceiroSync> call, Throwable t) {

            }
        });


        carregaLista();

    }
}
