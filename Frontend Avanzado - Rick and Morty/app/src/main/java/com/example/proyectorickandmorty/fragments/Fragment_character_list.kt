package com.example.proyectorickandmorty.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class fragment_character_list : Fragment(R.layout.fragment_character_list), CharacterAdapter.CharacterListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var characterList: MutableList<Character>
    private lateinit var toolbar: MaterialToolbar
    private lateinit var character: Character

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_characterList)

        toolbar = (activity as MainActivity).getToolBar()

        setupRecycler()
        setListeners()
    }

    private fun setupRecycler() {

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
                }
            }

            override fun onFailure(call: Call<AllApiResponse>, t: Throwable) {
                println("Error")
            }

        })


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
}