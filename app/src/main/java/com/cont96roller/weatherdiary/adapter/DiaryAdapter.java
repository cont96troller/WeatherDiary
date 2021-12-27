package com.cont96roller.weatherdiary.adapter;

import static com.cont96roller.weatherdiary.common.Constants.DIARY_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cont96roller.weatherdiary.Diary;
import com.cont96roller.weatherdiary.R;
import com.cont96roller.weatherdiary.ShowDiaryActivity;
import com.cont96roller.weatherdiary.common.Constants;
import com.cont96roller.weatherdiary.databinding.ItemDiaryBinding;
import com.cont96roller.weatherdiary.model.ApiInterface;
import com.cont96roller.weatherdiary.model.ResponseWeather;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private Context mContext;
    private ItemDiaryBinding mBinding;
    List<Diary> mDiaryList = null;

    public DiaryAdapter(List<Diary> mDiaryList) {
        this.mDiaryList = mDiaryList;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        mBinding = ItemDiaryBinding.inflate(LayoutInflater.from(mContext), parent, false);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary, parent, false);
        DiaryViewHolder viewHolder = new DiaryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Diary diary = mDiaryList.get(position);
        holder.mTxtTitle.setText(diary.getTitle());
        holder.mTxtDate.setText(String.valueOf(diary.getDate()));
        holder.mTxtWeather.setText(diary.getStatus());
        String icon = diary.getIcon();
        String url = Constants.PREFIX_WEATHER_ICON_URL + icon + Constants.SUFFIX_WEATHER_ICON_URL;
        mContext = holder.mConstList.getContext();
        Glide.with(mContext)
                .load(url)
                .into(holder.mImgWeather);

        holder.mConstList.setOnClickListener(view -> {

            Intent intent = new Intent(mContext, ShowDiaryActivity.class);
            intent.putExtra(DIARY_ID_KEY, diary.id);

            mContext.startActivity(intent);

            switch (view.getId()) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mDiaryList ? mDiaryList.size() : 0);
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        protected ImageView mImgWeather;
        protected TextView mTxtWeather;
        protected TextView mTxtTitle;
        protected TextView mTxtDate;
        protected ConstraintLayout mConstList;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mImgWeather = itemView.findViewById(R.id.img_item_weather);
            this.mTxtWeather = itemView.findViewById(R.id.txt_weather);
            this.mTxtTitle = itemView.findViewById(R.id.title);
            this.mTxtDate = itemView.findViewById(R.id.date);
            this.mConstList = itemView.findViewById(R.id.const_list);

        }
    }
}