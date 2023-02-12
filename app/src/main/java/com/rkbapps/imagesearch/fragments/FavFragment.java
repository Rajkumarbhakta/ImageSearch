package com.rkbapps.imagesearch.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.rkbapps.imagesearch.ImagePreviewActivity;
import com.rkbapps.imagesearch.R;
import com.rkbapps.imagesearch.RecyclerTouchListener;
import com.rkbapps.imagesearch.adapter.MyAdapterFav;
import com.rkbapps.imagesearch.db.MyFav;
import com.rkbapps.imagesearch.db.MyFavDatabase;

import java.util.ArrayList;
import java.util.List;


public class FavFragment extends Fragment {

    private RecyclerView recyclerView;
    List<MyFav> myFavList = new ArrayList<>();

    MyFavDatabase myFavDatabase;
    MyAdapterFav myAdapterFav;

    public FavFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMyFav);

        myFavDatabase = Room.databaseBuilder(getContext(), MyFavDatabase.class, "myFavDb")
                .allowMainThreadQueries().build();

        myAdapterFav = new MyAdapterFav(getContext(), myFavList);
        if (myFavDatabase.getMyFavDao().getAllFav().size() != 0)
            myFavList.addAll(myFavDatabase.getMyFavDao().getAllFav());

        //Log.d("img",myFavList.get(0).getAlt());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapterFav);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getContext(), ImagePreviewActivity.class);
                i.putExtra("imageInfoFav", myFavList.get(position));
                i.putExtra("class", "Fav");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        myFavList.clear();
        if (myFavDatabase.getMyFavDao().getAllFav().size() != 0)
            myFavList.addAll(myFavDatabase.getMyFavDao().getAllFav());
        myAdapterFav.notifyDataSetChanged();
    }
}