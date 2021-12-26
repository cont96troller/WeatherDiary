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

import com.cont96roller.weatherdiary.adapter.DiaryAdapter;
import com.cont96roller.weatherdiary.databinding.ActivityMainBinding;
import com.cont96roller.weatherdiary.interfaces.TestInterface;
import com.cont96roller.weatherdiary.model.DiaryModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //사용하지않는 변수정리
    //각종 변수 선언
    private RadioGroup mRadioGroup;
    private RadioButton mRadioWeather;
    private RadioButton mRadioDiary;
    private WeatherFragment mWeatherFragment;
    private DiaryFragment mDiaryFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
<<<<<<< HEAD
    private Button mBtnMoveWrite;
    private Button mButton2;
    private View mView;
    private Button mBtnBGM;
=======
//    private Button mBtnMoveWrite;
//    private Button mButton2;
//    private View mView;
//    private Button mBtnBGM;
>>>>>>> 7b9d41f3790b89bb77d768a9209fe008e41f5de9
    private Context mContext;
//    private String mPersonName = "";
    List<Diary> mDiaryList = null;
<<<<<<< HEAD

    List<Diary> diaryList;
    private DiaryDB diaryDB = null;
=======
    private DiaryDB mDiaryDB = null;
>>>>>>> 7b9d41f3790b89bb77d768a9209fe008e41f5de9
    private DiaryAdapter mDiaryAdater;
    private Button mWriteButton;
    private RecyclerView mRecyclerView;
    private ActivityMainBinding binding;

<<<<<<< HEAD
    final public static String TAG = "mk";
=======
    final public static String TAG = "mkkang";
>>>>>>> 7b9d41f3790b89bb77d768a9209fe008e41f5de9

    //Activity 생명주기 최초 onCreate
    //Runnable 정리
    //oncreate 밖으로 메소드 빼서 정리하기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        //이어플리케이션에 정보를 담아주고
        mWriteButton = findViewById(R.id.btn_write);
        mContext = getApplicationContext();
        mDiaryAdater = new DiaryAdapter(mDiaryList);

        //Room 사용을 위한 DB생성
        diaryDB = DiaryDB.getInstance(this);

        //mainThread에서 DB작업을 안하려고 다른 Thread를 만듬
        //Runnable을 상속받는 InsertPunnable 클래스 만들고
=======
        mContext = this;
//        mWriteButton = findViewById(R.id.btn_write);
        mContext = getApplicationContext();
        mDiaryAdater = new DiaryAdapter(mDiaryList);
        mDiaryDB = DiaryDB.getInstance(this);

>>>>>>> 7b9d41f3790b89bb77d768a9209fe008e41f5de9
        class CreateRunnable implements Runnable {

            @Override
            public void run() {
                try {
<<<<<<< HEAD
                    diaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
                    mDiaryAdater = new DiaryAdapter(mDiaryList);
                    //recyclerview를 다시 그려줌
                    mDiaryAdater.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mDiaryAdater);
                    //레이아웃 매니저를통해 수직정렬 정해줌
=======
                    mDiaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
                    mDiaryAdater = new DiaryAdapter(mDiaryList);
                    mDiaryAdater.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mDiaryAdater);
>>>>>>> 7b9d41f3790b89bb77d768a9209fe008e41f5de9
                    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                } catch (Exception e) {

                }
            }
        }
<<<<<<< HEAD
        //insertRunnable에 InsertRunnable을 인스턴스화해서 넣어주고
        CreateRunnable createRunnable = new CreateRunnable();
        Thread t = new Thread(createRunnable);
        t.start();

        mWriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), WriteDiaryActivity.class);
            startActivity(intent);
        });
=======
>>>>>>> 7b9d41f3790b89bb77d768a9209fe008e41f5de9

        CreateRunnable createRunnable = new CreateRunnable();
        Thread t = new Thread(createRunnable);
        t.start();
        //쓰레드 닫기
        //버튼 몰아서 정리
        //ViewBinding 먼저사용하기

<<<<<<< HEAD
        mBtnBGM = findViewById(R.id.btn_bgm);

        mBtnBGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), MusicService.class));
=======
//        mWriteButton.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), WriteDiaryActivity.class);
//            startActivity(intent);
//        });

>>>>>>> 7b9d41f3790b89bb77d768a9209fe008e41f5de9

//        mBtnBGM = findViewById(R.id.btn_bgm);
//        mBtnBGM.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startService(new Intent(getApplicationContext(), MusicService.class));
//            }
//        });

        //값이 넘어오는지 테스트하는 코드
//        DiaryModel diaryModel;
//        DiaryModel diaryModel2;
        //한글 사용 금지
        //주석에도 한글 금지
//        diaryModel = new DiaryModel("맑음", "이것은 제목", 0L);
//        diaryModel2 = new DiaryModel("흐림", "이것은 제목2", 0L);
//        ArrayList<DiaryModel> diaryList = new ArrayList<>();
//        diaryList.add(diaryModel);
//        diaryList.add(diaryModel2);
//        diaryList.add(new DiaryModel("바람많은", "춥다", 0L));

        //findViewById 사용하여 버튼 연결
        mRadioGroup = findViewById(R.id.radio_group);
        mRadioWeather = findViewById(R.id.radio_weather);
        mRadioDiary = findViewById(R.id.radio_diary);
        mWeatherFragment = new WeatherFragment();
        mDiaryFragment = new DiaryFragment();
        mFragmentManager = getSupportFragmentManager();

        //클릭했을때 변화를 감지
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mFragmentTransaction = mFragmentManager.beginTransaction();

                //삼항연산자 사용으로 코드 줄이기
                mFragmentTransaction.replace(R.id.framelayout, mDiaryFragment).commitAllowingStateLoss();
                mFragmentTransaction.replace(R.id.framelayout, mWeatherFragment).commitAllowingStateLoss();

//                if (i == R.id.radio_weather) {
//                    mFragmentTransaction.replace(R.id.framelayout, mWeatherFragment).commitAllowingStateLoss();
//                    mRadioDiary.setTypeface(null, Typeface.NORMAL);
//                    mRadioWeather.setTypeface(null, Typeface.BOLD);
//                } else if (i == R.id.radio_diary) {
//                    mFragmentTransaction.replace(R.id.framelayout, mDiaryFragment).commitAllowingStateLoss();
//                    mRadioDiary.setTypeface(null, Typeface.BOLD);
//                    mRadioWeather.setTypeface(null, Typeface.NORMAL);
//                }
            }
        });
        mRadioWeather.setChecked(true);
//        Log.d(TAG, "onCreate");
    }

    //oncreate 끝나는곳 밖으로 최대한 메소드 빼기

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy");
//    }

//    @Override
//    public void testMkMk() {
//    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_write:
                Intent intent = new Intent(mContext, WriteDiaryActivity.class);
                //FLAG사용 용도 파악, adb shell dumpsys
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("key2", "일기작성하기");
                startActivity(intent);
                break;
        }
    }

}