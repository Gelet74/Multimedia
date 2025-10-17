package com.example.proyecto1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto1.ui.theme.Proyecto1Theme
import com.example.proyecto1.R

// Definición de las opciones de pago (solo tarjetas)
data class OpcionPago(val nombre: String, val iconoResId: Int)

val opcionesPago = listOf(
    OpcionPago("VISA", R.drawable.visa),
    OpcionPago("MasterCard", R.drawable.mastercard),
    OpcionPago("Euro 6000", R.drawable.euro6000)
)

@Composable
fun FormularioPago(modifier: Modifier = Modifier) {
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaCaducidad by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var tipoPagoSeleccionado by remember { mutableStateOf(opcionesPago.first()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Formulario de Pago",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp, top = 8.dp)
        )

        Text(
            text = "Selecciona el tipo de tarjeta:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )


        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                opcionesPago.forEach { opcion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (opcion == tipoPagoSeleccionado),
                                onClick = { tipoPagoSeleccionado = opcion }
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (opcion == tipoPagoSeleccionado),
                            onClick = { tipoPagoSeleccionado = opcion }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = opcion.iconoResId),
                            contentDescription = opcion.nombre,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = opcion.nombre,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    if (opcion != opcionesPago.last()) {
                        Divider()
                    }
                }
            }
        }


        OutlinedTextField(
            value = numeroTarjeta,
            onValueChange = {
                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                    numeroTarjeta = it
                }
            },
            label = { Text("Número de Tarjeta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = fechaCaducidad,
                onValueChange = {
                    if (it.length <= 5) {
                        fechaCaducidad = it
                    }
                },
                label = { Text("Fecha Caducidad (MM/AA)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )

            OutlinedTextField(
                value = cvc,
                onValueChange = {
                    if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                        cvc = it
                    }
                },
                label = { Text("CVC") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "Cancelar", color = Color.Black)
            }

            Button(
                onClick = {  }
            ) {
                Text(text = "Aceptar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioPagoPreview() {
    Proyecto1Theme {
        Surface(color = Color(0xFFFFBB30)) {
            FormularioPago()
        }
    }
}