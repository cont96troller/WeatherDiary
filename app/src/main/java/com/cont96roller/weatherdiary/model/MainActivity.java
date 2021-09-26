package com.cont96roller.weatherdiary.model;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {



    public void setGetWeatherBtn(){

        String lat= "37.65171906925866";
        String lot = "127.07728375544342";

        Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);
        Call<Repo> call = service.requestWeather("d36d81339b59c0868af503708d9057b8", Double.valueOf(lat), Double.valueOf(lot));
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Response<Repo> response) {
                if (response.isSuccess()) {
                    Repo repo = response.body();

//                    tem.setText(String.valueOf(repo.getMain().getTemp()));
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
