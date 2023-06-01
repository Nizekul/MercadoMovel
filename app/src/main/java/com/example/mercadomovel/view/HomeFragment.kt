package com.example.mercadomovel.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.databinding.FragmentHomeBinding
import com.google.android.material.appbar.MaterialToolbar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
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
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navigation = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menu = requireActivity().findViewById<MaterialToolbar>(R.id.menu)

        menu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeIconID -> {
                    navigation.navigate(R.id.homeFragment)
                    true
                }
                R.id.vendasIconID -> {
                    navigation.navigate(R.id.homeFragment)
                    true
                }
                R.id.produtosIconID -> {
                    navigation.navigate(R.id.action_homeFragment_to_produtosListFragment)
                    true
                }
                R.id.clienteIconID -> {
                    navigation.navigate(R.id.action_homeFragment_to_clientesListFragment)
                    true
                }

                else -> {
                    false
                }
            }
        }

    }
}