package com.arctouch.codechallenge.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arctouch.codechallenge.MoviesApplication

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MoviesApplication).appComponent.inject(this)
    }
}
