package com.example.proyectorickandmorty.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.proyectorickandmorty.R
import com.example.proyectorickandmorty.entities.Character

class CharacterAdapter(
    private val dataSet: MutableList<Character>,
    private val characterListener: CharacterListener
):
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    interface CharacterListener {
        fun onCharacterClicked(character: Character, position: Int)
    }

    class ViewHolder(private val view: View,
                     private val listener: CharacterListener) : RecyclerView.ViewHolder(view) {
        private val imageType: ImageView = view.findViewById(R.id.image_itemCharacter_category)
        private val textName: TextView = view.findViewById(R.id.text_itemCharacter_name)
        private val textDetails: TextView = view.findViewById(R.id.text_itemCharacter_details)
        private val layout: ConstraintLayout = view.findViewById(R.id.layout_itemCharacter)
        private lateinit var character: Character

        @SuppressLint("SetTextI18n")
        fun setData(character: Character) {
            this.character = character
            imageType.load(character.image){
                placeholder(R.drawable.ic_downloading)
                error(R.drawable.ic_error)
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.DISABLED)
                diskCachePolicy(CachePolicy.DISABLED)
            }
            textName.text = character.name
            textDetails.text = "${character.species} - ${character.status}"
            setListeners()
        }

        private fun setListeners() {
            layout.setOnClickListener {
                listener.onCharacterClicked(character, this.adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_character, parent, false)

        return ViewHolder(view, characterListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

}