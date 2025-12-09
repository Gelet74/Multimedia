package com.example.ud6_primero.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Respuesta(
@SerialName(value="results")
    val resultados:List<Planeta>
)
