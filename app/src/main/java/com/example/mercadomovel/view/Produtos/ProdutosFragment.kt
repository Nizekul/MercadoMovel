package com.example.mercadomovel.view.Produtos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.databinding.FragmentCadastrosProdutosBinding
import com.google.android.material.appbar.MaterialToolbar


class ProdutosFragment : Fragment() {

    private lateinit var binding: FragmentCadastrosProdutosBinding
    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastrosProdutosBinding.inflate(inflater, container, false)
        navigation = findNavController()

        binding.cancelarButton.setOnClickListener { navigation.navigateUp() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}