package br.com.re98.autoescolamais.retrofit;

import br.com.re98.autoescolamais.services.AulaService;
import br.com.re98.autoescolamais.services.ExameService;
import br.com.re98.autoescolamais.services.FinanceiroService;
import br.com.re98.autoescolamais.services.LoginService;
import br.com.re98.autoescolamais.services.PerfilService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {

    private final Retrofit retrofit;

    public RetrofitInicializador(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client =  new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder().baseUrl("http://appautoescola.com.br/unici_restserver/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public PerfilService getPerfilService() {
        return retrofit.create(PerfilService.class);
    }

    public FinanceiroService getFinanceiroService(){
        return retrofit.create(FinanceiroService.class);
    }


    public LoginService getLoginService() {
        return retrofit.create(LoginService.class);
    }

    public ExameService getExameService(){
        return retrofit.create(ExameService.class);
    }

    public AulaService getAulaService() {
        return retrofit.create(AulaService.class);
    }
}