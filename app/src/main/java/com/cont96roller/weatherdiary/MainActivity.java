package com.cont96roller.weatherdiary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cont96roller.weatherdiary.adapter.DiaryAdapter;
import com.cont96roller.weatherdiary.interfaces.TestInterface;
import com.cont96roller.weatherdiary.model.DiaryModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TestInterface, View.OnClickListener {

    //각종 변수 선언
    private RadioGroup mRadioGroup;
    private RadioButton mRadioWeather;
    private RadioButton mRadioDiary;
    private WeatherFragment mWeatherFragment;
    private DiaryFragment mDiaryFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Button mBtnMoveWrite;
    private Button mButton2;
    private View mView;
    private Button btn_bgm;
    private Context mContext;
    private String mPersonName = "";
    List<Diary> mDiaryList = null;

    List<Diary> diaryList;
    private DiaryDB diaryDB = null;
    private DiaryAdapter diaryAdater;
    private Button mWriteButton;
    private RecyclerView mRecyclerView;

    //이거는 없어도 되지 않나?(아래 3줄)
    public String getmPersonName() {
        return mPersonName;
    }
    public void setmPersonName(String mPersonName) {
        this.mPersonName = mPersonName;
    }

    final public static String TAG = "pyorong";

    //Activity 생명주기 최초 onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //이어플리케이션에 정보를 담아주고
        mContext = this;
        mWriteButton = findViewById(R.id.btn_write);
        mContext = getApplicationContext();
        diaryAdater = new DiaryAdapter(mDiaryList);

        //DB 생성
        diaryDB = DiaryDB.getInstance(this);

        //Runnable을 상속받는 InsertPunnable 클래스 만들고
        class InsertRunnable implements Runnable {

            @Override
            public void run() {
                try {
                    diaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
                    diaryAdater = new DiaryAdapter(mDiaryList);
                    diaryAdater.notifyDataSetChanged();
                    mRecyclerView.setAdapter(diaryAdater);
                    //레이아웃 매니저를통해 수직정렬 정해줌
                    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                } catch (Exception e) {

                }

            }
        }
        //insertRunnable에 InsertRunnable을 인스턴스화해서 넣어주고
        InsertRunnable insertRunnable = new InsertRunnable();
        Thread t = new Thread(insertRunnable);
        t.start();

        mWriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), WriteDiaryActivity.class);
            startActivity(intent);
        });


        btn_bgm = findViewById(R.id.btn_bgm);

        btn_bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), MusicService.class));

            }
        });

        //값이 넘어오는지 테스트하는 코드
        DiaryModel diaryModel;
        DiaryModel diaryModel2;
        diaryModel = new DiaryModel("맑음", "이것은 제목", 0L);
        diaryModel2 = new DiaryModel("흐림", "이것은 제목2", 0L);
        ArrayList<DiaryModel> diaryList = new ArrayList<>();
        diaryList.add(diaryModel);
        diaryList.add(diaryModel2);
        diaryList.add(new DiaryModel("바람많은", "춥다", 0L));

        //findViewById로 버튼 연결
        mRadioGroup = findViewById(R.id.radio_group);
        mRadioWeather = findViewById(R.id.radio_weather);
        mRadioDiary = findViewById(R.id.radio_diary);
        mWeatherFragment = new WeatherFragment();
        mDiaryFragment = new DiaryFragment();
        //액티비티와 연결해주는 프레그먼트 매니저
        mFragmentManager = getSupportFragmentManager();

        //클릭했을때 변화를 감지
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                mFragmentTransaction = mFragmentManager.beginTransaction();

                if (i == R.id.radio_weather) {
                    mFragmentTransaction.replace(R.id.framelayout, mWeatherFragment).commitAllowingStateLoss();
                    mRadioWeather.setTypeface(null, Typeface.BOLD);
                    mRadioDiary.setTypeface(null, Typeface.NORMAL);


                } else if (i == R.id.radio_diary) {
                    mRadioWeather.setTypeface(null, Typeface.NORMAL);
                    mFragmentTransaction.replace(R.id.framelayout, mDiaryFragment).commitAllowingStateLoss();
                    mRadioDiary.setTypeface(null, Typeface.BOLD);
                }


            }
        });
        mRadioWeather.setChecked(true);
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void testMkMk() {
    }

    @Override
    public void onClick(View view) {

        //작성하기버튼으로 클릭하면 어디로 이동되는지
        switch (view.getId()) {
            case R.id.btn_write:
                Intent intent = new Intent(mContext, WriteDiaryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("key2", "일기작성하기");
                startActivity(intent);
                break;

        }

    }
}