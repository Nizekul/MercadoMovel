package com.example.mercadomovel.model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mercadomovel.bo.UsuarioBO
import com.example.mercadomovel.controller.Login.LoginUtils
import com.example.mercadomovel.dao.UsuariosDAO
import com.example.mercadomovel.databinding.FragmentLoginCadastroBinding
import com.example.projetinhodeestudo.data.AppDataBase


class UsuariosViewModel: ViewModel() {

    private lateinit var usuariosDAO: UsuariosDAO
    private lateinit var db: AppDataBase
    private lateinit var context: Context
    private lateinit var binding: FragmentLoginCadastroBinding

    fun init(usuariosDAO: UsuariosDAO, appDataBase: AppDataBase, context: Context, binding: FragmentLoginCadastroBinding) {
        this.usuariosDAO = usuariosDAO
        this.db = appDataBase
        this.context = context
        this.binding = binding
    }

    fun cadastrarUsuario(id: Int?, nome: String, login: String, senha: String, callback: (Boolean, String) -> Unit) {
        var validarLogin = LoginUtils.validarLogin(login, senha)
        if (validarLogin != null){
            callback(false, validarLogin)
            return
        }

        val usuarioExistente = usuariosDAO.getPorNome(login)
        if (usuarioExistente != null) {
            callback(false, "Este Login já existe no sistema!")
            return
        }

        val usuario = UsuarioBO(id, if (nome.isBlank()) null else nome, login, senha)
        id?.let {
            usuario.id = id
            usuariosDAO.update(usuario)
        } ?: run {
            usuariosDAO.insert(usuario)
        }

        callback(true, "Usuário cadastrado com sucesso!")
    }

}