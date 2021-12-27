package com.cont96roller.weatherdiary;

import static com.cont96roller.weatherdiary.common.Constants.ACTION_DETAILED_DIARY;
import static com.cont96roller.weatherdiary.common.Constants.DETAILED_KEY;
import static com.cont96roller.weatherdiary.common.Constants.ERROR_LOG;

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

    private DiaryAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private DiaryDB mDiaryDB = null;
    List<Diary> mDiaryList;
    private DiaryAdapter mDiaryAdater;
    private DiaryListUpdateReceiver mReceiver;
    private Thread mThreadGetDiary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        mDiaryDB = DiaryDB.getInstance(mContext);
        getDiaryData();
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setOnClickListener(view1 -> {
            Intent intent = new Intent(mContext, WriteDiaryActivity.class);
            intent.putExtra(DETAILED_KEY, ACTION_DETAILED_DIARY);
            startActivity(intent);
        });

        setDiaryList();
        initView(view);

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

        mAdapter = new DiaryAdapter(mDiaryList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setDiaryList() {
        mDiaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
    }

    private void getDiaryData() {
        mThreadGetDiary = new Thread(() -> {
            try {
                mDiaryList = DiaryDB.getInstance(mContext).diaryDao().getAll();
                mDiaryAdater = new DiaryAdapter(mDiaryList);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setAdapter(mDiaryAdater);
            } catch (Exception e) {
                Log.e(ERROR_LOG, e.getMessage());
            }
        });
        mThreadGetDiary.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
        //deprecated
//        mThreadGetDiary.stop();
        mThreadGetDiary.interrupt();
    }

    public class DiaryListUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction != null) {
                switch (intentAction) {
                    case Constants.ACTION_DELETE_DIARY:
                    case Constants.ACTION_EDIT_DIARY:
                        setDiaryList();
                        break;
                }
            }
        }
    }
}

