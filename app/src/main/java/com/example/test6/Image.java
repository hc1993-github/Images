package com.example.test6;

import android.os.Parcel;
import android.os.Parcelable;


public class Image implements Parcelable {
    private String path;
    private String parentpath;
    private long time;
    private String name;
    public Image(String name, String path, long time) {
        this.name = name;
        this.path = path;
        this.time = time;
        int index = path.lastIndexOf("/");
        this.parentpath = path.substring(0,index);
    }

    protected Image(Parcel in) {
        path = in.readString();
        parentpath = in.readString();
        time = in.readLong();
        name = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getParentpath() {
        return parentpath;
    }

//    public void setParentpath(String parentpath) {
//        this.parentpath = parentpath;
//    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
        parcel.writeString(parentpath);
        parcel.writeLong(time);
        parcel.writeString(name);
    }
}
