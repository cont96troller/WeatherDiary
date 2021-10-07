package com.cont96roller.weatherdiary;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cont96roller.weatherdiary.adapter.DiaryAdapter;
import com.cont96roller.weatherdiary.common.Constants;
import com.cont96roller.weatherdiary.model.ApiInterface;
import com.cont96roller.weatherdiary.model.ResponseWeather;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WriteDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnBack;
    private Context mContext;
    private Button mBtnSave;
    private EditText mEditTextContents;
    private EditText mEditTitle;
    private TextView mTxtTempMin;
    private TextView mTxtTempMax;
    private String mWeatherStatus;
    private String mIcon;
    private int mTemp_max;
    private int mTemp_min;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);
        mContext = this;

        initView();
        getWeatherInfo();

    }

    private void initView() {
        mBtnBack = findViewById(R.id.btn_back);
        mEditTextContents = findViewById(R.id.edit_diary_contents);
        mEditTitle = findViewById(R.id.edit_diary_title);
        mBtnSave = findViewById(R.id.btn_ok);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save();
                finish();
            }
        });
        setOnClicks();
    }

    private void setOnClicks() {
        mBtnBack.setOnClickListener(this);
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

                    TextView txtWeatherStatus = findViewById(R.id.txt_weather_status);
                    TextView txtTemperature = findViewById(R.id.txt_temp);
                    ImageView imgWeather = findViewById(R.id.img_weather);

                    ResponseWeather responseWeather = response.body();
                    String main = responseWeather.getWeather().get(0).getMain();
                    String icon = responseWeather.getWeather().get(0).getIcon();
                    int tempMaxInt = responseWeather.getMain().getTemp_max().intValue();
                    int tempMinInt = responseWeather.getMain().getTemp_min().intValue();
                    mWeatherStatus = main;
                    mIcon = icon;
                    mTemp_max = tempMaxInt;
                    mTemp_min = tempMinInt;

                    String url = Constants.PREFIX_WEATHER_ICON_URL + icon + Constants.SUFFIX_WEATHER_ICON_URL;

                    /*최고 최저 온도*/
                    String tempFormat = "%1s°C / %2s°C";
                    String tempMin = String.valueOf(tempMinInt - 274);
                    String tempMax = String.valueOf(tempMaxInt - 274);
                    String temperature = String.format(tempFormat, tempMin, tempMax);

                    txtWeatherStatus.setText(main);
                    txtTemperature.setText(temperature);


                    Glide.with(mContext)
                            .load(url)
                            .into(imgWeather);
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

        }
    }

    public void save() {
        InsertRunnable insertRunnable = new InsertRunnable();
        Thread t = new Thread(insertRunnable);
        t.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        DiaryDB.destroyInstance();
    }

    class InsertRunnable implements Runnable {

        @Override
        public void run() {

            Long now = System.currentTimeMillis();
            Date date = new Date(now);

            Diary diary = new Diary();
            diary.contents = mEditTextContents.getText().toString();
            diary.title = mEditTitle.getText().toString();
//            diary.temp_max = mTxtTempMax.getText().toString();
//            diary.temp_min = mTxtTempMin.getText().toString();
            diary.status = mWeatherStatus;
            diary.icon = mIcon;
            diary.temp_max = mTemp_max;
            diary.temp_min = mTemp_min;
            diary.date = date.getTime();

            DiaryDB.getInstance(mContext).diaryDao().insertAll(diary);
        }
    }

}