package com.rkbapps.imagesearch.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rkbapps.imagesearch.R;
import com.rkbapps.imagesearch.RecyclerTouchListener;
import com.rkbapps.imagesearch.adapter.MyAdapter;
import com.rkbapps.imagesearch.adapter.TopicAdapter;
import com.rkbapps.imagesearch.model.ImageModelClass;
import com.rkbapps.imagesearch.model.Photo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchFragment extends Fragment {
    ImageView imgSearch;
    Button btnSearch;
    EditText txtSearchText;
    ConstraintLayout searchView, topicView;
    String strSearchText;
    private RecyclerView recyclerView, recyclerViewTopic;
    private ImageModelClass img;
    private ProgressBar progressBar;
    private List<Photo> photoList = new ArrayList<>();
    private MyAdapter adapter;

    private static int page;

    public SearchFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        imgSearch = view.findViewById(R.id.imgSearch);
        btnSearch = view.findViewById(R.id.buttonSearch);
        txtSearchText = view.findViewById(R.id.txtSearch);
        searchView = view.findViewById(R.id.searchLayout);
        topicView = view.findViewById(R.id.topicLayout);
        searchView.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recyclerView2);
        progressBar = view.findViewById(R.id.progressBarSearch);
        recyclerViewTopic = view.findViewById(R.id.recyclerViewTopic);

        String[] topics = {"Peacock", "Moon", "Beautiful", "Landscape", "Animal", "Building", "Nature", "Sunset", "Beach", "Travel", "Forest", "Flowers", "Dog", "Dark", "Cat", "Technology", "House", "Love"};

        TopicAdapter topicAdapter = new TopicAdapter(topics);

        ExtendedFloatingActionButton ebSetting = view.findViewById(R.id.btnApplySetting2);
        LinearLayoutCompat liner = view.findViewById(R.id.linerLayout2);
        Button previous = view.findViewById(R.id.btnPreviousSearch);
        Button next = view.findViewById(R.id.btnNextSearch);
        liner.setVisibility(View.GONE);
        page = 1;
        strSearchText = "wallpaper";
        loadImages(strSearchText, "" + page);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewTopic.setLayoutManager(lm);
        recyclerViewTopic.setAdapter(topicAdapter);

        //On any topic click
        recyclerViewTopic.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerViewTopic, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                page = 1;
                strSearchText = "" + topics[position];
                loadImages(strSearchText, "" + page);
            }

            @Override
            public void onLongClick(View view, int position) {
                //noting
            }
        }));

        //search button
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicView.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSearchText = txtSearchText.getText().toString().trim();
                topicView.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);
                if (strSearchText.equals("")) {
                    Toast.makeText(getContext(), "nothing", Toast.LENGTH_SHORT).show();
                } else {
                    page = 1;
                    loadImages(strSearchText, "" + page);
                }
            }
        });

        ebSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ebSetting.setVisibility(View.GONE);
                liner.setVisibility(View.VISIBLE);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page > 1) {
                    page--;
                    loadImages(strSearchText, "" + page);
                    liner.setVisibility(View.GONE);
                    ebSetting.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "No previous page available", Toast.LENGTH_SHORT).show();
                    liner.setVisibility(View.GONE);
                    ebSetting.setVisibility(View.VISIBLE);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img.getNextPage() != null) {
                    page++;
                    loadImages(strSearchText, "" + page);
                    liner.setVisibility(View.GONE);
                    ebSetting.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "No more result available", Toast.LENGTH_SHORT).show();
                    liner.setVisibility(View.GONE);
                    ebSetting.setVisibility(View.VISIBLE);
                }
            }
        });

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Intent i = new Intent(getContext(), ImagePreviewActivity.class);
//                i.putExtra("link",photoList.get(position).getSrc().getOriginal());
//                startActivity(i);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        return view;
    }


    private void loadImages(String searchTxt, String page) {
        progressBar.setVisibility(View.VISIBLE);
        photoList.clear();
        String url = "https://api.pexels.com/v1/search/?page=" + page + "&per_page=30&query=" + searchTxt;
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = new Gson();
                img = gson.fromJson(response, ImageModelClass.class);
                photoList = img.getPhotos();
                adapter = new MyAdapter(getContext(), photoList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", HomeFragment.KEY);
                return headers;
            }
        };
        queue.add(stringRequest);
        progressBar.setVisibility(View.INVISIBLE);
    }

}