package com.example.projetinhodeestudo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projetinhodeestudo.data.dao.ProdutosDAO
import com.example.mercadomovel.view.Clientes.model.ProdutoModel

@Database(entities = [ProdutoModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun produtoDAO(): ProdutosDAO

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this) {
                var instance: AppDataBase? = INSTANCE
                if(instance == null){
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