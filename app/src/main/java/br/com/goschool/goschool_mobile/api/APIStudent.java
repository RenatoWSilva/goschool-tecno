package br.com.goschool.goschool_mobile.api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIStudent {

    public static final String ENDPOINT = "http://192.168.82.102:8080/";

    public static Retrofit getStudent(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
