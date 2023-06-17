package com.example.mercadomovel.controller.Home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadomovel.R
import com.example.mercadomovel.dao.ClientesDAO
import com.example.mercadomovel.databinding.FragmentHomeBinding
import com.example.mercadomovel.model.Util
import com.example.mercadomovel.model.converterParaDouble
import com.example.mercadomovel.model.formatarParaDinheiro
import com.example.projetinhodeestudo.data.AppDataBase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO
import com.google.android.material.appbar.MaterialToolbar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navigation: NavController
    private lateinit var db: AppDataBase
    private lateinit var produtoDAO: ProdutosDAO
    private lateinit var clienteDAO: ClientesDAO
    lateinit var produtoListView: RecyclerView
    private val produtos = mutableListOf<HomeFragment.Produto>()
    val produtosSelecionados = mutableListOf<Produto>()
    private lateinit var produtoAdapter: ProdutoAdapter
    private var total: Double = 0.0
    data class Produto(val nome: String, val id: Int, val valor: String, val qtd: Double)

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
        produtoListView = binding.ProdutosListView

        preencherCliente()
        preencherProduto()
        binding.limparbtn.setOnClickListener {
            produtosSelecionados.clear()
            produtoAdapter.notifyDataSetChanged()
            binding.nomeProdutoaddID.setSelection(0)
            binding.qtdProdutoaddID.text.clear()
            binding.valorTotalTextViewID.text = "R$ 0,00"
        }
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
        var produtoNome = mutableListOf<String>()
        var produtoID = mutableListOf<Int>()
        var valorUnidade = mutableListOf<Double>()
        var textTotal = binding.valorTotalTextViewID.text.toString()
        total = Util.converterParaDouble(textTotal)
        val spinner = binding.nomeProdutoaddID
        produtoListView.layoutManager = LinearLayoutManager(requireContext())

        produtoAdapter = ProdutoAdapter(produtosSelecionados)
        produtoListView.adapter = produtoAdapter

        produtoList?.let { produtos ->
            for (produto in produtos) {
                produto.id?.let {
                    produtoID.add(it)
                    valorUnidade.add(produto.valor)
                    produtoNome.add(produto.nome)
                }
            }
        }

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, produtoNome)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val produtoSelecionado = produtoList?.get(position)
                if (produtoSelecionado != null) {
                    val produtoSelecionado = produtoList[position]
                    val nomeProdutoSelecionado = produtoSelecionado.nome
                    val idProdutoSelecionado = produtoSelecionado.id
                    val valorUnidadeProdutoSelecionado =
                        produtoSelecionado.valor.formatarParaDinheiro()
                    val qtdEdt = binding.qtdProdutoaddID.text.toString()

                    val quantidade = if (qtdEdt.isNotEmpty()) qtdEdt.toDouble() else 0.0

                    // Adicionar o produto selecionado à RecyclerView
                    val produto = HomeFragment.Produto(
                        nomeProdutoSelecionado,
                        idProdutoSelecionado!!,
                        valorUnidadeProdutoSelecionado,
                        quantidade
                    )
                    produtos.add(produto)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nenhuma ação necessária quando nenhum item é selecionado
            }
        }

        binding.btnAdicionar.setOnClickListener {
            val nomeProduto = binding.nomeProdutoaddID.selectedItem.toString()
            val quantidade = binding.qtdProdutoaddID.text.toString()
            val qtdEdt = binding.qtdProdutoaddID.text.toString()

            if (nomeProduto.isNotEmpty() && quantidade.isNotEmpty()) {
                val produtoSelecionado =
                    produtoList?.get(binding.nomeProdutoaddID.selectedItemPosition)
                if (produtoSelecionado != null) {
                    val nomeProdutoSelecionado = qtdEdt + " - " + produtoSelecionado.nome
                    val idProdutoSelecionado = produtoSelecionado.id
                    val valorUnidadeProdutoSelecionado =
                        "Unidade R$ " + produtoSelecionado.valor.formatarParaDinheiro()

                    val produto = Produto(
                        nomeProdutoSelecionado,
                        idProdutoSelecionado!!,
                        valorUnidadeProdutoSelecionado,
                        qtdEdt.toDouble()
                    )
                    produtosSelecionados.add(produto)
                    adapter.notifyDataSetChanged()

                    // Limpar os campos após adicionar o produto
                    binding.nomeProdutoaddID.setSelection(0)
                    binding.qtdProdutoaddID.text.clear()

                    // Atualizar o total
                    total += produtoSelecionado.valor * quantidade.toDouble()
                    binding.valorTotalTextViewID.text = "R$ " + total.formatarParaDinheiro()

                }
            } else {
                // Exibir uma mensagem de erro caso algum campo esteja vazio
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    inner class ProdutoAdapter(private val produtoList: List<Produto>) :
        RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview, parent, false)
            return ProdutoViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
            val currentProduto = produtoList[position]
            holder.bind(currentProduto, position)
        }

        override fun getItemCount() = produtoList.size

        inner class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nomeProdutoTextView: TextView = itemView.findViewById(R.id.nomeProduto)
            val valorUnidadeTextView: TextView = itemView.findViewById(R.id.valorUnidade)

            fun bind(produto: Produto, position: Int) {
                nomeProdutoTextView.text = produto.nome
                valorUnidadeTextView.text = produto.valor
                // Configurar outros campos do item do RecyclerView, se houver

                itemView.setOnClickListener {
                    val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                    alertDialogBuilder.setTitle("Remover Produto")
                    alertDialogBuilder.setMessage("Deseja remover este produto?")
                    alertDialogBuilder.setPositiveButton("Sim") { _, _ ->
                        total -= produto.valor.converterParaDouble() * produto.qtd
                        binding.valorTotalTextViewID.text = "R$ " + total.formatarParaDinheiro()
                        produtosSelecionados.removeAt(position)
                        notifyItemRemoved(position)

                    }
                    alertDialogBuilder.setNegativeButton("Não", null)
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
            }
        }
    }
}