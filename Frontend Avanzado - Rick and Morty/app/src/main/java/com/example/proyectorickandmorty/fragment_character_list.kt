package com.example.proyectorickandmorty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorickandmorty.adapters.CharacterAdapter
import com.example.proyectorickandmorty.database.RickAndMortyDB
import com.example.proyectorickandmorty.entities.Character
import com.google.android.material.appbar.MaterialToolbar


class fragment_character_list : Fragment(R.layout.fragment_character_list), CharacterAdapter.CharacterListener {

    private  lateinit var recyclerView: RecyclerView
    private  lateinit var  characterList: MutableList<Character>
    private lateinit var toolbar: MaterialToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_characterList)

        toolbar = (activity as MainActivity).getToolBar()

        setupRecycler()
        setListeners()
    }

    private fun setupRecycler() {
        characterList = RickAndMortyDB.getCharacters()
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
                character
            )
        )
    }
}