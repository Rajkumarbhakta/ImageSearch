package com.rkbapps.imagesearch.adapter;

import android.content.Context;
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
import com.rkbapps.imagesearch.R;
import com.rkbapps.imagesearch.db.MyFav;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterFav extends RecyclerView.Adapter<MyAdapterFav.MyAdapterViewHolder> {
    
    Context context;
    List<MyFav> myFavList = new ArrayList<>();

    public MyAdapterFav(Context context, List<MyFav> myFavList) {
        this.context = context;
        this.myFavList = myFavList;
    }

    @NonNull
    @Override
    public MyAdapterFav.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterFav.MyAdapterViewHolder holder, int position) {

        Glide.with(context).load(myFavList.get(position).getMediumImageLink()).into(holder.image);
        holder.creatorName.setText(myFavList.get(position).getPhotographer());
        holder.addToMyFav.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return myFavList.size();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView image,addToMyFav;
        CircularImageView creatorImage;
        TextView creatorName;
        CardView cardView;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            creatorImage=itemView.findViewById(R.id.imgCreatorImage);
            creatorName=itemView.findViewById(R.id.txtCreatorName);
            cardView=itemView.findViewById(R.id.cardView);
            addToMyFav=itemView.findViewById(R.id.addToMyFav);
        }
    }
}
