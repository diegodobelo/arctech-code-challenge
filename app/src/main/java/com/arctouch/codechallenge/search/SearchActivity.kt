package com.arctouch.codechallenge.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.arctouch.codechallenge.R
import kotlinx.android.synthetic.main.home_activity.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfig  = AppBarConfiguration.Builder()
            .setFallbackOnNavigateUpListener {
                this.onBackPressed()
                true
            }
            .build();
        toolbar.setupWithNavController(navController, appBarConfig)
        supportActionBar?.title = navController.currentDestination?.label
    }
}
