package com.example.proyecto1.modelo

data class Pedido(
    val idpedido: Int,
    val pizza: String,
    val tamanoPizza: String,
    val bebida: String,
    val precioPizza: Double,
    val precioBebida: Double,
    val precioTotal: Double,

){

}
