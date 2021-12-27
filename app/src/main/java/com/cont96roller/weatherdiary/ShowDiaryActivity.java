package com.cont96roller.weatherdiary;

import static com.cont96roller.weatherdiary.common.Constants.DIARY_DEY;
import static com.cont96roller.weatherdiary.common.Constants.DIARY_ID_KEY;
import static com.cont96roller.weatherdiary.common.Constants.ISEDITMODE_KEY;
import static com.cont96roller.weatherdiary.common.Constants.TEMP_FORMAT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cont96roller.weatherdiary.common.Constants;
import com.cont96roller.weatherdiary.databinding.ActivityShowDiaryBinding;


public class ShowDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private DiaryDao mDiaryDao;
    private Diary mDiary;
    private ActivityShowDiaryBinding mBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityShowDiaryBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mContext = this;
        int diaryId = getIntent().getIntExtra(DIARY_ID_KEY, 0);
        DiaryDB diaryDB = DiaryDB.getInstance(mContext);
        mDiaryDao = diaryDB.diaryDao();
        mDiary = mDiaryDao.getDiary(diaryId);
        initView();

    }

    private void initView() {
        if (mDiary == null) {
            return;
        }

        mBinding.txtWeatherStatus.setText(mDiary.getStatus());
        String tempFormat = TEMP_FORMAT;
        String temperature = String.format(tempFormat, mDiary.getTempMin(), mDiary.getTempMax());
        mBinding.txtTemp.setText(temperature);
        String url = Constants.PREFIX_WEATHER_ICON_URL + mDiary.getIcon() + Constants.SUFFIX_WEATHER_ICON_URL;
        Glide.with(mContext)
                .load(url)
                .into(mBinding.imgWeather);

        mBinding.txtDiaryTitle.setText(mDiary.getTitle());
        mBinding.txtDiaryContents.setText(mDiary.getContents());
        String date = String.valueOf(mDiary.getDate());
        mBinding.txtDate.setText(String.valueOf(mDiary.getDate()));
        setOnClicks();
    }

    private void setOnClicks() {
        mBinding.btnBack.setOnClickListener(this);
        mBinding.imgBtnEdit.setOnClickListener(this);
        mBinding.imgBtnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.img_btn_edit:
                Intent intent = new Intent(mContext, WriteDiaryActivity.class);
                boolean isEditMode = true;
                intent.putExtra(ISEDITMODE_KEY, isEditMode);
                intent.putExtra(DIARY_DEY, mDiary);
                startActivity(intent);
                finish();
                break;

            case R.id.img_btn_delete:
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(Constants.ACTION_DELETE_DIARY);
                sendBroadcast(broadcastIntent);

//                Diary diary = new Diary();
                int diaryId = getIntent().getIntExtra(DIARY_ID_KEY, 0);
                mDiaryDao.deleteByDiaryId(diaryId);
                finish();
                break;

        }
    }
}