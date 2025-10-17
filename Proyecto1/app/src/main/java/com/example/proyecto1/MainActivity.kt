package com.example.proyecto1


import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto1.ui.theme.Proyecto1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  Inicio(
                        modifier = Modifier.padding(innerPadding)
                    )
                    /*HacerPedido(
                        modifier = Modifier.padding(innerPadding),

                    )*/
                }
            }
        }
    }
}

@Composable
fun Inicio(modifier: Modifier = Modifier) {
    val imagen = painterResource(R.drawable.logo_pizza)

    Box(
        modifier = modifier.fillMaxSize()
            .background(color = Color(0xFFFFBB30))

    ) {
        Image(
            painter = imagen,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    Column {
        Card(modifier = Modifier .padding(start=50.dp)
            .padding(top=30.dp)
            .height(100.dp)
        ) {
            Row {
                Column (modifier = Modifier .padding(top=20.dp)){
                    Text(
                        text = stringResource(R.string.label_nombre) + stringResource(R.string.nombre)
                    )

                    Text(
                        text =  stringResource(R.string.label_correo) + stringResource(R.string.correo)
                    )
                    Text(
                        text = stringResource(R.string.label_tfno) + stringResource(R.string.tfno)
                    )
                }
                Image(
                    painter = painterResource(id=R.drawable.imagen1),
                    contentDescription = null
                )
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top=650.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {},
                modifier = Modifier

            ) {
                Text(text = "HACER PEDIDO")
            }
            Button(
                onClick = {},
                modifier = Modifier

            ) {
                Text(text = "VER PEDIDOS")
            }
        }
    }
}
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Proyecto1Theme {
        HacerPedido()
    }
}