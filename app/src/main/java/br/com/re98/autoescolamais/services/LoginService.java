package br.com.re98.autoescolamais.services;


import br.com.re98.autoescolamais.dto.LoginSync;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoginService {


    @GET("entrar/{usuario}")
    Call<LoginSync> lista(@Path("usuario") String usuario);

}


