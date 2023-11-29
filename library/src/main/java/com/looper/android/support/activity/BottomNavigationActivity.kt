package com.looper.android.support.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.content.res.Configuration

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.fragment.NavHostFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.appbar.AppBarLayout

import com.looper.android.support.activity.BaseActivity
import com.looper.android.support.R

open class BottomNavigationActivity : BaseActivity() {

    protected lateinit var navController: NavController
    protected lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)    
    
        // Set content view.
        setContentView(getContentView())
                
        // Initialize and set up the toolbar as the action bar.
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Find the navigation controller.        
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_content_main) as NavHostFragment  
        navController = navHostFragment.navController
                                                    
        // Get the view of bottom navigation.
        navView = findViewById(R.id.nav_view) 
    }

    protected fun setupNavigation(navGraphId: Int, menuId: Int) {
        // Inflate the navigation graph.    
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(navGraphId)
        
        // Set the inflated navigation graph as the graph for the navigation controller.
        navController.graph = navGraph
        
        // Inflate the menu for the bottom navigation view.
        navView.inflateMenu(menuId)
        
        // Set up bottom navigation with the navigation controller.
        navView.setupWithNavController(navController)
    }
    
    override fun getContentView(): Int {
        return R.layout.bottom_navigation_activity
    }
}