package com.example.proyecto1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto1.datos.Pedido
import com.example.proyecto1.datos.BebidaPedida
import com.example.proyecto1.modelo.Margarita
import com.example.proyecto1.ui.theme.Proyecto1Theme

data class DatosPago(
    val tipoTarjeta: String = "Visa",
    val ultimosDigitos: String = "**** 4321"
)

@Composable
fun ResumenPago(modifier: Modifier = Modifier) {

    val pedidoDePrueba = Pedido(
        pizza = Margarita("Vegana"),
        tamano = "Mediana",
        cantidadPizza = 2,
        bebida = BebidaPedida("Cola", 0.0, 3),
        cantidadBebida = 3,
        precioTotal = 25.0
    )
    val datosPagoDePrueba = DatosPago(tipoTarjeta = "Euro6000")
    val nombreCliente = "Sara Gómez"

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = obtenerImagenTarjeta(datosPagoDePrueba.tipoTarjeta) ?: R.drawable.ic_launcher_foreground),
                contentDescription =  stringResource(R.string.tipo_tarj),
                modifier = Modifier.size(180.dp)
            )
        }

        Text(
            text = stringResource(R.string.pago_resumen_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = stringResource(R.string.label_cliente)+": "  +nombreCliente,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 4.dp))

                ItemConFotoPequena(
                    nombre =  stringResource(R.string.tarjeta_elegida),
                    descripcion = datosPagoDePrueba.tipoTarjeta,
                    imagenResId = null
                )

                ItemConFotoPequena(
                    nombre = stringResource(R.string. label_num_tarjeta)+": ",
                    descripcion = datosPagoDePrueba.ultimosDigitos,
                    imagenResId = null
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.label_total_pagado) + " " + "%.2f".format(pedidoDePrueba.precioTotal) + "€",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {}) {
                Text(stringResource(R.string.btn_aceptar))
            }
            Button(onClick = {}) {
                Text(stringResource(R.string.btn_enviar))
            }
        }
    }
}

@Composable
private fun ItemConFotoPequena(nombre: String, descripcion: String, imagenResId: Int?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imagenResId != null) {
            Image(
                painter = painterResource(id = imagenResId),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(nombre, style = MaterialTheme.typography.titleMedium)
            if (descripcion.isNotEmpty()) {
                Text(descripcion, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

private fun obtenerImagenTarjeta(tipo: String): Int? {
    return when (tipo) {
        "Visa" -> R.drawable.visa
        "MasterCard" -> R.drawable.mastercard
        "Euro6000" -> R.drawable.euro6000
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun ResumenPagoPreview() {
    Proyecto1Theme {
        Surface(color = Color(0xFFE0E0E0)) {
            ResumenPago()
        }
    }
}