package com.example.mercadomovel.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mercadomovel.bo.ClienteBO
import com.example.mercadomovel.bo.ProdutoBO


@Entity(
    tableName = "Vendas",
    foreignKeys = [ForeignKey(
        entity = ClienteBO::class,
        parentColumns = ["id"],
        childColumns = ["clienteId"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = ProdutoBO::class,
            parentColumns = ["id"],
            childColumns = ["produtoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class VendasBO(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "Valor") val valor: Double,
    @ColumnInfo(name = "array_produtos_ID") val quantidade_produtos: String,
    @ColumnInfo(name = "array_qtd_produtos") val array_qtd_produtos: String,
    @ColumnInfo(name = "debito") val debito: Boolean,
    @ColumnInfo(name = "clienteId") val clienteId: Int?,
    @ColumnInfo(name = "produtoId") val produtoId: Int?

)