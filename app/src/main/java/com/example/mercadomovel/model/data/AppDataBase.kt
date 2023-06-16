package com.example.projetinhodeestudo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mercadomovel.bo.ClienteBO
import com.example.mercadomovel.bo.ProdutoBO
import com.example.mercadomovel.dao.ClientesDAO
import com.example.mercadomovel.dao.VendasDAO
import com.example.mercadomovel.model.data.VendasBO
import com.example.projetinhodeestudo.data.dao.ProdutosDAO


@Database(entities = [ProdutoBO::class, ClienteBO::class, VendasBO::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun produtoDAO(): ProdutosDAO
    abstract fun clientesDAO(): ClientesDAO
    abstract fun vendasDAO(): VendasDAO

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this) {
                var instance: AppDataBase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        "app_database"
                    ).allowMainThreadQueries().build()
                }
                return instance
            }
        }
    }

}