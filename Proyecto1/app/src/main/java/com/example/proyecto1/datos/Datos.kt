package com.example.proyecto1.datos

import com.example.proyecto1.modelo.Pedido

class Datos {
    fun cargarPedidos(): List<Pedido> {
        return listOf(
            Pedido(
                idpedido = 1,
                pizza = "Barbacoa",
                tamanoPizza = "Mediana",
                bebida = "Agua",
                precioPizza = 0.0,
                precioBebida = 0.0,
                precioTotal = 0.0
        ),
        Pedido(
            idpedido = 2,
            pizza = "Margarita",
            tamanoPizza = "Pequena",
            bebida = "Cola",
            precioPizza = 0.0,
            precioBebida = 0.0,
            precioTotal = 0.0
        ),
        Pedido (
            idpedido = 3,
            pizza = "Romana",
            tamanoPizza = "Grande",
            bebida = "dd",
            precioPizza = 0.0,
            precioBebida = 0.0,
            precioTotal = 0.0
        )
        )
    }
}

