package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto1.datos.Pedido
import com.example.proyecto1.datos.BebidaPedida
import com.example.proyecto1.datos.Precios
import com.example.proyecto1.modelo.*
import com.example.proyecto1.ui.theme.Proyecto1Theme

class RealizarPedido : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HacerPedido(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HacerPedido(modifier: Modifier = Modifier) {
    var pedido by remember { mutableStateOf(Pedido()) }

    fun calcularPrecioTotal() {
        val precioTamano = Precios.tamanos.find { it.nombre == pedido.tamano }?.precio ?: 0.0
        val precioBebidas = pedido.bebida?.precio ?: 0.0
        val total = (precioTamano * pedido.cantidadPizza) + (precioBebidas * pedido.cantidadBebida)
        pedido = pedido.copy(precioTotal = total)
    }

    fun seleccionarPizza(tipoPizza: String) {
        val pizza = when (tipoPizza) {
            "Romana" -> Romana()
            "Barbacoa" -> Barbacoa()
            "Margarita" -> Margarita()
            else -> return
        }
        pedido = pedido.copy(pizza = pizza)
        calcularPrecioTotal()
    }

    fun seleccionarBebida(bebida: String) {
        val precio = Precios.bebidas.find { it.nombre == bebida }?.precio ?: 0.0
        pedido = pedido.copy(bebida = BebidaPedida(nombre = bebida, precio = precio, cantidad = 1))
        calcularPrecioTotal()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Realizar Pedido",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Precio total: ${"%.2f".format(pedido.precioTotal)}€",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecciona tu pizza:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Precios.tiposPizza.forEach { tipoPizza ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { seleccionarPizza(tipoPizza) }
            ) {
                Text(text = tipoPizza, modifier = Modifier.padding(16.dp))
            }
        }

        pedido.pizza?.let { pizza ->
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Opciones para ${pizza.nombre}:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            pizza.opciones.forEach { opcion ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    onClick = {
                        val pizzaActualizada = when (pizza) {
                            is Romana -> Romana(opcion)
                            is Barbacoa -> Barbacoa(opcion)
                            is Margarita -> Margarita(opcion)
                            else -> pizza
                        }
                        pedido = pedido.copy(pizza = pizzaActualizada)
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = opcion)
                        if (pizza.opcionSeleccionada == opcion) {
                            Text("✓", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cantidad de pizzas:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (pedido.cantidadPizza > 1) {
                            pedido = pedido.copy(cantidadPizza = pedido.cantidadPizza - 1)
                            calcularPrecioTotal()
                        }
                    },
                    enabled = pedido.cantidadPizza > 1
                ) { Text("-") }

                Text(
                    text = pedido.cantidadPizza.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Button(
                    onClick = {
                        pedido = pedido.copy(cantidadPizza = pedido.cantidadPizza + 1)
                        calcularPrecioTotal()
                    }
                ) { Text("+") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecciona bebida:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Precios.bebidas.forEach { bebida ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { seleccionarBebida(bebida.nombre) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(bebida.nombre, style = MaterialTheme.typography.bodyLarge)
                    if (pedido.bebida?.nombre == bebida.nombre) {
                        Text("✓", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        pedido.bebida?.let { bebida ->
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cantidad de ${bebida.nombre}:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (pedido.cantidadBebida > 1) {
                            pedido = pedido.copy(cantidadBebida = pedido.cantidadBebida - 1)
                            calcularPrecioTotal()
                        }
                    },
                    enabled = pedido.cantidadBebida > 1
                ) { Text("-") }

                Text(
                    text = pedido.cantidadBebida.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Button(
                    onClick = {
                        pedido = pedido.copy(cantidadBebida = pedido.cantidadBebida + 1)
                        calcularPrecioTotal()
                    }
                ) { Text("+") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HacerPedidoPreview() {
    Proyecto1Theme {
        HacerPedido()
    }
}