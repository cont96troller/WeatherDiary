package com.cont96roller.weatherdiary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.session.PlaybackState;
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
import com.cont96roller.weatherdiary.common.Constants;

import java.util.List;

public class DiaryFragment extends Fragment {

    //    List<DiaryModel> mDiaryList = new ArrayList<>();
    DiaryAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private DiaryDB diaryDB = null;
    List<Diary> diaryList;
    private DiaryAdapter diaryAdater;
    private DeleteReceiver mReceiver;
    private EditReceiver mEditReceiver;

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
//                Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
            }
        });


        getDiaryList();
        initView(view);

        mReceiver = new DeleteReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_DELETE_DIARY);
        filter.addAction(Constants.ACTION_EDIT_DIARY);
        getActivity().registerReceiver(mReceiver, filter);

        mEditReceiver = new EditReceiver();
        IntentFilter editFilter = new IntentFilter();
        editFilter.addAction(Constants.ACTION_EDIT_DIARY);
        getActivity().registerReceiver(mEditReceiver, filter);

        return view;
    }

    public void initView(View viewTest) {
        RecyclerView mRecyclerView = viewTest.findViewById(R.id.recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new DiaryAdapter(diaryList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getDiaryList() {


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }

        if(mEditReceiver != null) {
            getActivity().unregisterReceiver(mEditReceiver);
        }
    }

    public class DeleteReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction != null) {
                switch (intentAction) {
                    case Constants
                            .ACTION_DELETE_DIARY:
                        getDiaryList();
                        break;

                }
            }
        }
    }

    public class EditReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if(intentAction != null) {
                switch (intentAction) {
                    case Constants
                            .ACTION_EDIT_DIARY:
                        getDiaryList();
                        break;
                }
            }

        }


    }

}

