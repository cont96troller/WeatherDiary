package com.cont96roller.weatherdiary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cont96roller.weatherdiary.model.ApiInterface;
import com.cont96roller.weatherdiary.model.ResponseWeather;

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
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        String lat= "37.65171906925866";
        String lot = "127.07728375544342";

        Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);
        Call<ResponseWeather> call = service.requestWeather("d36d81339b59c0868af503708d9057b8", Double.valueOf(lat), Double.valueOf(lot));
        call.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(Response<ResponseWeather> response) {
                if (response.isSuccess()) {
                    ResponseWeather responseWeather = response.body();
                    String main = response.body().getWeather().get(0).getMain();
                    TextView textview = view.findViewById(R.id.txt_weather_status);
                    textview.setText(main);
                    //response에있는 main을 가져온다.
                    //xml에 textview 변수로 선언해주고
                    //id를 가져온다
                    //선언해준 textview에 main을 넣어준다.

                    //1. xml에 ImageView만들고d 위치잡고 id부여
                    //2. gilde사용
                    //3. ImageView 변수로 선언
                    //4. id 가져오고
                    //5. 선언해준 ImageView에 ico_back_nor.png 넣어준다.
                    //6. 선언해준 ImageView에 https://openweathermap.org/img/w/04n.png 넣어준다.
                    //7. icon을 선언해주고 String
                    //8. icon에 icon값을담아 url경로를 만들어준다. ""

                    ImageView imageView = view.findViewById(R.id.imageView);
//                    imageView.setImageResource(R.drawable.ico_back_nor);

                    String icon;
                    icon = response.body().getWeather().get(0).getIcon();

                    String url = "https://openweathermap.org/img/w/" + icon + ".png";

                    Glide.with(mContext)

//                            .load("https://openweathermap.org/img/w/04n.png")
                            .load(url)

//                            .load(R.drawable.ico_back_nor)
                            .into(imageView);




//                            .error(R.drawable.imagenotfound);

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




        return view;
    }
}
