package com.example.test6;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Folder> folders;
    public MyAdapter(Context context,ArrayList<Folder> folders){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.folders = folders;
    }
    @NonNull
    @Override
    public MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_item,parent,false);
        return new MyAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterHolder holder, final int position) {
        Folder folder = folders.get(position);
        final ArrayList<Image> foldimages =  folder.getImages();
        final Image image = foldimages.get(0);
        Glide.with(context).load(new File(image.getPath())).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.imageView);
        holder.foldername.setText(folder.getFoldername());
        holder.imagesize.setText(String.valueOf(folder.getImages().size()));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity2.openMainActivity2(foldimages,context);
                Intent intent = new Intent(context,MainActivity2.class);
                intent.putExtra("images",foldimages);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class MyAdapterHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView foldername;
        TextView imagesize;
        public MyAdapterHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            foldername = itemView.findViewById(R.id.foldername);
            imagesize = itemView.findViewById(R.id.imagesize);
        }
    }
}
