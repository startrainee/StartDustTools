package com.stardust.tools.startdusttools.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.stardust.tools.startdusttools.poisearch.PoiModel;
import com.stardust.tools.startdusttools.R;
import com.stardust.tools.startdusttools.poisearch.POISearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class PoiSearchActivity extends AppCompatActivity {
    private static final String TAG = "wcy-PoiSearchActivity";

    PoiSearch mPoiSearch;
    FloatingSearchView mSearchView;

    RecyclerView mRvOfPOISearch;
    POISearchResultAdapter mPOISearchResultAdapter;
    List<PoiInfo> poiModelList;

    SuggestionSearch mSuggestionSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initRecyclerView();
        initPoiSuggestionSearch();
        initPoiSearch();

        mSearchView = findViewById(R.id.floating_search_view);
        //在xml中设置了键盘的回车键改为搜索键的话，就需要监听两个点击事件，该函数包括来两个点击事件，
        //第一个为搜索推荐项的点击事件，第二个为键盘直接点击搜索按钮的点击事件
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                String keyword = searchSuggestion.getBody();
                Log.d(TAG,"onSuggestionClicked() body: " + keyword);
                String city = "沈阳";
                poiSearchInCity(keyword,city);
                mSearchView.showProgress();

            }

            @Override
            public void onSearchAction(String currentQuery) {
                Log.d(TAG,"setOnSearchListener() currentQuery: " + currentQuery);
                String city = "沈阳";
                poiSearchInCity(currentQuery,city);
                mSearchView.clearSuggestions();
                mSearchView.showProgress();
            }
        });

        //检测搜索字符变化的监听器
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    Log.d(TAG,"setOnSearchListener() newQuery: " + newQuery);
                    String city = "沈阳";
                    searchPoiSuggestion(newQuery,city);
                }
            }
        });

        //自定义 搜索 结果元素 的 样式
        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                leftIcon.setImageResource(R.mipmap.icon_poi_location);
                leftIcon.setPadding(30,30,30,30);
            }
        });
    }

    private void poiSearchInCity(String newQuery, String city) {
        mPoiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(newQuery));
        mSearchView.showProgress();
    }

    private void initRecyclerView() {

        mRvOfPOISearch = findViewById(R.id.recyclerview);
        mPOISearchResultAdapter = new POISearchResultAdapter(null,this);
        mRvOfPOISearch.setAdapter(mPOISearchResultAdapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        mRvOfPOISearch.setLayoutManager(linearLayout);
        mRvOfPOISearch.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mPOISearchResultAdapter.setOnItemClickListener(new POISearchResultAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                Log.d(TAG, "poiInfo.position: " + position);
                Toast.makeText(PoiSearchActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initPoiSuggestionSearch() {
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(getSuggestionResultListener());
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
                poiModelList = new ArrayList<>();
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
                        poiModelList.add(info);
                    }
                } else {
                  PoiInfo poiInfo = new PoiInfo();
                  poiInfo.name = getString(R.string.text_no_search_result);
                  poiModelList.add(poiInfo);
                }
                mSearchView.hideProgress();
                mSearchView.clearSuggestions();
                mPOISearchResultAdapter.refreshData(poiModelList);
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

    private OnGetSuggestionResultListener getSuggestionResultListener(){
        return new OnGetSuggestionResultListener() {
            public void onGetSuggestionResult(SuggestionResult res) {

                if (res == null || res.getAllSuggestions() == null) {
                    return;
                    //未找到相关结果
                }

                //获取在线建议检索结果
                List<PoiModel> poiModelList = new ArrayList<>();
                List<SuggestionResult.SuggestionInfo> suggestionInfoList = res.getAllSuggestions();

                for(SuggestionResult.SuggestionInfo info : suggestionInfoList){
                    Log.d(TAG, "OnGetSuggestionResultListener info.key: " + info.key);
                    Log.d(TAG, "OnGetSuggestionResultListener info.city: " + info.city);
                    Log.d(TAG, "OnGetSuggestionResultListener info.district: " + info.district);
                    Log.d(TAG, "OnGetSuggestionResultListener info.tag: " + info.tag);
                    Log.d(TAG, "OnGetSuggestionResultListener info.pt: " + info.pt);
                    Log.d(TAG, "OnGetSuggestionResultListener info.address: " + info.address);
                    Log.d(TAG, "OnGetSuggestionResultListener info.toString(): " + info.toString());
                    poiModelList.add(new PoiModel(info.key));
                }
                mSearchView.hideProgress();
                mSearchView.swapSuggestions(poiModelList);

            }
        };
    }

    private void searchPoiSuggestion(String keyWord,String city){

        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword(keyWord)
                .city(city));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }
}
