package com.example.mercadomovel.view

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mercadomovel.R
import com.example.mercadomovel.databinding.FragmentProdutosBinding

class ProdutosFragment : Fragment() {

    private lateinit var binding: FragmentProdutosBinding
    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProdutosBinding.inflate(inflater, container, false)
        navigation = findNavController()

        return binding.root
    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val typeface = Typeface.createFromAsset(requireContext().assets, "Vintage_Signature.otf")
//        val tituloTextView = view.findViewById<TextView>(R.id.tituloTextView)
//        tituloTextView.typeface = typeface
//    }
}