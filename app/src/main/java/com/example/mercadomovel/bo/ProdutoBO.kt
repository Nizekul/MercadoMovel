package com.example.mercadomovel.bo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "Produtos")
@TypeConverters(DateModel::class)
data class ProdutoBO(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @NonNull
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "descricao") val descricao: String?,
    @NonNull
    @ColumnInfo(name = "valor") val valor: Double,
    @ColumnInfo(name = "dtVencimento") val dtVencimento: Date? = null,
    @ColumnInfo(name = "quantidade") val quantidade: Int?
)

