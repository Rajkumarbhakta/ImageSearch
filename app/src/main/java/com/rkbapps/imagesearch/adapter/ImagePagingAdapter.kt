package com.rkbapps.imagesearch.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rkbapps.imagesearch.databinding.RecyclerViewItemBinding
import com.rkbapps.imagesearch.model.Photo

class ImagePagingAdapter :
    PagingDataAdapter<Photo, ImagePagingAdapter.ImageViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("Result",item.toString())
        if (item != null) {
            holder.binding.photo = item
        }
    }

    class ImageViewHolder(itemView: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }


    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }


}