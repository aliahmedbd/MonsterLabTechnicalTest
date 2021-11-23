package com.monsterlab.technicaltest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.monsterlab.technicaltest.databinding.ItemImageListBinding
import com.monsterlab.technicaltest.model.ImagesModel

class ImageListAdapter (private val context: Context):
    PagingDataAdapter<ImagesModel, ImageListAdapter.GalleryViewHolder>(Diff()) {

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val image = getItem(position)
        if (image != null) {
            holder.binds(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(
            context,
            ItemImageListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    class GalleryViewHolder(
        private val context: Context,
        private val binding: ItemImageListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(image: ImagesModel) {
            val shimmer =
                Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                    .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                    .setBaseAlpha(0.9f) //the alpha of the underlying children
                    .setHighlightAlpha(0.8f) // the shimmer alpha amount
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .build()

// This is the placeholder for the imageView
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
            binding.apply {
                Glide.with(context)
                    .load(image.download_url)
                    .placeholder(shimmerDrawable)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this.image)
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<ImagesModel>() {
        override fun areItemsTheSame(
            oldItem: ImagesModel,
            newItem: ImagesModel
        ): Boolean =
            oldItem.download_url == newItem.download_url

        override fun areContentsTheSame(
            oldItem: ImagesModel,
            newItem: ImagesModel
        ): Boolean =
            oldItem == newItem
    }
}
