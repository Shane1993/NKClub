package net.lzs.club.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.activity.CreateClubActivity;
import net.lzs.club.activity.LikedClubIntroductionActivity;
import net.lzs.club.activity.TypedClubsActivity;
import net.lzs.club.adapter.AdapterClub;
import net.lzs.club.config.Config;
import net.lzs.club.model.Club;
import net.lzs.club.net.GetClubs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEE on 2016/4/14.
 */
public class FragmentGroup extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener
{
    Context context;

    View view;
    ListView lvGroups;
    AdapterClub adapterClub;
    List<Club> list;

    GetClubs getClubs;

    LinearLayout llCreateClub;

    ImageButton btnTiyu, btnXiao, btnYuan, btnShijian, btnGongyi, btnKoucai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.i("FragmentGroup","onCreateView");

        view = inflater.inflate(R.layout.fragment_group, null);

        initView();


        return view;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.i("FragmentGroup","onDestroyView");

    }

    private void initView()
    {
        context = getActivity();

        lvGroups = (ListView) view.findViewById(R.id.fragment_lv_groups);
        list = new ArrayList<Club>();
        adapterClub = new AdapterClub(context, list);
        lvGroups.setAdapter(adapterClub);
        lvGroups.setOnItemClickListener(this);

        llCreateClub = (LinearLayout) view.findViewById(R.id.fragment_linearlayout_left);
        llCreateClub.setOnClickListener(this);

        btnTiyu = (ImageButton) view.findViewById(R.id.fragment_ib_tiyu);
        btnXiao = (ImageButton) view.findViewById(R.id.fragment_ib_xiao);
        btnYuan = (ImageButton) view.findViewById(R.id.fragment_ib_yuan);
        btnShijian = (ImageButton) view.findViewById(R.id.fragment_ib_shijian);
        btnGongyi = (ImageButton) view.findViewById(R.id.fragment_ib_gongyi);
        btnKoucai = (ImageButton) view.findViewById(R.id.fragment_ib_koucai);

        btnTiyu.setOnClickListener(this);
        btnXiao.setOnClickListener(this);
        btnYuan.setOnClickListener(this);
        btnShijian.setOnClickListener(this);
        btnGongyi.setOnClickListener(this);
        btnKoucai.setOnClickListener(this);

        getClubs = new GetClubs(context, 10, new GetClubs.SuccessCallback()
        {
            @Override
            public void onSuccess(List<Club> result)
            {
                list.addAll(result);
                adapterClub.notifyDataSetChanged();

                //Cache the url
                for (Club club : result)
                {
                    Config.cacheKVData(context, club.getName(), club.getIconUrl());
                }


            }
        }, new GetClubs.FailCallback()
        {
            @Override
            public void onFail(String errer)
            {
                Toast.makeText(context, errer, Toast.LENGTH_SHORT).show();
            }
        });

        refreshList();

    }

    private void refreshList()
    {
        getClubs.showNextClubs();


//        new GetClubsName(context, new GetClubsName.SuccessCallback()
//        {
//            @Override
//            public void onSuccess(String clubs)
//            {
//
//                final String cachedClubs = Config.getCachedData(context, CacheType.CLUB);
//
//                if (cachedClubs.equals(clubs))
//                {
//                    return;
//                }
//
//                Config.cacheData(context, clubs, CacheType.CLUB);
//                final FileUtils fileUtils = new FileUtils();
//
//                new AsyncTask<String, Void, List<Club>>()
//                {
//                    @Override
//                    protected List<Club> doInBackground(String... params)
//                    {
//
//                        for (final String clubName : params)
//                        {
//                            if (!fileUtils.isFileExist(params + ".jpg", Config.APP_NAME))
//                            {
//                                new GetClubPictureUrl(context, clubName, new GetClubPictureUrl.SuccessCallback()
//                                {
//                                    @Override
//                                    public void onSuccess(String clubPictureUrl)
//                                    {
//                                        DownloadPicture.downFile(clubPictureUrl, Config.APP_NAME, clubName + ".jpg");
//
//                                        StringBuilder sb = new StringBuilder(Config.getCachedData(context, CacheType.CLUB));
//                                        sb.append("," + clubName);
//                                        Config.cacheData(context, sb.toString(), CacheType.CLUB);
//
//                                        new GetClub(context, clubName, new GetClub.SuccessCallback()
//                                        {
//                                            @Override
//                                            public void onSuccess(Club club)
//                                            {
//                                                list.add(club);
//                                                adapterClub.notifyDataSetChanged();
//                                            }
//                                        }, null);
//                                    }
//                                }, null);
//
//                                continue;
//                            }
//
//                            new GetClub(context, clubName, new GetClub.SuccessCallback()
//                            {
//                                @Override
//                                public void onSuccess(Club club)
//                                {
//
//                                    list.add(club);
//                                    adapterClub.notifyDataSetChanged();
//                                }
//                            }, null);
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(List<Club> clubs)
//                    {
//                        pd.dismiss();
//                    }
//                }.execute(clubs.split(","));
//
//            }
//        }, new GetClubsName.FailCallback()
//        {
//            @Override
//            public void onFail()
//            {
//                pd.dismiss();
//                Toast.makeText(context, "加载社团失败", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Club club = list.get(position);

        Intent intent = new Intent(context, LikedClubIntroductionActivity.class);
        intent.putExtra(Config.OBJECTID, club.getObjectId());
        intent.putExtra(Config.CLUBNAME, club.getName());
        intent.putExtra(Config.CLUBTYPE, club.getType());
        intent.putExtra(Config.CLUBTIME, club.getTime());
        intent.putExtra(Config.CLUBDESCRIPTION, club.getDescription());
        startActivity(intent);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = null;
        switch (v.getId())
        {
            case R.id.fragment_linearlayout_left:

                intent = new Intent(context, CreateClubActivity.class);

                break;
            case R.id.fragment_ib_tiyu:
                intent = new Intent(context, TypedClubsActivity.class);
                intent.putExtra(Config.CLUBTYPE, "体育类");
                break;
            case R.id.fragment_ib_xiao:
                intent = new Intent(context, TypedClubsActivity.class);
                intent.putExtra(Config.CLUBTYPE, "校组织");

                break;
            case R.id.fragment_ib_yuan:
                intent = new Intent(context, TypedClubsActivity.class);
                intent.putExtra(Config.CLUBTYPE, "院组织");

                break;
            case R.id.fragment_ib_shijian:
                intent = new Intent(context, TypedClubsActivity.class);
                intent.putExtra(Config.CLUBTYPE, "实践类");

                break;
            case R.id.fragment_ib_gongyi:
                intent = new Intent(context, TypedClubsActivity.class);
                intent.putExtra(Config.CLUBTYPE, "公益类");

                break;
            case R.id.fragment_ib_koucai:
                intent = new Intent(context, TypedClubsActivity.class);
                intent.putExtra(Config.CLUBTYPE, "口才类");



                //Test
//                refreshList();


                break;
        }

        if (intent != null)
            startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (resultCode == 1)
        {
            Club club = (Club) data.getSerializableExtra(Config.CLUB);
            list.add(club);
            adapterClub.notifyDataSetChanged();


//            final String clubObjectId = data.getStringExtra(Config.OBJECTID);
//            final String clubName = data.getStringExtra(Config.CLUBNAME);
//            final String clubType = data.getStringExtra(Config.CLUBTYPE);
//            final String clubTime = data.getStringExtra(Config.CLUBTIME);
//            final String clubDescription = data.getStringExtra(Config.CLUBDESCRIPTION);
//            final String clubIconUri = data.getStringExtra(Config.CLUBICONURI);
//
//            new GetClubPictureUrl(context, clubName, new GetClubPictureUrl.SuccessCallback()
//            {
//                @Override
//                public void onSuccess(String clubPictureUrl)
//                {
//                    DownloadPicture.downFile(clubPictureUrl, Config.APP_NAME, clubName + ".jpg");
//
//                    StringBuilder sb = new StringBuilder(Config.getCachedData(context, CacheType.CLUB));
//                    sb.append("," + clubName);
//                    Config.cacheData(context, sb.toString(), CacheType.CLUB);
//
//                    Club club = new Club(clubObjectId,clubName, clubType, clubTime, clubDescription,"");
//
//                    list.add(club);
//                    adapterClub.notifyDataSetChanged();
//
//                }
//            }, new GetClubPictureUrl.FailCallback()
//            {
//                @Override
//                public void onFail(String error)
//                {
//
//                    Toast.makeText(context, "加载社团失败"+error, Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

}
