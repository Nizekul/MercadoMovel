package com.example.mercadomovel.bo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mercadomovel.bo.ClienteBO
import com.example.mercadomovel.bo.ProdutoBO
import java.util.Date


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
@TypeConverters(DateModel::class)
data class VendasBO(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "Valor") val valor: Double,
    @ColumnInfo(name = "debito") val debito: Boolean,
    @ColumnInfo(name = "criadoEm") val criadoEm: Date?,
    @ColumnInfo(name = "clienteId") val clienteId: Int,
    @ColumnInfo(name = "produtoId") val produtoId: Int
)