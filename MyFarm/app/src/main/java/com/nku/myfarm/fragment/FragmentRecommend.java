package com.nku.myfarm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nku.myfarm.R;

/**
 * Created by Shane on 2017/3/27.
 */

public class FragmentRecommend extends Fragment {

    View view;

    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, null);

        initView();

        return view;
    }

    private void initView() {
        context = getContext();
    }
}
