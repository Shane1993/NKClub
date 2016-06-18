package net.lzs.club.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.Club;
import net.lzs.club.net.GetMyClubs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by LEE on 2016/4/16.
 */
public class CreateActivityActivity extends Activity implements DatePickerDialog.OnDateSetListener
{
    @Bind(R.id.activity_createactivity_et_name)
    EditText etName;
    @Bind(R.id.activity_createactivity_sp_type)
    Spinner spOrganizer;
    @Bind(R.id.activity_createactivity_tv_time)
    TextView tvTime;
    @Bind(R.id.activity_createactivity_et_content)
    EditText etContent;

    List<String> list;
    ArrayAdapter<String> adapter;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createactivity);

        ButterKnife.bind(this);

        calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.YEAR)+"年"+
                (calendar.get(Calendar.MONTH)+1)+"月"+
                calendar.get(Calendar.DAY_OF_MONTH)+"日"
                ;
        tvTime.setText(date);

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(CreateActivityActivity.this,android.R.layout.simple_list_item_1,list);
        spOrganizer.setAdapter(adapter);

        Intent intent = getIntent();
        String clubNames = intent.getStringExtra(Config.CLUBNAME);
        for(String c : clubNames.split(","))
        {
            list.add(c);
        }
        adapter.notifyDataSetChanged();

    }

    @OnClick(R.id.activity_createactivity_btn_time)
    public void selectTime()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateActivityActivity.this,this,
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }



    @OnClick(R.id.activity_createactivity_btn_ok)
    public void createActivity()
    {
        if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etContent.getText().toString()))
        {
            Toast.makeText(CreateActivityActivity.this, "请输入完整的信息", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(CreateActivityActivity.this);
        pd.setMessage("上传数据中。。。");
        pd.show();


        net.lzs.club.model.Activity activity = new net.lzs.club.model.Activity();
        activity.setName(etName.getText().toString());
        activity.setOrganizer(spOrganizer.getSelectedItem().toString());
        activity.setTime(tvTime.getText().toString());
        activity.setContent(etContent.getText().toString());
        activity.setBelongs(Config.getCachedData(CreateActivityActivity.this, CacheType.USERNAME));
        activity.setIconUri(Config.getCachedKVData(getApplicationContext(),activity.getOrganizer()));


        activity.save(CreateActivityActivity.this, new SaveListener()
        {
            @Override
            public void onSuccess()
            {
                pd.dismiss();
                Toast.makeText(CreateActivityActivity.this, "发布活动成功", Toast.LENGTH_SHORT).show();
                setResult(1);
                CreateActivityActivity.this.finish();
            }

            @Override
            public void onFailure(int i, String s)
            {
                pd.dismiss();
                Toast.makeText(CreateActivityActivity.this, "发布活动失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        setResult(0);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        tvTime.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
    }

}
