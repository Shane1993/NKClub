package net.lzs.club.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.adapter.AdapterClub;
import net.lzs.club.config.Config;
import net.lzs.club.model.Club;
import net.lzs.club.net.GetTypedClubs;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LEE on 2016/4/18.
 */
public class TypedClubsActivity extends Activity implements AdapterView.OnItemClickListener
{
    private Context context;
    @Bind(R.id.activity_listclubs_tv)
    TextView title;
    @Bind(R.id.activity_listclubs_lv)
    ListView lv;

    List<Club> list;
    AdapterClub adapterClub;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clubs);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String type = intent.getStringExtra(Config.CLUBTYPE);

        title.setText(type);

        context = TypedClubsActivity.this;

        list = new ArrayList<Club>();
        adapterClub = new AdapterClub(context,list);
        lv.setAdapter(adapterClub);
        lv.setOnItemClickListener(this);

        new GetTypedClubs(context,type,new GetTypedClubs.SuccessCallback()
        {

            @Override
            public void onSuccess(List<Club> resultList)
            {

                list.addAll(resultList);
                adapterClub.notifyDataSetChanged();
            }
        },new GetTypedClubs.FailCallback()
        {

            @Override
            public void onFail()
            {
                Toast.makeText(context, "获取社团信息失败", Toast.LENGTH_SHORT).show();
            }
        });

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
}
