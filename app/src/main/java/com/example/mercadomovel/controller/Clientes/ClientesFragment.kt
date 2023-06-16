package com.example.mercadomovel.controller.Clientes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.bo.ClienteBO
import com.example.mercadomovel.dao.ClientesDAO
import com.example.mercadomovel.databinding.FragmentCadastrosClientesBinding
import com.example.mercadomovel.model.ClientesViewModel
import com.example.projetinhodeestudo.data.AppDataBase


class ClientesFragment : Fragment() {

    private lateinit var binding: FragmentCadastrosClientesBinding
    private lateinit var navigation: NavController
    private lateinit var clienteDAO: ClientesDAO
    private lateinit var db: AppDataBase
    private var clienteID: Int? = null
    private val viewModelCliente: ClientesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastrosClientesBinding.inflate(inflater, container, false)
        navigation = findNavController()

        db = AppDataBase.getInstance(requireContext().applicationContext)
        clienteDAO = db.clientesDAO()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelCliente.init(clienteDAO, db, requireContext())

        arguments?.let {
            clienteID = it.getInt("clienteID", -1)
        }

        clienteID?.let {
            var cliente = clienteDAO.getPorID(it)

            binding.nomeEditText.setText(cliente?.nome)
            binding.nomeEmpresa.setText(cliente?.NomeEmpresa)
            binding.enderecotextID.setText(cliente?.endereco)
            binding.telefonetextID.setText(cliente?.telefone)
            binding.emailtextID.setText(cliente?.email)
            binding.cnpjtextID.setText(cliente?.cnpj)
        }

        binding.cancelarButton.setOnClickListener { navigation.navigateUp() }
        binding.salvarButton.setOnClickListener { CadastrarCliente(clienteID) }
    }

    private fun CadastrarCliente(id: Int?) {
        val nome = binding.nomeEditText.text.toString()
        var nome_empresa = binding.nomeEmpresa.text.toString()
        var enderecotextID: String? = binding.enderecotextID.text.toString()
        var telefonetextID: String? = binding.telefonetextID.text.toString()
        var emailtextID: String? = binding.emailtextID.text.toString()
        var cnpjtextID: String? = binding.cnpjtextID.text.toString()

        val sucessoCadastro = viewModelCliente.cadastrarCliente(
            id,
            nome,
            nome_empresa,
            enderecotextID,
            telefonetextID,
            emailtextID,
            cnpjtextID
        )


        if (sucessoCadastro) {
            navigation.navigateUp()
        }
    }

}