package com.example.proyectorickandmorty.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.proyectorickandmorty.activities.MainActivity
import com.example.proyectorickandmorty.R
import com.example.proyectorickandmorty.adapters.CharacterAdapter
import com.example.proyectorickandmorty.data.local_source.Database
import com.example.proyectorickandmorty.datasource.api.RetrofitInstance
import com.example.proyectorickandmorty.datasource.model.AllApiResponse
import com.example.proyectorickandmorty.data.local_source.model.Character
import com.example.proyectorickandmorty.ui.character_list.login.Fragment_login
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
    private lateinit var database: Database

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_characterList)

        toolbar = (activity as MainActivity).getToolBar()

        fragment_login = Fragment_login()

        characterList = ArrayList()

        database = Room.databaseBuilder(
            requireContext(),
            Database::class.java,
            "dbname"
        ).build()

        getUsers()
        setListeners()
    }

    private fun cargarDatosInternet() {
        characterList.clear()
        //getCrypto. No tiene ningun parametro, sería un https://rickandmortyapi.com/api/character
        RetrofitInstance.api.getCrypto().enqueue(object: Callback<AllApiResponse> {
            override fun onResponse(
                call: Call<AllApiResponse>,
                response: Response<AllApiResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    var characterListAPI = response.body()!!.characters as MutableList<com.example.proyectorickandmorty.datasource.model.Character>
                    agregarAPIaLaLista(characterListAPI)
                    println(response.body())
                    setupRecycler()
                }
            }

            override fun onFailure(call: Call<AllApiResponse>, t: Throwable) {
                println("Error")
            }

        })
    }

    private fun agregarAPIaLaLista(listaAPI: MutableList<com.example.proyectorickandmorty.datasource.model.Character>){
        for(character in listaAPI){
            characterList.add(
                Character(
                    episode = character.episode.size,
                    gender = character.gender,
                    id = character.id,
                    image = character.image,
                    location = character.location.name,
                    name = character.name,
                    origin = character.origin.name,
                    species = character.species,
                    status = character.status,
                    type = character.type
                )
            )
        }
        for(character in characterList){
            CoroutineScope(Dispatchers.IO).launch {
                database.characterDao().insert(character)
            }
        }
    }

    private fun setupRecycler() {
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

                R.id.menu_sincronizar -> {
                    cargarDatosInternet()
                    true
                }

                R.id.menu_cerrar_sesion -> {
                    requireView().findNavController().navigate(
                        fragment_character_listDirections.actionFragmentCharacterListToLogin()
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        fragment_login.deleteDataStore(requireContext())
                        database.characterDao().deleteAll()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun getUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val characters = database.characterDao().getCharacters()
            if(characters.isEmpty()){
                cargarDatosInternet()
            }
            else {
                characterList.clear()
                characterList.addAll(characters)
                CoroutineScope(Dispatchers.Main).launch {
                    setupRecycler()
                }
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