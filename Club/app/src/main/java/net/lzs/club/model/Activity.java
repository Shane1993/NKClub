package net.lzs.club.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by LEE on 2016/4/14.
 */
public class Activity extends BmobObject
{
    private String name;
    private String organizer;
    private String time;
    private String content;
    private String belongs;
    public Activity()
    {
    }

    public Activity(String name, String organizer, String time, String content,String belongs)
    {
        this.name = name;
        this.organizer = organizer;
        this.time = time;
        this.content = content;
        this.belongs = belongs;
    }
    public Activity(String objectId,String name, String organizer, String time, String content,String belongs)
    {
        setObjectId(objectId);
        this.name = name;
        this.organizer = organizer;
        this.time = time;
        this.content = content;
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

    public String getOrganizer()
    {
        return organizer;
    }

    public void setOrganizer(String organizer)
    {
        this.organizer = organizer;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getBelongs()
    {
        return belongs;
    }

    public void setBelongs(String belongs)
    {
        this.belongs = belongs;
    }

    @Override
    public String toString()
    {
        return "Activity{" +
                "name='" + name + '\'' +
                ", organizer='" + organizer + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", belongs='" + belongs + '\'' +
                '}';
    }
}

