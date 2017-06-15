package com.nku.myfarm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nku.myfarm.R;
import com.nku.myfarm.adapter.ListViewAdapter;
import com.nku.myfarm.model_proto.ModelProto;
import com.nku.myfarm.net.GetAllCategory;
import com.nku.myfarm.net.GetProductByCategory;
import com.nku.myfarm.net.Login;
import com.nku.myfarm.net.Register;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.lv)
    ListView lv;
    List<ModelProto.ProductStructProto> mList;
    ListViewAdapter adapter;

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

        mList = new ArrayList<>();
        adapter = new ListViewAdapter(context, mList);
        lv.setAdapter(adapter);

    }


    @OnClick(R.id.btn_register)
    public void register() {

        new Register("lee", "123123", new Register.Callback() {
            @Override
            public void onSuccess(ModelProto.RegisterRspProto registerRspProto) {
                Log.d(TAG, registerRspProto.getUsername() + ".." + registerRspProto.getRspcode() + ".." + registerRspProto.getRspmessage() + ".." + registerRspProto.getRsptype());
            }

            @Override
            public void onError(String errorStr) {

            }
        });

    }

    @OnClick(R.id.btn_login)
    public void login() {

        new Login("lee", "123123", new Login.Callback() {
            @Override
            public void onSuccess(ModelProto.LoginRspProto loginRspProto) {
                Log.d(TAG, loginRspProto.getUsername() + ".." + loginRspProto.getRsptype() + ".." + loginRspProto.getRspmessage());
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
        new GetAllCategory(new GetAllCategory.Callback() {
            @Override
            public void onSuccess(ModelProto.GetAllCategoryRspProto getAllCategoryRspProto) {


                List<ModelProto.CategoryStructProto> list = getAllCategoryRspProto.getCategoriesList();
                for (ModelProto.CategoryStructProto proto : list) {
                    Log.d(TAG, "categoryid" + proto.getCategoryid());
                    Log.d(TAG, "categoryname" + proto.getCategoryname());
                }

                Log.d(TAG, "rspcode:" + getAllCategoryRspProto.getRspmessage());
                Log.d(TAG, "rsptype:" + getAllCategoryRspProto.getRsptype());
                Log.d(TAG, "rspmessage:" + getAllCategoryRspProto.getRspmessage());
            }

            @Override
            public void onError(String errorStr) {
                Log.d(TAG, errorStr);
            }
        });
    }

    @OnClick(R.id.btn_getcartbyid)
    public void getCartById() {

    }

    @OnClick(R.id.btn_getcartbyuser)
    public void getCartByUser() {

    }

    @OnClick(R.id.btn_getorderbyid)
    public void getOederById() {

    }

    @OnClick(R.id.btn_getorderbyuser)
    public void getOrderByUser() {

    }

    @OnClick(R.id.btn_getproductbycategory)
    public void getProductByCategory() {


        new GetProductByCategory(String.valueOf(1496903557057304327l), new GetProductByCategory.Callback() {
            @Override
            public void onSuccess(ModelProto.GetProductByCateGoryRspProto getProductByCateGoryRspProto) {
                List<ModelProto.ProductStructProto> list = getProductByCateGoryRspProto.getProductsList();

                for (ModelProto.ProductStructProto item : list) {
                    Log.d(TAG, item.getProname());
                    mList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorStr) {
                Log.d(TAG, errorStr);

            }
        });
    }

    @OnClick(R.id.btn_getproductbyname)
    public void getProductByName() {

    }


}
