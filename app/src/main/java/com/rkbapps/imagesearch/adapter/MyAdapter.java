package com.rkbapps.imagesearch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rkbapps.imagesearch.ImagePreviewActivity;
import com.rkbapps.imagesearch.R;
import com.rkbapps.imagesearch.db.MyFav;
import com.rkbapps.imagesearch.db.MyFavDatabase;
import com.rkbapps.imagesearch.model.ImageModelClass;
import com.rkbapps.imagesearch.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    List<Photo> photoList = new ArrayList<>();


    public MyAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MyFavDatabase myFavDatabase = Room.databaseBuilder(context,MyFavDatabase.class,"myFavDb")
                .allowMainThreadQueries()
                .build();

            Glide.with(context).load(photoList.get(position).getSrc().getMedium()).into(holder.image);
            holder.creatorName.setText(photoList.get(position).getPhotographer());
        Intent i = new Intent(context, ImagePreviewActivity.class);
        i.putExtra("imageInfo",photoList.get(position));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(i);
                }
            });


            if(myFavDatabase.getMyFavDao().isMyFavExist(photoList.get(position).getId())) {
                holder.addToMyFav.setColorFilter(Color.RED);
                holder.addToMyFav.setImageResource(R.drawable.heart_filled);
                i.putExtra("isFav",true);
            }
            holder.addToMyFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myFavDatabase.getMyFavDao().isMyFavExist(photoList.get(position).getId())) {
                        holder.addToMyFav.setImageResource(R.drawable.heart);
                        holder.addToMyFav.setColorFilter(Color.BLACK);
                        myFavDatabase.getMyFavDao().removeMyFav(photoList.get(position).getId());
                        Toast.makeText(context, "Removed from Favourite list", Toast.LENGTH_SHORT).show();
                    }else {
                        MyFav mFav = new MyFav(photoList.get(position).getId(), photoList.get(position).getHeight(), photoList.get(position).getWidth(), photoList.get(position).getPhotographer(), photoList.get(position).getPhotographerId(), photoList.get(position).getSrc().getOriginal(), photoList.get(position).getSrc().getMedium(), photoList.get(position).getAlt());
                        myFavDatabase.getMyFavDao().addToMyFav(mFav);
                        holder.addToMyFav.setColorFilter(Color.RED);
                        holder.addToMyFav.setImageResource(R.drawable.heart_filled);
                        Toast.makeText(context, "Added to Favourite list", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView  image,addToMyFav;
        CircularImageView creatorImage;
        TextView creatorName;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            creatorImage=itemView.findViewById(R.id.imgCreatorImage);
            creatorName=itemView.findViewById(R.id.txtCreatorName);
            cardView=itemView.findViewById(R.id.cardView);
            addToMyFav=itemView.findViewById(R.id.addToMyFav);
        }
    }
}
