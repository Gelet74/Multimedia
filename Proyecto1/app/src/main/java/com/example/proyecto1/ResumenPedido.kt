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
import androidx.compose.ui.res.stringResource
import com.example.proyecto1.R

@Composable
fun ResumenPedido(modifier: Modifier = Modifier) {
    val pedidoDePrueba = Pedido(
        pizza = Barbacoa("Carne de Cerdo"),
        tamano =  stringResource(R.string.tamano_mediana),
        cantidadPizza = 2,
        bebida = BebidaPedida(stringResource(R.string.bebida1), 2.0, 3),
        cantidadBebida = 2,
        precioTotal = 13.9
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
            text = stringResource(R.string.resumen_title),
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
                        text = stringResource(R.string.label_tamano)+" " + pedidoDePrueba.tamano,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = stringResource(R.string.label_cantidad)+" " + pedidoDePrueba.cantidadPizza,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    if (pizza.opcionSeleccionada.isNotEmpty()) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ItemConFotoPequena(
                            nombre = stringResource(R.string.label_opcion)+ " "  + pizza.opcionSeleccionada,
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
                    descripcion = stringResource(R.string.label_cantidad) + bebida.cantidad,
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
                Text(stringResource(R.string.btn_cancelar))
            }
            Button(onClick = { }) {
                Text(stringResource(R.string.pago_form_title))
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
            "Con Champiñones", "With Mushrooms" -> R.drawable.champi
            "Sin Champiñones", "Without Mushrooms"  -> R.drawable.sin_champi
            else -> null
        }
        "Barbacoa" -> when (opcion) {
            "Carne de Pollo", "Chicken Meat" -> R.drawable.pollo
            "Carne de Cerdo", "Pork Meat" -> R.drawable.cerdo
            "Carne de Ternera", "Beef Meat" -> R.drawable.ternera
            else -> null
        }
        "Margarita" -> when (opcion) {
            "Con piña", "With Pineapple" -> R.drawable.pina
            "Sin piña", "Without Pineapple" -> R.drawable.sin_pina
            "Vegana", "Vegan" -> R.drawable.vegana
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

@Composable
fun obtenerOpcionTraducida(opcionEstatica: String): String {
    val stringId = when (opcionEstatica) {
        "Con Champiñones" -> R.string.opcion_champi
        "Sin Champiñones" -> R.string.opcion_sin_champi
        "Carne de Cerdo" -> R.string.opcion_cerdo
        "Carne de Pollo" -> R.string.opcion_pollo
        "Carne de Ternera" -> R.string.opcion_ternera
        "Con piña" -> R.string.opcion_pina
        "Sin piña" -> R.string.opcion_sin_pina
        "Vegana" -> R.string.opcion_vegana
        else -> return opcionEstatica
    }
    return stringResource(id = stringId)
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