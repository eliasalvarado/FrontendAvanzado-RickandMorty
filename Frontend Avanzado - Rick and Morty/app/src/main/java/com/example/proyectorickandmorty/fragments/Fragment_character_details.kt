package com.example.proyectorickandmorty.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.proyectorickandmorty.R
import com.example.proyectorickandmorty.activities.MainActivity
import com.example.proyectorickandmorty.data.local_source.Database
import com.example.proyectorickandmorty.datasource.api.RetrofitInstance
import com.example.proyectorickandmorty.data.local_source.model.Character
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fragment_character_details : Fragment(R.layout.fragment_character_details) {

    private lateinit var image: ImageView
    private lateinit var inputName: TextInputLayout
    private lateinit var inputSpecie: TextInputLayout
    private lateinit var inputStatus: TextInputLayout
    private lateinit var inputGender: TextInputLayout
    private lateinit var inputOrigin: TextInputLayout
    private lateinit var inputEpisodes: TextInputLayout
    private lateinit var btnGuardar: Button
    private lateinit var character: Character
    private lateinit var toolbar: MaterialToolbar
    private lateinit var database: Database
    private val args:fragment_character_detailsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image = view.findViewById(R.id.image_fragment_details)
        inputName = view.findViewById(R.id.input_characterDetail_name)
        inputSpecie = view.findViewById(R.id.input_characterDetail_Specie)
        inputStatus = view.findViewById(R.id.input_characterDetail_Status)
        inputGender = view.findViewById(R.id.input_characterDetail_Gender)
        inputOrigin = view.findViewById(R.id.input_characterDetail_Origin)
        inputEpisodes = view.findViewById(R.id.input_characterDetail_Episodes)
        btnGuardar = view.findViewById(R.id.btn_characterDetail_Guardar)

        toolbar = (activity as MainActivity).getToolBar()

        database = Room.databaseBuilder(
            requireContext(),
            Database::class.java,
            "dbname"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            character = database.characterDao().getCharacterById(args.id)!!
            setDetails()
        }

        //setDetails()
        setListeners()
    }

    private fun setListeners(){
        btnGuardar.setOnClickListener(){
            updateCharacter()
        }
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_sincronizar -> {
                    cargarApi()
                    true
                }

                else -> false
            }
        }
    }

    private fun updateCharacter() {
        val updatedCharacter = character.copy(
            episode = inputEpisodes.editText!!.text.toString().toInt(),
            gender = inputGender.editText!!.text.toString(),
            name = inputName.editText!!.text.toString(),
            origin = inputOrigin.editText!!.text.toString(),
            species = inputSpecie.editText!!.text.toString(),
            status = inputStatus.editText!!.text.toString()
        )

        CoroutineScope(Dispatchers.IO).launch {
            database.characterDao().update(updatedCharacter)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    requireContext(),
                    "Personaje actualizado exitosamente",
                    Toast.LENGTH_LONG
                ).show()
                requireActivity().onBackPressed()
            }
        }
    }

    private fun cargarApi() {
        //getCharacter. Tiene un parametro (ID), sería un https://rickandmortyapi.com/api/character/1
        RetrofitInstance.api.getCharacter(args.id).enqueue(object : Callback<com.example.proyectorickandmorty.datasource.model.Character> {
            override fun onResponse(call: Call<com.example.proyectorickandmorty.datasource.model.Character>, response: Response<com.example.proyectorickandmorty.datasource.model.Character>) {
                response.body()!!.apply {
                    character = Character(
                        episode = episode.size,
                        gender = gender,
                        id = id,
                        image = image,
                        location = location.name,
                        name = name,
                        origin = origin.name,
                        species = species,
                        status = status,
                        type = type
                    )
                }
                println(response.body())
                setDetails()
            }

            override fun onFailure(call: Call<com.example.proyectorickandmorty.datasource.model.Character>, t: Throwable) {
                println("Error")
            }
        })
        setDetails()
    }

    private fun setDetails() {
        image.load(character.image){
            placeholder(R.drawable.ic_downloading)
            error(R.drawable.ic_error)
            transformations(CircleCropTransformation())
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
        }

        inputName.editText!!.setText(character.name)
        inputSpecie.editText!!.setText(character.species)
        inputStatus.editText!!.setText(character.status)
        inputGender.editText!!.setText(character.gender)
        inputOrigin.editText!!.setText(character.origin)
        inputEpisodes.editText!!.setText(character.episode.toString())
    }

}