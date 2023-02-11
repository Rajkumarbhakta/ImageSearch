package com.rkbapps.imagesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.rkbapps.imagesearch.model.Photo;

import org.w3c.dom.Text;

public class ImagePreviewActivity extends AppCompatActivity {

    private ImageView previewImage;
    private TextView txtAlt;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        previewImage=findViewById(R.id.imgPreviewImage);
        txtAlt=findViewById(R.id.txtAlt);
        ExtendedFloatingActionButton ebInfo,ebFav,ebDownload;
        ebInfo=findViewById(R.id.ebImageInfo);
        ebFav=findViewById(R.id.ebAddToFav);
        ebDownload=findViewById(R.id.ebDownloadImage);

        Photo photo = (Photo) getIntent().getSerializableExtra("imageInfo") ;
        boolean isFav = getIntent().getBooleanExtra("isFav",false);

        txtAlt.setTextColor(Color.parseColor(photo.getAvgColor()));

        Glide.with(this).load(photo.getSrc().getOriginal()).into(previewImage);
        if(photo.getAlt()!=null)
            txtAlt.setText(photo.getAlt());
        else
            txtAlt.setText("");

        if(isFav)
            ebFav.setIconTint(ColorStateList.valueOf(Color.RED));

        ebInfo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(ImagePreviewActivity.this);
                d.setContentView(R.layout.image_info_dialog);
                d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView height = d.findViewById(R.id.textHeight);
                TextView width = d.findViewById(R.id.textWidth);
                TextView photographerName = d.findViewById(R.id.textPhotographerName);
                TextView photographerId = d.findViewById(R.id.textPhotographerId);
                TextView aboutImage = d.findViewById(R.id.textAboutImage);

                height.setText(""+photo.getHeight());
                width.setText(""+photo.getWidth());
                photographerName.setText(""+photo.getPhotographer());
                photographerId.setText(""+photo.getPhotographerId());
                aboutImage.setText(""+photo.getAlt());

                d.show();
            }
        });
    }

}