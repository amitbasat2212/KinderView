package com.example.kinderview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kinderview.model.Model;
import com.example.kinderview.model.Post;
import com.example.kinderview.viewModel.PostViewModel;

import java.util.List;

public class Home_page extends Fragment {
    PostViewModel viewModel;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page,container,false);

        swipeRefresh = view.findViewById(R.id.Post_swiperefresh);
        swipeRefresh.setOnRefreshListener(Model.instance::refreshPostList);

        RecyclerView list = view.findViewById(R.id.Postlist_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);



        setHasOptionsMenu(true);
        viewModel.getData().observe(getViewLifecycleOwner(), list1 -> refresh());
        swipeRefresh.setRefreshing(Model.instance.getPostsListLoadingState().getValue() == Model.PostsListLoadingState.loading);
        Model.instance.getPostsListLoadingState().observe(getViewLifecycleOwner(), studentListLoadingState -> {
            if (studentListLoadingState == Model.PostsListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });
        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder{
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




    //////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.row_feed,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tv_name.setText(viewModel.getData().getValue().get(position).getUsername());
            holder.tv_time.setText(viewModel.getData().getValue().get(position).getDate());
            holder.tv_likes.setText(viewModel.getData().getValue().get(position).getLikes());
            holder.tv_comments.setText(viewModel.getData().getValue().get(position).getComment() + " comments");
            holder.tv_status.setText(viewModel.getData().getValue().get(position).getStatus());

        }

        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue() == null){
                return 0;
            }
            return viewModel.getData().getValue().size();
        }
    }




}