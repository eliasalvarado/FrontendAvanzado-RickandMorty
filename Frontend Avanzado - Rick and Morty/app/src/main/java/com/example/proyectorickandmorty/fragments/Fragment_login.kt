package com.example.proyectorickandmorty.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.proyectorickandmorty.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class Fragment_login : Fragment(R.layout.fragment_login) {
    private lateinit var inputCorreo: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var buttonLogin: Button
    private var instance: Fragment_login? = null

    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin = view.findViewById(R.id.button_iniciar_sesion)
        inputCorreo = view.findViewById(R.id.input_correo)
        inputPassword = view.findViewById(R.id.input_contraseña)

        CoroutineScope(Dispatchers.Main).launch {
            if(getValueFromCorreo(getString(R.string.correo_login)) == "alv21808@uvg.edu.gt")
            {
                requireView().findNavController().navigate(
                    Fragment_loginDirections.actionLoginToFragmentCharacterList()
                )
            }
        }

        setListeners()
    }

    private fun setListeners() {
        buttonLogin.setOnClickListener{
            val correo = inputCorreo.editText!!.text.toString()
            val password = inputPassword.editText!!.text.toString()
            if(correo == password && correo.isNotEmpty() && password.isNotEmpty())
            {
                CoroutineScope(Dispatchers.IO).launch {
                    saveCorreoLogin(
                        correo = correo,
                        password = password
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        inputCorreo.editText!!.text.clear()
                        inputPassword.editText!!.text.clear()
                        requireView().findNavController().navigate(
                            Fragment_loginDirections.actionLoginToFragmentCharacterList()
                        )
                    }
                }
            }
            else
            {
                inputCorreo.editText!!.text.clear()
                inputPassword.editText!!.text.clear()

                Toast.makeText(
                    context,
                    getString(R.string.ingreso_invalido),
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    private suspend fun saveCorreoLogin(correo: String, password: String) {
        val dataStoreKey = stringPreferencesKey(correo)
        context?.dataStore?.edit { settings ->
            settings[dataStoreKey] = password
        }
    }

    private suspend fun getValueFromCorreo(correo: String) : String? {
        val dataStoreKey = stringPreferencesKey(correo)
        val preferences = context?.dataStore?.data?.first()

        return preferences?.get(dataStoreKey) ?: "null"
    }

    public suspend fun deleteDataStore()
    {
        context?.dataStore?.edit { settings ->
            settings.clear()
        }
    }
}