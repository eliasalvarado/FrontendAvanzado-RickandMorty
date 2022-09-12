package com.example.proyectorickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.google.android.material.appbar.MaterialToolbar


class fragment_character_details : Fragment(R.layout.fragment_character_details) {

    private lateinit var image: ImageView
    private lateinit var textName: TextView
    private lateinit var textSpecie: TextView
    private lateinit var textStatus: TextView
    private lateinit var textGender: TextView
    private lateinit var toolbar: MaterialToolbar
    private val args:fragment_character_detailsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        image = view.findViewById(R.id.image_fragment_details)
        textName = view.findViewById(R.id.text_characterDetails_name)
        textSpecie = view.findViewById(R.id.text_characterDetails_specie)
        textStatus = view.findViewById(R.id.text_characterDetails_status)
        textGender = view.findViewById(R.id.text_characterDetails_gender)

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
    }

}