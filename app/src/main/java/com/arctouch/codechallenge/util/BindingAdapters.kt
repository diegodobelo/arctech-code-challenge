package com.arctouch.codechallenge.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:poster")
fun setPoster(imageView: ImageView, posterPath: String?) {
    val imageUrl = posterPath?.let { MovieImageUrlBuilder.buildPosterUrl(it) }
    Glide.with(imageView)
        .load(imageUrl)
        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
        .into(imageView)
}

@BindingAdapter("app:backdrop")
fun setBackdrop(imageView: ImageView, backdropPath: String?) {
    val imageUrl = backdropPath?.let { MovieImageUrlBuilder.buildBackdropUrl(it) }
    Glide.with(imageView)
        .load(imageUrl)
        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
        .into(imageView)
}

@BindingAdapter("app:pagedListAdapter")
fun setPagedListAdapter(recyclerView: RecyclerView, pagedListAdapter: PagedListAdapter<*, *>) {
    recyclerView.adapter = pagedListAdapter
}