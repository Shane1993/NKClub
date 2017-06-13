package com.nku.myfarm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.nku.myfarm.R;
import com.nku.myfarm.model.BaseCategory;
import com.nku.myfarm.model.category.FirstCategory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Shane on 2017/3/27.
 */

public class HomeAdapter extends RecyclerView.Adapter {

    public static final int VIEWTYPE_HEADER = 0;
    public static final int VIEWTYPE_FIRST_CATEGORY = 1;
    public static final int VIEWTYPE_SECOND_CATEGORY = 2;

    private int mHeaderCount = 1;//头部View的个数


    private OnRecycleViewListener recycleViewListener;
    private List<BaseCategory> list;
    private LayoutInflater inflater;

    private List<BaseCategory> bannerList;
    private RollAdapter rollAdapter;
    private OnRollBannerListener rollBannerListener;
    private RollPagerView rollPagerView;

    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HomeAdapter(Context context, List<BaseCategory> list, List<BaseCategory> bannerList) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.bannerList = bannerList;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(25))
                .build();
    }

    public HomeAdapter(Context context, List<BaseCategory> list, List<BaseCategory> bannerList,
                       OnRecycleViewListener recycleViewListener, OnRollBannerListener rollBannerListener) {
        this(context, list, bannerList);
        this.recycleViewListener = recycleViewListener;
        this.rollBannerListener = rollBannerListener;

    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return getItemViewType(position) == VIEWTYPE_HEADER;
    }

    //判断当前item是否是FirstCategory
    public boolean isFirstCategory(int position) {
        return getItemViewType(position) == VIEWTYPE_FIRST_CATEGORY;
    }

    public int getHeaderCount() {
        return mHeaderCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder vh = null;

        switch (viewType) {
            case VIEWTYPE_HEADER:
                view = inflater.inflate(R.layout.rv_header, parent, false);
                vh = new RpvViewHolder(view);
                break;
            case VIEWTYPE_FIRST_CATEGORY:
                view = inflater.inflate(R.layout.list_item_home_tv, parent, false);
                vh = new TvViewHodler(view);
                break;
            case VIEWTYPE_SECOND_CATEGORY:
                view = inflater.inflate(R.layout.list_item_home_img, parent, false);
//                view.setOnClickListener(this);
                vh = new ImgViewHodler(view);
                break;
            default:
                return null;
        }

        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //设置轮播数据列表和点击事件
        if (isHeaderView(position)) {

            if (rollAdapter == null) {
                rollAdapter = new RollAdapter(bannerList, new RollAdapter.OnRollListener() {
                    @Override
                    public void onItemClick(View view) {
                        if (rollBannerListener != null) {

                            BaseCategory baseCategory = bannerList.get(rollPagerView.getViewPager().getCurrentItem());
                            rollBannerListener.onRollItemClick(baseCategory);
                        }
                    }
                });
                rollAdapter.notifyDataSetChanged();
                ((RpvViewHolder) holder).rpv.setAdapter(rollAdapter);
                rollPagerView = ((RpvViewHolder) holder).rpv;
            }
            return;
        }


        //显示不是轮播的其余数据，注意减去头部的view个数
        BaseCategory categoty = list.get(position - getHeaderCount());
        if (categoty instanceof FirstCategory) {
            ((TvViewHodler) holder).tv.setText(categoty.getName());
        } else {
            imageLoader.displayImage(categoty.getImg(), ((ImgViewHodler) holder).iv, options);
            ((ImgViewHodler) holder).iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewListener != null) {
                        recycleViewListener.onItemClick(position - getHeaderCount());
                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return list.size() + mHeaderCount;
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderCount != 0 && position < mHeaderCount) {
            return VIEWTYPE_HEADER;
        }

//        //因为RecyclerView中有Header，因此获取到的position要比list中元素的位置大了mHeaderCount
//        return list.get(position - getHeaderCount()).getViewType();

        BaseCategory category = list.get(position - getHeaderCount());
        if (category instanceof FirstCategory) {
            return VIEWTYPE_FIRST_CATEGORY;
        } else {
            return VIEWTYPE_SECOND_CATEGORY;
        }


    }

    class TvViewHodler extends RecyclerView.ViewHolder {

        TextView tv;

        public TvViewHodler(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_adapter_home);
        }

    }

    class ImgViewHodler extends RecyclerView.ViewHolder {

        ImageView iv;

        public ImgViewHodler(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_adapter_home);
        }

    }

    class RpvViewHolder extends RecyclerView.ViewHolder {

        RollPagerView rpv;

        public RpvViewHolder(View itemView) {
            super(itemView);
            rpv = (RollPagerView) itemView.findViewById(R.id.rpv_home);
        }
    }


    public static interface OnRecycleViewListener {
        void onItemClick(int positionInCategoryList);
    }

    public static interface OnRollBannerListener {
        void onRollItemClick(BaseCategory baseCategory);
    }
}
