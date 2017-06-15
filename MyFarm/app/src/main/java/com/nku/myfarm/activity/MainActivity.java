package com.nku.myfarm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nku.myfarm.R;
import com.nku.myfarm.fragment.FragmentHome;
import com.nku.myfarm.fragment.FragmentMy;
import com.nku.myfarm.fragment.FragmentRecommend;
import com.nku.myfarm.fragment.FragmentShopCart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private Fragment fragmentHome, fragmentRecommend, fragmentShopCart, fragmentMy;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragments;

    @BindView(R.id.ll_tab_home)
    LinearLayout tabHome;
    @BindView(R.id.ll_tab_recommend)
    LinearLayout tabRecommand;
    @BindView(R.id.ll_tab_shopcart)
    LinearLayout tabShopCart;
    @BindView(R.id.ll_tab_my)
    LinearLayout tabMy;

    @BindView(R.id.ib_tab_home)
    ImageButton imgHome;
    @BindView(R.id.ib_tab_recommend)
    ImageButton imgRecommend;
    @BindView(R.id.ib_tab_shopcart)
    ImageButton imgShopCart;
    @BindView(R.id.ib_tab_my)
    ImageButton imgMy;

    @BindView(R.id.tv_tab_home)
    TextView tvHome;
    @BindView(R.id.tv_tab_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_tab_shopcart)
    TextView tvShopCart;
    @BindView(R.id.tv_tab_my)
    TextView tvMy;

    private static int color_normal, color_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();




    }

    private void initView() {

        color_normal = getResources().getColor(R.color.normol);
        color_pressed = getResources().getColor(R.color.pressed);

        fragmentHome = new FragmentHome();
        fragmentRecommend = new FragmentRecommend();
        fragmentShopCart = new FragmentShopCart();
        fragmentMy = new FragmentMy();

        viewPager = (ViewPager) findViewById(R.id.vp_main);
        fragments = new ArrayList<Fragment>();
        fragments.add(fragmentHome);
        fragments.add(fragmentRecommend);
        fragments.add(fragmentShopCart);
        fragments.add(fragmentMy);


        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);

        initEvent();

        setSelect(0);
    }

    public void initEvent() {
        tabHome.setOnClickListener(this);
        tabRecommand.setOnClickListener(this);
        tabShopCart.setOnClickListener(this);
        tabMy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_home:
                setSelect(0);
                break;
            case R.id.ll_tab_recommend:
                setSelect(1);
                break;
            case R.id.ll_tab_shopcart:
                setSelect(2);
                break;
            case R.id.ll_tab_my:
                setSelect(3);
                break;
        }
    }

    public void setSelect(int position) {
        resetBottom();

        switch (position) {
            case 0:
                imgHome.setImageResource(R.mipmap.home_pressed);
                tvHome.setTextColor(color_pressed);
                break;
            case 1:
                imgRecommend.setImageResource(R.mipmap.recommend_pressed);
                tvRecommend.setTextColor(color_pressed);
                break;
            case 2:
                imgShopCart.setImageResource(R.mipmap.shopcart_pressed);
                tvShopCart.setTextColor(color_pressed);
                break;
            case 3:
                imgMy.setImageResource(R.mipmap.my_pressed);
                tvMy.setTextColor(color_pressed);
                break;
            default:
                break;
        }

        viewPager.setCurrentItem(position);

    }

    public void resetBottom() {
        imgHome.setImageResource(R.mipmap.home_normal);
        imgRecommend.setImageResource(R.mipmap.recommend_normal);
        imgShopCart.setImageResource(R.mipmap.shopcart_normal);
        imgMy.setImageResource(R.mipmap.my_normal);

        tvHome.setTextColor(color_normal);
        tvRecommend.setTextColor(color_normal);
        tvShopCart.setTextColor(color_normal);
        tvMy.setTextColor(color_normal);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
