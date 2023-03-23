package com.rkbapps.imagesearch.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
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
import com.rkbapps.imagesearch.adapter.MyAdapter;
import com.rkbapps.imagesearch.model.ImageModelClass;
import com.rkbapps.imagesearch.model.Photo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    public static final String KEY = "x9Yf1QT8KXSpwH4ILI0smrHAIGY7BwFarViZrYM8jxlY2vABSWIOyDX5";
    private RecyclerView recyclerView;
    private ImageModelClass img;
    private ProgressBar progressBar;
    private List<Photo> photoList = new ArrayList<>();
    private MyAdapter adapter;
    private int page, perPage;
    private boolean isLoading = false;

    Map<String, Object> totalPages = new HashMap<>();

    int pastVisiblesItems, visibleItemCount, totalItemCount;


    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        ExtendedFloatingActionButton ebSetting = view.findViewById(R.id.btnApplySetting);
        LinearLayoutCompat liner = view.findViewById(R.id.linerLayout);
        Button previous = view.findViewById(R.id.btnPreviousHome);
        Button next = view.findViewById(R.id.btnNextHome);
        liner.setVisibility(View.GONE);

        page = 1;
        perPage = 30;

        adapter = new MyAdapter(getContext(), photoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadImages("" + page, "" + perPage);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == photoList.size() - 1) {
                        //bottom of list!
                        if (img.getNextPage() != null) {
                            page++;
                            loadImages("" + page, "" + perPage);
                        } else {
                            Toast.makeText(getContext(), "No more result available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        ebSetting.setVisibility(View.GONE);

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
                    loadImages("" + page, "" + perPage);
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
                    loadImages("" + page, "" + perPage);
                    liner.setVisibility(View.GONE);
                    ebSetting.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "No more result available", Toast.LENGTH_SHORT).show();
                    liner.setVisibility(View.GONE);
                    ebSetting.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    private void loadImages(String page, String perPage) {
        progressBar.setVisibility(View.VISIBLE);
        isLoading = true;
        String url = "https://api.pexels.com/v1/curated?page=" + page + "&per_page=" + perPage;
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = new Gson();
                img = gson.fromJson(response, ImageModelClass.class);
                photoList.addAll(img.getPhotos());
                adapter.notifyDataSetChanged();
                isLoading = false;
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoading = false;
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", KEY);
                return headers;
            }
        };
        queue.add(stringRequest);
        progressBar.setVisibility(View.INVISIBLE);
    }

//    private void lodeMore(RecyclerView recyclerView){
//
//        LinearLayoutManager lm = (LinearLayoutManager)recyclerView.getLayoutManager();
//
//        //lm.findFirstVisibleItemPosition();
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0) { //check for scroll down
//                    visibleItemCount = lm.getChildCount();
//                    totalItemCount = lm.getItemCount();
//                    pastVisiblesItems = lm.findFirstVisibleItemPosition();
//
//                    if (mLoading) {
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                            mLoading = false;
//                            Log.v("...", "Last Item Wow !");
//                            if(img.getNextPage()!=null){
//                                loadImages(img.getNextPage(),""+perPage );
//                            }
//                            mLoading = true;
//                        }
//                    }
//                }
//            }
//        });
//    }


}