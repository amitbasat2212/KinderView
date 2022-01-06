package com.example.kinderview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kinderview.model.Model;
import com.example.kinderview.viewModel.PostViewModel;

import java.util.ArrayList;


public class Home_page extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Model> modelArrayList = new ArrayList<>();
    Adapter adapter;

    private PostViewModel viewModel;

    public Home_page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragments_home);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(getContext(),modelArrayList);
        recyclerView.setAdapter(adapter);
        populateRecyclerView();

        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

    }

    public void populateRecyclerView() {
        Model model = new Model(1,5,2,R.drawable.profile,R.drawable.kids,"liem mazal","2 hours","the kids are playing");
        Model model2 = new Model(2,7,2,R.drawable.profile,R.drawable.kids,"liem mazal1","4 hours","the kids are playing888");
        Model model3 = new Model(3,10,2,R.drawable.profile, R.drawable.kids2,"liem mazal2","3 hours","the kids are playing111");

        modelArrayList.add(model);
        modelArrayList.add(model2);
        modelArrayList.add(model3);

        adapter.notifyDataSetChanged();

    }
}