package com.example.mercadomovel.controller.Login

import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginCadastroFragmentTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login_vazio() {
        val username = ""
        val assert = LoginUtils.validarLogin(username, "dflja@3")
        assert(assert == "dgite um login")
    }


    @Test
    fun nome_menor_que_4() {
        val username = "djl"
        assert(
            LoginUtils.validarLogin(username, "dflja@3")
                    == "nome muito curto"
        )
    }

    @Test
    fun nome_maior_que_20(){
        val username="fdlksakjfklafjkjfklkaljfklajfkajfkjasldkjfajlksfjlakjfakl"
        assert(
            LoginUtils.validarLogin(username, "dflja@3")
                    == "nome maior que 20 caracteres"
        )
    }
}