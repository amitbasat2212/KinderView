package com.example.kinderview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;
import com.example.kinderview.viewModel.PostViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    List<Post> modelArrayList;
    RequestManager glide;
    PostViewModel viewModel;


    public Adapter(PostViewModel viewModel1,List<Post> list) {
         viewModel=viewModel1;
        modelArrayList=new LinkedList<>();

        //glide= Glide.with(context);

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
        holder.tv_name.setText(viewModel.getData().getValue().get(position).getUsername());
        holder.tv_time.setText(viewModel.getData().getValue().get(position).getDate());
        holder.tv_likes.setText(viewModel.getData().getValue().get(position).getLikes());
        holder.tv_comments.setText(viewModel.getData().getValue().get(position).getComment() + " comments");
        holder.tv_status.setText(viewModel.getData().getValue().get(position).getStatus());

        /*
        glide.load(model.getProPic()).into(holder.imgview_propic);
        if(model.getPostPic()==0){
            holder.imgview_postpic.setVisibility(View.GONE);
        }else{
            holder.imgview_postpic.setVisibility(View.VISIBLE);
        }
        glide.load(model.getPostPic()).into(holder.imgview_postpic);

         */

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
