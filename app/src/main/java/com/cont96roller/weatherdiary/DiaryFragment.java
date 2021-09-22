package com.cont96roller.weatherdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cont96roller.weatherdiary.adapter.DiaryAdapter;
import com.cont96roller.weatherdiary.model.DiaryModel;

import java.util.ArrayList;

public class DiaryFragment extends Fragment {

    ArrayList<DiaryModel> mDiaryList = new ArrayList<>();
    DiaryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary, container, false);


        addDiaryModel();
        initView(view);


        return view;
    }

    public void initView(View viewTest) {
        RecyclerView mRecyclerView = viewTest.findViewById(R.id.recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new DiaryAdapter(mDiaryList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void addDiaryModel() {
        mDiaryList.clear();
        mDiaryList.add(new DiaryModel("바람많은", "춥다", "2021.08.21"));
        mDiaryList.add(new DiaryModel("비가오는", "싸늘", "2021.07.21"));
        mDiaryList.add(new DiaryModel("날이더운", "더운", "2021.06.21"));

    }
}

