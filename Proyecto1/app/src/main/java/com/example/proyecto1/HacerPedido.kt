package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

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

    val pizzaRomanaNombre = "Romana"
    val pizzaBarbacoaNombre = "Barbacoa"
    val pizzaMargaritaNombre = "Margarita"

    fun calcularPrecioTotal() {
        val precioTamano = Precios.tamanos.find { it.nombre == pedido.tamano }?.precio ?: 0.0
        val precioBebidas = pedido.bebida?.precio ?: 0.0
        val total = (precioTamano * pedido.cantidadPizza) + (precioBebidas * pedido.cantidadBebida)
        pedido = pedido.copy(precioTotal = total)
    }

    fun seleccionarPizza(tipoPizza: String) {
        val nuevaPizza = when (tipoPizza) {
            pizzaRomanaNombre -> Romana()
            pizzaBarbacoaNombre -> Barbacoa()
            pizzaMargaritaNombre -> Margarita()
            else -> return
        }

        val pizzaConOpcion = when (nuevaPizza) {
            is Romana -> Romana("")
            is Barbacoa -> Barbacoa("")
            is Margarita -> Margarita("")
            else -> nuevaPizza
        }

        pedido = pedido.copy(
            pizza = pizzaConOpcion,
            cantidadPizza = 1,
            tamano = pedido.tamano.ifEmpty { Precios.tamanos.first().nombre }
        )
        calcularPrecioTotal()
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(stringResource(R.string.titulo_realizar_pedido), style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        Card(Modifier.fillMaxWidth()) {
            Text(
                text = "Precio total: ${"%.2f".format(pedido.precioTotal)}€",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = stringResource(R.string.txt_seleccionar_pizza),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Precios.tiposPizza.forEach { tipoPizza ->
            val isSelected = pedido.pizza?.nombre == tipoPizza
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { seleccionarPizza(tipoPizza) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(tipoPizza)
                }
            }
        }

        pedido.pizza?.let { pizza ->
            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.label_seleccionar_tamano), style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Precios.tamanos.forEach { tamano ->
                    Button(
                        onClick = {
                            pedido = pedido.copy(tamano = tamano.nombre)
                            calcularPrecioTotal()
                        },
                        colors = ButtonDefaults.buttonColors(

                            containerColor = if (pedido.tamano == tamano.nombre)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("${tamano.nombre} (${"%.2f".format(tamano.precio)}€)")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Opciones para ${pizza.nombre}:", style = MaterialTheme.typography.titleMedium)
            pizza.opciones.forEach { opcion ->
                val opcionSeleccionada = pizza.opcionSeleccionada == opcion
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            val pizzaActualizada = when (pizza) {
                                is Romana -> Romana(opcion)
                                is Barbacoa -> Barbacoa(opcion)
                                is Margarita -> Margarita(opcion)
                                else -> pizza
                            }
                            pedido = pedido.copy(pizza = pizzaActualizada)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (opcionSeleccionada)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(opcion)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.label_cantidad_bebidas), style = MaterialTheme.typography.titleMedium)
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
                    pedido.cantidadPizza.toString(),
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

        Spacer(Modifier.height(24.dp))
        Text(stringResource(R.string.txt_seleccionar_bebida), style = MaterialTheme.typography.titleMedium)

        Precios.bebidas.forEach { bebida ->
            val isSelected = pedido.bebida?.nombre == bebida.nombre
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        if (isSelected || bebida.nombre == "Sin bebida") {
                            pedido = pedido.copy(bebida = null, cantidadBebida = 0)
                        } else {
                            pedido = pedido.copy(
                                bebida = BebidaPedida(nombre = bebida.nombre, precio = bebida.precio, cantidad = 1),
                                cantidadBebida = 1
                            )
                        }
                        calcularPrecioTotal()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    bebida.nombre,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        if (pedido.bebida != null && pedido.bebida?.nombre != "Sin bebida") {
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.label_cantidad_bebidas), style = MaterialTheme.typography.titleMedium)
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
                    pedido.cantidadBebida.toString(),
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

        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { })
            {
                Text(stringResource(R.string.btn_cancelar) )
            }
            Button(onClick = { }) {
                Text(stringResource(R.string.btn_aceptar)   )
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