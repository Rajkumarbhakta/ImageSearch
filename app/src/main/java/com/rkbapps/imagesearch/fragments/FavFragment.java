package com.rkbapps.imagesearch.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rkbapps.imagesearch.R;
import com.rkbapps.imagesearch.adapter.MyAdapterFav;
import com.rkbapps.imagesearch.db.MyFav;
import com.rkbapps.imagesearch.db.MyFavDatabase;

import java.util.ArrayList;
import java.util.List;


public class FavFragment extends Fragment {

    private RecyclerView recyclerView;
    List<MyFav> myFavList = new ArrayList<>();


    public FavFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView=view.findViewById(R.id.recyclerViewMyFav);

        MyFavDatabase myFavDatabase = Room.databaseBuilder(getContext(),MyFavDatabase.class,"myFavDb")
                .allowMainThreadQueries().build();

        MyAdapterFav myAdapterFav = new MyAdapterFav(getContext(),myFavList);
       if(myFavDatabase.getMyFavDao().getAllFav().size()!=0)
           myFavList.addAll(myFavDatabase.getMyFavDao().getAllFav());

        //Log.d("img",myFavList.get(0).getAlt());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapterFav);


        return view;
    }

}