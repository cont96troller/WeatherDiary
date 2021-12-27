package com.cont96roller.weatherdiary;

import static com.cont96roller.weatherdiary.common.Constants.API_APP_KEY;
import static com.cont96roller.weatherdiary.common.Constants.PREFIX_WEATHER_ICON_URL;
import static com.cont96roller.weatherdiary.common.Constants.SUFFIX_WEATHER_ICON_URL;
import static com.cont96roller.weatherdiary.common.Constants.WEATHER_BASE_URL;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cont96roller.weatherdiary.databinding.FragmentWeatherBinding;
import com.cont96roller.weatherdiary.model.ApiInterface;
import com.cont96roller.weatherdiary.model.ResponseWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherFragment extends Fragment {

    private Context mContext;
    private FragmentWeatherBinding mBinding;
    private GpsTracker mGpsTracker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        mBinding = FragmentWeatherBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mGpsTracker = new GpsTracker(getActivity());

        double lat = mGpsTracker.getmLatitude();
        double lng = mGpsTracker.getmLongitude();
        Retrofit client = new Retrofit.Builder().baseUrl(WEATHER_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);
        Call<ResponseWeather> call = service.requestWeather(API_APP_KEY, Double.valueOf(lat), Double.valueOf(lng));
        call.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(Response<ResponseWeather> response) {
                if (response.isSuccess()) {
                    String main = response.body().getWeather().get(0).getMain();
                    mBinding.txtWeatherStatus.setText(main);
                    String icon;
                    icon = response.body().getWeather().get(0).getIcon();
                    String url = PREFIX_WEATHER_ICON_URL + icon + SUFFIX_WEATHER_ICON_URL;
                    Glide.with(mContext)
                            .load(url)
                            .into(mBinding.imageView);
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return view;
    }
}