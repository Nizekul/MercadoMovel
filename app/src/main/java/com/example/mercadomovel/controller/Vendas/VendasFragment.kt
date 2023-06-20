package com.example.mercadomovel.controller.Vendas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadomovel.R
import com.example.mercadomovel.dao.VendasDAO
import com.example.mercadomovel.databinding.FragmentVendasBinding
import com.example.mercadomovel.model.data.VendasModel
import com.example.mercadomovel.model.formatarParaDinheiro
import com.example.projetinhodeestudo.data.AppDataBase
import java.time.LocalDate
import java.util.Calendar
import java.util.Date


class VendasFragment : Fragment() {

    private lateinit var binding: FragmentVendasBinding
    private lateinit var db: AppDataBase
    private lateinit var navigation: NavController
    private lateinit var vendasDAO: VendasDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVendasBinding.inflate(inflater, container, false)
        db = AppDataBase.getInstance(requireContext())
        navigation = findNavController()
        vendasDAO = db.vendasDAO()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBuscar.setOnClickListener { buscar() }
    }

    fun buscar(){
        var data_inicio: Date = Calendar.getInstance().time
        var data_fim: Date = Calendar.getInstance().time

        var nome = binding.nomeEditText.text.toString()
        var cnpjText = binding.cnpjEditText.text.toString()

        val vendas = vendasDAO.buscar(nome, cnpjText)
        val listIdVendas = mutableListOf<Int>()
        val dataCriado = mutableListOf<Date>()
        val nome_empresa = mutableListOf<String>()
        val valor = mutableListOf<Double>()

        vendas?.let { vendaslist ->
            for (venda in vendaslist) {
                venda.clienteId?.let {
                    listIdVendas.add(it)
                    dataCriado.add(venda.criadoEm)
                    nome_empresa.add(venda.NomeEmpresa)
                    valor.add(venda.valor)
                }
            }
        }

        val vendasRecyclerView: RecyclerView = binding.ProdutosListView
        val vendasAdapter = VendasAdapter(vendas)
        vendasRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        vendasRecyclerView.adapter = vendasAdapter
    }

    class VendasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeCliente: TextView = itemView.findViewById(R.id.nomeCliente)
        val remover: TextView = itemView.findViewById(R.id.remover)
        val valorTotal: TextView = itemView.findViewById(R.id.valorTotal)
    }

    class VendasAdapter(private val vendas: List<VendasModel>?) : RecyclerView.Adapter<VendasViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendasViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_vendas, parent, false)
            return VendasViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: VendasViewHolder, position: Int) {
            val venda = vendas?.get(position)
            if (venda != null) {
                holder.nomeCliente.text = venda.NomeEmpresa
                holder.remover.setOnClickListener {
                    // Lógica para remover o item da venda
                }
                holder.valorTotal.text = "Total" + venda.valor.formatarParaDinheiro()
            }
        }

        override fun getItemCount(): Int {
            return vendas?.size ?: 0
        }
    }


}