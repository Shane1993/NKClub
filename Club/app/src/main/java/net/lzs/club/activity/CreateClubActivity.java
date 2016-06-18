package net.lzs.club.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.Club;
import net.lzs.club.net.GetClub;

import java.io.File;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by LEE on 2016/4/16.
 */
public class CreateClubActivity extends Activity implements DatePickerDialog.OnDateSetListener
{
    @Bind(R.id.activity_createclub_btn_ok)
    Button btnOk;
    @Bind(R.id.activity_createclub_ib_add)
    ImageButton btnAdd;
    @Bind(R.id.activity_createclub_et_name)
    EditText etName;
    @Bind(R.id.activity_createclub_sp_type)
    Spinner spType;
    @Bind(R.id.activity_createclub_tv_time)
    TextView tvTime;
    @Bind(R.id.activity_createclub_btn_time)
    Button btnTime;
    @Bind(R.id.activity_createclub_et_description)
    EditText etDescription;

    Calendar calendar;

    int RESULT_LOAD_IMAGE = 1;
    String picturePath;
    boolean selectPicture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclub);

        ButterKnife.bind(this);

        calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.YEAR)+"年"+
                (calendar.get(Calendar.MONTH)+1)+"月"+
                calendar.get(Calendar.DAY_OF_MONTH)+"日"
                ;
        tvTime.setText(date);



    }

    @OnClick(R.id.activity_createclub_ib_add)
    public void addPicture()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,RESULT_LOAD_IMAGE);

    }

    @OnClick(R.id.activity_createclub_btn_time)
    public void selectTime()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateClubActivity.this,this,
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }



    @OnClick(R.id.activity_createclub_btn_ok)
    public void createClub()
    {
        if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etDescription.getText().toString()))
        {
            Toast.makeText(CreateClubActivity.this, "请输入完整的信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!selectPicture)
        {
            Toast.makeText(CreateClubActivity.this, "请选择图标", Toast.LENGTH_SHORT).show();
            return;
        }


        final ProgressDialog pd = new ProgressDialog(CreateClubActivity.this);
        pd.setMessage("上传数据中。。。");
        pd.show();

        final BmobFile file = new BmobFile(new File(picturePath));


        file.upload(CreateClubActivity.this, new UploadFileListener()
        {
            @Override
            public void onSuccess()
            {
                final Club club = new Club(etName.getText().toString(),
                        spType.getSelectedItem().toString(),
                        tvTime.getText().toString(),
                        etDescription.getText().toString(),
                        Config.getCachedData(CreateClubActivity.this,CacheType.USERNAME)
                );

                club.setPicture(file);

                Log.i("CreateClubActivity",club.toString());

                club.save(CreateClubActivity.this, new SaveListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        pd.dismiss();
                        Toast.makeText(CreateClubActivity.this, "创建社团成功", Toast.LENGTH_SHORT).show();


                        new GetClub(CreateClubActivity.this, club.getName(), new GetClub.SuccessCallback()
                        {
                            @Override
                            public void onSuccess(Club club)
                            {
                                Intent data = new Intent();
                                data.putExtra(Config.OBJECTID,club.getObjectId());
                                data.putExtra(Config.CLUBNAME,club.getName());
                                data.putExtra(Config.CLUBTYPE,club.getType());
                                data.putExtra(Config.CLUBTIME,club.getTime());
                                data.putExtra(Config.CLUBDESCRIPTION,club.getDescription());
                                setResult(1,data);
                                CreateClubActivity.this.finish();
                            }
                        }, new GetClub.FailCallback()
                        {
                            @Override
                            public void onFail()
                            {
                                Toast.makeText(CreateClubActivity.this, "CreateClubActivity失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onFailure(int i, String s)
                    {
                        pd.dismiss();
                        Toast.makeText(CreateClubActivity.this, "创建社团失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(int i, String s)
            {
                pd.dismiss();

                Toast.makeText(CreateClubActivity.this, "上传图片失败", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();

            Cursor cursor = getContentResolver().query(selectedImage,
                    null, null, null, null);
            if(cursor.moveToFirst()) {
                picturePath = cursor.getString(cursor.getColumnIndex("_data"));
                cursor.close();

                btnAdd.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                selectPicture = true;
            }
        }
    }
}
