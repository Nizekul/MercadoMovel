package com.example.mercadomovel.controller.Login

object LoginUtils {

    fun validarLogin(login: String, senha: String): String? {

        if (login.isEmpty()) return "Login precisa ser preenchido"
        if (login.length < 4) return "Digite um login maior que 4 digitos!"

        if(senha.isEmpty()) return "Senha precisa ser preenchida"
        if(senha.length<6)return "Digite uma senha com mais de 6 caracteres"
        return null
    }

}