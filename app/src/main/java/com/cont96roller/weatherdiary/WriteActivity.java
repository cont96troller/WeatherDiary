//package com.cont96roller.weatherdiary;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.cont96roller.weatherdiary.model.ApiInterface;
//import com.cont96roller.weatherdiary.model.DiaryModel;
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
//public class WriteActivity extends AppCompatActivity {
//
//    private DiaryDB diaryDB = null;
//    private Button mButton2;
//    private Button mButton3;
//    private Context mContext;
//    private EditText mEditTextContents;
//    private TextView mTitle;
//    private TextView mDate;
//    private TextView mTempMin;
//    private TextView mTempMax;
//
//    private LocationManager locationManager;
//    private LocationListener locationListener;
//
////    final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////    final LocationListener gpsLocationListener = new LocationListener() {
////        public void onLocationChanged(Location location) {
////            String provider = location.getProvider(); //위치
////            Latitude = location.getLongitude(); //위도
////            Longitude = location.getLatitude(); //경도
////            double altitude = location.getAltitude(); //고도
////
//////                textView.setText("위치정보 : " + provider + "\n" +
//////                        "위도 : " + Latitude + "\n" +
//////                        "경도 : " + Longitude + "\n" +
//////                        "고도  : " + altitude);
////        }
////
////        @Override
////        public void onLocationChanged(@NonNull Location location) {
////
////        }
////
////        public void onStatusChanged(String provider, int status, Bundle extras) {
////        }
////        public void onProviderEnabled(String provider) {
////        }
////        public void onProviderDisabled(String provider) {
////        }
////    };
////
////        if ( Build.VERSION.SDK_INT >= 23 &&
////            ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
////        ActivityCompat.requestPermissions( MainActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
////                0 );
////    }
////        else{
////        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////        String provider = location.getProvider(); //위치
////        Latitude = location.getLongitude(); // 위도
////        Longitude = location.getLatitude(); // 경도
////        double altitude = location.getAltitude(); // 고도
////
//////            textView.setText("위치정보 : " + provider + "\n" +
//////                    "위도 : " + Latitude + "\n" +
//////                    "경도 : " + Longitude + "\n" +
//////                    "고도  : " + altitude);
////
////        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
////                1000,
////                1,
////                gpsLocationListener);
////        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
////                1000,
////                1,
////                gpsLocationListener);
////    }
//
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_write);
//        mContext = this;
//
//
//        String lat = "37.65171906925866";
//        String lot = "127.07728375544342";
//
//
//        Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();
//
//
//        ApiInterface service = client.create(ApiInterface.class);
//        Call<ResponseWeather> call = service.requestWeather("d36d81339b59c0868af503708d9057b8", Double.valueOf(lat), Double.valueOf(lot));
//
//        call.enqueue(new Callback<ResponseWeather>() {
//            @Override
//            public void onResponse(Response<ResponseWeather> response) {
//                if (response.isSuccess()) {
//                    ResponseWeather responseWeather = response.body();
//                    String main = responseWeather.getWeather().get(0).getMain();
//                    TextView textView = findViewById(R.id.txt_weather);
//                    textView.setText(main);
//
////                    Double temp_min = responseWeather.getMain().getTemp_min();
////                    Double temp_max = responseWeather.getMain().getTemp_max();
//                    int temp_min = responseWeather.getMain().getTemp_min().intValue();
//                    int temp_max = responseWeather.getMain().getTemp_max().intValue();
//                    String min_result = String.valueOf(temp_min - 274);
//                    String max_result = String.valueOf(temp_max - 274);
//                    String last_result = String.format("%s", min_result) + "°C" + "/" + String.format("%s", max_result) + "°C";
//                    TextView textView1 = findViewById(R.id.txt_temp);
//                    textView1.setText(last_result);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });
//
////        View view = inflater.inflate(R.layout.fragment_weather, container, false);
//        //1. 날씨값을 가져온다.
//
//
//        //2. settext로 값을 출력한다.
//
//        TextView textview3 = findViewById(R.id.txt_weather);
//        textview3.setText("일기작성하기");
//
//        Intent intent = null;
//        intent = getIntent();
//
//        if (intent.hasExtra("key")) {
//
//            DiaryModel value = null;
//
//        }
//
//
//        if (intent.hasExtra("key2")) ;
//        {
//            String value = intent.getStringExtra("key2");
//            TextView textview2 = findViewById(R.id.title);
//            textview2.setText(value);
//        }
//
//
////        mButton2 = findViewById(R.id.btn_ok);
////        mButton2.setOnClickListener((new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                finish();
////            }
////        }));
////
////        mButton3 = findViewById(R.id.btn_back);
////        mButton3.setOnClickListener((new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                finish();
////            }
////        }));
//
//        mButton2 = findViewById(R.id.btn_ok);
//        mEditTextContents = findViewById(R.id.mEditTextContents);
//
//        diaryDB = DiaryDB.getInstance(this);
//        mContext = getApplicationContext();
//
//        class InsertRunnalbe implements Runnable {
//            @Override
//            public void run() {
//
//                long now = System.currentTimeMillis();
//                Date mDate = new Date(now);
//
//                Diary diary = new Diary();
//                diary.contents = mEditTextContents.getText().toString();
//                diary.title = mTitle.getText().toString();
//                diary.temp_max = mTempMax.getText().toString();
//                diary.temp_min = mTempMin.getText().toString();
//                diary.date = mDate.getTime();
////                ResponseWeather responseWeather = response.body();
////                String main = responseWeather.getWeather().get(0).getMain();
//
//                DiaryDB.getInstance(mContext).diaryDao().insertAll(diary);
//
//            }
//        }
//
//        mButton2.setOnClickListener(v -> {
//
//            InsertRunnalbe insertRunnalbe = new InsertRunnalbe();
//            Thread addThread = new Thread(insertRunnalbe);
//            addThread.start();
//
//            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent1);
//            finish();
//
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        DiaryDB.destroyInstance();
//
//    }
//
//}
//
//
