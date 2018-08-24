package com.stardust.tools.startdusttools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "wcy-MainActivity";

    PoiSearch mPoiSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPoiSearch();

        FloatingSearchView searchView = findViewById(R.id.floating_search_view);

    }

    private void initPoiSearch() {

        //POI检索 初始化
        mPoiSearch = PoiSearch.newInstance();
        //POI检索监听器
        OnGetPoiSearchResultListener poiListener = getOnGetPoiSearchResultListener();
        //设置POI检索监听器
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        
    }

    //POI搜索返回结果监听器
    private OnGetPoiSearchResultListener getOnGetPoiSearchResultListener() {
        return new OnGetPoiSearchResultListener() {

            public void onGetPoiResult(PoiResult result) {
                //获取POI检索结果
                Log.d(TAG, "OnGetPoiSearchResultListener onGetPoiResult() " + result.getAllPoi());
                List<PoiInfo> res = result.getAllPoi();
                if (res != null && res.size() != 0) {
                    for (PoiInfo info : res) {
                        if(info == null){
                            Log.e(TAG, "OnGetPoiSearchResultListener info is null! ");
                            continue;
                        }
                        Log.d(TAG, "OnGetPoiSearchResultListener info.name: " + info.name);
                        Log.d(TAG, "OnGetPoiSearchResultListener info.location: " + info.location);
                        Log.d(TAG, "OnGetPoiSearchResultListener info.address: " + info.address);
                        Log.d(TAG, "OnGetPoiSearchResultListener info: " + info.toString());
                        /*if (ooPolygon != null && SpatialRelationUtil.isPolygonContainsPoint(ooPolygon.getPoints(), info.location)) {
                            mResultOfPOISearch.add(info);
                        }*/
                    }
                }
            }

            public void onGetPoiDetailResult(PoiDetailResult result) {
                //获取Place详情页检索结果
                Log.d(TAG, "OnGetPoiSearchResultListener onGetPoiDetailResult() " + result.toString());
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Log.d(TAG, "OnGetPoiSearchResultListener onGetPoiIndoorResult() " + poiIndoorResult.toString());
            }
        };
    }
}
