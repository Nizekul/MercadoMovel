package com.example.mercadomovel.controller.Home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadomovel.R
import com.example.mercadomovel.bo.ProdutoBO
import com.example.mercadomovel.controller.Produtos.ProdutosListFragment
import com.example.mercadomovel.dao.ClientesDAO
import com.example.mercadomovel.databinding.FragmentHomeBinding
import com.example.mercadomovel.model.Util
import com.example.mercadomovel.model.formatarParaDinheiro
import com.example.projetinhodeestudo.data.AppDataBase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO
import com.google.android.material.appbar.MaterialToolbar
import com.example.mercadomovel.model.formatarParaDinheiro

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navigation: NavController
    private lateinit var db: AppDataBase
    private lateinit var produtoDAO: ProdutosDAO
    private lateinit var clienteDAO: ClientesDAO
    lateinit var produtoListView: ListView
    lateinit var qtdProdutosListView: ListView
    lateinit var valorProdutosListView: ListView
    data class Produto(val nome: String, val id: Int, val valor: Double)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navigation = findNavController()
        db = AppDataBase.getInstance(requireContext().applicationContext)
        produtoDAO = db.produtoDAO()
        clienteDAO = db.clientesDAO()
        produtoListView = binding.nomeProdutoListView
        qtdProdutosListView = binding.qtdProdutosListView
        valorProdutosListView = binding.valorListView

        preencherCliente()
        preencherProduto()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menu = requireActivity().findViewById<MaterialToolbar>(R.id.menu)
        menu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeIconID -> {
                    navigation.navigate(R.id.homeFragment)
                    true
                }

                R.id.vendasIconID -> {
                    navigation.navigate(R.id.homeFragment)
                    true
                }

                R.id.produtosIconID -> {
                    navigation.navigate(R.id.produtosListFragment)
                    true
                }

                R.id.clienteIconID -> {
                    navigation.navigate(R.id.clientesListFragment)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun preencherCliente() {
        val clientesList = clienteDAO.getTodas()
        var nomeClientes = mutableListOf<String>()
        var enderecoCliente = mutableListOf<String>()
        var cnpj = mutableListOf<String>()
        var telefone = mutableListOf<String>()
        var nomeEmpresaENome = ""
        val spinner = binding.clientSpinner

        clientesList?.let { clienteList ->
            for (cliente in clienteList) {
                nomeEmpresaENome = "${cliente.NomeEmpresa} - ${cliente.nome}"
                nomeClientes.add(nomeEmpresaENome)

                cliente.telefone?.let { telefone.add(it) } ?: telefone.add("")
                cliente.endereco?.let { enderecoCliente.add(it) } ?: enderecoCliente.add("")
                cliente.cnpj?.let { cnpj.add(it) } ?: cnpj.add("")
            }
        }

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nomeClientes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Atualizar os campos de telefone, endereço e CNPJ com base na seleção do Spinner
                var telFormatada = "Telefone: " + telefone[position]
                var endFormatada = "Endereço: " + enderecoCliente[position]
                var cnpjFormatada = "CNPJ/CPF: " + cnpj[position]

                binding.telefonetextID.setText(telFormatada)
                binding.enderecotextID.setText(endFormatada)
                binding.cnpjtextID.setText(cnpjFormatada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun preencherProduto() {
        val produtoList = produtoDAO.getTodas()
        val itemEdt = binding.nomeProdutoaddID
        val qtdEdt = binding.qtdProdutoaddID
        var produtoNome = mutableListOf<String>()
        var produtoID = mutableListOf<Int>()
        var valorUnidade = mutableListOf<Double>()
        val produtosSelecionados = mutableListOf<String>()
        val quantidadesSelecionadas = ArrayList<String>()
        val valorXproduto = ArrayList<String>()
        var textTotal = binding.valorTotalTextViewID.text.toString()
        var total = Util.converterParaDouble(textTotal)

        if (produtoList != null) {
            for (produto in produtoList) {
                produto.id?.let {
                    Produto(produto.nome, it, produto.valor)
                }
            }
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            produtoList?.map { "${it.nome}|${it.id}|${it.valor}" } ?: emptyList()
        )
        val context = activity?.baseContext as Context

        itemEdt.setAdapter(adapter)
        itemEdt.threshold = 1

        var produtoSelecionado: String? = null

        itemEdt.setOnItemClickListener { parent, view, position, _ ->
            produtoSelecionado = adapter.getItem(position)
            val nome = produtoSelecionado!!.substringBefore("|")
            itemEdt.setText(nome)
            itemEdt.clearFocus()
        }

        val addButton = binding.btnAdicionar
        addButton.setOnClickListener {
            val quantidade = qtdEdt.text.toString()

            if (produtoSelecionado != null && quantidade.isNotEmpty()) {
                val nome = produtoSelecionado!!.substringBefore("|")
                val valorUnidade = produtoSelecionado!!.substringAfterLast("|").toDouble()

                produtosSelecionados.add(nome)
                quantidadesSelecionadas.add(quantidade)

                var valorSomaProduto = quantidade.toInt() * valorUnidade
                valorXproduto.add(Util.formatarParaDinheiro(valorSomaProduto.toString()))
                total += quantidade.toInt() * valorUnidade

                val adapterNome: ArrayAdapter<String> = ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    produtosSelecionados
                )
                val adapterQtd: ArrayAdapter<String> = ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    quantidadesSelecionadas
                )
                val adapterValorXQuantidade: ArrayAdapter<String> = ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    valorXproduto
                )

                produtoListView.adapter = adapterNome
                qtdProdutosListView.adapter = adapterQtd
                valorProdutosListView.adapter = adapterValorXQuantidade

                binding.valorTotalTextViewID.text = Util.formatarParaDinheiro(total.toString())

                itemEdt.text.clear()
                qtdEdt.text.clear()
                produtoSelecionado = null
            } else {
                Toast.makeText(
                    context,
                    "Insira a quantidade e selecione um produto",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}