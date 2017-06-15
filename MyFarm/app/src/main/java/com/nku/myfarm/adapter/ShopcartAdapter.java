package com.nku.myfarm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nku.myfarm.R;
import com.nku.myfarm.model.ShopcartItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Shane on 2017/6/6.
 */

public class ShopcartAdapter extends RecyclerView.Adapter {


    private LayoutInflater inflater;
    private List<ShopcartItem> list;

    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private OnSelectedListener selectedListener;
    private OnCountEditedListener numberEditedListener;

    public ShopcartAdapter(Context context, List<ShopcartItem> list, OnSelectedListener selectedListener, OnCountEditedListener numberEditedListener) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.selectedListener = selectedListener;
        this.numberEditedListener = numberEditedListener;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(3))
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_shopcart, parent, false);
        ShopcartViewHolder svh = new ShopcartViewHolder(view);

        return svh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ShopcartItem shopcartItem = list.get(position);
        ShopcartViewHolder svh = (ShopcartViewHolder) holder;

        imageLoader.displayImage(shopcartItem.getImg(), svh.ivImage);
        svh.tvName.setText(shopcartItem.getName());
        svh.tvPrice.setText(String.valueOf(shopcartItem.getPrice()));
        svh.btnNumberEdit.setText(String.valueOf(shopcartItem.getCount()));
        svh.cbSelected.setChecked(shopcartItem.isSelected());


        svh.btnNumberEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberEditedListener != null) {
                    numberEditedListener.onCountEdited(position);
                }
            }
        });
        svh.btnNumberDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberEditedListener != null) {
                    numberEditedListener.onCountDecrease(position);
                }
            }
        });
        svh.btnNumberIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberEditedListener != null) {
                    numberEditedListener.onCountIncrease(position);
                }
            }
        });
        svh.cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selectedListener != null) {
                    if (isChecked) {
                        selectedListener.onItemSelected(position);
                    }else {
                        selectedListener.onItemUnSelected(position);
                    }
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ShopcartViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName;
        TextView tvPrice;
        Button btnNumberEdit;
        Button btnNumberDecrease;
        Button btnNumberIncrease;
        CheckBox cbSelected;

        public ShopcartViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.iv_shopcart_image);
            tvName = (TextView) itemView.findViewById(R.id.tv_shopcart_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_shopcart_price);
            btnNumberEdit = (Button) itemView.findViewById(R.id.btn_shopcart_number);
            btnNumberDecrease = (Button) itemView.findViewById(R.id.btn_shopcart_decrease);
            btnNumberIncrease = (Button) itemView.findViewById(R.id.btn_shopcart_increase);
            cbSelected = (CheckBox) itemView.findViewById(R.id.cb_shopcart_select_item);

        }

    }

    public static interface OnCountEditedListener {
        void onCountEdited(int position);
        void onCountDecrease(int position);
        void onCountIncrease(int position);
    }

    public static interface OnSelectedListener {
        void onItemSelected(int position);
        void onItemUnSelected(int position);

    }


}
