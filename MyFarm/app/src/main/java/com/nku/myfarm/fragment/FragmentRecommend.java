package com.nku.myfarm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.R;
import com.nku.myfarm.model_proto.ModelProto;
import com.nku.myfarm.net.GetAllCategory;
import com.nku.myfarm.net.GetProductByCategory;
import com.nku.myfarm.net.Login;
import com.nku.myfarm.net.MultipartRequest;
import com.nku.myfarm.net.NetConfig;
import com.nku.myfarm.net.Register;
import com.nku.myfarm.util.ProtoUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shane on 2017/3/27.
 */

public class FragmentRecommend extends Fragment {

    private static final String TAG = "FragmentRecommend";

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

        ButterKnife.bind(this, view);

    }



    @OnClick(R.id.btn_register)
    public void register() {
        ModelProto.RegisterProto registerProto =
                ModelProto.RegisterProto.newBuilder().setReqtype(ModelProto.ReqType.tReqTypeRegister).setUsername("lzs").setPassword("123456").build();

        new Register(registerProto, new Register.Callback() {
            @Override
            public void onSuccess(ModelProto.RegisterRspProto registerRspProto) {
                Log.d(TAG, registerRspProto.getUsername()+".."+registerRspProto.getRspcode()+".."+registerRspProto.getRspmessage()+".."+registerRspProto.getRsptype());
            }

            @Override
            public void onError(String errorStr) {

            }
        });

    }

    @OnClick(R.id.btn_login)
    public void login() {
        ModelProto.LoginProto loginProto =
                ModelProto.LoginProto.newBuilder().setReqtype(ModelProto.ReqType.tReqTypeLogin).setUsername("lzs").setPassword("123456").build();

        new Login(loginProto, new Login.Callback() {
            @Override
            public void onSuccess(ModelProto.LoginRspProto loginRspProto) {
                Log.d(TAG, loginRspProto.getUsername()+".."+loginRspProto.getRsptype()+".."+loginRspProto.getRspmessage());
            }

            @Override
            public void onError(String errorStr) {

            }
        });
    }

    @OnClick(R.id.btn_addcart)
    public void addCart() {

    }

    @OnClick(R.id.btn_addcategory)
    public void addCategory() {

    }

    @OnClick(R.id.btn_addorder)
    public void addOrder() {

    }

    @OnClick(R.id.btn_addproduct)
    public void addProduct() {

    }

    @OnClick(R.id.btn_delproductbyname)
    public void delProductByName() {

    }

    @OnClick(R.id.btn_getallcategory)
    public void getAllCategory() {
        ModelProto.GetAllCategoryProto proto = ModelProto.GetAllCategoryProto.newBuilder().setReqtype(ModelProto.ReqType.tReqTypeGetAllCategory).build();
        new GetAllCategory(proto, new GetAllCategory.Callback() {
            @Override
            public void onSuccess(ModelProto.GetAllCategoryRspProto getAllCategoryRspProto) {


                List<ModelProto.CategoryStructProto> list = getAllCategoryRspProto.getCategoriesList();
                for(ModelProto.CategoryStructProto proto : list) {
                    Log.d(TAG,"categoryid" + proto.getCategoryid());
                    Log.d(TAG,"categoryname" + proto.getCategoryname());
                }

                Log.d(TAG,"rspcode:" + getAllCategoryRspProto.getRspmessage());
                Log.d(TAG,"rsptype:" + getAllCategoryRspProto.getRsptype());
                Log.d(TAG,"rspmessage:" + getAllCategoryRspProto.getRspmessage());
            }

            @Override
            public void onError(String errorStr) {
                Log.d(TAG,errorStr);
            }
        });
    }

    @OnClick(R.id.btn_getcartbyid)
    public void getCartById(){

    }

    @OnClick(R.id.btn_getcartbyuser)
    public void getCartByUser() {

    }

    @OnClick(R.id.btn_getorderbyid)
    public void getOederById() {

    }

    @OnClick(R.id.btn_getorderbyuser)
    public void getOrderByUser(){

    }

    @OnClick(R.id.btn_getproductbycategory)
    public void getProductByCategory() {

        ModelProto.GetProductByCateGoryProto proto = ModelProto.GetProductByCateGoryProto.newBuilder()
                .setReqtype(ModelProto.ReqType.tReqTypeGetProductByCategory)
                .setCategoryid(String.valueOf(1496903557057304327l)).build();

        new GetProductByCategory(proto, new GetProductByCategory.Callback() {
            @Override
            public void onSuccess(ModelProto.GetProductByCateGoryRspProto getProductByCateGoryRspProto) {
                List<ModelProto.ProductStructProto> list = getProductByCateGoryRspProto.getProductsList();

                for(ModelProto.ProductStructProto item : list) {
                    Log.d(TAG, item.getProid());
                    Log.d(TAG, item.getProname());
                }
            }

            @Override
            public void onError(String errorStr) {
                Log.d(TAG,errorStr);

            }
        });
    }

    @OnClick(R.id.btn_getproductbyname)
    public void getProductByName() {

    }





}
