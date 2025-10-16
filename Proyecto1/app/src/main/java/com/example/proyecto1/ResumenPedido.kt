package com.example.proyecto1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto1.datos.BebidaPedida
import com.example.proyecto1.datos.Pedido
import com.example.proyecto1.modelo.*
import com.example.proyecto1.ui.theme.Proyecto1Theme
import androidx.compose.ui.graphics.Color
import com.example.proyecto1.R

@Composable
fun ResumenPedido(modifier: Modifier = Modifier) {
    val pedidoDePrueba = Pedido(
        pizza = Margarita("Vegana"),
        tamano = "Mediana",
        cantidadPizza = 2,
        bebida = BebidaPedida("Cola", 0.0, 3),
        cantidadBebida = 3,
        precioTotal = 25.0
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        pedidoDePrueba.pizza?.let { pizza ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = obtenerImagenPizza(pizza.nombre) ?: R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(180.dp)
                )
            }
        }

        Text(
            text = "Resumen del Pedido",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        pedidoDePrueba.pizza?.let { pizza ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Pizza: ${pizza.nombre}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Tamaño: ${pedidoDePrueba.tamano}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Cantidad: ${pedidoDePrueba.cantidadPizza}",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    if (pizza.opcionSeleccionada.isNotEmpty()) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ItemConFotoPequena(
                            nombre = "Opción: ${pizza.opcionSeleccionada}",
                            descripcion = "",
                            imagenResId = obtenerImagenOpcionPizza(pizza.nombre, pizza.opcionSeleccionada)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        pedidoDePrueba.bebida?.let { bebida ->
            Card(modifier = Modifier.fillMaxWidth()) {
                ItemConFotoPequena(
                    nombre = bebida.nombre,
                    descripcion = "Cantidad: ${bebida.cantidad}",
                    imagenResId = obtenerImagenBebida(bebida.nombre),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "TOTAL: ${"%.2f".format(pedidoDePrueba.precioTotal)}€",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { }) {
                Text("Cancelar")
            }
            Button(onClick = { }) {
                Text("Formulario de pago")
            }
        }
    }
}

@Composable
private fun ItemConFotoPequena(nombre: String, descripcion: String, imagenResId: Int?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imagenResId != null) {
            Image(
                painter = painterResource(id = imagenResId),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .padding(end = 16.dp)
            )
        }
        Column {
            Text(nombre, style = MaterialTheme.typography.titleLarge)
            if (descripcion.isNotEmpty()) {
                Text(descripcion, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


private fun obtenerImagenPizza(nombre: String): Int? {
    return when (nombre) {
        "Romana" -> R.drawable.romana
        "Barbacoa" -> R.drawable.barbacoa
        "Margarita" -> R.drawable.margarita
        else -> null
    }
}

private fun obtenerImagenOpcionPizza(nombrePizza: String, opcion: String): Int? {
    return when (nombrePizza) {
        "Romana" -> when (opcion) {
            "Con Champiñones" -> R.drawable.champi
            "Sin Champiñones" -> R.drawable.sin_champi
            else -> null
        }
        "Barbacoa" -> when (opcion) {
            "Carne de Pollo" -> R.drawable.pollo
            "Carne de Cerdo" -> R.drawable.cerdo
            "Carne de Ternera" -> R.drawable.ternera
            else -> null
        }
        "Margarita" -> when (opcion) {
            "Con piña" -> R.drawable.pina
            "Sin piña" -> R.drawable.sin_pina
            "Vegana" -> R.drawable.vegana
            else -> null
        }
        else -> null
    }
}

private fun obtenerImagenBebida(nombre: String): Int? {
    return when (nombre) {
        "Agua" -> R.drawable.agua
        "Cola" -> R.drawable.cola
        "Sin bebida" -> R.drawable.sin_bebida
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun ResumenPedidoPreview() {
    Proyecto1Theme {
        Surface(color = Color(0xFFFFBB30)) {
            ResumenPedido()
        }
    }
}