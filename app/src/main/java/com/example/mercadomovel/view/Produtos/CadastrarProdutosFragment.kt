package com.example.mercadomovel.view.Produtos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.databinding.FragmentCadastrosClientesBinding
import com.google.android.material.appbar.MaterialToolbar


class CadastrarProdutosFragment : Fragment() {

    private lateinit var binding: FragmentCadastrosClientesBinding
    private lateinit var navigation: NavController
    private lateinit var menu: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastrosClientesBinding.inflate(inflater, container, false)
        navigation = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding
    }
}