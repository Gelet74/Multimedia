package com.example.proyecto1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto1.datos.Datos
import com.example.proyecto1.datos.Precios
import com.example.proyecto1.modelo.Pedido

@Composable
fun VerPedidos(modifier: Modifier = Modifier) {
    val pedidos = Datos().cargarPedidos()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Resumen de Pedidos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (pedidos.isEmpty()) {
            Text("No hay pedidos registrados.")
        } else {
            LazyColumn {
                items(pedidos) { pedido ->
                    PedidoTarjetaExpandible(pedido = pedido)
                }
            }
        }
    }
}

@Composable
private fun PedidoTarjetaExpandible(pedido: Pedido) {
    var abierto by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
                    Text(
                        text = "Pedido #${pedido.idpedido}: ${pedido.pizza}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Bebida: ${if (pedido.bebida == "dd") "Sin bebida" else pedido.bebida}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(onClick = { abierto = !abierto }) {
                    Icon(
                        imageVector = if (abierto) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (abierto) "Colapsar" else "Expandir",
                    )
                }
            }

            AnimatedVisibility(
                visible = abierto,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
                DetalleContenidoExpandido(
                    pedido = pedido,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun DetalleContenidoExpandido(pedido: Pedido, modifier: Modifier = Modifier) {

    val precioPizzaUnitario = Precios.tamanos.find {
        it.nombre.equals(pedido.tamanoPizza, ignoreCase = true)
    }?.precio ?: 0.0

    val precioBebidaUnitario = Precios.bebidas.find {
        it.nombre.equals(pedido.bebida, ignoreCase = true)
    }?.precio ?: 0.0


    val precioCalculado = (precioPizzaUnitario * pedido.cantidadPizza) + (precioBebidaUnitario * pedido.cantidadBebida)

    val nombreBebida = if (pedido.bebida == "dd") "Sin bebida" else pedido.bebida


    Column(modifier = modifier.fillMaxWidth()) {

        Divider(modifier = Modifier.padding(vertical = 4.dp))

        Text("Tipo de Pizza: ${pedido.pizza}")
        Text("Tamaño de Pizza: ${pedido.tamanoPizza}")
        Text("Cantidad de Pizzas: ${pedido.cantidadPizza}")

        Divider(modifier = Modifier.padding(vertical = 4.dp))

        Text("Bebida: ${nombreBebida}")
        if (nombreBebida != "Sin bebida") {
            Text("Cantidad de Bebida: ${pedido.cantidadBebida}")
            Divider(modifier = Modifier.padding(vertical = 4.dp))
        }


        Text(
            text = "PRECIO TOTAL: ${"%.2f".format(precioCalculado)}€",
            style = MaterialTheme.typography.titleLarge
        )
    }
}