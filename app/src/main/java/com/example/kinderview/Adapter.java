package com.example.kinderview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.kinderview.model.Model;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    ArrayList<Model> modelArrayList=new ArrayList<>();
    RequestManager glide;


    public Adapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        glide= Glide.with(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed,parent,false);
        MyViewHolder ViewHolder=new MyViewHolder(view);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Model model=modelArrayList.get(position);
        holder.tv_name.setText(model.getName());
        holder.tv_time.setText(model.getTime());
        holder.tv_likes.setText(String.valueOf(model.getLikes()));
        holder.tv_comments.setText(model.getComments() + " comments");
        holder.tv_status.setText(model.getStatus());
        glide.load(model.getProPic()).into(holder.imgview_propic);
        if(model.getPostPic()==0){
            holder.imgview_postpic.setVisibility(View.GONE);
        }else{
            holder.imgview_postpic.setVisibility(View.VISIBLE);
        }
        glide.load(model.getPostPic()).into(holder.imgview_postpic);

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_time,tv_likes,tv_comments,tv_status;
        ImageView imgview_propic,imgview_postpic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview_propic=(ImageView)itemView.findViewById(R.id.row_feed_profile);
            imgview_postpic=(ImageView)itemView.findViewById(R.id.row_feed_postimage);
            tv_name=(TextView)itemView.findViewById(R.id.row_feed_authname);
            tv_time=(TextView)itemView.findViewById(R.id.row_feed_time);
            tv_likes=(TextView)itemView.findViewById(R.id.row_feed_likesnumber);
            tv_comments=(TextView)itemView.findViewById(R.id.row_feed_comments);
            tv_status=(TextView)itemView.findViewById(R.id.row_feed_statustext);

        }
    }
}
