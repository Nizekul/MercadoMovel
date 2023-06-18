package com.example.mercadomovel.controller.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.dao.UsuariosDAO
import com.example.mercadomovel.databinding.FragmentLoginListBinding
import com.example.projetinhodeestudo.data.AppDataBase

class LoginListFragment : Fragment() {

    private lateinit var binding: FragmentLoginListBinding
    private lateinit var db: AppDataBase
    private lateinit var naviation: NavController
    private lateinit var usuariosDAO: UsuariosDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginListBinding.inflate(inflater, container, false)
        db = AppDataBase.getInstance(requireContext())
        usuariosDAO = db.usuarioDAO()
        naviation = findNavController()

        binding.btnAdicionarProduto.setOnClickListener {
            naviation.navigate(R.id.action_loginListFragment_to_loginCadastroFragment)
        }

        return binding.root
    }
}