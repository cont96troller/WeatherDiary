package com.cont96roller.weatherdiary;

import android.content.Context;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WriteDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    //각종 버튼 변수 선언
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
    private TextView mTxtWeatherStatus;
    private TextView mTxtTemperature;
    private ImageView mImgWeather;
    private TextView mTxtTitle;
    private Diary mDiary;
    private boolean mIsEditMode;

    //액티비티 최초실행 onCreate
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);
        mContext = this;
        //각종 버튼 연결 메소드
        initView();
        Intent intent = getIntent();
        //intent트를 통해 정보를 가져오는데 참인지 거짓인지
        //유지보수를 위해 작성화면으로 수정, 상세보기를 구현
        mIsEditMode = intent.getBooleanExtra("key_isEditMode", false);
        if (mIsEditMode == true) {
            //오브젝트 타입을 다른액티비로 전달받기위한 SerializalbeExtra
            mDiary = (Diary) intent.getSerializableExtra("key_diary");
            mTxtWeatherStatus.setText(mDiary.getStatus());
            String tempFormat = "%1s°C / %2s°C";
            //
            String tempMin = String.valueOf(mDiary.getTemp_min() - 274);
            String tempMax = String.valueOf(mDiary.getTemp_max() - 274);
            String temperature = String.format(tempFormat, tempMin, tempMax);
            mTxtTemperature.setText(temperature);
            String url = Constants.PREFIX_WEATHER_ICON_URL + mDiary.getIcon() + Constants.SUFFIX_WEATHER_ICON_URL;
            Glide.with(mContext)
                    .load(url)
                    .into(mImgWeather);
            mTxtTitle.setText("일기수정하기");
            mEditTitle.setText(mDiary.getTitle());
            mEditTextContents.setText(mDiary.getContents());


        } else {
            //mIsEditMode에 값이 없을경후 메소드 호출
            getWeatherInfo();
        }


    }

    private void initView() {
        mTxtTitle = findViewById(R.id.txt_title);
        mTxtWeatherStatus = findViewById(R.id.txt_weather_status);
        mTxtTemperature = findViewById(R.id.txt_temp);
        mImgWeather = findViewById(R.id.img_weather);
        mBtnBack = findViewById(R.id.btn_back);
        mEditTextContents = findViewById(R.id.edit_diary_contents);
        mEditTitle = findViewById(R.id.edit_diary_title);
        mBtnSave = findViewById(R.id.btn_ok);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editBroadcastIntent = new Intent();
                editBroadcastIntent.setAction(Constants.ACTION_EDIT_DIARY);
                sendBroadcast(editBroadcastIntent);
                save();
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

                    mTxtWeatherStatus.setText(main);
                    mTxtTemperature.setText(temperature);


                    Glide.with(mContext)
                            .load(url)
                            .into(mImgWeather);
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
        Intent editBroadcastIntent = new Intent();
        editBroadcastIntent.setAction(Constants.ACTION_EDIT_DIARY);
        sendBroadcast(editBroadcastIntent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        DiaryDB.destroyInstance();
    }

    class InsertRunnable implements Runnable {

        @Override
        public void run() {
            if (mIsEditMode == true) {
                mDiary.setTitle(mEditTitle.getText().toString());
                mDiary.setContents(mEditTextContents.getText().toString());
                DiaryDB.getInstance(mContext).diaryDao().updateDiary(mDiary);

            } else {
                Long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate);

                Diary diary = new Diary();
                diary.contents = mEditTextContents.getText().toString();
                diary.title = mEditTitle.getText().toString();
                diary.status = mWeatherStatus;
                diary.icon = mIcon;
                diary.temp_max = mTemp_max;
                diary.temp_min = mTemp_min;
                diary.date = getTime;

                DiaryDB.getInstance(mContext).diaryDao().insertAll(diary);
            }

        }
    }

}