package net.lzs.club.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lzs.club.R;
import net.lzs.club.config.Config;
import net.lzs.club.model.Activity;
import net.lzs.club.model.Club;
import net.lzs.club.net.GetClubPictureUrl;
import net.lzs.club.util.GetBitmapFromClubName;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by LEE on 2016/4/14.
 */
public class AdapterActivity extends BaseAdapter
{


    private Context context;
    private List<Activity> list;

    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private String imageUri;
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(2);


    public AdapterActivity(Context context,List<Activity> list)
    {
        this.context = context;
        this.list = list;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Activity getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_fragment_activity,null);

            viewHolder.icon = (ImageView) convertView.findViewById(R.id.list_item_iv_activity_icon);
            viewHolder.name = (TextView) convertView.findViewById(R.id.list_item_tv_activity_name);
            viewHolder.organizer = (TextView) convertView.findViewById(R.id.list_item_tv_activity_organizer);
            viewHolder.time = (TextView) convertView.findViewById(R.id.list_item_tv_activity_time);
            viewHolder.description = (TextView) convertView.findViewById(R.id.list_item_tv_activity_description);

            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Activity activity = getItem(position);


        imageUri = Config.getCachedKVData(context,activity.getOrganizer());
        if(TextUtils.isEmpty(imageUri))
            loadUri(context,activity.getOrganizer());

        imageLoader.displayImage(imageUri,viewHolder.icon,options);

        viewHolder.name.setText(activity.getName());
        viewHolder.organizer.setText(activity.getOrganizer());
        viewHolder.time.setText(activity.getTime());
        viewHolder.description.setText(activity.getContent());

        return convertView;
    }

    class ViewHolder
    {
        ImageView icon;
        TextView name,organizer,time,description;
    }

    private void loadUri(Context context, String organizer)
    {

        new GetClubPictureUrl(context, organizer, new GetClubPictureUrl.SuccessCallback()
        {


            @Override
            public void onSuccess(String clubPictureUrl)
            {
                imageUri = clubPictureUrl;
                try
                {
                    cyclicBarrier.await();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, null);

        try
        {
            cyclicBarrier.await();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
