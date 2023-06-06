package com.example.mercadomovel.view.Clientes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.databinding.FragmentClientesListBinding

class ClientesListFragment : Fragment() {

    private lateinit var binding: FragmentClientesListBinding
    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentClientesListBinding.inflate(inflater, container, false)
        navigation = findNavController()

        binding.btnAdicionar.setOnClickListener { navigation.navigate(R.id.action_clientesListFragment_to_clientesFragment) }

        return binding.root
    }
}