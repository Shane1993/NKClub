package net.lzs.club.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by LEE on 2016/4/14.
 */
public class Club extends BmobObject
{
    private BmobFile picture;
    private String name;
    private String type;
    private String time;
    private String description;
    private String belongs;

    public Club(){}

    public Club(String name, String type, String time, String description)
    {
        this.name = name;
        this.type = type;
        this.time = time;
        this.description = description;
    }
    public Club(String name, String type, String time, String description,String belongs)
    {
        this.name = name;
        this.type = type;
        this.time = time;
        this.description = description;
        this.belongs = belongs;
    }
    public Club(String objectIdFromServer, String name, String type, String time, String description, String belongs)
    {
        setObjectId(objectIdFromServer);
        this.name = name;
        this.type = type;
        this.time = time;
        this.description = description;
        this.belongs = belongs;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }


    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getBelongs()
    {
        return belongs;
    }

    public void setBelongs(String belongs)
    {
        this.belongs = belongs;
    }

    public BmobFile getPicture()
    {
        return picture;
    }

    public void setPicture(BmobFile picture)
    {
        this.picture = picture;
    }

    @Override
    public String toString()
    {
        return "Club{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", belongs='" + belongs + '\'' +
                '}';
    }
}
