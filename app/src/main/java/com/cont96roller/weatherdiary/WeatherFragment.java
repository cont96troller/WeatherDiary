package com.cont96roller.weatherdiary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cont96roller.weatherdiary.model.ApiInterface;
import com.cont96roller.weatherdiary.model.DiaryModel;
import com.cont96roller.weatherdiary.model.Repo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherFragment extends Fragment {

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getContext();

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
//
//        TestClass.dong();
//
//        TestClass testClass = new TestClass();
//        testClass.myoung();
//
//        DiaryModel diaryModel = new DiaryModel("명길");
//
//        diaryModel.setWeatherStatus("명길");
//
//        MainActivity mainActivity = new MainActivity();
//
//        mainActivity.setmPersonName("명길");
//
//        TestClass testClass1 = new TestClass();
//        testClass1.toast(mContext);
//
//        testClass1.kang();
//
//        Toast.makeText(mContext, testClass1.kang(), Toast.LENGTH_SHORT).show();




        return inflater.inflate(R.layout.fragment_weather, container, false);
    }
}
