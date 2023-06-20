package com.example.mercadomovel.model.data

import androidx.room.TypeConverters
import com.example.mercadomovel.bo.DateModel
import java.util.Date

@TypeConverters(DateModel::class)
data class VendasModel(
    val clienteId: Int,
    val criadoEm: Date,
    val debito: Boolean,
    val NomeEmpresa: String,
    val valor: Double

)