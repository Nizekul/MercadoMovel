package com.example.mercadomovel.model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mercadomovel.bo.ClienteBO
import com.example.mercadomovel.dao.ClientesDAO
import com.example.projetinhodeestudo.data.AppDataBase

class ClientesViewModel : ViewModel() {

    private lateinit var clienteDAO: ClientesDAO
    private lateinit var db: AppDataBase
    private lateinit var context: Context

    fun init(clientesDAO: ClientesDAO, appDataBase: AppDataBase, context: Context) {
        clienteDAO = clientesDAO
        db = appDataBase
        this.context = context
    }

    fun getClienteByID(clienteID: Int): ClienteBO? {
        return clienteDAO.getPorID(clienteID)
    }

    fun cadastrarCliente(id: Int?, nome: String, nomeEmpresa: String, endereco: String?, telefone: String?, email: String?, cnpj: String?): Boolean {
        if (nome.isBlank()) {
            showToast("Nome do cliente precisa ser preenchido!")
            return false
        } else if (nomeEmpresa.isBlank()) {
            showToast("Nome do comércio precisa ser preenchido!")
            return false
        }

        val cliente = ClienteBO(id, nome, endereco, telefone, nomeEmpresa, email, cnpj)

        id?.let {
            cliente.id = id
            clienteDAO.update(cliente)
        } ?: run {
            clienteDAO.insert(cliente)
        }

        showToast("Cliente cadastrado com sucesso!")
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}