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
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.re98.autoescolamais.converter.ConverteData;
import br.com.re98.autoescolamais.dao.LoginDAO;
import br.com.re98.autoescolamais.dao.PerfilDAO;
import br.com.re98.autoescolamais.dto.PerfilSync;
import br.com.re98.autoescolamais.modelo.Perfil;
import br.com.re98.autoescolamais.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PerfilFragment extends Fragment {

    View myView;
    private TextView nome;
    private TextView cpf;
    private TextView rg;
    private TextView validadeProcesso;
    private TextView dataExameMedico;
    private TextView dataExameTeorico;
    private TextView dataExameMoto;
    private TextView dataExameCarro;
    private String idProcesso = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.perfil, container, false);

        nome = (TextView) myView.findViewById(R.id.perfil_nome);
        cpf = (TextView) myView.findViewById(R.id.perfil_cpf);
        rg = (TextView) myView.findViewById(R.id.perfil_rg);
        validadeProcesso = (TextView) myView.findViewById(R.id.perfil_validadeprocesso);
        dataExameMedico = (TextView) myView.findViewById(R.id.perfil_examemedico);
        dataExameTeorico = (TextView) myView.findViewById(R.id.perfil_exameteorico);
        dataExameMoto = (TextView) myView.findViewById(R.id.perfil_examemoto);
        dataExameCarro = (TextView) myView.findViewById(R.id.perfil_examecarro);


        return myView;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        LoginDAO dao = new LoginDAO(getContext());
       idProcesso = dao.buscaIdProcesso();
       dao.close();
       Log.i("TESTE",idProcesso);



        Call<PerfilSync> call = new RetrofitInicializador().getPerfilService().lista(idProcesso);

        call.enqueue(new Callback<PerfilSync>() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<PerfilSync> call, Response<PerfilSync> response) {
                PerfilSync perfilSync = response.body();
                PerfilDAO dao = new PerfilDAO(getContext());
                dao.sincroniza(perfilSync.getPerfil());
                carregaPerfil(perfilSync.getPerfil());
                dao.close();

            }

            @Override
            public void onFailure(Call<PerfilSync> call, Throwable t) {
                Log.e("onFailure chamado", t.getMessage());
            }
        });

        //carregaPerfil();

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void carregaPerfil() {
        PerfilDAO dao = new PerfilDAO(getContext());
        List<Perfil> perfil =  dao.buscaPerfil();
        dao.close();
        carregaPerfil(perfil);

    }

    public void carregaPerfil(List<Perfil> perfils) {

        ConverteData dt = new ConverteData();

        for (Perfil perfil:
                perfils ) {
            nome.setText(perfil.getNome());
            cpf.setText(perfil.getCpf());
            rg.setText(perfil.getRg());
            if(perfil.getValidadeProcesso() != null){
                validadeProcesso.setText(dt.converteData(perfil.getValidadeProcesso(),1));
            }
            if(perfil.getDataAprovacaoCarro() != null){
                dataExameCarro.setText(dt.converteData(perfil.getDataAprovacaoCarro(),1));
            }
            if(perfil.getDataAprovacaoExameMedico() != null){
                dataExameMedico.setText(dt.converteData(perfil.getDataAprovacaoExameMedico(),1));
            }
            if(perfil.getDataAprovacaoExameTeorico() != null){
                dataExameTeorico.setText(dt.converteData(perfil.getDataAprovacaoExameTeorico(),1));
            }
            if(perfil.getDataAprovacaoMoto() != null){
                dataExameMoto.setText(dt.converteData(perfil.getDataAprovacaoMoto(),1));
            }

        }
    }



}
