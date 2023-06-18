package com.example.mercadomovel.controller.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.bo.UsuarioBO
import com.example.mercadomovel.dao.UsuariosDAO
import com.example.mercadomovel.databinding.FragmentLoginBinding
import com.example.projetinhodeestudo.data.AppDataBase


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var db: AppDataBase
    private lateinit var usuarioDAO: UsuariosDAO
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        db = AppDataBase.getInstance(requireContext())

        usuarioDAO = db.usuarioDAO()
        navigation = findNavController()

        criarOuBuscarUsuario("admin")

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnEntrar: Button = binding.btnlogarID

        btnEntrar.setOnClickListener {
            val loginEditText: EditText = binding.loginID
            val senhaEditText: EditText = binding.senhaID

            val login = loginEditText.text.toString()
            val senha = senhaEditText.text.toString()

            if (verificarLogin(login, senha)) {
                // Login válido
                navigation.navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                // Login inválido
                showToast("Usuário ou senha está incorreto!")
            }
        }
    }

    fun criarOuBuscarUsuario(login: String) {
        val usuarioExistente = usuarioDAO.getPorNome(login)
        if (usuarioExistente == null) {
            val novoUsuario = UsuarioBO(
                id = null,
                nome = "admin",
                login = login,
                senha = "admin"
            )
            usuarioDAO.insert(novoUsuario)
        }
    }

    private fun verificarLogin(login: String, senha: String): Boolean {
        var usuario = usuarioDAO.getPorNome(login)
        return usuario?.login == login && senha == usuario?.senha
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}