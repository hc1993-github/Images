package com.example.test6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private ArrayList<Image> images;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private Button button;
    MyAdapter2 myAdapter2;
    private List<Image> selectedimages;
//    public static void openMainActivity2(List<Image> imgs, Context context){
//        images = imgs;
//        Intent intent = new Intent(context,MainActivity2.class);
//        context.startActivity(intent);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        images = intent.getParcelableArrayListExtra("images");
        recyclerView = findViewById(R.id.itemrecycle);
        button = findViewById(R.id.button);
        gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        myAdapter2 = new MyAdapter2(this,images);
        recyclerView.setAdapter(myAdapter2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedimages = myAdapter2.getSelectimages();
                Toast.makeText(MainActivity2.this,"共选择了"+selectedimages.size()+"张图片",Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void getImages(final String foldname, final Callback2 callback2) {
//        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            Toast.makeText(this,"没有sdcard",Toast.LENGTH_LONG).show();
//            return;
//        }
//        //progressDialog = ProgressDialog.show(this,null,"正在加载");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Uri imageuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                ContentResolver contentResolver = MainActivity2.this.getContentResolver();
//                Cursor cursor = contentResolver.query(imageuri,new String[]{MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATE_ADDED,MediaStore.Images.Media._ID},null,null,MediaStore.Images.Media.DATE_ADDED);
//                images = new ArrayList<>();
//                while (cursor.moveToNext()){
//                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
//                    long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
//                    if(!path.contains(foldname)){
//                        continue;
//                    }
//                    images.add(new Image(name,path,time));
//                }
//                cursor.close();
//                Collections.reverse(images);
//                //handler.sendEmptyMessage(1);
//                callback2.onSuccess(images);
//            }
//        }).start();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        images = null;
        selectedimages = null;
        gridLayoutManager = null;
        myAdapter2 = null;
        MainActivity2.this.finish();
    }
    private interface Callback2{
        void onSuccess(List<Image> images);
    }
}