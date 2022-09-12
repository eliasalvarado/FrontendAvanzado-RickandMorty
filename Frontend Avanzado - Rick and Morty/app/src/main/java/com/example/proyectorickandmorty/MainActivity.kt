package com.example.proyectorickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private  lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostController = supportFragmentManager.findFragmentById(
            R.id.fragmentContainer_basicToolbarActivity
        ) as NavHostFragment

        navController = navHostController.navController


        val appbarConfig = AppBarConfiguration(navController.graph)

        toolbar = findViewById(R.id.toolbar_basicToolbarActivity)
        toolbar.setupWithNavController(navController, appbarConfig)

        setListeners()
        setNavigationChange()
    }

    private fun setListeners() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_az -> {
                    //TODO
                    true
                }

                R.id.menu_item_za -> {
                    //TODO
                    true
                }
                else -> false
            }
        }
    }

    public fun getToolBar(): MaterialToolbar {
        return toolbar
    }

    private fun setNavigationChange() {
        navController.addOnDestinationChangedListener {_, destination, _ ->
            when(destination.id) {
                R.id.fragment_character_list -> {
                    toolbar.title = "Characters"
                    toolbar.menu.findItem(R.id.menu_item_az).isVisible = true
                    toolbar.menu.findItem(R.id.menu_item_za).isVisible = true
                }

                R.id.fragment_character_details -> {
                    toolbar.title = "Character Detail"
                    toolbar.menu.findItem(R.id.menu_item_az).isVisible = false
                    toolbar.menu.findItem(R.id.menu_item_za).isVisible = false
                }
            }
        }
    }
}