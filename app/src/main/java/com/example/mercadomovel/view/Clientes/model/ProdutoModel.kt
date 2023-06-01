package com.example.mercadomovel.view.Clientes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "Produtos")
@TypeConverters(DateModel::class)
data class ProdutoModel(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "descricao") val descricao: String?,
    @ColumnInfo(name = "dtVencimento") val dtVencimento: Date,
    @ColumnInfo(name = "quantidade") val quantidade: Double?,
    @ColumnInfo(name = "valor") val valor: Double,
    @ColumnInfo(name = "nome") val nome: String
)

