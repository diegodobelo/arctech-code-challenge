package com.arctouch.codechallenge.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arctouch.codechallenge.MoviesApplication

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.FragmentSearchBinding
import javax.inject.Inject

class SearchFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var searchQuery : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SearchViewModel::class.java)
        binding.viewModel = viewModel
        searchQuery?.let {
            viewModel.performSearch(it)
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MoviesApplication).appComponent.inject(this)
        if (Intent.ACTION_SEARCH == activity?.intent?.action) {
            activity?.intent?.getStringExtra(SearchManager.QUERY)?.also { query ->
                searchQuery = query
            }
        }
    }
}
