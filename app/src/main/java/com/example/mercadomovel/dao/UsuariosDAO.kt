package com.example.mercadomovel.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mercadomovel.bo.UsuarioBO

@Dao
interface UsuariosDAO {
    @Insert
    fun insert(usuarioBO: UsuarioBO)

    @Delete
    fun delete(usuarioBO: UsuarioBO)

    @Update
    fun update(usuarioBO: UsuarioBO)

    @Query("DELETE FROM Usuarios")
    fun deleteAll()

    @Query("DELETE FROM Usuarios WHERE id = :id")
    fun deletePorID(id: Int)

    @Query("SELECT * FROM Usuarios WHERE id = :id")
    fun getPorID(id: Int): UsuarioBO

    @Query("SELECT * FROM Usuarios WHERE login = :login LIMIT 1")
    fun getPorNome(login: String): UsuarioBO?

    @Query("SELECT * FROM Usuarios")
    fun getTodas(): List<UsuarioBO>?

}