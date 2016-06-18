package net.lzs.club.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.adapter.AdapterClub;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.Club;
import net.lzs.club.net.GetMyLikedClubs;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LEE on 2016/4/19.
 */
public class MyLikedClubsActivity extends android.app.Activity implements AdapterView.OnItemClickListener
{
    @Bind(R.id.activity_listclubs_tv)
    TextView title;
    @Bind(R.id.activity_listclubs_lv)
    ListView lv;

    List<Club> clubs;
    AdapterClub adapterClub;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clubs);

        ButterKnife.bind(this);

        title.setText("关注的社团");

        clubs = new ArrayList<Club>();
        adapterClub = new AdapterClub(this, clubs);
        lv.setAdapter(adapterClub);
        lv.setOnItemClickListener(this);

        new GetMyLikedClubs(MyLikedClubsActivity.this, Config.getCachedData(MyLikedClubsActivity.this, CacheType.LIKEDCLUBS),
                new GetMyLikedClubs.SuccessCallback()
                {
                    @Override
                    public void onSuccess(List<Club> result)
                    {
                        clubs.addAll(result);
                        adapterClub.notifyDataSetChanged();
                    }
                }, new GetMyLikedClubs.FailCallback()
        {
            @Override
            public void onFail(String error)
            {
                Toast.makeText(MyLikedClubsActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Club club = clubs.get(position);

        Intent intent = new Intent(MyLikedClubsActivity.this, LikedClubIntroductionActivity.class);
        intent.putExtra(Config.OBJECTID, club.getObjectId());
        intent.putExtra(Config.CLUBNAME, club.getName());
        intent.putExtra(Config.CLUBTYPE, club.getType());
        intent.putExtra(Config.CLUBTIME, club.getTime());
        intent.putExtra(Config.CLUBDESCRIPTION, club.getDescription());
        startActivity(intent);

        MyLikedClubsActivity.this.finish();
    }
}
