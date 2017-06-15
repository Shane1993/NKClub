package com.nku.myfarm.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.nku.myfarm.R;
import com.nku.myfarm.model.BaseCategory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Shane on 2017/4/18.
 */

public class RollAdapter extends StaticPagerAdapter implements View.OnClickListener{

    public static final String TAG = "RollAdapter";

    //数据集
    private List<BaseCategory> list;

    private OnRollListener onRollListener;

    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public RollAdapter(List<BaseCategory> list) {
        this(list, null);
    }

    public RollAdapter(List<BaseCategory> list, OnRollListener listener) {

        this.list = list;
        this.onRollListener = listener;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0))
                .build();
    }


    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(this);

        BaseCategory category = list.get(position);

        imageLoader.displayImage(category.getImg(), view, options);
        Log.d(TAG, category.getImg());


        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        if(onRollListener != null) {
            onRollListener.onItemClick(view);
        }
    }

    public static interface OnRollListener {
        public void onItemClick(View view);
    }
}
