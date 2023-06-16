package com.example.mercadomovel.controller.Produtos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.bo.ProdutoBO
import com.example.mercadomovel.databinding.FragmentCadastrosProdutosBinding
import com.example.mercadomovel.model.ClientesViewModel
import com.example.mercadomovel.model.ProdutosViewModel
import com.example.mercadomovel.model.converterParaDouble
import com.example.mercadomovel.model.formatarData
import com.example.mercadomovel.model.formatarParaDinheiro
import com.example.projetinhodeestudo.data.AppDataBase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ProdutosFragment : Fragment() {

    private lateinit var binding: FragmentCadastrosProdutosBinding
    private lateinit var navigation: NavController
    private lateinit var produtoDAO: ProdutosDAO
    private lateinit var db: AppDataBase
    private var produtoID: Int? = null
    private val viewModelProduto: ProdutosViewModel by viewModels()

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

        binding.valorEditText.formatarParaDinheiro()
        binding.dataVencimentoEditText.formatarData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModelProduto.init(produtoDAO, db, requireContext(), binding)

        arguments?.let {
            produtoID = it.getInt("produtoID", -1)
        }

        produtoID?.let {
            var produto = produtoDAO.getPorID(it)

            binding.nomeEditText.setText(produto?.nome)
            binding.descricaoEditText.setText(produto?.descricao)
            binding.valorEditText.setText(produto?.valor.toString())
            binding.valorEditText.formatarParaDinheiro()
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formatedDate = produto?.dtVencimento?.let { dateFormat.format(it) } ?: ""
            binding.dataVencimentoEditText.setText(formatedDate)
            binding.quantidadeEditText.setText(produto?.quantidade.toString())
        }

        binding.salvarButton.setOnClickListener { CadastrarProduto(produtoID) }
    }

    private fun CadastrarProduto(id: Int?) {
        val nome = binding.nomeEditText.text.toString()
        var descricao: String? = binding.descricaoEditText.text.toString()
        val valorEditTExt = binding.valorEditText.text.toString()
        var validade: String? = binding.dataVencimentoEditText.text.toString()
        var qtd: String? = binding.quantidadeEditText.text.toString()

        val valor = valorEditTExt.converterParaDouble()

        var qtdProduto = qtd?.toIntOrNull()

        viewModelProduto.cadastrarProduto(id, nome, descricao, valor, validade, qtdProduto){ sucesso, mensagem ->
            if (sucesso) {
                navigation.navigateUp()
            } else {
                showToast(mensagem)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext().applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}