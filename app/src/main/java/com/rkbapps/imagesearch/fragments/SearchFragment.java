package com.rkbapps.imagesearch.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rkbapps.imagesearch.R;
import com.rkbapps.imagesearch.adapter.MyAdapter;
import com.rkbapps.imagesearch.adapter.TopicAdapter;
import com.rkbapps.imagesearch.databinding.FragmentSearchBinding;
import com.rkbapps.imagesearch.model.ImageModelClass;
import com.rkbapps.imagesearch.model.Photo;
import com.rkbapps.imagesearch.services.ApiInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class SearchFragment extends Fragment {

    private ImageModelClass img;
    private List<Photo> photoList = new ArrayList<>();
    private MyAdapter adapter;

    private static int page;
    private String strSearchText;

    private boolean isLoading = false;
    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);


        String[] topics = {"Peacock", "Moon", "Beautiful", "Landscape", "Animal", "Building", "Nature", "Sunset", "Beach", "Travel", "Forest", "Flowers", "Dog", "Dark", "Cat", "Technology", "House", "Love"};
        TopicAdapter topicAdapter = new TopicAdapter(topics);

        page = 1;

        binding.recyclerSearchResult.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MyAdapter(getContext(), photoList);
        binding.recyclerSearchResult.setAdapter(adapter);

        strSearchText = "wallpaper";
        loadImages(strSearchText, page);

        binding.searchView.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                binding.searchBar.setText(binding.searchView.getText());
                strSearchText = binding.searchView.getText().toString();
                page = 1;
                photoList.clear();
                loadImages(strSearchText, page);
                binding.searchView.hide();
                return false;
            }
        });

        binding.recyclerSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == photoList.size() - 1) {
                    //bottom of list!
                    if (!isLoading) {
                        if (img.getNextPage() != null) {
                            page++;
                            loadImages(strSearchText, page);
                        } else {
                            Toast.makeText(getContext(), "No more result available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        return binding.getRoot();
    }


    private void loadImages(String query, Integer page) {
        isLoading = true;
        ApiInstance.getApi().getSearchResult(query, page, 10).enqueue(new Callback<ImageModelClass>() {
            @Override
            public void onResponse(Call<ImageModelClass> call, retrofit2.Response<ImageModelClass> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        img = response.body();
                        photoList.addAll(response.body().getPhotos());
                        adapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageModelClass> call, Throwable t) {
                Toast.makeText(requireContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}