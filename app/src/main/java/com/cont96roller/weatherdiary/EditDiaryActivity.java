//package com.cont96roller.weatherdiary;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.cont96roller.weatherdiary.adapter.DiaryAdapter;
//import com.cont96roller.weatherdiary.common.Constants;
//import com.cont96roller.weatherdiary.model.ApiInterface;
//import com.cont96roller.weatherdiary.model.ResponseWeather;
//
//import java.util.Date;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.GsonConverterFactory;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//
//public class EditDiaryActivity extends AppCompatActivity implements View.OnClickListener {
//
//    private Button mBtnBack;
//    private ImageButton mBtnEdit;
//    private TextView mTxtDiaryTitle;
//    private TextView mTxtDiaryContents;
//    private TextView mTextTemperature;
//    private Context mContext;
//    List<Diary> diaryList;
//    private RecyclerView mRecyclerView;
//    private DiaryAdapter diaryAdater;
//    private Button mButton2;
//    private EditText mEditTextContents;
//    private TextView mTitle;
//    private TextView mTempMin;
//    private TextView mTempMax;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_diary);
//        mContext = this;
//
//        initView();
//        getWeatherInfo();
//
//    }
//
//    private void initView() {
//        mBtnBack = findViewById(R.id.btn_back);
//        mBtnEdit = findViewById(R.id.img_btn_edit);
//        mTxtDiaryTitle = findViewById(R.id.edit_diary_title);
//        mTxtDiaryContents = findViewById(R.id.edit_diary_contents);
//        mButton2 = findViewById(R.id.btn_ok);
//        setOnClicks();
//    }
//
//    private void setOnClicks() {
//        mBtnBack.setOnClickListener(this);
//        mBtnEdit.setOnClickListener(this);
//    }
//
//    private void getWeatherInfo() {
//        String lat = "37.65171906925866";
//        String lot = "127.07728375544342";
//
//        Retrofit client = new Retrofit.Builder().baseUrl(Constants.WEATHER_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//
//        ApiInterface service = client.create(ApiInterface.class);
//        Call<ResponseWeather> call = service.requestWeather(Constants.API_APP_KEY, Double.valueOf(lat), Double.valueOf(lot));
//
//        call.enqueue(new Callback<ResponseWeather>() {
//            @Override
//            public void onResponse(Response<ResponseWeather> response) {
//                if (response.isSuccess()) {
//                    ResponseWeather responseWeather = response.body();
//                    String main = responseWeather.getWeather().get(0).getMain();
//                    String icon = responseWeather.getWeather().get(0).getIcon();
//                    TextView textView = findViewById(R.id.txt_weather_status);
//                    ImageView imageView = findViewById(R.id.img_weather);
//                    textView.setText(main);
//                    String url = Constants.PREFIX_WEATHER_ICON_URL + icon + Constants.SUFFIX_WEATHER_ICON_URL;
//                    Glide.with(mContext)
//                            .load(url)
//                            .into(imageView);
//                }
//            }
//
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_back:
//                finish();
//                break;
//
//        }
//    }
//
//    public void addDiaryModel() {
//
//
//        diaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
//        String title = diaryList.get(0).contents;
//        InsertRunnable insertRunnable = new InsertRunnable();
//        Thread t = new Thread(insertRunnable);
//        t.start();
////        diaryList.clear();
////        diaryList.add(new Diary(title));/
////        diaryList.add(new DiaryModel(title, "춥다", "2021.08.21"));
////        diaryList.add(new DiaryModel("비가오는", "싸늘", "2021.07.21"));
////        diaryList.add(new DiaryModel("날이더운", "더운", "2021.06.21"));
//
//    }
//
//    class InsertRunnable implements Runnable {
//
//        @Override
//        public void run() {
//
//            Long now = System.currentTimeMillis();
//            Date mDate = new Date(now);
//
//            Diary diary = new Diary();
//            diary.contents = mEditTextContents.getText().toString();
//            diary.title = mTitle.getText().toString();
////            diary.temp_max = mTempMax.getText().toString();
////            diary.temp_min = mTempMin.getText().toString();
//            diary.date = mDate.getTime();
//
//            DiaryDB.getInstance(mContext).diaryDao().insertAll(diary);
//        }
//    }
//
//
//
////        mButton2.setOnClickListener(v -> {
////
////        InsertRunnalbe insertRunnalbe = new InsertRunnalbe();
////        Thread addThread = new Thread(insertRunnalbe);
////        addThread.start();
////
////        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
////        startActivity(intent1);
////        finish();
////
////    });
////}
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        DiaryDB.destroyInstance();
//    }
//}