package com.nku.myfarm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nku.myfarm.R;
import com.nku.myfarm.model_proto.ModelProto;
import com.nku.myfarm.net.NetConfig;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Shane on 2017/6/15.
 */

/**
 * 测试用
 */
public class ListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ModelProto.ProductStructProto> list;

    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();


    public ListViewAdapter(Context context, List<ModelProto.ProductStructProto> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(25))
                .build();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ModelProto.ProductStructProto getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.list_item_test, parent, false);

        ModelProto.ProductStructProto proto = getItem(position);

        ((TextView)convertView.findViewById(R.id.tv_name)).setText(proto.getProname());
        ((TextView)convertView.findViewById(R.id.tv_price)).setText(proto.getPrice()+"");
        ImageView iv = (ImageView)convertView.findViewById(R.id.iv_pic);
        imageLoader.displayImage(NetConfig.SERVER_PICTURE_URL+proto.getProid(),iv);

        return convertView;
    }
}
