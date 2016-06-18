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

    public AdapterClub(Context context,List<Club> list)
    {
        this.context = context;
        this.list = list;
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

        Bitmap bm = GetBitmapFromClubName.getBitmap(club.getName());
        if(bm == null)
        {
            viewHolder.ivIcon.setImageResource(R.mipmap.icon);
        }else
        {
            viewHolder.ivIcon.setImageBitmap(bm);
        }
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
