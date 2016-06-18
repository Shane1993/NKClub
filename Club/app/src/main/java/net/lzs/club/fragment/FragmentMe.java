package net.lzs.club.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import net.lzs.club.R;
import net.lzs.club.activity.LoginActivity;
import net.lzs.club.activity.MyActivitiesActivity;
import net.lzs.club.activity.MyClubsActivity;
import net.lzs.club.activity.MyLikedActivitiesActivity;
import net.lzs.club.activity.MyLikedClubsActivity;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;

import cn.bmob.v3.BmobUser;

/**
 * Created by LEE on 2016/4/14.
 */
public class FragmentMe extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener
{
    private Context context;

    ListView lv;
    Button btnQuit;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_me,null);

        initView();

        return view;
    }

    private void initView()
    {
        context = getActivity();

        lv = (ListView) view.findViewById(R.id.fragment_lv_me);
        btnQuit = (Button) view.findViewById(R.id.fragment_btn_quit);

        btnQuit.setOnClickListener(this);

        lv.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);

        Config.cacheData(context,null, CacheType.TOKEN);
        BmobUser.logOut(context);

        getActivity().finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = null;
        switch (position)
        {
            case 0:
                intent = new Intent(context, MyActivitiesActivity.class);
                break;
            case 1:
                intent = new Intent(context, MyClubsActivity.class);

                break;
            case 2:
                intent = new Intent(context, MyLikedActivitiesActivity.class);

                break;
            case 3:
                intent = new Intent(context, MyLikedClubsActivity.class);
                break;
        }

        startActivity(intent);
    }


}
