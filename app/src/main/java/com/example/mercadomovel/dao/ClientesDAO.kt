package com.example.mercadomovel.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mercadomovel.bo.ClienteBO

@Dao
interface ClientesDAO {
    @Insert
    fun insert(clienteBO: ClienteBO)

    @Delete
    fun delete(clienteBO: ClienteBO)

    @Update
    fun update(clienteBO: ClienteBO)

    @Query("DELETE FROM Clientes WHERE id = :id")
    fun deletePorID(id: Int)

    @Query("SELECT * FROM Clientes WHERE id = :id")
    fun getPorID(id: Int): ClienteBO?

    @Query("SELECT * FROM Clientes")
    fun getTodas(): List<ClienteBO>?

}