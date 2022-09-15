package com.example.proyectorickandmorty.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.proyectorickandmorty.R
import com.example.proyectorickandmorty.datasource.api.RetrofitInstance
import com.example.proyectorickandmorty.datasource.model.Character
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class fragment_character_details : Fragment(R.layout.fragment_character_details) {

    private lateinit var image: ImageView
    private lateinit var textName: TextView
    private lateinit var textSpecie: TextView
    private lateinit var textStatus: TextView
    private lateinit var textGender: TextView
    private lateinit var textOrigin: TextView
    private lateinit var textEpisodes: TextView
    private lateinit var character: Character
    private val args:fragment_character_detailsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image = view.findViewById(R.id.image_fragment_details)
        textName = view.findViewById(R.id.text_characterDetails_name)
        textSpecie = view.findViewById(R.id.text_characterDetails_specie)
        textStatus = view.findViewById(R.id.text_characterDetails_status)
        textGender = view.findViewById(R.id.text_characterDetails_gender)
        textOrigin = view.findViewById(R.id.text_characterDetails_origin)
        textEpisodes = view.findViewById(R.id.text_characterDetails_episodes)

        ///getCharacter. Tiene un parametro (ID), sería un https://rickandmortyapi.com/api/character/1
        RetrofitInstance.api.getCharacter(args.id).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                character = response.body()!!
                println(response.body())
                setDetails()
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                println("Error")
            }

        })

/*
        image.load(args.character.image){
            placeholder(R.drawable.ic_downloading)
            error(R.drawable.ic_error)
            transformations(CircleCropTransformation())
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
        }
        textName.text = args.character.name.toString()
        textSpecie.text = args.character.species.toString()
        textStatus.text = args.character.status.toString()
        textGender.text = args.character.gender.toString()

 */
    }

    private fun setDetails() {
        image.load(character.image){
            placeholder(R.drawable.ic_downloading)
            error(R.drawable.ic_error)
            transformations(CircleCropTransformation())
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
        }
        textName.text = character.name.toString()
        textSpecie.text = character.species.toString()
        textStatus.text = character.status.toString()
        textGender.text = character.gender.toString()
        textOrigin.text = character.origin.name.toString()
        textEpisodes.text = character.episode.size.toString()
    }

}