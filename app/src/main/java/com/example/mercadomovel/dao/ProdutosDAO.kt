package com.example.projetinhodeestudo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mercadomovel.view.Clientes.model.ProdutoModel

@Dao
interface ProdutosDAO {

    @Insert
    fun insert(produtoBO: ProdutoModel)

    @Delete
    fun delete(produtoBO: ProdutoModel)

    @Update
    fun update(produtoBO: ProdutoModel)

    @Query("DELETE FROM Produtos")
    fun deleteAll()

    @Query("DELETE FROM Produtos WHERE id = :id")
    fun deletePorID(id: Int)

    @Query("SELECT * FROM Produtos WHERE id = :id")
    fun getPorID(id: Int): ProdutoModel?

    @Query("SELECT * FROM Produtos")
    fun getTodas(): List<ProdutoModel>

}