package com.example.mercadomovel.controller.Produtos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadomovel.R
import com.example.mercadomovel.databinding.FragmentProdutosListBinding
import com.example.projetinhodeestudo.data.AppDataBase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO
import com.google.android.material.appbar.MaterialToolbar

class ProdutosListFragment : Fragment() {

    private lateinit var binding: FragmentProdutosListBinding
    private lateinit var navigation: NavController
    private lateinit var db: AppDataBase
    private lateinit var produtoDAO: ProdutosDAO
    private lateinit var listView: ListView

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
        listView = binding.qtdProdutosID

        binding.btnAdicionarProduto.setOnClickListener { navigation.navigate(R.id.action_produtosListFragment_to_produtosFragment) }
        listar()

        return binding.root
    }

    private fun listar() {
        var list = produtoDAO.getTodas()
        var qtdList = mutableListOf<String>()
        var produtosIdList = mutableListOf<Int>()

        list?.let { productList ->
            for (produto in productList) {
                var qtd = produto.quantidade

                val displayQtd = qtd?.toString() ?: "-- --"
                qtdList.add(displayQtd)
                produto.id?.let { produtosIdList.add(it) }
            }
        }

        var context = activity?.baseContext as Context
        var resource = android.R.layout.simple_list_item_1

        var adapter: ArrayAdapter<String> = ArrayAdapter(context, resource, qtdList)

        listView.adapter = adapter

    }

}