package com.cont96roller.weatherdiary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cont96roller.weatherdiary.adapter.DiaryAdapter;
import com.cont96roller.weatherdiary.common.Constants;

import java.util.List;

public class DiaryFragment extends Fragment {


    //접근제한자 확실하게 사용(다시 공부하기)
    DiaryAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private DiaryDB diaryDB = null;
    List<Diary> diaryList;
    private DiaryAdapter diaryAdater;
    private DiaryListUpdateReceiver mReceiver;

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
            }
        });

        getDiaryList();
        initView(view);

        //Receiver로 정보를 받아서 최신화시켜준다.
        mReceiver = new DiaryListUpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_DELETE_DIARY);
        filter.addAction(Constants.ACTION_EDIT_DIARY);
        getActivity().registerReceiver(mReceiver, filter);

        return view;
    }

    public void initView(View viewTest) {
        RecyclerView mRecyclerView = viewTest.findViewById(R.id.recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //diaryList 의미파악 어려움
        mAdapter = new DiaryAdapter(diaryList);
        mRecyclerView.setAdapter(mAdapter);
    }

    //get인데 void인것이 부적절(네이밍규칙 지키기) = set사용(줄일수있으면 줄이기)
    public void getDiaryList() {

        diaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
        InsertRunnable insertRunnable = new InsertRunnable();
        Thread t = new Thread(insertRunnable);
        t.start();
    }

    class InsertRunnable implements Runnable {

        @Override
        public void run() {
            try {
                diaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
                diaryAdater = new DiaryAdapter(diaryList);
                //한번 사용하는것은 변수로 사용하지 말것
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setAdapter(diaryAdater);
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
    }

    public class DiaryListUpdateReceiver extends BroadcastReceiver {
        //
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction != null) {
                switch (intentAction) {
                    case Constants.ACTION_DELETE_DIARY:
                    case Constants.ACTION_EDIT_DIARY:
                        getDiaryList();
                        break;

                }
            }
        }
    }
}

