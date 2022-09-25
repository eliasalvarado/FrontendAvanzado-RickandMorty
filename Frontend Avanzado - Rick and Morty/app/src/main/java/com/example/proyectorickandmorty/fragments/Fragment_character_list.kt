package com.example.proyectorickandmorty.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorickandmorty.activities.MainActivity
import com.example.proyectorickandmorty.R
import com.example.proyectorickandmorty.adapters.CharacterAdapter
import com.example.proyectorickandmorty.datasource.api.RetrofitInstance
import com.example.proyectorickandmorty.datasource.model.AllApiResponse
import com.example.proyectorickandmorty.datasource.model.Character
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class fragment_character_list : Fragment(R.layout.fragment_character_list), CharacterAdapter.CharacterListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var characterList: MutableList<Character>
    private lateinit var toolbar: MaterialToolbar
    private lateinit var fragment_login: Fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_characterList)

        toolbar = (activity as MainActivity).getToolBar()

        fragment_login = Fragment_login()

        characterList = ArrayList()

        //getCrypto. No tiene ningun parametro, sería un https://rickandmortyapi.com/api/character
        RetrofitInstance.api.getCrypto().enqueue(object: Callback<AllApiResponse> {
            override fun onResponse(
                call: Call<AllApiResponse>,
                response: Response<AllApiResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    characterList = response.body()!!.characters as MutableList<Character>
                    println(response.body())
                    setupRecycler()
                }
            }

            override fun onFailure(call: Call<AllApiResponse>, t: Throwable) {
                println("Error")
            }

        })

        setupRecycler()
        setListeners()
    }

    private fun setupRecycler() {
        //characterList = RickAndMortyDB.getCharacters()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = CharacterAdapter(characterList, this)

    }

    private fun setListeners() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_az -> {
                    characterList.sortBy { character -> character.name }
                    recyclerView.adapter!!.notifyDataSetChanged()
                    true
                }

                R.id.menu_item_za -> {
                    characterList.sortByDescending { character -> character.name }
                    recyclerView.adapter!!.notifyDataSetChanged()
                    true
                }
                R.id.menu_cerrar_sesion -> {
                    requireView().findNavController().navigate(
                        fragment_character_listDirections.actionFragmentCharacterListToLogin()
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        fragment_login.deleteDataStore()
                    }
                    //eliminarKey()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCharacterClicked(character: Character, position: Int) {
        requireView().findNavController().navigate(
            fragment_character_listDirections.actionFragmentCharacterListToFragmentCharacterDetails(
                character.id
            )
        )
    }

    private suspend fun eliminarKey(){
        //
    }
}