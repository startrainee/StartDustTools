package com.stardust.tools.startdusttools.activity.recycleview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.stardust.tools.startdusttools.R;
import com.stardust.tools.startdusttools.activity.recycleview.adapter.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.stardust.tools.startdusttools.activity.recycleview.adapter.RecycleViewAdapter.PER_PAGE;

public class RecycleViewActivity extends AppCompatActivity implements RecycleViewAdapter.OnLoadMoreListener {

    private final String TAG = getClass().getSimpleName();
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    int mCurrentPage = 0;
    List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new RecycleViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        adapter.refreshData(mData);
        adapter.setOnLoadMoreListener(this);
        adapter.setCanLoadMore(true);
        onLoadMore(mCurrentPage);
    }

    @Override
    public void onLoadMore(int curPage) {
        //todo

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<String> list = new ArrayList<>();
                for (int i = 20 * mCurrentPage; i < 20 + 10 * mCurrentPage; i++) {
                    String text = "334路 — " + i;
                    list.add(text);
                }
                mData.addAll(list);
                Log.d(TAG, "size1: " + list.size() + "  -  size2: " + mData.size());
                //如果未填满一整页，那么肯定没有更多数据了
                if (list.size() <= PER_PAGE) {
                    adapter.setCanLoadMore(true);
                } else {
                    adapter.setCanLoadMore(false);
                }
                adapter.refreshData(mData);
            }
        }, 3000);

    }
}
