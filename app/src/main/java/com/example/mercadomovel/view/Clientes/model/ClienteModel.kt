package com.example.mercadomovel.view.Clientes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Clientes")
data class ClienteModel(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "endereco") val endereco: String?,
    @ColumnInfo(name = "telefone") val telefone: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "cnpj") val cnpj: String?
)
