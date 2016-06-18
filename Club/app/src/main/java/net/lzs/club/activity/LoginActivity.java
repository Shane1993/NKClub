package net.lzs.club.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.User;
import net.lzs.club.net.GetUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by LEE on 2016/4/14.
 */
public class LoginActivity extends Activity
{
    private Context context;

    @Bind(R.id.loginaty_et_username)
    EditText etUserName;
    @Bind(R.id.loginaty_et_password)
    EditText etPassword;

    String userName;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        context  = this;


        if(!TextUtils.isEmpty(Config.getCachedData(context, CacheType.USERNAME)) &&
                !TextUtils.isEmpty(Config.getCachedData(context, CacheType.PASSWORD)))
        {
            etUserName.setText(Config.getCachedData(context,CacheType.USERNAME));
            etPassword.setText(Config.getCachedData(context,CacheType.PASSWORD));
        }

    }



    @OnClick(R.id.loginaty_btn_login)
    void login()
    {
        if(!judgeLegelInput())
            return;

        final User user = new User();
        user.setUsername(userName);
        user.setPassword(password);

        user.login(context, new SaveListener()
        {
            @Override
            public void onSuccess()
            {
                Config.cacheData(context,userName,CacheType.USERNAME);
                Config.cacheData(context,password,CacheType.PASSWORD);
                Config.cacheData(context,Config.TOKEN,CacheType.TOKEN);

                new GetUser(context, userName, new GetUser.SuccessCallback()
                {
                    @Override
                    public void onSuccess(List<User> result)
                    {
                        User user = result.get(0);
                        Config.cacheData(context,user.getLikedActivities(),CacheType.LIKEDACTIVITIES);
                        Config.cacheData(context,user.getLikedClubs(),CacheType.LIKEDCLUBS);

                        startActivity(new Intent(context,MainActivity.class));
                        LoginActivity.this.finish();
                    }
                },null);



            }

            @Override
            public void onFailure(int i, String s)
            {

                Toast.makeText(LoginActivity.this, "登录失败:"+s, Toast.LENGTH_SHORT).show();
            }
        });
        

    }

    @OnClick(R.id.loginaty_btn_signup)
    void signup()
    {
        if(!judgeLegelInput())
            return;

        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int i, String s) {
                progressDialog.dismiss();
                Toast.makeText(context, "注册失败 : " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean judgeLegelInput()
    {
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



}
