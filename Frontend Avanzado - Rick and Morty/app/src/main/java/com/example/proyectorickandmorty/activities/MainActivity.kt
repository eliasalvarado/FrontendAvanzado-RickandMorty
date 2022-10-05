package com.example.proyectorickandmorty.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.proyectorickandmorty.R
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private  lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostController = supportFragmentManager.findFragmentById(
            R.id.fragmentContainer_basicToolbarActivity
        ) as NavHostFragment

        navController = navHostController.navController


        //val appbarConfig = AppBarConfiguration(navController.graph)
        val appbarConfig = AppBarConfiguration(setOf(R.id.fragment_character_list))

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setNavigationChange() {
        navController.addOnDestinationChangedListener {_, destination, _ ->
            when(destination.id) {
                R.id.login -> {
                    toolbar.visibility = View.GONE
                }
                R.id.fragment_character_list -> {
                    toolbar.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.setDisplayShowHomeEnabled(true)
                    //toolbar.navigationIcon = null
                    toolbar.title = "Characters"
                    toolbar.menu.findItem(R.id.menu_item_az).isVisible = true
                    toolbar.menu.findItem(R.id.menu_item_za).isVisible = true
                    toolbar.menu.findItem(R.id.menu_cerrar_sesion).isVisible = true
                    toolbar.menu.findItem(R.id.menu_sincronizar).isVisible = true
                    toolbar.menu.findItem(R.id.menu_sincronizar).icon.setTint(getColor(R.color.black))
                    toolbar.menu.findItem(R.id.menu_eliminar).isVisible = false
                }

                R.id.fragment_character_details -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.title = "Character Detail"
                    toolbar.menu.findItem(R.id.menu_item_az).isVisible = false
                    toolbar.menu.findItem(R.id.menu_item_za).isVisible = false
                    toolbar.menu.findItem(R.id.menu_cerrar_sesion).isVisible = false
                    toolbar.menu.findItem(R.id.menu_sincronizar).isVisible = true
                    toolbar.menu.findItem(R.id.menu_sincronizar).icon.setTint(getColor(R.color.topbar_menu_sincronizar))
                    toolbar.menu.findItem(R.id.menu_eliminar).isVisible = true
                }
            }
        }
    }
}