package com.example.test6;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyAdapterHolder2> {
    Context context;
    LayoutInflater inflater;
    List<Image> images;
    List<Image> selectimages;
    public MyAdapter2(Context context, List<Image> imgs){
        this.selectimages = new ArrayList<>();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.images = imgs;

    }
    @NonNull
    @Override
    public MyAdapterHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_item2,parent,false);
        return new MyAdapterHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterHolder2 holder, final int position) {
        Glide.with(context).load(new File(images.get(position).getPath())).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectimages.contains(images.get(position))){
                    holder.imageView.setAlpha(1.0f);
                    selectimages.remove(images.get(position));
                }else {
                    holder.imageView.setAlpha(0.5f);
                    selectimages.add(images.get(position));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
    public List<Image> getSelectimages(){
        return selectimages;
    }
    public class MyAdapterHolder2 extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyAdapterHolder2(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview2);
        }
    }
}
