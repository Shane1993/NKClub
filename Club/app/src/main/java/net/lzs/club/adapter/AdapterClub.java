package net.lzs.club.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import net.lzs.club.R;
import net.lzs.club.model.Club;
import net.lzs.club.util.GetBitmapFromClubName;

import java.util.List;

/**
 * Created by LEE on 2016/4/14.
 */
public class AdapterClub extends BaseAdapter
{
    private Context context;
    private List<Club> list;

    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();


    public AdapterClub(Context context,List<Club> list)
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
    public Club getItem(int position)
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
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_fragment_club,null);

            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.list_item_iv_club_icon);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.list_item_tv_club_name);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.list_item_tv_club_type);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.list_item_tv_club_description);
            convertView.setTag(viewHolder);

        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Club club = getItem(position);

        imageLoader.displayImage(club.getIconUrl(),viewHolder.ivIcon,options);
        viewHolder.tvName.setText(club.getName());
        viewHolder.tvType.setText(club.getType());
        viewHolder.tvDescription.setText(club.getDescription());


        return convertView;
    }

    class ViewHolder
    {
        ImageView ivIcon;
        TextView tvName;
        TextView tvType;
        TextView tvDescription;
    }
}
