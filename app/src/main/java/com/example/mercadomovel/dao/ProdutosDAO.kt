package com.example.projetinhodeestudo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mercadomovel.bo.ProdutoBO

@Dao
interface ProdutosDAO {

    @Insert
    fun insert(produtoBO: ProdutoBO)

    @Delete
    fun delete(produtoBO: ProdutoBO)

    @Update
    fun update(produtoBO: ProdutoBO)

    @Query("DELETE FROM Produtos")
    fun deleteAll()

    @Query("DELETE FROM Produtos WHERE id = :id")
    fun deletePorID(id: Int)

    @Query("SELECT * FROM Produtos WHERE id = :id")
    fun getPorID(id: Int): ProdutoBO?

    @Query("SELECT * FROM Produtos")
    fun getTodas(): List<ProdutoBO>?

}