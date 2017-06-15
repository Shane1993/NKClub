package com.nku.myfarm.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nku.myfarm.R;
import com.nku.myfarm.activity.ScanResultActivity;
import com.nku.myfarm.activity.SearchActivity;
import com.nku.myfarm.adapter.HomeAdapter;
import com.nku.myfarm.model.BaseCategory;
import com.nku.myfarm.model.category.FirstCategory;
import com.nku.myfarm.model.category.SecondCategory;
import com.nku.myfarm.net.NetConfig;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Shane on 2017/3/27.
 */

public class FragmentHome extends Fragment {

    private static final String TAG = "FragmentHome";

    View view;
    Context context;

    @BindView(R.id.ib_top_search)
    ImageButton btnSearch;

    @BindView(R.id.ib_top_scan)
    ImageButton btnScan;


    private FirstCategory[] categories;

    //以下的跟展示的列表有关
    private HomeAdapter homeAdapter;
    private List<BaseCategory> categoryList;
    @BindView(R.id.rv_home)
    RecyclerView recyclerView;

    GridLayoutManager layoutManager;


    //以下跟轮播控件有关
//    @BindView(R.id.rpv_home)
//    RollPagerView rpv;
//    private RollAdapter rollAdapter;
    private List<BaseCategory> bannerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);

