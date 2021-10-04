package com.cont96roller.weatherdiary;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cont96roller.weatherdiary.common.Constants;
import com.cont96roller.weatherdiary.model.ApiInterface;
import com.cont96roller.weatherdiary.model.ResponseWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnBack;
    private ImageButton mBtnEdit;
    private ImageButton mBtnDelete;
    private TextView mTxtDiaryTitle;
    private TextView mTxtDiaryContents;
    private TextView mTextTemperature;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diary);
        mContext = this;

        initView();
        getWeatherInfo();

    }

    private void initView() {
        mBtnBack = findViewById(R.id.btn_back);
        mBtnEdit = findViewById(R.id.img_btn_edit);
        mBtnDelete = findViewById(R.id.img_btn_delete);
        mTxtDiaryTitle = findViewById(R.id.txt_diary_title);
        mTxtDiaryContents = findViewById(R.id.txt_diary_contents);
        setOnClicks();
    }

    private void setOnClicks() {
        mBtnBack.setOnClickListener(this);
        mBtnEdit.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
    }

    private void getWeatherInfo() {
        String lat = "37.65171906925866";
        String lot = "127.07728375544342";

        Retrofit client = new Retrofit.Builder().baseUrl(Constants.WEATHER_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);
        Call<ResponseWeather> call = service.requestWeather(Constants.API_APP_KEY, Double.valueOf(lat), Double.valueOf(lot));

        call.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(Response<ResponseWeather> response) {
                if (response.isSuccess()) {
                    ResponseWeather responseWeather = response.body();
                    String main = responseWeather.getWeather().get(0).getMain();
                    String icon = responseWeather.getWeather().get(0).getIcon();
                    TextView textView = findViewById(R.id.txt_weather_status);
                    ImageView imageView = findViewById(R.id.img_weather);
                    textView.setText(main);
                    String url = Constants.PREFIX_WEATHER_ICON_URL + icon + Constants.SUFFIX_WEATHER_ICON_URL;
                    Glide.with(mContext)
                            .load(url)
                            .into(imageView);
                }
            }


            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.img_btn_edit:
                Toast.makeText(mContext, "수정 버튼 선택", Toast.LENGTH_SHORT).show();
                break;

            case R.id.img_btn_delete:
                Toast.makeText(mContext, "삭제 버튼 선택", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
