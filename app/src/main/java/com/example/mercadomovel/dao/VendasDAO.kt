package com.example.mercadomovel.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mercadomovel.model.data.VendasBO

@Dao
interface VendasDAO {
        @Insert
        fun insert(vendaBO: VendasBO)

        @Delete
        fun delete(vendaBO: VendasBO)

        @Update
        fun update(vendaBO: VendasBO)

        @Query("DELETE FROM Vendas")
        fun deleteAll()

        @Query("DELETE FROM Vendas WHERE id = :id")
        fun deletePorID(id: Int)

        @Query("SELECT * FROM Vendas WHERE id = :id")
        fun getPorID(id: Int): VendasBO

        @Query("SELECT * FROM Vendas")
        fun getTodas(): List<VendasBO>?

        @Query("SELECT SUM(Valor) FROM Vendas V WHERE clienteId = :id")
        fun getVendaPorCLienteID(id: Int): Double

        @Query("SELECT COUNT(ID) FROM Vendas WHERE clienteId = :id AND Debito = 1")
        fun getQuantidadeDividasPorCLienteID(id: Int): Int
}