package com.example.mercadomovel.controller.Produtos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.databinding.FragmentProdutosListBinding
import com.example.projetinhodeestudo.data.AppDataBase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO

class ProdutosListFragment : Fragment() {

    private lateinit var binding: FragmentProdutosListBinding
    private lateinit var navigation: NavController
    private lateinit var db: AppDataBase
    private lateinit var produtoDAO: ProdutosDAO
    private lateinit var listViewQtd: ListView
    private lateinit var listViewNome: ListView
    private lateinit var listViewValor: ListView
    private lateinit var listViewAcoesEditar: ListView
    private lateinit var listViewAcoesRemover: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProdutosListBinding.inflate(inflater, container, false)
        navigation = findNavController()


        db = AppDataBase.getInstance(requireContext().applicationContext)
        produtoDAO = db.produtoDAO()
//        db.close()
//        requireContext().deleteDatabase("app_database")
        //List Views
        listViewQtd = binding.qtdProdutosID
        listViewNome = binding.nomeProdutoID
        listViewValor = binding.valorProdutoID
        listViewAcoesEditar = binding.EditarID
        listViewAcoesRemover = binding.removerID

        binding.btnAdicionarProduto.setOnClickListener { navigation.navigate(R.id.action_produtosListFragment_to_produtosFragment) }
        listar()

        return binding.root
    }

    private fun listar() {
        var list = produtoDAO.getTodas()
        var qtdList = mutableListOf<String>()
        var produtosIdList = mutableListOf<Int>()
        var nomeProduto = mutableListOf<String>()
        val iconEditarId = R.drawable.editar
        val iconRemoverId = R.drawable.remove_produtos

        var valor = mutableListOf<Double>()

        list?.let { productList ->
            for (produto in productList) {
                var qtd = produto.quantidade
                val displayQtd = qtd?.toString() ?: "-- --"

                nomeProduto.add(produto.nome)
                valor.add(produto.valor)
                qtdList.add(displayQtd)
                produto.id?.let { produtosIdList.add(it) }
            }
        }

        var context = activity?.baseContext as Context
        var resource = android.R.layout.simple_list_item_1

        var adapterQtd: ArrayAdapter<String> = ArrayAdapter(context, resource, qtdList)
        val adapterValor: TextAdapter = TextAdapter(context, R.layout.list_textos, nomeProduto)
        val adapterNome: TextAdapter = TextAdapter(context, resource, nomeProduto)
        val adapterAcoesEditar: CustomAdapter = CustomAdapter(context, R.layout.list_itens_icons, produtosIdList, iconEditarId)
        var adapterAcoesRemover: CustomAdapter = CustomAdapter(context, R.layout.list_itens_icons, produtosIdList, iconRemoverId)

        listViewQtd.adapter = adapterQtd
        listViewNome.adapter = adapterNome
        listViewValor.adapter = adapterValor
        listViewAcoesEditar.adapter = adapterAcoesEditar
        listViewAcoesRemover.adapter = adapterAcoesRemover

        listViewAcoesRemover.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val produtoID = produtosIdList[position]
            deletarProduto(produtoID)
        }

        listViewAcoesEditar.setOnItemClickListener { _, _, position, _ ->
            val produtoID = produtosIdList[position]

            val bundle = Bundle()

            bundle.putInt("produtoID", produtoID)
            navigation.navigate(R.id.action_produtosListFragment_to_produtosFragment, bundle)
        }

    }

    private fun deletarProduto(id: Int) {
        val confimacao = AlertDialog.Builder(requireContext())

        confimacao.setTitle("Tem certeza que deseja exluir este produto?")
        confimacao.setMessage("Ao clicar em \"Confirmar\" séra exluido permanentemente?")

        confimacao.setPositiveButton("Confirmar") { _, _ ->
            produtoDAO.deletePorID(id)
            listar()
        }

        confimacao.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        confimacao.show()
    }


    class CustomAdapter(context: Context, resource: Int, objects: List<Int>, private val iconResourceId: Int) :
        ArrayAdapter<Int>(context, resource, objects) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = LayoutInflater.from(context)
            val view = convertView ?: inflater.inflate(R.layout.list_itens_icons, parent, false)

            val icon = view.findViewById<ImageView>(R.id.editar)

            icon.setImageResource(iconResourceId)

            return view
        }
    }

    class TextAdapter(context: Context, resource: Int, objects: List<String>) :
        ArrayAdapter<String>(context, resource, objects) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = LayoutInflater.from(context)
            val view = convertView ?: inflater.inflate(R.layout.list_textos, parent, false)

            val textView = view.findViewById<TextView>(R.id.listaprodutos)
            textView.text = getItem(position)

            return view
        }
    }
}