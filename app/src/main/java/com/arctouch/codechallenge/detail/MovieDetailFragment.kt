package com.arctouch.codechallenge.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.FragmentMovieDetailBinding
import com.arctouch.codechallenge.model.Movie

class MovieDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentMovieDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        val movie = arguments?.get(MOVIE_EXTRA) as Movie
        binding.movie = movie
        return binding.root
    }

    companion object {
        const val MOVIE_EXTRA = "movie"
    }
}
