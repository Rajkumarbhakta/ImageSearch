package com.rkbapps.imagesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImagePreviewActivity extends AppCompatActivity {

    private ImageView previewImage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        previewImage=findViewById(R.id.imgPreviewImage);
        String imageLink = getIntent().getStringExtra("link");

        Glide.with(this).load(imageLink).into(previewImage);

    }
}