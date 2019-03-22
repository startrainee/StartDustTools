package com.stardust.tools.startdusttools.activity.recycleview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.stardust.tools.startdusttools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/3/22.
 *
 * @author siasun-wangchongyang
 */
public class RecycleViewAdapter extends RecyclerView.Adapter {

    private static final int VIEW_FOOT = 1;
    private static final int VIEW_CONTENT = 0;
    public static final int PER_PAGE = 10;
    private List<String> mData;
    private Context mContext;
    private boolean isCanLoadMore = false;
    private boolean isLoading = false;
    private Animation rotateAnimator;

    private RecyclerOnScrollerListener onScrollListener;
    private OnLoadMoreListener onLoadMoreListener;

    public RecycleViewAdapter(Context context) {
        this.mContext = context;
    }

    public void refreshData(List<String> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 1 : mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return VIEW_FOOT;
        }
        return VIEW_CONTENT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (VIEW_FOOT == viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_view_foot, parent, false);
            return new FootViewHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_view_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("RecycleViewAdapter", " onBindViewHolder() position: " + position);
        if(holder instanceof FootViewHolder){
            if(rotateAnimator == null){
                rotateAnimator = AnimationUtils.loadAnimation(mContext,R.anim.act_loading);
                rotateAnimator.setInterpolator(new LinearInterpolator());//动画速度为匀速
            }
            if(isCanLoadMore()){
                ((FootViewHolder)holder).showLoading();
                return;
            }
            String s = "无法加载更多数据";
            ((FootViewHolder)holder).showTextOnly(s);
            return;
        }

        if(holder instanceof ContentViewHolder){
            ((ContentViewHolder)holder).binding(mData.get(position),position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.d("RecycleViewAdapter", "onAttachedToRecyclerView()");
        super.onAttachedToRecyclerView(recyclerView);
        onScrollListener = new RecyclerOnScrollerListener(recyclerView) {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrollListener = new RecyclerOnScrollerListener(recyclerView) {
                    @Override
                    public void onLoadMore(int currentPage) {
                        Log.d("RecycleViewAdapter", "currentPage: " + currentPage);
                        if(onLoadMoreListener != null){
                            onLoadMoreListener.onLoadMore(currentPage);
                        }
                    }
                };
                recyclerView.addOnScrollListener(onScrollListener);
                //初始化的时候如果未填满一页，那么肯定就没有更多数据了
                if (mData != null && mData.size() < PER_PAGE) {
                    setCanLoadMore(false);
                } else {
                    setCanLoadMore(true);
                }
            }

            @Override
            public void onLoadMore(int currentPage) {

            }
        };

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        super.onDetachedFromRecyclerView(recyclerView);
        if (onScrollListener != null) {
            recyclerView.removeOnScrollListener(onScrollListener);
        }
        onScrollListener = null;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore(int curPage);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
    private boolean isCanLoadMore() {
        return isCanLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        isCanLoadMore = canLoadMore;
        onScrollListener.setCanLoadMore(isCanLoadMore);
        onScrollListener.setLoading(false);
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView  text;

        FootViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.foot_image);
            text  = itemView.findViewById(R.id.foot_text);
        }

        void showTextOnly(String s){
                image.setVisibility(View.GONE);
                text.setText(s);
        }

        void showLoading(){
            if(isLoading){
                image.setVisibility(View.VISIBLE);
                image.startAnimation(rotateAnimator);
                text.setText("加载刷新中");
            }
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView left;
        TextView center;
        TextView right;

        ContentViewHolder(View itemView) {
            super(itemView);
            left   = itemView.findViewById(R.id.content_left);
            center = itemView.findViewById(R.id.content_center);
            right  = itemView.findViewById(R.id.content_right);
        }

        void binding(String string,int position){
            String text = "-"+ position;
            left.setText(text);
            center.setText(string);
            right.setText("No."+ mData.size());

        }
    }
}
