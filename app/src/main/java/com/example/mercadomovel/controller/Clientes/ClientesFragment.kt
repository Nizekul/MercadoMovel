package com.example.mercadomovel.controller.Clientes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.databinding.FragmentCadastrosClientesBinding
import com.google.android.material.appbar.MaterialToolbar

class ClientesFragment : Fragment() {

    private lateinit var binding: FragmentCadastrosClientesBinding
    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastrosClientesBinding.inflate(inflater, container, false)
        navigation = findNavController()

        binding.cancelarButton.setOnClickListener { navigation.navigateUp() }

        return binding.root
    }


}