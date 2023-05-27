package com.example.mercadomovel

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var menu: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menu = findViewById(R.id.menu)

        menu.setNavigationOnClickListener {
            Toast.makeText(this, "Clicado", Toast.LENGTH_SHORT).show()
        }

        menu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.homeID -> {
                    Toast.makeText(this, "Home clicado", Toast.LENGTH_SHORT).show()

                    true
                }
                else -> {
                    false
                }
            }
        }
    }

}