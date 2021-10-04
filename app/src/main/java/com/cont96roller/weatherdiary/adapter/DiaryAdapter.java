package com.cont96roller.weatherdiary.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cont96roller.weatherdiary.Diary;
import com.cont96roller.weatherdiary.MainActivity;
import com.cont96roller.weatherdiary.R;
import com.cont96roller.weatherdiary.ShowDiaryActivity;
import com.cont96roller.weatherdiary.model.DiaryModel;

import java.io.Serializable;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> implements Serializable {

    private Context mContext;


    List<Diary> mDiaryList = null;
    final private static String TAG = "pyorong";


    public DiaryAdapter(List<Diary> mDiaryList) {
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
        Diary dairy = mDiaryList.get(position);

        mContext = holder.mConstList.getContext();

//        holder.mTxtWeather.setText(dairy.getgetWeatherStatus());
        holder.mTxtTitle.setText(dairy.getContents());
//        holder.mTxtDate.setText(dairy.getDate());
//        holder.mConstList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, WriteActivity.class);
//                intent.putExtra("key", holder.getAdapterPosition());
////                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
//                mContext.startActivity(intent);
//            }
//        });





        mContext = holder.mConstList.getContext();

        holder.mConstList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ShowDiaryActivity.class);
                intent.putExtra("key2", "일기조회하기");


//                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);

                switch(view.getId()) {



                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return (null != mDiaryList ? mDiaryList.size() : 0);
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        protected TextView mTxtWeather;
        protected TextView mTxtTitle;
        protected TextView mTxtDate;
        protected ConstraintLayout mConstList;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTxtWeather = itemView.findViewById(R.id.txt_weather);
            this.mTxtTitle = itemView.findViewById(R.id.title);
            this.mTxtDate = itemView.findViewById(R.id.date);
            this.mConstList = itemView.findViewById(R.id.const_list);

        }
    }

}
