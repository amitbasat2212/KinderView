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
import com.squareup.picasso.Picasso;


import java.util.List;

public class Home_page extends Fragment {
    PostViewModel viewModel;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;



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

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
               String stId = viewModel.getData().getValue().get(position).getId();
               String stUsername = viewModel.getData().getValue().get(position).getUsername();
               String status = viewModel.getData().getValue().get(position).getStatus();
               String likes = viewModel.getData().getValue().get(position).getLikes();
               String date = viewModel.getData().getValue().get(position).getDate();
               String url = viewModel.getData().getValue().get(position).getUrlImagePost();
                if(url==null){
                    url="0";
                }
                Navigation.findNavController(view).navigate(Home_pageDirections.actionHomePage2ToFragmentEditPost(stUsername, date, status, likes,stId,url));

            }
        });
        adapter.notifyDataSetChanged();


        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_time,tv_likes,tv_status;
        ImageView imgview_propic,imgview_postpic;
        ImageView imgedit, imgdelete;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            imgview_propic=(ImageView)itemView.findViewById(R.id.row_feed_profile);
            imgview_postpic=(ImageView)itemView.findViewById(R.id.row_feed_postimage);
            tv_name=(TextView)itemView.findViewById(R.id.row_feed_authname);
            tv_time=(TextView)itemView.findViewById(R.id.row_feed_time);
            tv_likes=(TextView)itemView.findViewById(R.id.row_feed_likesnumber);
            tv_status=(TextView)itemView.findViewById(R.id.row_feed_statustext);
            imgedit =(ImageView)itemView.findViewById(R.id.row_feed_editpost);
            imgdelete =(ImageView)itemView.findViewById(R.id.row_feed_deletepost);

            imgedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position,itemView);
                }
            });


        }
        public void bind(Post post){
            tv_name.setText(post.getUsername());
            tv_time.setText(post.getDate());
            tv_likes.setText(post.getLikes());
            tv_status.setText(post.getStatus());
            if (post.getUrlImagePost() != null) {
                Picasso.get()
                        .load(post.getUrlImagePost())
                        .into(imgview_postpic);
            }

        }
    }


    //////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////
    interface OnItemClickListener{
        void onItemClick(int position,View view);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        public void setListener(OnItemClickListener listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.row_feed,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
          Post post = viewModel.getData().getValue().get(position);
          holder.bind(post);

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