//        initView();

        return view;
    }

    private void initView() {

        context = getContext();

        ButterKnife.bind(this, view);


        layoutManager = new GridLayoutManager(context, 2);
        layoutManager.canScrollVertically();
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //这里的表达式不要写反了
                return (homeAdapter.isHeaderView(position) || homeAdapter.isFirstCategory(position)) ? layoutManager.getSpanCount() : 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //填充轮播和列表数据
        fillData();

        homeAdapter = new HomeAdapter(context, categoryList, bannerList, new HomeAdapter.OnRecycleViewListener() {
            @Override
            public void onItemClick(int position) {
                //在这里处理首页的点击事件
//                int position = recyclerView.getChildAdapterPosition(view) - homeAdapter.getHeaderCount();
                onItemSelected(position);
            }
        }, new HomeAdapter.OnRollBannerListener() {
            @Override
            public void onRollItemClick(BaseCategory baseCategory) {
                //在这里处理轮播的点击事件
                Toast.makeText(context, baseCategory.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();


    }


    private void fillData() {

        //从服务器获取json格式的数据
        String data = getDataFromServer();

        //将json格式的数据解析成对象数组
        categories = parseDataToArray(data);

        //填充列表
        categoryList = parseCategoryList(categories);

        //填充轮播数据
        bannerList = getListFromArray(categories);

    }

    public FirstCategory[] parseDataToArray(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, FirstCategory[].class);
    }


    /**
     * 从服务器中获取数据，这里需要修改
     *
     * @return
     */
    public String getDataFromServer() {

        // TODO: 2017/6/9

        return categoryStr;
    }

    public List<BaseCategory> parseCategoryList(FirstCategory[] categories) {
        List<BaseCategory> list = new ArrayList<BaseCategory>();
        SecondCategory[] secondCategoties;

        for (FirstCategory fc : categories) {
            list.add(fc);
//            //为了补齐
//            list.add(new FirstCategory(VIEWTYPE_FIRST_CATEGORY));

            secondCategoties = fc.getCids();
//            int i = 0;
            for (SecondCategory sc : secondCategoties) {
                sc.setImg(NetConfig.SERVER_URL + "categories/" + fc.getName() + "/" + sc.getImg() + ".imageset/" + sc.getName() + ".jpg");
                list.add(sc);
//                i++;
            }
//            //为了补齐九宫格视图
//            if((i & 1) != 0) {
//                list.add(new SecondCategory(VIEWTYPE_SECOND_CATEGORY));
//            }
        }
        return list;
    }

    public List<BaseCategory> getListFromArray(FirstCategory[] categories) {
        List<BaseCategory> list = new ArrayList<>();

        for (FirstCategory fc : categories) {
            list.add(fc);
        }
        return list;
    }


    @OnClick(R.id.ib_top_search)
    public void searchProduct() {
        startActivity(new Intent(context, SearchActivity.class));
    }

    @OnClick(R.id.ib_top_scan)
    public void scan() {
        startActivityForResult(new Intent(context, CaptureActivity.class), 0);
    }

    /**
     * 用于打开二维码扫描结果的界面
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Intent intent = new Intent(context, ScanResultActivity.class);
            intent.putExtra("result", scanResult);
            startActivity(intent);
        }

    }

    /**
     * 选中列表项之后工作，需要后期完成
     *
     * @param position
     */
    public void onItemSelected(int position) {

        // TODO: 2017/6/9

        Log.d(TAG, categoryList.get(position).getName() + "");
        Toast.makeText(context, categoryList.get(position).getName(), Toast.LENGTH_SHORT).show();
    }


    String categoryStr = "[\n" +
            "            {\n" +
            "                \"id\": 103532,\n" +
            "                \"name\": \"优选水果\",\n" +
            "                \"cids\": [\n" +
            "                    {\n" +
            "                        \"id\": 103533,\n" +
            "                        \"name\": \"国产浆果\",\n" +
            "                        \"pcid\": 103532,\n" +
            "                        \"img\": \"国产浆果\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103534,\n" +
            "                        \"name\": \"苹果类\",\n" +
            "                        \"pcid\": 103532,\n" +
            "                        \"img\": \"苹果类\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103537,\n" +
            "                        \"name\": \"瓜类\",\n" +
            "                        \"pcid\": 103532,\n" +
            "                        \"img\": \"瓜类-水果\"\n" +
            "                    },\n" +
            "                   {\n" +
            "                        \"id\": 103536,\n" +
            "                        \"name\": \"梨类\",\n" +
            "                        \"pcid\": 103532,\n" +
            "                        \"img\": \"梨类\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103538,\n" +
            "                        \"name\": \"柑橘蜜柚\",\n" +
            "                        \"pcid\": 103532,\n" +
            "                        \"img\": \"柑橘蜜柚\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103539,\n" +
            "                        \"name\": \"热带水果\",\n" +
            "                        \"pcid\": 103532,\n" +
            "                        \"img\": \"热带水果\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"img\": \"http://img01.bqstatic.com/upload/activity/201/603/0309/20160303091930_007490.jpg\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 103536,\n" +
            "                \"name\": \"新鲜时蔬\",\n" +
            "                \"cids\": [\n" +
            "                    {\n" +
            "                        \"id\": 103537,\n" +
            "                        \"name\": \"调味类\",\n" +
            "                        \"pcid\": 103536,\n" +
            "                        \"img\": \"调味类\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103538,\n" +
            "                        \"name\": \"菌菇类\",\n" +
            "                        \"pcid\": 103536,\n" +
            "                        \"img\": \"菌菇类\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103539,\n" +
            "                        \"name\": \"瓜类\",\n" +
            "                        \"pcid\": 103536,\n" +
            "                        \"img\": \"瓜类-蔬菜\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103540,\n" +
            "                        \"name\": \"根茎果实\",\n" +
            "                        \"pcid\": 103536,\n" +
            "                        \"img\": \"根茎果实\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103541,\n" +
            "                        \"name\": \"果实类\",\n" +
            "                        \"pcid\": 103536,\n" +
            "                        \"img\": \"果实类\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103542,\n" +
            "                        \"name\": \"叶菜类\",\n" +
            "                        \"pcid\": 103536,\n" +
            "                        \"img\": \"叶菜类\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"img\": \"http://img01.bqstatic.com/upload/activity/201/603/0309/20160303092031_070370.jpg\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 103565,\n" +
            "                \"name\": \"肉蛋家禽\",\n" +
            "                \"cids\": [\n" +
            "                    {\n" +
            "                        \"id\": 103566,\n" +
            "                        \"name\": \"蛋类\",\n" +
            "                        \"pcid\": 103565,\n" +
            "                        \"img\": \"蛋类\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103567,\n" +
            "                        \"name\": \"牛羊肉类\",\n" +
            "                        \"img\": \"牛羊肉类\",\n" +
            "                        \"pcid\": 103565\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103568,\n" +
            "                        \"name\": \"禽肉\",\n" +
            "                        \"pcid\": 103565,\n" +
            "                        \"img\": \"禽肉\"\n" +
            "                    },\n" +
            "                     {\n" +
            "                        \"id\": 103569,\n" +
            "                        \"name\": \"肉丸\",\n" +
            "                        \"pcid\": 103565,\n" +
            "                        \"img\": \"肉丸\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 103570,\n" +
            "                        \"name\": \"猪肉\",\n" +
            "                        \"pcid\": 103565,\n" +
            "                        \"img\": \"猪肉\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"img\": \"http://img01.bqstatic.com/upload/activity/201/603/0309/20160303092038_600042.jpg\"\n" +
            "            }\n" +
            "        ]";
}
