package com.nku.myfarm.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nku.myfarm.R;
import com.nku.myfarm.adapter.ShopcartAdapter;
import com.nku.myfarm.model.ShopcartItem;
import com.nku.myfarm.util.MyToast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Shane on 2017/3/27.
 */

public class FragmentShopCart extends Fragment {

    private static final String TAG = "FragmentShopcart";

    View view;
    Context context;

    @BindView(R.id.tv_shopcart_null)
    TextView tvNull;

    @BindView(R.id.rv_shopcart)
    RecyclerView rvShopcart;

    @BindView(R.id.cb_shopcart_select_all)
    CheckBox cbSelectAll;

    @BindView(R.id.tv_shopcart_sum)
    TextView tvSum;

    @BindView(R.id.ll_shopcart_delete)
    LinearLayout llDelete;

    @BindView(R.id.btn_shopcart_delete)
    Button btnDelete;

    @BindView(R.id.btn_shopcart_confirm_buy)
    Button btnBuy;

    @BindView(R.id.btn_top_shopcart_edit)
    Button btnEdit;


    //用于存储从服务器拉取的购物车列表
    private List<ShopcartItem> shopcartItemList;
    private ShopcartAdapter shopcartAdapter;
    private LinearLayoutManager layoutManager;

    //用于存储选中的列表项
    private List<ShopcartItem> selectedItemList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopcart, null);

        initView();

        return view;
    }

    private void initView() {

        context = getContext();

        ButterKnife.bind(this, view);

        selectedItemList = new LinkedList<>();
        String data = getDataFromServer();
        shopcartItemList = parseDataToList(data);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvShopcart.setLayoutManager(layoutManager);

        shopcartAdapter = new ShopcartAdapter(context, shopcartItemList, new ShopcartAdapter.OnSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                changeSelectedState(position, true);
            }

            @Override
            public void onItemUnSelected(int position) {
                changeSelectedState(position, false);
            }
        }, new ShopcartAdapter.OnCountEditedListener() {
            @Override
            public void onCountEdited(int position) {
                ShopcartItem item = shopcartItemList.get(position);
                int count = item.getCount();

                refreshNewCountFromDialog(position, count);

            }

            @Override
            public void onCountDecrease(int position) {
                ShopcartItem item = shopcartItemList.get(position);
                int count = item.getCount();
                if (count <= 1) {
                    MyToast.showShort(context, "数量不能少于1个");
                    return;
                }

                count--;

                modifyCountAndSelected(position, count);

            }

            @Override
            public void onCountIncrease(int position) {
                ShopcartItem item = shopcartItemList.get(position);
                int count = item.getCount();
                if (count >= 1000) {
                    MyToast.showShort(context, "数量不能超过1000个");
                    return;
                }

                count++;

                modifyCountAndSelected(position, count);


            }
        });
        rvShopcart.setAdapter(shopcartAdapter);
        shopcartAdapter.notifyDataSetChanged();


        btnEdit.setTag(TAG_EDIT);

    }

    /**
     * 改变购物车列表项的选中状态
     *
     * @param position
     * @param selected
     */
    private void changeSelectedState(int position, boolean selected) {
        ShopcartItem item = shopcartItemList.get(position);

        if (selected) {
            if (!selectedItemList.contains(item)) {
                selectedItemList.add(item);
            }
        } else {
            if (selectedItemList.contains(item)) {
                selectedItemList.remove(item);

                //取消全选
                cbSelectAll.setChecked(false);
            }
        }
        item.setSelected(selected);

        refreshSumPrice();
    }

    /**
     * 根据购买清单buyList（选中的列表项）中的项目计算总额
     */
    private void refreshSumPrice() {
        float sum = 0;

        for (ShopcartItem item : selectedItemList) {
            sum += item.getPrice() * item.getCount();
        }

        String sumStr = getFormatPrice(sum);

        tvSum.setText(sumStr);
    }


    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    /**
     * 获取一位小数的价格，因为直接用float型数据会出现多位小数
     *
     * @param sum
     * @return
     */
    private String getFormatPrice(float sum) {
        return decimalFormat.format(sum);
    }


    /**
     * 修改购物车中的列表的数量，包括服务器和本地的，最后还要选中修改数量的列表项
     * 注意在‘成功’修改服务器中的数量之后，要及时修改本地列表的数据
     * "注"，一般修改本地列表的步骤是在成功修改服务器数据之后的异步方法里进行
     * <p>
     * 这里没有服务器，因此只是简单的改一下
     *
     * @param position
     * @param newCount
     */
    private void modifyCountAndSelected(int position, int newCount) {

        modifyCountInServer(position, newCount);

        modifyCountInLocal(position, newCount);

        changeSelectedState(position, true);
    }


    /**
     * 需要编写的方法:修改服务器中的数据
     *
     * @param position
     * @param newCount
     */
    public void modifyCountInServer(int position, int newCount) {

        // TODO: 2017/6/7
    }

    /**
     * 修改本地列表中的数据
     *
     * @param position
     * @param newCount
     */
    public void modifyCountInLocal(int position, int newCount) {

        shopcartItemList.get(position).setCount(newCount);
        shopcartAdapter.notifyDataSetChanged();
    }


    /**
     * 刷新数据列表
     */
    private void refreshDataList() {

        //清空选中列表
        selectedItemList.clear();
        shopcartItemList.clear();

        //拉取数据
        String data = getDataFromServer();
        shopcartItemList.addAll(parseDataToList(data));
        shopcartAdapter.notifyDataSetChanged();

        //购物车为空时的界面
        if (shopcartItemList.size() == 0) {
            tvNull.setVisibility(View.VISIBLE);
        } else {
            tvNull.setVisibility(View.GONE);
        }

    }

    /**
     * 从服务器中获取数据，在搭建服务器之后，方法需要重写
     *
     * @return
     */
    public String getDataFromServer() {
        // TODO: 2017/6/8

        return productTestStr;
    }

    public List<ShopcartItem> parseDataToList(String data) {

        ShopcartItem[] shopcartItems = parseDataToArray(data);

        return getListFromArray(shopcartItems);

    }

    public ShopcartItem[] parseDataToArray(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, ShopcartItem[].class);
    }

    public List<ShopcartItem> getListFromArray(ShopcartItem[] array) {
        List<ShopcartItem> list = new ArrayList<>();

        for (ShopcartItem item : array) {
            list.add(item);
        }

        return list;
    }


    /**
     * 用于在对话框中设置购物车列表项的数量
     *
     * @param count
     * @return
     */
    private void refreshNewCountFromDialog(final int position, final int count) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_shopcart_count_edit, null);
        final EditText etCount = (EditText) dialogView.findViewById(R.id.et_shopcart_dialog_count);
        final AlertDialog ad = new AlertDialog.Builder(context).create();

        setEditTextProperty(etCount, count);

        ad.setTitle("修改购买数量");
        ad.setView(dialogView);
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String countStr = etCount.getText().toString();
                if (!TextUtils.isEmpty(countStr)) {
                    int newCount = Integer.parseInt(countStr);

                    if (count != newCount) {

                        modifyCountAndSelected(position, newCount);
                    }

                    ad.dismiss();

                }

            }
        });
        ad.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ad.dismiss();

            }
        });
        ad.setCanceledOnTouchOutside(false);
        ad.show();


    }

    private final int MAX_COUNT = 1000;

    public void setEditTextProperty(final EditText etCount, int count) {
        etCount.setText(String.valueOf(count));
        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    int num = Integer.parseInt(s.toString());
                    if (num > MAX_COUNT) {
                        etCount.setText("1000");
                        MyToast.showShort(context, "最大数量不能超过" + MAX_COUNT);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 确认购买，也就是将选中的列表项进行下单处理，这个后续需要完成
     */
    @OnClick(R.id.btn_shopcart_confirm_buy)
    public void confirmBuy() {
        Log.d(TAG, selectedItemList.toString());

        // TODO: 2017/6/8
    }

//    /**
//     * 全选
//     *
//     * @param isChecked
//     */
//    @OnCheckedChanged(R.id.cb_shopcart_select_all)
//    public void selectAll(boolean isChecked) {
//
//        if(isChecked) {
//            for(ShopcartItem item : shopcartItemList) {
//                item.setSelected(true);
//                if (!selectedItemList.contains(item)) {
//                    selectedItemList.add(item);
//                }
//            }
//        }else {
//            for(ShopcartItem item : shopcartItemList) {
//                item.setSelected(false);
//                if (selectedItemList.contains(item)) {
//                    selectedItemList.remove(item);
//                }
//            }
//        }
//
//        shopcartAdapter.notifyDataSetChanged();
//
//    }

    @OnClick(R.id.cb_shopcart_select_all)
    public void selectAll() {

        if (cbSelectAll.isChecked()) {
            for (ShopcartItem item : shopcartItemList) {
                item.setSelected(true);
                if (!selectedItemList.contains(item)) {
                    selectedItemList.add(item);
                }
            }
        } else {
            for (ShopcartItem item : shopcartItemList) {
                item.setSelected(false);
                if (selectedItemList.contains(item)) {
                    selectedItemList.remove(item);
                }
            }
        }

        shopcartAdapter.notifyDataSetChanged();

    }


    private static final Integer TAG_EDIT = 0;
    private static final Integer TAG_EDIT_COMPLETE = 1;
    private static final String TEXT_EDIT = "编辑";
    private static final String TEXT_EDIT_COMPLETE = "完成";

    @OnClick(R.id.btn_top_shopcart_edit)
    public void edit() {
        Integer tag = (Integer) btnEdit.getTag();

        if (tag.equals(TAG_EDIT)) {

            llDelete.setVisibility(View.VISIBLE);

            btnEdit.setTag(TAG_EDIT_COMPLETE);
            btnEdit.setText(TEXT_EDIT_COMPLETE);

        } else if (tag.equals(TAG_EDIT_COMPLETE)) {

            llDelete.setVisibility(View.GONE);


            btnEdit.setTag(TAG_EDIT);
            btnEdit.setText(TEXT_EDIT);
        }
    }

    /**
     * 删除购物车选中的列表项，这里采取的方式是在成功删除服务器中的数据之后
     * 直接重新从服务器中拉取数据刷新数据列表
     */
    @OnClick(R.id.btn_shopcart_delete)
    public void onClickDelete() {

        deleteItemsAndRefresh(selectedItemList);

    }

    /**
     * 删除服务器中的选中列表中的数据并刷新
     * 注意刷新应该在删除成功之后的回调中调用
     * <p>
     * 待完成
     *
     * @param list
     */
    public void deleteItemsAndRefresh(List<ShopcartItem> list) {

        deleteItemsInServer(list);

        refreshDataList();

    }

    /**
     * 删除服务器中的数据
     *
     * @param list
     */
    public void deleteItemsInServer(List<ShopcartItem> list) {

        // TODO: 2017/6/8
    }

    String productTestStr = "[\n" +
            "                {\n" +
            "                    \"id\": 101998,\n" +
            "                    \"name\": \"蜂觅·水晶富士\",\n" +
            "                    \"store_nums\": 50,\n" +
            "                    \"specifics\": \"320-380g 2粒/盒\",\n" +
            "                    \"market_price\": 19.9,\n" +
            "                    \"partner_price\": 7.9,\n" +
            "                    \"pre_img\": \"20160314100316_796965.jpg\",\n" +
            "                    \"pre_imgs\": \"20160314100320_342771.jpg\",\n" +
            "                    \"category_id\": 103532,\n" +
            "                    \"cid\": 103532,\n" +
            "                    \"child_cid\": 103533,\n" +
            "                    \"price\": 7.9,\n" +
            "                    \"img\": \"http://img01.bqstatic.com/upload/goods/201/603/1410/20160314100316_796965.jpg@200w_200h_90Q.jpg\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 101765,\n" +
            "                    \"name\": \"爱鲜蜂·红颜草莓\",\n" +
            "                    \"store_nums\": 50,\n" +
            "                    \"specifics\": \"380g/盒\",\n" +
            "                    \"market_price\": 49,\n" +
            "                    \"partner_price\": 23.8,\n" +
            "                    \"pre_img\": \"20160304161529_578626.jpg\",\n" +
            "                    \"pre_imgs\": \"20160304161532_230452.jpg\",\n" +
            "                    \"category_id\": 103532,\n" +
            "                    \"cid\": 103532,\n" +
            "                    \"child_cid\": 103534,\n" +
            "                    \"price\": 8.8,\n" +
            "                    \"img\": \"http://img01.bqstatic.com/upload/goods/201/603/0416/20160304161529_578626.jpg@200w_200h_90Q.jpg\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 91698,\n" +
            "                    \"name\": \"蜂觅·越南直采红心火龙果\",\n" +
            "                    \"store_nums\": 50,\n" +
            "                    \"specifics\": \"340-450g/粒\",\n" +
            "                    \"market_price\": 49,\n" +
            "                    \"partner_price\": 11.9,\n" +
            "                    \"pre_img\": \"20160304170813_928198.jpg\",\n" +
            "                    \"pre_imgs\": \"20160304170818_266474.jpg\",\n" +
            "                    \"category_id\": 103532,\n" +
            "                    \"cid\": 103532,\n" +
            "                    \"child_cid\": 103535,\n" +
            "                    \"price\": 11.9,\n" +
            "                    \"img\": \"http://img01.bqstatic.com/upload/goods/201/603/0417/20160304170813_928198.jpg@200w_200h_90Q.jpg\"\n" +
            "                }]";

}
