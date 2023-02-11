package com.rkbapps.imagesearch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rkbapps.imagesearch.ImagePreviewActivity;
import com.rkbapps.imagesearch.R;
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
            Glide.with(context).load(photoList.get(position).getSrc().getMedium()).into(holder.image);
            holder.creatorName.setText(photoList.get(position).getPhotographer());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ImagePreviewActivity.class);
                    i.putExtra("link",photoList.get(position).getSrc().getOriginal());
                    context.startActivity(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView  image;
        CircularImageView creatorImage;
        TextView creatorName;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            creatorImage=itemView.findViewById(R.id.imgCreatorImage);
            creatorName=itemView.findViewById(R.id.txtCreatorName);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
