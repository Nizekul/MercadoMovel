package com.example.mercadomovel.bo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuarios")
data class UsuarioBO(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "nome") val nome: String?,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "senha") val senha: String?,
)
