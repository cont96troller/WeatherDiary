package com.cont96roller.weatherdiary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WeatherFragment mWeatherFragment;
    private DiaryFragment mDiaryFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Context mContext;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mContext = getApplicationContext();

        mBinding.btnWrite.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), WriteDiaryActivity.class);
            startActivity(intent);
        });

        mWeatherFragment = new WeatherFragment();
        mDiaryFragment = new DiaryFragment();
        mFragmentManager = getSupportFragmentManager();

        mBinding.radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            mFragmentTransaction = mFragmentManager.beginTransaction();

            Boolean isWeather = (i == R.id.radio_weather);
            mFragmentTransaction.replace(R.id.framelayout, isWeather ? mWeatherFragment : mDiaryFragment).commitAllowingStateLoss();
            mBinding.radioDiary.setTypeface(null, isWeather ? Typeface.NORMAL : Typeface.BOLD);
            mBinding.radioWeather.setTypeface(null, isWeather ? Typeface.BOLD : Typeface.NORMAL);

        });
        mBinding.radioWeather.setChecked(true);
    }
}