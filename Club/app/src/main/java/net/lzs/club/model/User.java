package net.lzs.club.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by LEE on 2016/4/15.
 */
public class User extends BmobUser
{
    private String likedActivities = "";
    private String likedClubs = "";

    public User(){}

    public User(String likedActivities, String likedClubs)
    {
        this.likedActivities = likedActivities;
        this.likedClubs = likedClubs;
    }


    public String getLikedActivities()
    {
        return likedActivities;
    }

    public void setLikedActivities(String likedActivities)
    {
        this.likedActivities = likedActivities;
    }

    public String getLikedClubs()
    {
        return likedClubs;
    }

    public void setLikedClubs(String likedClubs)
    {
        this.likedClubs = likedClubs;
    }
}
