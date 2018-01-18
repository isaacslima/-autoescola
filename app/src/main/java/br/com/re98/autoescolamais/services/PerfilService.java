package br.com.re98.autoescolamais.services;

import br.com.re98.autoescolamais.dto.PerfilSync;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PerfilService {



        @GET("perfil/{idProcesso}")
        Call<PerfilSync> lista(@Path("idProcesso") String idProcesso);

}
