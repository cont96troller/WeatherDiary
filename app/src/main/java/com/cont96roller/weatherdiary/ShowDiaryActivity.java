package com.cont96roller.weatherdiary;

import static com.cont96roller.weatherdiary.common.Constants.DIARY_ID_KEY;

import android.content.Context;
import android.content.Intent;
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


public class ShowDiaryActivity extends AppCompatActivity implements View.OnClickListener {
    //버튼
    private Button mBtnBack;
    private ImageButton mBtnEdit;
    private ImageButton mBtnDelete;
    //내용
    private TextView mTxtDiaryTitle;
    private TextView mTxtDiaryContents;
    private TextView mTxtTemperature;
    private ImageView mImgWeather;
    private TextView mTxtWeatherStatus;
    private Context mContext;
    private DiaryDao mDiaryDao;
    private Diary mDiary;
    private TextView mTxtDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diary);
        mContext = this;

        int diaryId = getIntent().getIntExtra(DIARY_ID_KEY, 0);
        DiaryDB diaryDB = DiaryDB.getInstance(mContext);
        mDiaryDao = diaryDB.diaryDao();
        mDiary = mDiaryDao.getDiary(diaryId);
//        mDiaryDao.deleteByDiaryId(diaryId);


        initView();
        getWeatherInfo();

    }

    private void initView() {
        mTxtDate = findViewById(R.id.txt_date);
        mBtnBack = findViewById(R.id.btn_back);
        mBtnEdit = findViewById(R.id.img_btn_edit);
        mBtnDelete = findViewById(R.id.img_btn_delete);
        mTxtDiaryTitle = findViewById(R.id.txt_diary_title);
        mTxtDiaryContents = findViewById(R.id.txt_diary_contents);
        mTxtWeatherStatus = findViewById(R.id.txt_weather_status);
        mImgWeather = findViewById(R.id.img_weather);
        mTxtTemperature = findViewById(R.id.txt_temp);

        mTxtWeatherStatus.setText(mDiary.getStatus());
//        mImgWeather.setImageIcon(mDiary.getIcon());

        //온도가져오기
        String tempFormat = "%1s°C / %2s°C";
        String temperature = String.format(tempFormat, mDiary.getTemp_min(), mDiary.getTemp_max());
        mTxtTemperature.setText(temperature);
        //아이콘 가져오기
        String url = Constants.PREFIX_WEATHER_ICON_URL + mDiary.getIcon() + Constants.SUFFIX_WEATHER_ICON_URL;
        Glide.with(mContext)
                .load(url)
                .into(mImgWeather);

        mTxtDiaryTitle.setText(mDiary.getTitle());
        mTxtDiaryContents.setText(mDiary.getContents());
        String date = String.valueOf(mDiary.getDate());
        mTxtDate.setText(String.valueOf(mDiary.getDate()));

        setOnClicks();
    }

    private void setOnClicks() {
        mBtnBack.setOnClickListener(this);
        mBtnEdit.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
    }

    private void getWeatherInfo() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.img_btn_edit:
                Toast.makeText(mContext, "수정 버튼 선택", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, WriteDiaryActivity.class);
                boolean isEditMode = true;
                intent.putExtra("key_isEditMode", isEditMode);
                intent.putExtra("key_diary", mDiary);
                startActivity(intent);
                finish();

//                Intent editBroadcastIntent = new Intent();
//                editBroadcastIntent.setAction(Constants.ACTION_EDIT_DIARY);
//                sendBroadcast(editBroadcastIntent);
                break;

            case R.id.img_btn_delete:
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(Constants.ACTION_DELETE_DIARY);
                sendBroadcast(broadcastIntent);

                Diary diary = new Diary();
                int diaryId = getIntent().getIntExtra(DIARY_ID_KEY, 0);
                mDiaryDao.deleteByDiaryId(diaryId);
                finish();
//                diary.setId(0);
//                mDiaryDao.delete(diary);

//                Toast.makeText(mContext, "삭제 버튼 선택", Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
