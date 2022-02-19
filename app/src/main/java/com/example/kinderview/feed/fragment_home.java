package com.example.kinderview.feed;

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

import com.example.kinderview.R;
import com.example.kinderview.model.Model;
import com.example.kinderview.model.ModelFireBase;
import com.example.kinderview.model.Post;
import com.example.kinderview.model.Profile;
import com.example.kinderview.viewModel.PostViewModel;
import com.squareup.picasso.Picasso;


import java.util.List;

public class fragment_home extends Fragment {
    PostViewModel viewModel;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;
    ImageView imagePostFrame;

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
        Model.instance.getPostsListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == Model.PostsListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });


        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view,int idview) {
                String stId = viewModel.getData().getValue().get(position).getId();
                String stUsername = viewModel.getData().getValue().get(position).getEmail();
                String status = viewModel.getData().getValue().get(position).getStatus();
                String date = viewModel.getData().getValue().get(position).getDate();
                String url = viewModel.getData().getValue().get(position).getUrlImagePost();
                if(url==null){
                    url="0";
                }

                if(view.findViewById(R.id.row_feed_editpost).getId()==idview) {
                    Navigation.findNavController(view).navigate(fragment_homeDirections.actionHomePage2ToFragmentEditPost(stUsername, date, status, stId, url));
                }else
                if(view.findViewById(R.id.row_feed_deletepost).getId()==idview){
                    viewModel.deletePost(viewModel.getData().getValue().get(position), () -> {
                        Model.instance.refreshPostList();


                    });
                }

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
        TextView tv_name,tv_time,tv_likes,tv_status,Editview,Deleteview;
        ImageView imgview_propic,imgview_postpic;
        ImageView imgedit, imgdelete;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            imgview_propic=(ImageView)itemView.findViewById(R.id.row_feed_profile);
            imgview_postpic=(ImageView)itemView.findViewById(R.id.row_feed_postimage);
            tv_name=(TextView)itemView.findViewById(R.id.row_feed_authname);
            tv_time=(TextView)itemView.findViewById(R.id.row_feed_time);
            tv_status=(TextView)itemView.findViewById(R.id.row_feed_statustext);
            imgedit =(ImageView)itemView.findViewById(R.id.row_feed_editpost);
            imgdelete =(ImageView)itemView.findViewById(R.id.row_feed_deletepost);

            Editview = itemView.findViewById(R.id.rowfweed_edit_view);
            Deleteview = itemView.findViewById(R.id.rowfweed_delete_view);
            imagePostFrame = itemView.findViewById(R.id.row_feed_postimage);


            imgedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int viewid = v.getId();
                    listener.onItemClick(position,itemView,viewid);
                }
            });


            imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();
                    listener.onItemClick(position,itemView,viewid);
                }
            });



        }
        public void bind(Post post){
            tv_name.setText(post.getEmail());
            tv_time.setText(post.getDate());
            tv_status.setText(post.getStatus());

            Model.instance.getprofilebyEmail(post.getEmail(), new Model.GetProfileById() {
                @Override
                public void onComplete(Profile profile) {
                    if (post.getEmail().equals(profile.getEmail())) {

                        if(profile.getUrlImage()==null){
                            profile.setUrlImage("0");
                        }

                        Picasso.get().load(profile.getUrlImage()).resize(50, 50)
                                .centerCrop().into(imgview_propic);

                        if (!profile.getUrlImage().equals("0")) {
                            Picasso.get().load(profile.getUrlImage()).into(imgview_propic);
                        }
                        else{
                            Picasso.get().load(R.drawable.profile).into(imgview_propic);
                        }
                    }
                }
            });

            imgdelete.setVisibility(View.GONE);
            imgedit.setVisibility(View.GONE);
            Editview.setVisibility(View.GONE);
            Deleteview.setVisibility(View.GONE);

            Model.instance.getUserConnect(new Model.connect() {
                @Override
                public void onComplete(Profile profile) {
                    if(profile.getEmail().equals(tv_name.getText().toString())){
                        Model.instance.mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                imgdelete.setVisibility(View.VISIBLE);
                                imgedit.setVisibility(View.VISIBLE);
                                Editview.setVisibility(View.VISIBLE);
                                Deleteview.setVisibility(View.VISIBLE);

                            }
                        });

                    }
                }
            });

            if (post.getUrlImagePost() != null) {
                Picasso.get()
                        .load(post.getUrlImagePost()).resize(250, 180)
                        .centerCrop()
                        .into(imgview_postpic);
            }

        }
    }


    //////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////
    interface OnItemClickListener{
        void onItemClick(int position,View view,int idview);
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