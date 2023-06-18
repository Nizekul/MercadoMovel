package com.example.mercadomovel.controller.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.dao.UsuariosDAO
import com.example.mercadomovel.databinding.FragmentLoginCadastroBinding
import com.example.mercadomovel.model.UsuariosViewModel
import com.example.projetinhodeestudo.data.AppDataBase

class LoginCadastroFragment : Fragment() {

    private lateinit var binding: FragmentLoginCadastroBinding
    private lateinit var db: AppDataBase
    private lateinit var navigation: NavController
    private lateinit var usuariosDAO: UsuariosDAO
    private var usuarioID: Int? = null
    private val viewModelUsuarios: UsuariosViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginCadastroBinding.inflate(inflater, container, false)
        db = AppDataBase.getInstance(requireContext())
        usuariosDAO = db.usuarioDAO()
        navigation = findNavController()

        binding.cancelarButton.setOnClickListener { navigation.navigateUp() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelUsuarios.init(usuariosDAO, db, requireContext(), binding)

        arguments?.let {
            usuarioID = it.getInt("usuarioID", -1)
        }

        usuarioID?.let {
            var usuario = usuariosDAO.getPorID(it)

            binding.nomeEditText.setText(usuario?.nome)
            binding.login.setText(usuario?.login)
            binding.senha.setText(usuario?.senha)
        }

        binding.salvarButton.setOnClickListener { CadastrarUsuario(usuarioID) }
    }

    private fun CadastrarUsuario(id: Int?) {
        val nome = binding.nomeEditText.text.toString()
        var login = binding.login.text.toString()
        val senha = binding.senha.text.toString()


        viewModelUsuarios.cadastrarUsuario(id, nome, login, senha){ sucesso, mensagem ->
            if (sucesso) {
                navigation.navigateUp()
            } else {
                showToast(mensagem)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}