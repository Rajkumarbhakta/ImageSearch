package com.rkbapps.imagesearch.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rkbapps.imagesearch.R;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    String[] topics;

    public TopicAdapter(String[] topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public TopicAdapter.TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_single_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TopicAdapter.TopicViewHolder holder, int position) {
        holder.txtTopic.setText("" + topics[position]);
    }

    @Override
    public int getItemCount() {
        return topics.length;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView txtTopic;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTopic = itemView.findViewById(R.id.textViewTopic);
        }
    }
}
