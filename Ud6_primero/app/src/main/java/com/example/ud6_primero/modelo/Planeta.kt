package com.example.ud6_primero.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Planeta(
@SerialName("name")
    val nombre: String,
@SerialName("diameter")
    val diametro: String,
@SerialName("population")
    val poblacion: String
)
