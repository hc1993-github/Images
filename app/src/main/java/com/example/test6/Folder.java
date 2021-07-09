package com.example.test6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Folder {
    private String foldername;//文件夹名
    private ArrayList<Image> images;//文件夹下包含的图片

    public Folder(String foldername) {
        this.foldername = foldername;
        if(this.images==null){
            this.images = new ArrayList<>();
        }
    }

//    public Folder(String foldername, List<Image> images) {
//        this.foldername = foldername;
//        this.images = images;
//    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }


    public void addimage(Image image) {

        images.add(image);
    }
}
