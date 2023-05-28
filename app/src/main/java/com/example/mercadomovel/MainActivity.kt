package com.example.mercadomovel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var menu: MaterialToolbar
    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
////        navigation = findNavController(R.id.nav_host_fragment)
//        menu = findViewById(R.id.menu)
//
//        menu.setNavigationOnClickListener {
//            Toast.makeText(this, "Clicado", Toast.LENGTH_SHORT).show()
//        }
//
//        menu.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.homeID -> {
//                    navigation.navigate(R.id.action_produtosFragment_to_homeFragment)
//                    true
//                }
//                R.id.vendasID -> {
//                    true
//                }
//                else -> {
//                    false
//                }
//            }
//        }
    }
}