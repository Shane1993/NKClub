package com.nku.myfarm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nku.myfarm.R;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Shane on 2017/4/20.
 */

public class SearchAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private static final String TAG = "SearchAdapter";

    public static final int VIEWTYPE_TITLE = 0;
    public static final int VIEWTYPE_HOT = 1;
    public static final int VIEWTYPE_HISTORY = 2;
    public static final int VIEWTYPE_CLEAR = 3;

    private OnClickHotListener hotListener;
    private OnClickHistoryListener historyListener;
    private OnClickClearListener clearListener;

    private Context mContext;
    private LayoutInflater inflater;
    private List<String> hotList;
    private List<String> historyList;

    //有两个标题项，分别是热门搜索和搜索历史
    private int mTitleCount = 2;

    public SearchAdapter(Context context, List<String> hotList, List<String> historyList) {
        this(context, hotList, historyList, null, null, null);
    }

    public SearchAdapter(Context context, List<String> hotList, List<String> historyList,
                         OnClickHotListener hotListener, OnClickHistoryListener historyListener,
                         OnClickClearListener clearListener) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.hotList = hotList;
        this.historyList = historyList;
        this.hotListener = hotListener;
        this.historyListener = historyListener;
        this.clearListener = clearListener;
    }


    //用于获取热门搜索的数量
    private int getHotCount() {
        return hotList.size();
    }

    //用于获取历史记录的数量
    private int getHistotyCount() {
        return historyList.size();
    }

    //通过总的位置获取热门搜索列表项在整个列表中的位置
    public int getPositionInHotList(int position) {
        return position - 1;
    }

    //通过总的位置获取搜索历史列表项在整个列表中的位置
    public int getPositionInHistoryList(int position) {
        return position - mTitleCount - getHotCount();
    }



    //判断当前列表项是否为标题项，其中热门搜索位于第0项，
    public boolean isTitle(int position) {
        return position == 0 || (position == getHotCount() + 1);
    }

    //判断当前列表项是否为热门搜索列表项，
    public boolean isHotItem(int position) {
        return position > 0 && position <= getHotCount();
    }

    //判断当前列表项是否为历史记录列表项，
    public boolean isHistoryItem(int position) {
        return (position > hotList.size() + 1)
                && (position < mTitleCount + getHotCount() + getHistotyCount());

    }

    //判断当前列表项是否为清空历史记录列表项，
    public boolean isHistoryClearItem(int position) {
        return position == mTitleCount + getHotCount() + getHistotyCount();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        View view = null;

        switch (viewType) {
            case VIEWTYPE_TITLE:
                view = inflater.inflate(R.layout.list_item_search_title, parent, false);
                viewHolder = new TitleViewHolder(view);
                break;
            case VIEWTYPE_HOT:
                view = inflater.inflate(R.layout.list_item_search_hot, parent, false);
                viewHolder = new HotItemViewHolder(view);
                view.setOnClickListener(this);
//                ((HotItemViewHolder)viewHolder).tvName.setOnClickListener(this);
                break;
            case VIEWTYPE_HISTORY:
                view = inflater.inflate(R.layout.list_item_search_history, parent, false);
                viewHolder = new HistoryItemViewHolder(view);
//                ((HistoryItemViewHolder)viewHolder).tvName.setOnClickListener(this);
//                ((HistoryItemViewHolder)viewHolder).ibDelete.setOnClickListener(this);
                view.setOnClickListener(this);
                break;
            case VIEWTYPE_CLEAR:
                view = inflater.inflate(R.layout.list_item_search_clear_history, parent, false);
                viewHolder = new ClearViewHolder(view);
//                ((ClearViewHolder)viewHolder).btnClear.setOnClickListener(this);
                view.setOnClickListener(this);
                break;
            default:
                break;
        }
        if (view != null)
            view.setTag(new Integer(viewType));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(isTitle(position)) {
            if (position == 0)
                ((TitleViewHolder)holder).tvTitle.setText("热门搜索");
            else
                ((TitleViewHolder)holder).tvTitle.setText("搜索历史");

        }else if(isHotItem(position)) {
            //前三个标号背景是有颜色的
            int p = getPositionInHotList(position);
            TextView tvNum = ((HotItemViewHolder)holder).tvNum;
            TextView tvName = ((HotItemViewHolder)holder).tvName;

            switch(p) {
                case 0:
                    tvNum.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_search_number_hot1));
                    tvNum.setTextColor(Color.WHITE);
                    break;
                case 1:
                    tvNum.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_search_number_hot2));
                    tvNum.setTextColor(Color.WHITE);
                    break;
                case 2:
                    tvNum.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_search_number_hot3));
                    tvNum.setTextColor(Color.WHITE);
                    break;
                default:
                    tvNum.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_search_number_hot_other));
                    tvNum.setTextColor(Color.BLACK);
                    break;
            }

            tvNum.setText((p+1)+"");
            tvName.setText(hotList.get(p));

        }else if(isHistoryItem(position)) {
            int p = getPositionInHistoryList(position);
            ((HistoryItemViewHolder)holder).tvName.setText(historyList.get(p));
            ((HistoryItemViewHolder)holder).ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(historyListener != null) {
                        historyListener.onClickHistoryDelete(getPositionInHistoryList(position));
                    }
                }
            });
        }else {
            //最后一项是清空搜索历史列表项，无需做事情
        }
    }

    @Override
    public int getItemCount() {
        //标题项 + 热门和历史列表项 + 清空历史列表项
        return mTitleCount + hotList.size() + historyList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(isTitle(position)) {
            return VIEWTYPE_TITLE;
        }else if(isHotItem(position)) {
            return VIEWTYPE_HOT;
        }else if(isHistoryItem(position)) {
            return VIEWTYPE_HISTORY;
        }else {
            return VIEWTYPE_CLEAR;
        }
    }

    @Override
    public void onClick(View v) {


        switch ((Integer)v.getTag()) {
            case VIEWTYPE_HOT:
                if (hotListener != null) {
                    hotListener.onClickHot(v);
                }
                break;
            case VIEWTYPE_HISTORY:
                if (historyListener != null) {
                    historyListener.onClickHistory(v);
                }
                break;
            case VIEWTYPE_CLEAR:
                if(clearListener != null){
                    clearListener.onClickClearHistory();
                }
                break;
        }
    }


    class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_search_title);
        }
    }

    class HotItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum;
        TextView tvName;

        public HotItemViewHolder(View itemView) {
            super(itemView);
            tvNum = (TextView) itemView.findViewById(R.id.tv_search_hot_num);
            tvName = (TextView) itemView.findViewById(R.id.tv_search_hot_name);
        }
    }

    class HistoryItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageButton ibDelete;

        public HistoryItemViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_search_history_name);
            ibDelete = (ImageButton) itemView.findViewById(R.id.ib_search_histiry_delete);
        }
    }

    class ClearViewHolder extends RecyclerView.ViewHolder {
        Button btnClear;

        public ClearViewHolder(View itemView) {
            super(itemView);
            btnClear = (Button) itemView.findViewById(R.id.btn_search_clear);
        }
    }



    public static interface OnClickHotListener {
        public void onClickHot(View view);
    }

    public static interface OnClickHistoryListener {
        public void onClickHistory(View view);

        public void onClickHistoryDelete(int positionInHistoryList);
    }

    public static interface OnClickClearListener {
        public void onClickClearHistory();
    }

}


