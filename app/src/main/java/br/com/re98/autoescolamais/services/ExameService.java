package br.com.re98.autoescolamais.services;

import br.com.re98.autoescolamais.dto.ExameSync;
import br.com.re98.autoescolamais.dto.PerfilSync;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by isaac on 1/7/18.
 */

public interface ExameService {


    @GET("meusexames/{idProcesso}")
    Call<ExameSync> lista(@Path("idProcesso") String idProcesso);
}
