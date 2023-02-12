package com.rkbapps.imagesearch;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.rkbapps.imagesearch.db.MyFav;
import com.rkbapps.imagesearch.db.MyFavDatabase;
import com.rkbapps.imagesearch.model.Photo;

public class ImagePreviewActivity extends AppCompatActivity {

    private ImageView previewImage;
    private TextView txtAlt;

    ExtendedFloatingActionButton ebInfo, ebFav, ebDownload;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        previewImage = findViewById(R.id.imgPreviewImage);
        txtAlt = findViewById(R.id.txtAlt);

        ebInfo = findViewById(R.id.ebImageInfo);
        ebFav = findViewById(R.id.ebAddToFav);
        ebDownload = findViewById(R.id.ebDownloadImage);

        MyFavDatabase myFavDatabase = Room.databaseBuilder(getApplicationContext(),MyFavDatabase.class,"myFavDb")
                .allowMainThreadQueries()
                .build();


        if (getIntent().getStringExtra("class").equals("Adapter")) {
            Photo photo = (Photo) getIntent().getSerializableExtra("imageInfo");
            Glide.with(this).load(photo.getSrc().getOriginal()).into(previewImage);
            if (photo.getAlt() != null)
                txtAlt.setText(photo.getAlt());
            else
                txtAlt.setText("");
            txtAlt.setTextColor(Color.parseColor(photo.getAvgColor()));
           // boolean isFav = getIntent().getBooleanExtra("isFav", false);


                isItFav(photo.getId(),myFavDatabase);

            ebInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageDetails(photo.getHeight()+"",""+photo.getWidth(),""+photo.getPhotographer(),""+photo.getPhotographerId(),""+photo.getAlt());
                }
            });

            ebFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myFavDatabase.getMyFavDao().isMyFavExist(photo.getId())) {
                        ebFav.setIconTint(ColorStateList.valueOf(Color.BLACK));
                        myFavDatabase.getMyFavDao().removeMyFav(photo.getId());
                        Toast.makeText(getApplicationContext(), "Removed from Favourite list", Toast.LENGTH_SHORT).show();
                    }else {
                        MyFav mFav = new MyFav(photo.getId(), photo.getHeight(), photo.getWidth(), photo.getPhotographer(), photo.getPhotographerId(), photo.getSrc().getOriginal(), photo.getSrc().getMedium(), photo.getAlt());
                        myFavDatabase.getMyFavDao().addToMyFav(mFav);
                        ebFav.setIconTint(ColorStateList.valueOf(Color.RED));
                        Toast.makeText(getApplicationContext(), "Added to Favourite list", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        if (getIntent().getStringExtra("class").equals("Fav")) {
            MyFav myFav = (MyFav) getIntent().getSerializableExtra("imageInfoFav");
            Glide.with(this).load(myFav.getOriginalImageLink()).into(previewImage);
            if (myFav.getAlt() != null)
                txtAlt.setText(myFav.getAlt());
            else
                txtAlt.setText("");
            txtAlt.setTextColor(Color.parseColor("#ff758f"));

            isItFav(myFav.getImageId(),myFavDatabase);
            ebInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageDetails(""+myFav.getHeight(),""+myFav.getWidth(),""+myFav.getPhotographer(),""+myFav.getPhotographerId(),""+myFav.getAlt());
                }
            });

            ebFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myFavDatabase.getMyFavDao().isMyFavExist(myFav.getImageId())) {
                        ebFav.setIconTint(ColorStateList.valueOf(Color.BLACK));
                        myFavDatabase.getMyFavDao().removeMyFav(myFav.getImageId());
                        Toast.makeText(getApplicationContext(), "Removed from Favourite list", Toast.LENGTH_SHORT).show();
                    }else {
                        MyFav mFav = myFav;
                        myFavDatabase.getMyFavDao().addToMyFav(mFav);
                        ebFav.setIconTint(ColorStateList.valueOf(Color.RED));
                        Toast.makeText(getApplicationContext(), "Added to Favourite list", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }

    private void showImageDetails(String height, String width, String photographer, String photographerId, String alt) {
        Dialog d = new Dialog(ImagePreviewActivity.this);
        d.setContentView(R.layout.image_info_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView txtHeight = d.findViewById(R.id.textHeight);
        TextView txtWidth = d.findViewById(R.id.textWidth);
        TextView txtPhotographerName = d.findViewById(R.id.textPhotographerName);
        TextView txtPhotographerId = d.findViewById(R.id.textPhotographerId);
        TextView aboutImage = d.findViewById(R.id.textAboutImage);

        txtHeight.setText(height);
        txtWidth.setText(width);
        txtPhotographerName.setText(photographer);
        txtPhotographerId.setText(photographerId);
        aboutImage.setText(alt);
        d.show();
    }

    private void isItFav(int imageId,MyFavDatabase myFavDatabase){
        if(myFavDatabase.getMyFavDao().isMyFavExist(imageId)) {
            ebFav.setIconTint(ColorStateList.valueOf(Color.RED));
        }else {
            ebFav.setIconTint(ColorStateList.valueOf(Color.BLACK));
        }
    }

//    private void addToMyFav(ExtendedFloatingActionButton ebAdd , MyFavDatabase myFavDatabase,int imageId){
//        ebAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(myFavDatabase.getMyFavDao().isMyFavExist(imageId)) {
//                    ebFav.setIconTint(ColorStateList.valueOf(Color.BLACK));
//                    myFavDatabase.getMyFavDao().removeMyFav(imageId);
//                    Toast.makeText(getApplicationContext(), "Removed from Favourite list", Toast.LENGTH_SHORT).show();
//                }else {
//                    MyFav mFav = new MyFav(photoList.get(position).getId(), photoList.get(position).getHeight(), photoList.get(position).getWidth(), photoList.get(position).getPhotographer(), photoList.get(position).getPhotographerId(), photoList.get(position).getSrc().getOriginal(), photoList.get(position).getSrc().getMedium(), photoList.get(position).getAlt());
//                    myFavDatabase.getMyFavDao().addToMyFav(mFav);
//                    holder.addToMyFav.setColorFilter(Color.RED);
//                    holder.addToMyFav.setImageResource(R.drawable.heart_filled);
//                    Toast.makeText(context, "Added to Favourite list", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }


}