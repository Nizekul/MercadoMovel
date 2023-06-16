package com.example.mercadomovel.bo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Clientes")
data class ClienteBO(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "endereco") val endereco: String?,
    @ColumnInfo(name = "telefone") val telefone: String?,
    @ColumnInfo(name = "NomeEmpresa") val NomeEmpresa: String,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "cnpj") val cnpj: String?
)
