package com.example.mercadomovel.view.Produtos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.databinding.FragmentProdutosListBinding
import com.google.android.material.appbar.MaterialToolbar

class ProdutosListFragment : Fragment() {

    private lateinit var binding: FragmentProdutosListBinding
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

        binding = FragmentProdutosListBinding.inflate(inflater, container, false)
        navigation = findNavController()

        binding.btnAdicionarProduto.setOnClickListener { navigation.navigate(R.id.action_produtosListFragment_to_produtosFragment) }

        return binding.root
    }

}