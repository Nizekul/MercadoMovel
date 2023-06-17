package com.example.mercadomovel.controller.Clientes

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.controller.Produtos.ProdutosListFragment
import com.example.mercadomovel.dao.ClientesDAO
import com.example.mercadomovel.dao.VendasDAO
import com.example.mercadomovel.databinding.FragmentClientesListBinding
import com.example.mercadomovel.model.Util
import com.example.mercadomovel.model.formatarParaDinheiro
import com.example.projetinhodeestudo.data.AppDataBase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO

class ClientesListFragment : Fragment() {

    private lateinit var binding: FragmentClientesListBinding
    private lateinit var navigation: NavController
    private lateinit var db: AppDataBase
    private lateinit var clienteDAO: ClientesDAO
    private lateinit var vendasDAO: VendasDAO
    private lateinit var listViewDebitos: ListView
    private lateinit var listViewCliente: ListView
    private lateinit var listViewDividas: ListView
    private lateinit var listViewAcoesEditar: ListView
    private lateinit var listViewAcoesRemover: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentClientesListBinding.inflate(inflater, container, false)
        navigation = findNavController()

        listViewDebitos = binding.debitosViewID
        listViewCliente = binding.clienteViewID
        listViewDividas = binding.valorTotalID
        listViewAcoesEditar = binding.EditarID
        listViewAcoesRemover = binding.removerID

        db = AppDataBase.getInstance(requireContext().applicationContext)
        clienteDAO = db.clientesDAO()
        vendasDAO = db.vendasDAO()

        binding.btnAdicionar.setOnClickListener { navigation.navigate(R.id.action_clientesListFragment_to_clientesFragment) }
        listar()

        return binding.root
    }

    private fun listar() {
        var list = clienteDAO.getTodas()

        var clientesIdList = mutableListOf<Int>()
        var nomeCliente = mutableListOf<String>()
        var dividaTotal = mutableListOf<String>()
        var debitos = mutableListOf<String>()

        val iconEditarId = R.drawable.editar
        val iconRemoverId = R.drawable.remove_cliente
        var valorFormatado = ""

        list?.let { clientestList ->
            for (cliente in clientestList) {

                nomeCliente.add(cliente.nome)

                cliente.id?.let {
                    clientesIdList.add(it)
                    debitos.add(vendasDAO.getQuantidadeDividasPorCLienteID(it).toString())
                    valorFormatado = vendasDAO.getVendaPorCLienteID(it).toString()
//                    valorFormatado = Util.formatarParaDinheiro("R$ "+ valorFormatado.toDouble())
                    dividaTotal.add(valorFormatado)
                }
            }
        }

        var context = activity?.baseContext as Context

        var adapterDebitos: ProdutosListFragment.TextAdapter =
            ProdutosListFragment.TextAdapter(context, R.layout.list_textos, debitos)
        val adapterNome: ProdutosListFragment.TextAdapter =
            ProdutosListFragment.TextAdapter(context, R.layout.list_textos, nomeCliente)
        val adapterDividas: ProdutosListFragment.TextAdapter =
            ProdutosListFragment.TextAdapter(context, R.layout.list_textos, dividaTotal)

        val adapterAcoesEditar: ProdutosListFragment.CustomAdapter =
            ProdutosListFragment.CustomAdapter(
                context,
                R.layout.list_itens_icons,
                clientesIdList,
                iconEditarId
            )
        var adapterAcoesRemover: ProdutosListFragment.CustomAdapter =
            ProdutosListFragment.CustomAdapter(
                context,
                R.layout.list_itens_icons,
                clientesIdList,
                iconRemoverId
            )

        listViewDebitos.adapter = adapterDebitos
        listViewCliente.adapter = adapterNome
        listViewDividas.adapter = adapterDividas
        listViewAcoesEditar.adapter = adapterAcoesEditar
        listViewAcoesRemover.adapter = adapterAcoesRemover

        listViewAcoesRemover.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val clienteID = clientesIdList[position]
                deletarCliente(clienteID)
            }

        listViewAcoesEditar.setOnItemClickListener { _, _, position, _ ->
            val clienteID = clientesIdList[position]

            val bundle = Bundle()

            bundle.putInt("clienteID", clienteID)
            navigation.navigate(R.id.action_clientesListFragment_to_clientesFragment, bundle)
        }
    }

    private fun deletarCliente(id: Int) {
        val confimacao = AlertDialog.Builder(requireContext())

        confimacao.setTitle("Tem certeza que deseja exluir este Cliente?")
        confimacao.setMessage("Ao clicar em \"Confirmar\" séra exluido permanentemente!")

        confimacao.setPositiveButton("Confirmar") { _, _ ->
            clienteDAO.deletePorID(id)
            listar()
        }

        confimacao.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        confimacao.show()
    }
}