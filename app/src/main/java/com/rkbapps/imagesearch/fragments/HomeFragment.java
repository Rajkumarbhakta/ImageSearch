package com.rkbapps.imagesearch.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rkbapps.imagesearch.R;
import com.rkbapps.imagesearch.adapter.MyAdapter;
import com.rkbapps.imagesearch.databinding.FragmentHomeBinding;
import com.rkbapps.imagesearch.model.ImageModelClass;
import com.rkbapps.imagesearch.model.Photo;
import com.rkbapps.imagesearch.services.ApiInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {

    private ImageModelClass img;
    private List<Photo> photoList = new ArrayList<>();
    private MyAdapter adapter;
    private int page = 1;
    private int perPage = 30;
    private boolean isLoading = false;

    private FragmentHomeBinding binding;


    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);


        adapter = new MyAdapter(getContext(), photoList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        loadImages(page, perPage);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            loadImages(page, perPage);
                        } else {
                            Toast.makeText(getContext(), "No more result available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        return binding.getRoot();
    }

    private void loadImages(Integer page, Integer perPage) {
        binding.progressBar.setVisibility(View.VISIBLE);
        isLoading = true;

        ApiInstance.getApi().getCuratedImages(page, perPage).enqueue(new Callback<ImageModelClass>() {
            @Override
            public void onResponse(Call<ImageModelClass> call, retrofit2.Response<ImageModelClass> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        photoList.addAll(response.body().getPhotos());
                        img = response.body();
                        adapter.notifyDataSetChanged();
                        isLoading = false;
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageModelClass> call, Throwable t) {
                isLoading = false;
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}