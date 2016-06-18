package net.lzs.club.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.lzs.club.R;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.fragment.FragmentActivities;
import net.lzs.club.fragment.FragmentGroup;
import net.lzs.club.fragment.FragmentMe;
import net.lzs.club.fragment.FragmentMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements View.OnClickListener,ViewPager.OnPageChangeListener
{

    private ViewPager viewPager;
    private Fragment fragmentActivities,fragmentGroup,fragmentMe;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragments;

    @Bind(R.id.tab_activity)
    LinearLayout tabActivity;
    @Bind(R.id.tab_group)
    LinearLayout tabGroup;
    @Bind(R.id.tab_me)
    LinearLayout tabMe;

    @Bind(R.id.ib_tab_activity)
    ImageButton imgActivity;
    @Bind(R.id.ib_tab_group)
    ImageButton imgGroup;
    @Bind(R.id.ib_tab_me)
    ImageButton imgMe;
    @Bind(R.id.tv_tab_activity)
    TextView tvActivity;
    @Bind(R.id.tv_tab_group)
    TextView tvGroup;
    @Bind(R.id.tv_tab_me)
    TextView tvMe;

    private static int color_normal,color_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(Config.getCachedData(MainActivity.this, CacheType.FLAG) == "2")
        {
            Config.cacheData(MainActivity.this,"0",CacheType.FLAG);
            MainActivity.this.recreate();
        }
    }

    public void initView()
    {
        color_normal = getResources().getColor(R.color.normol);
        color_pressed = getResources().getColor(R.color.pressed);

        fragmentActivities = new FragmentActivities();
        fragmentGroup = new FragmentGroup();
        fragmentMe = new FragmentMe();

        viewPager = (ViewPager) findViewById(R.id.vp_main);
        fragments = new ArrayList<Fragment>();
        fragments.add(fragmentGroup);
        fragments.add(fragmentActivities);
        fragments.add(fragmentMe);


        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return fragments.get(position);
            }

            @Override
            public int getCount()
            {
                return fragments.size();
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(2);

        initEvent();

        setSelect(0);

    }


    public void initEvent()
    {
        tabActivity.setOnClickListener(this);
        tabGroup.setOnClickListener(this);
        tabMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.tab_activity:
                setSelect(1);
                break;

            case R.id.tab_group:
                setSelect(0);
                break;

            case R.id.tab_me:
                setSelect(2);
                break;
        }
    }

    public void setSelect(int position)
    {
        resetBottom();

        switch (position)
        {
            case 1:
                imgActivity.setImageResource(R.mipmap.activity_pressed);
                tvActivity.setTextColor(color_pressed);
                break;
            case 0:
                imgGroup.setImageResource(R.mipmap.group_pressed);
                tvGroup.setTextColor(color_pressed);
                break;
            case 2:
                imgMe.setImageResource(R.mipmap.me_pressed);
                tvMe.setTextColor(color_pressed);
                break;
            default:
                break;
        }

        viewPager.setCurrentItem(position);

    }

    public void resetBottom()
    {
        imgActivity.setImageResource(R.mipmap.activity_normal);
        imgGroup.setImageResource(R.mipmap.group_normal);
        imgMe.setImageResource(R.mipmap.me_normal);

        tvActivity.setTextColor(color_normal);
        tvGroup.setTextColor(color_normal);
        tvMe.setTextColor(color_normal);

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        setSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }


}
