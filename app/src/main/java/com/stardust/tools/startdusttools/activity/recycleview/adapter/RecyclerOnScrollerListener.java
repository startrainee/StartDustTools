package com.stardust.tools.startdusttools.activity.recycleview.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created on 2019/3/22.
 *
 * @author siasun-wangchongyang
 */
public abstract class  RecyclerOnScrollerListener extends RecyclerView.OnScrollListener {

    String TAG = "RecyclerScrollerListener";
    private RecyclerView recyclerView;

    private int currentPage = 0;

    //是否正在加载
    private boolean isLoading = false;
    //是否能够加载更多
    private boolean isCanLoadMore = false;

    RecyclerOnScrollerListener(RecyclerView recyclerView) {
        super();
        this.recyclerView = recyclerView;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.d(TAG,"onScrolled()");
        //判断是否符合下拉加载条件
        if(isCanLoadMore()){
            Log.d(TAG,"onScrolled() isSlideBottom: " + isSlideBottom());
            if(isSlideBottom()){

                onLoadMore(currentPage);
                currentPage++;
                setLoading(true);
            }
        }
    }

    private boolean isSlideBottom(){

        if(recyclerView == null){
          return false;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            int totalVisibleItem        = linearLayoutManager.getItemCount();
            return !isLoading() && lastVisibleItemPosition > 0 && lastVisibleItemPosition == totalVisibleItem - 1;
        }
        return false;
    }

    public abstract void onLoadMore(int currentPage);

    private boolean isCanLoadMore() {
        Log.d(TAG,"onScrolled() isCanLoadMore: " + isCanLoadMore);
        return isCanLoadMore;
    }

    void setCanLoadMore(boolean canLoadMore) {
        isCanLoadMore = canLoadMore;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
