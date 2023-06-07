package com.example.mercadomovel.controller.Produtos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.databinding.FragmentCadastrosProdutosBinding
import com.example.mercadomovel.model.ProdutoModel
import com.example.projetinhodeestudo.data.AppDataBase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import javax.crypto.NullCipher


class ProdutosFragment : Fragment() {

    private lateinit var binding: FragmentCadastrosProdutosBinding
    private lateinit var navigation: NavController
    private lateinit var produtoDAO: ProdutosDAO
    private lateinit var db: AppDataBase
    private var produtoID: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastrosProdutosBinding.inflate(inflater, container, false)
        navigation = findNavController()
        db = AppDataBase.getInstance(requireContext().applicationContext)

        produtoDAO = db.produtoDAO()

        binding.cancelarButton.setOnClickListener { navigation.navigateUp() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            produtoID = it.getInt("notaID", -1)
        }

        produtoID?.let {
            var nota = produtoDAO.getPorID(it)

            binding.nomeEditText.setText(nota?.nome)
            binding.descricaoEditText.setText(nota?.descricao)
            binding.valorEditText.setText(nota?.valor.toString())
            binding.dataVencimentoEditText.setText(nota?.dtVencimento.toString())
            binding.quantidadeEditText.setText(nota?.quantidade.toString())
        }

        binding.salvarButton.setOnClickListener { CadastrarProduto(produtoID) }
    }

    private fun CadastrarProduto(id: Int?) {
        val nome = binding.nomeEditText.text.toString()
        var descricao: String? = binding.descricaoEditText.text.toString()
        val valor = binding.valorEditText.text.toString()
        var validade: String? = binding.dataVencimentoEditText.text.toString()
        var qtd: String? = binding.quantidadeEditText.text.toString()
        val data = Date()

        if (nome.isNullOrBlank()) {
            showToast("Nome precisa ser preenchido!")
        } else if (valor.isNullOrBlank()) {
            showToast("O valor precisa ser preenchido!")
        } else {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            if (validade.isNullOrBlank())
                validade = null
            else{
                data.time = dateFormat.parse(validade).time
            }

            if (descricao.isNullOrBlank())
                descricao = null

            var qtdProduto = qtd?.toIntOrNull()

            var produto = ProdutoModel(null, nome, descricao, valor.toDouble(), data, qtdProduto)

            id?.let {
                produtoDAO.update(produto)
            } ?: run {
                produtoDAO.insert(produto)
            }

            navigation.navigateUp()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext().applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}