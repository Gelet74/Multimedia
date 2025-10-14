package com.example.proyecto1.datos

import com.example.proyecto1.modelo.Pizza

data class Pedido(
    val id: Int = 0,
    val pizza: Pizza? = null,
    val tamano: String = "",
    val bebida: BebidaPedida? = null,
    val cantidadPizza: Int = 1,
    val cantidadBebida: Int = 1,
    val precioTotal: Double = 0.0
)

data class BebidaPedida(
    val nombre: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 1
)