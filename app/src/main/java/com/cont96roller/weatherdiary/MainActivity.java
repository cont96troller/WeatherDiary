package com.cont96roller.weatherdiary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cont96roller.weatherdiary.interfaces.TestInterface;
import com.cont96roller.weatherdiary.model.DiaryModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TestInterface, View.OnClickListener {

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
    /*중요*/
    private Context mContext;
    private String mPersonName = "";
    ArrayList<DiaryModel> mDiaryList = null;



    public String getmPersonName() {
        return mPersonName;
    }

    public void setmPersonName(String mPersonName) {
        this.mPersonName = mPersonName;
    }

    final public static String TAG = "pyorong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

//        mBtnMoveWrite = findViewById(R.id.btn_write);
//        mBtnMoveWrite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, WriteActivity.class);
//                intent.putExtra("key", "명길아!");
//                startActivity(intent);
//                Toast.makeText(mContext, "명길 추워", Toast.LENGTH_SHORT).show();
//            }
//        });

        mBtnMoveWrite = findViewById(R.id.btn_write);
        mBtnMoveWrite.setOnClickListener(this);
//        mBtnMoveWrite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, WriteActivity.class);
//                intent.putExtra("key2", "명길아!");
//                startActivity(intent);
//                Toast.makeText(mContext, "명길 추워", Toast.LENGTH_SHORT).show();
//            }
//        });




        DiaryModel diaryModel;
        DiaryModel diaryModel2;
        diaryModel = new DiaryModel("맑음", "이것은 제목", "2021.09.21");
        diaryModel2 = new DiaryModel("흐림", "이것은 제목2", "2021.09.22");
        ArrayList<DiaryModel> diaryList = new ArrayList<>();
//        diaryList.add("맑음");
//        diaryList.add("이것은 제목");
//        diaryList.add("2021.09.21");
        diaryList.add(diaryModel);
        diaryList.add(diaryModel2);
        diaryList.add(new DiaryModel("바람많은", "춥다", "2021.08.21"));


        mRadioGroup = findViewById(R.id.radio_group);
        mRadioWeather = findViewById(R.id.radio_weather);
        mRadioDiary = findViewById(R.id.radio_diary);
        mWeatherFragment = new WeatherFragment();
        mDiaryFragment = new DiaryFragment();

        mFragmentManager = getSupportFragmentManager();

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.framelayout, mWeatherFragment).commitAllowingStateLoss();


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                Log.e(TAG, "선택된값 : " + i);
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

        switch(view.getId()) {
            case R.id.btn_write: Intent intent = new Intent(mContext, WriteActivity.class);
                intent.putExtra("key2", "명길아!");
                startActivity(intent);
                Toast.makeText(mContext, "명길 추워", Toast.LENGTH_SHORT).show();
                break;


        }

    }
}