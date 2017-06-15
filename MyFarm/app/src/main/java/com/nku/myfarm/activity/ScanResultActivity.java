package com.nku.myfarm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nku.myfarm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;

/**
 * Created by Shane on 2017/4/17.
 */

public class ScanResultActivity extends Activity {

    @BindView(R.id.tv_scan_content)
    TextView tvScan;

    @BindView(R.id.ib_top_back)
    ImageButton btnBack;
    @BindView(R.id.tv_top_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ButterKnife.bind(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanResultActivity.this.finish();
            }
        });
        tvTitle.setText("扫描结果");

        Intent intent = getIntent();
        showContent(intent);
    }

    public void showContent(Intent intent) {
        String scanResult = intent.getStringExtra("result");
        tvScan.setText(scanResult);
    }
}
