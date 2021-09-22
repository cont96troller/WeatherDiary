package com.cont96roller.weatherdiary.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cont96roller.weatherdiary.MainActivity;
import com.cont96roller.weatherdiary.R;
import com.cont96roller.weatherdiary.model.DiaryModel;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    ArrayList<DiaryModel> mDiaryList = null;
    final private static String TAG = "pyorong";

    public DiaryAdapter(ArrayList<DiaryModel> mDiaryList) {
        this.mDiaryList = mDiaryList;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//RecyclerView의 아이템 xml 파일로 무엇을 사용할지 선언
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary, parent, false);

        DiaryViewHolder viewHolder = new DiaryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
//        Item이 하나씩 그려질 때 마다 호출됨
        Log.e(MainActivity.TAG, "동희" + position);
        DiaryModel model = mDiaryList.get(position);


        holder.mWeather.setText(model.getWeatherStatus());
        holder.mTitle.setText(model.getTitle());
        holder.mDate.setText(model.getDate());
    }

    @Override
    public int getItemCount() {
        return (null != mDiaryList ? mDiaryList.size() : 0);
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        protected TextView mWeather;
        protected TextView mTitle;
        protected TextView mDate;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mWeather = itemView.findViewById(R.id.txt_weather);
            this.mTitle = itemView.findViewById(R.id.title);
            this.mDate = itemView.findViewById(R.id.date);

        }
    }
}
