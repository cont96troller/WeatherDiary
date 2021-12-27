package com.cont96roller.weatherdiary;

import static com.cont96roller.weatherdiary.common.Constants.DIARY_ID_KEY;
import static com.cont96roller.weatherdiary.common.Constants.EDIT_DIARY;
import static com.cont96roller.weatherdiary.common.Constants.TEMP_FORMAT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cont96roller.weatherdiary.common.Constants;
import com.cont96roller.weatherdiary.databinding.ActivityWriteDiaryBinding;
import com.cont96roller.weatherdiary.model.ApiInterface;
import com.cont96roller.weatherdiary.model.ResponseWeather;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WriteDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private String mWeatherStatus;
    private String mIcon;
    private int mTemp_max;
    private int mTemp_min;
    private Diary mDiary;
    private boolean mIsEditMode;
    private ActivityWriteDiaryBinding mBinding;
    private Thread mThreadGetDiary;
    private GpsTracker mGpsTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWriteDiaryBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mContext = this;
        getDiaryData();
        initView();
        Intent intent = getIntent();

        mIsEditMode = intent.getBooleanExtra("key_isEditMode", false);
        if (mIsEditMode == true) {
            mDiary = (Diary) intent.getSerializableExtra(DIARY_ID_KEY);
            mBinding.txtWeatherStatus.setText(mDiary.getStatus());
            String tempFormat = TEMP_FORMAT;
            String tempMin = String.valueOf(mDiary.getTempMin() - 274);
            String tempMax = String.valueOf(mDiary.getTempMax() - 274);
            String temperature = String.format(tempFormat, tempMin, tempMax);
            mBinding.txtWeatherStatus.setText(temperature);
            String url = Constants.PREFIX_WEATHER_ICON_URL + mDiary.getIcon() + Constants.SUFFIX_WEATHER_ICON_URL;
            Glide.with(mContext)
                    .load(url)
                    .into(mBinding.imgWeather);
            mBinding.txtTitle.setText(EDIT_DIARY);
            mBinding.editDiaryTitle.setText(mDiary.getTitle());
            mBinding.editDiaryContents.setText(mDiary.getContents());

        } else {
            getWeatherInfo();
        }
    }

    private void initView() {
        mBinding.btnOk.setOnClickListener(view -> {
            Intent editBroadcastIntent = new Intent();
            editBroadcastIntent.setAction(Constants.ACTION_EDIT_DIARY);
            sendBroadcast(editBroadcastIntent);
            save();
        });
        setOnClicks();
    }

    private void setOnClicks() {
        mBinding.btnBack.setOnClickListener(this);
    }

    private void getWeatherInfo() {

        mGpsTracker = new GpsTracker(mContext);

        double lat = mGpsTracker.getmLatitude();
        double lot = mGpsTracker.getmLongitude();

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

                    String tempFormat = TEMP_FORMAT;
                    String tempMin = String.valueOf(tempMinInt - 274);
                    String tempMax = String.valueOf(tempMaxInt - 274);
                    String temperature = String.format(tempFormat, tempMin, tempMax);

                    mBinding.txtWeatherStatus.setText(main);
                    mBinding.txtTemp.setText(temperature);

                    Glide.with(mContext)
                            .load(url)
                            .into(mBinding.imgWeather);
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
        mThreadGetDiary.start();
        Intent editBroadcastIntent = new Intent();
        editBroadcastIntent.setAction(Constants.ACTION_EDIT_DIARY);
        sendBroadcast(editBroadcastIntent);
        finish();
    }

    private void getDiaryData() {
        mThreadGetDiary = new Thread(() -> {
            if (mIsEditMode) {
                mDiary.setTitle(mBinding.editDiaryTitle.getText().toString());
                mDiary.setContents(mBinding.editDiaryContents.getText().toString());
                DiaryDB.getInstance(mContext).diaryDao().updateDiary(mDiary);

            } else {
                Long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate);

                Diary diary = new Diary();
                diary.contents = mBinding.editDiaryContents.getText().toString();
                diary.title = mBinding.editDiaryTitle.getText().toString();
                diary.status = mWeatherStatus;
                diary.icon = mIcon;
                diary.tempMax = mTemp_max;
                diary.tempMin = mTemp_min;
                diary.date = getTime;
                DiaryDB.getInstance(mContext).diaryDao().insertAll(diary);
            }
        });
//        mThreadGetDiary.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mThreadGetDiary.stop();
        //deprecated
        mThreadGetDiary.interrupt();

    }
}