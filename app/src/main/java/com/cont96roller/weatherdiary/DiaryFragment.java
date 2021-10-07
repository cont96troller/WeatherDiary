package com.cont96roller.weatherdiary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cont96roller.weatherdiary.adapter.DiaryAdapter;
import com.cont96roller.weatherdiary.model.DiaryModel;

import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends Fragment {

//    List<DiaryModel> mDiaryList = new ArrayList<>();
    DiaryAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private DiaryDB diaryDB = null;
    List<Diary> diaryList;
    private DiaryAdapter diaryAdater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        diaryDB = DiaryDB.getInstance(mContext);
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WriteDiaryActivity.class);
                intent.putExtra("key2", "일기조회하기");
                startActivity(intent);
                Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
            }
        });


        addDiaryModel();
        initView(view);


        return view;
    }

    public void initView(View viewTest) {
        RecyclerView mRecyclerView = viewTest.findViewById(R.id.recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new DiaryAdapter(diaryList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void addDiaryModel() {


        diaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();

//        String title = diaryList.get(0).contents;
//        long date = diaryList.get(0).date;

        InsertRunnable insertRunnable = new InsertRunnable();
        Thread t = new Thread(insertRunnable);
        t.start();
//        diaryList.clear();
//        diaryList.add(new Diary(title));/
//        diaryList.add(new Diary());
//        diaryList.add(new DiaryModel("비가오는", "싸늘", "2021.07.21"));
//        diaryList.add(new DiaryModel("날이더운", "더운", "2021.06.21"));

    }

    class InsertRunnable implements Runnable {


        @Override
        public void run() {
            try {
                diaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
                diaryAdater = new DiaryAdapter(diaryList);
                diaryAdater.notifyDataSetChanged();
//                    String title = diaryList.get(0).contents;
                mRecyclerView.setAdapter(diaryAdater);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
            } catch (Exception e) {

            }

//                diaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
//                String title = diaryList.get(0).contents;
//                InsertRunnable insertRunnable = new InsertRunnable();
//                Thread t = new Thread(insertRunnable);
//                t.start();
//            diaryList.clear();
//            diaryList.add(new DiaryModel(title, "춥다", "2021.08.21"));
//            diaryList.add(new DiaryModel("비가오는", "싸늘", "2021.07.21"));
//            diaryList.add(new DiaryModel("날이더운", "더운", "2021.06.21"));

        }
    }


}

