package com.arctouch.codechallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.MovieItemBinding
import com.arctouch.codechallenge.detail.MovieDetailFragment
import com.arctouch.codechallenge.model.Movie
import javax.inject.Inject

class MoviesAdapter @Inject constructor() : PagedListAdapter<Movie, MoviesAdapter.ViewHolder>(MoviesDiffCallback) {

    class ViewHolder(private val itemBinding: MovieItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movie: Movie?) {
            itemBinding.movie = movie
            itemView.setOnClickListener {
                val bundle = bundleOf(MovieDetailFragment.MOVIE_EXTRA to movie)
                itemView.findNavController()
                    .navigate(R.id.goToMovieDetailFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title && oldItem.releaseDate == newItem.releaseDate
        }

    }
}
