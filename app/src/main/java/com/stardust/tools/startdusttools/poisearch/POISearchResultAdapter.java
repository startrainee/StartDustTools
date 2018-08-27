package com.stardust.tools.startdusttools.poisearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.stardust.tools.startdusttools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/6/22.
 *
 * @author siasun-wangchongyang
 */
public class POISearchResultAdapter extends RecyclerView.Adapter<POISearchResultAdapter.POIViewHolder> {

    private List<PoiInfo> data;       // 行程列表

    private LayoutInflater mInflater;

    private OnItemClickListener listener;

    public POISearchResultAdapter(List<PoiInfo> data, Context context) {
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
    }

    public void refreshData(List<PoiInfo> routeBeanList) {
        if (routeBeanList == null || routeBeanList.size() == 0) {
            this.data = new ArrayList<>();
        } else {
            this.data = routeBeanList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public POIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new POIViewHolder(mInflater.inflate(R.layout.layout_item_view_poi_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull POIViewHolder holder, int position) {
        PoiInfo poiInfo = data.get(position);
        holder.bind(position,poiInfo, listener);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class POIViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rootLayout;
        TextView name;
        TextView address;
        TextView noResult;
        ImageView rightIcon;
        LinearLayoutCompat linearLayout;

        POIViewHolder(View itemView) {
            super(itemView);
            rootLayout   = itemView.findViewById(R.id.poi_root_layout);
            noResult     = itemView.findViewById(R.id.poi_no_result);
            linearLayout = itemView.findViewById(R.id.poi_search_container);
            name         = itemView.findViewById(R.id.poi_title);
            address      = itemView.findViewById(R.id.poi_address);
            rightIcon    = itemView.findViewById(R.id.iv_icon_right);

        }

        void bind(final int position, PoiInfo poiInfo, final OnItemClickListener onItemClickListener) {

            if(poiInfo.name!= null && "没有搜索结果".equals(poiInfo.name)){
                noResult.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                rightIcon.setVisibility(View.GONE);
                rootLayout.setOnClickListener(null);
                return;
            }
            name.setText(poiInfo.name);
            address.setText(poiInfo.address);
            noResult.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            rightIcon.setVisibility(View.VISIBLE);

            rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(position);
                }
            });

        }

    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}
