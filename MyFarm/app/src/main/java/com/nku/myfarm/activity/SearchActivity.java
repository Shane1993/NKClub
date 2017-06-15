package com.nku.myfarm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.nku.myfarm.R;
import com.nku.myfarm.adapter.SearchAdapter;
import com.nku.myfarm.config.CacheType;
import com.nku.myfarm.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.id.list;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Shane on 2017/4/18.
 */

public class SearchActivity extends Activity implements SearchView.OnQueryTextListener{

    @BindView(R.id.sv_search)
    SearchView searchView;

    @BindView(R.id.rv_search)
    RecyclerView recyclerView;

    private GridLayoutManager gridLayoutManager;

    private SearchAdapter searchAdapter;

    private List<String> hotList;
    private List<String> historyList;

    //设置历史记录的个数
    private static final int mHistoryCount = 8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onDestroy() {

        //在程序关闭的时候更新搜索历史，只保留mHistoryCount个
        StringBuilder sb = new StringBuilder();
        if(historyList.size() <= mHistoryCount) {
            for(String s : historyList) {
                sb.append(s+",");
            }
        }else {
            for(int i = historyList.size()-mHistoryCount;i<historyList.size();i++) {
                sb.append(historyList.get(i)+",");
            }
        }
        Config.cacheData(SearchActivity.this, sb.toString(), CacheType.SEARCHDATA);

        super.onDestroy();
    }

    //根据字符串搜索
    public void searchProduct(String query) {
        //添加不重复搜索记录，并且利用了LRU算法将最近访问的放在末尾
        if(historyList.contains(query)) {
            historyList.remove(query);
        }
        historyList.add(query);
        searchAdapter.notifyDataSetChanged();

        Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
    }

    private void initView() {

        searchView.setOnQueryTextListener(this);

        gridLayoutManager = new GridLayoutManager(SearchActivity.this, 2);
        gridLayoutManager.canScrollVertically();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //设置只有热门搜索项才有两列
                //这里的表达式不要写反了
                return searchAdapter.isHotItem(position) ? 1 : gridLayoutManager.getSpanCount();
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


        fillList();

        searchAdapter = new SearchAdapter(SearchActivity.this, hotList, historyList,
                hotListener, historyListener, clearListener);

        recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }


    private void fillList() {
        hotList = getHotList();
        historyList = getHistoryList();

    }

    private List<String> getHotList() {
        List<String> list = new ArrayList<String>();

        for(String s : hotData) {
            list.add(s);
        }

        return list;
    }

    private List<String> getHistoryList() {

        List<String> list = new ArrayList<String>();
        String searchData = Config.getCachedData(SearchActivity.this, CacheType.SEARCHDATA);

        if(!TextUtils.isEmpty(searchData)) {
            String[] arr = searchData.split(",");
            for(String s : arr) {
                list.add(s);
            }
        }

        return list;
    }

    //设置点击热门搜索后的事件
    private SearchAdapter.OnClickHotListener hotListener = new SearchAdapter.OnClickHotListener() {
        @Override
        public void onClickHot(View view) {
            int positionInRecyclerView = recyclerView.getChildAdapterPosition(view);
            int positionInHotList = searchAdapter.getPositionInHotList(positionInRecyclerView);
            searchProduct(hotList.get(positionInHotList));
        }
    };

    //设置点击热门搜索后的事件
    private SearchAdapter.OnClickHistoryListener historyListener = new SearchAdapter.OnClickHistoryListener() {
        @Override
        public void onClickHistory(View view) {
            int positionInRecyclerView = recyclerView.getChildAdapterPosition(view);
            int positionInHistoryList = searchAdapter.getPositionInHistoryList(positionInRecyclerView);
            searchProduct(historyList.get(positionInHistoryList));
        }

        @Override
        public void onClickHistoryDelete(int positonInHistoryList) {
            historyList.remove(positonInHistoryList);
            searchAdapter.notifyDataSetChanged();
        }
    };

    private SearchAdapter.OnClickClearListener clearListener = new SearchAdapter.OnClickClearListener() {
        @Override
        public void onClickClearHistory() {

            Config.cacheData(SearchActivity.this, "", CacheType.SEARCHDATA);
            historyList.clear();
            searchAdapter.notifyDataSetChanged();
        }
    };

    @OnClick(R.id.tv_search_cancel)
    public void cancel() {
        SearchActivity.this.finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        searchProduct(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    private String[] hotData = {"苹果","毛冬瓜","进口蓝莓","香蕉","北京白菜","秋葵","琵琶",
            "西兰花","鸡腿","牛腩块","传香鸡块","优质肘子","黑美人西瓜","经典鸡块",};

    private String[] historyData = {"毛冬瓜","进口蓝莓","西兰花","鸡腿"};

}
