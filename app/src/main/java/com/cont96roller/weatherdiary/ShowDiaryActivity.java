package com.cont96roller.weatherdiary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cont96roller.weatherdiary.model.ApiInterface;
import com.cont96roller.weatherdiary.model.DiaryModel;
import com.cont96roller.weatherdiary.model.ResponseWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowDiaryActivity extends AppCompatActivity {

    private Button mButton2;
    private Button mButton3;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diary);
        mContext = this;

        String lat = "37.65171906925866";
        String lot = "127.07728375544342";


        Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();


        ApiInterface service = client.create(ApiInterface.class);
        Call<ResponseWeather> call = service.requestWeather("d36d81339b59c0868af503708d9057b8", Double.valueOf(lat), Double.valueOf(lot));

        call.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(Response<ResponseWeather> response) {
                if (response.isSuccess()) {
                    ResponseWeather responseWeather = response.body();
                    String main = responseWeather.getWeather().get(0).getMain();
                    String icon = responseWeather.getWeather().get(0).getIcon();
                    TextView textView = findViewById(R.id.txt_temp);
                    ImageView imageView = findViewById(R.id.imageView);
                    textView.setText(main);
                    String url = "https://openweathermap.org/img/w/" + icon + ".png";
                    Glide.with(mContext)
                            .load(url)
                            .into(imageView);


                }
            }





            @Override
            public void onFailure(Throwable t) {

            }
        });

        mButton3 = findViewById(R.id.btn_back);
        mButton3.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }));

    }
}
