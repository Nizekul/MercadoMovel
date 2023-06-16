package com.example.mercadomovel.model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mercadomovel.bo.ProdutoBO
import com.example.mercadomovel.databinding.FragmentCadastrosProdutosBinding
import com.example.mercadomovel.databinding.FragmentProdutosListBinding
import com.example.projetinhodeestudo.data.AppDataBase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ProdutosViewModel : ViewModel() {

    private lateinit var produtoDAO: ProdutosDAO
    private lateinit var db: AppDataBase
    private lateinit var context: Context
    private lateinit var binding: FragmentCadastrosProdutosBinding
//    private lateinit var bindingList: FragmentProdutosListBinding

    fun init(produtoDAO: ProdutosDAO, appDataBase: AppDataBase, context: Context, binding: FragmentCadastrosProdutosBinding) {
        this.produtoDAO = produtoDAO
        this.db = appDataBase
        this.context = context
        this.binding = binding
    }

//    fun initList(produtoDAO: ProdutosDAO, appDataBase: AppDataBase, context: Context, binding: FragmentProdutosListBinding) {
//        this.produtoDAO = produtoDAO
//        this.db = appDataBase
//        this.context = context
//        this.bindingList = binding
//    }

    fun cadastrarProduto(id: Int?, nome: String, descricao: String?, valor: Double, validade: String?, quantidade: Int?, callback: (Boolean, String) -> Unit) {
        if (nome.isBlank()) {
            callback(false, "Nome precisa ser preenchido!")
            return
        } else if (valor == 0.0) {
            callback(false, "O valor precisa ser preenchido!")
            return
        }

        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        var dataValidade: Date? = null

        if (validade != null && validade.isNotBlank()) {
            try {
                val validadeFormatted = validade.replace("/", "-")
                val partes = validadeFormatted.split("-")
                if (partes.size >= 2) {
                    val mes = partes[1].toIntOrNull()
                    if (mes != null && mes > 12) {
                        callback(false, "Insira um mês válido (até 12)")
                        return
                    }
                }
                dataValidade = dateFormat.parse(validadeFormatted)

            } catch (e: ParseException) {
                callback(false, "Validade precisa ser preenchida no formato de Dia-Mês-Ano!")
                return
            }
        }

        val produto = ProdutoBO(id, nome, descricao, valor, dataValidade, quantidade)
        id?.let {
            produto.id = id
            produtoDAO.update(produto)
        } ?: run {
            produtoDAO.insert(produto)
        }

        callback(true, "Produto cadastrado com sucesso!")
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}

