package com.example.test6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
//    ProgressDialog progressDialog;

//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            progressDialog.dismiss();
//        }
//    };
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleview);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getImages(new Callback() {
            @Override
            public void onSuccess(ArrayList<Folder> folders) {
                    myAdapter = new MyAdapter(MainActivity.this,folders);
                    recyclerView.setAdapter(myAdapter);
            }
        });
    }
    private void getImages(final Callback callback) {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this,"没有sdcard",Toast.LENGTH_LONG).show();
            return;
        }
        //progressDialog = ProgressDialog.show(this,null,"正在加载");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri imageuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = MainActivity.this.getContentResolver();
                Cursor cursor = contentResolver.query(imageuri,new String[]{MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATE_ADDED,MediaStore.Images.Media._ID},null,null,MediaStore.Images.Media.DATE_ADDED);
                List<Image> images = new ArrayList<>();
                while (cursor.moveToNext()){
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                    images.add(new Image(name,path,time));
                }
                cursor.close();
                Collections.reverse(images);
                //handler.sendEmptyMessage(1);
                callback.onSuccess(split2folder(images));
            }
        }).start();
    }

    private ArrayList<Folder> split2folder(List<Image> images) {//拆分所有图片路径
        Set<String> foldernames = new HashSet<>();//遍历文件夹 去重
        for(int i=0;i<images.size();i++){
            String folderName = getFolderName(images.get(i).getParentpath());
            foldernames.add(folderName);
        }
        ArrayList<Folder> folders = new ArrayList<>(); //创建文件夹
        for(String foldername:foldernames){
            Folder folder = new Folder(foldername);
            folders.add(folder);
        }
        for(int i=0;i<images.size();i++){ //遍历所有图片 添加到对应文件夹
            for(int j=0;j<folders.size();j++){
                if(images.get(i).getPath().contains(folders.get(j).getFoldername())){
                    folders.get(j).addimage(images.get(i));
                }else {
                    continue;
                }
            }
        }
        return folders;
    }
    private String getFolderName(String parentpath) { //获取文件夹名
        int index = parentpath.lastIndexOf("/");
        return parentpath.substring(index+1,parentpath.length());
    }

    private interface Callback{
        void onSuccess(ArrayList<Folder> folders);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        linearLayoutManager = null;
        myAdapter = null;
        MainActivity.this.finish();
    }
}