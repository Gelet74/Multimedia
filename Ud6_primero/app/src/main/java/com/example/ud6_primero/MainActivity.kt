package com.example.ud6_primero

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ud6_primero.ui.StarWarsViewModel
import com.example.ud6_primero.ui.theme.Ud6_primeroTheme
import com.example.ud6_primero.modelo.Respuesta
import com.example.ud6_primero.ui.StarWarsUIState

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ud6_primeroTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaDatos(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PantallaDatos(
    modifier: Modifier = Modifier,
    viewModel: StarWarsViewModel = viewModel(factory = StarWarsViewModel.Factory)
) {
    when (val estado = viewModel.starWarsUIState) {
        is StarWarsUIState.Cargando -> {
            android.util.Log.d("PantallaDatos", "Estado: Cargando")
            PantallaCargando(modifier = modifier.fillMaxSize())
        }
        is StarWarsUIState.Exito -> {
            android.util.Log.d("PantallaDatos", "Estado: Exito con ${estado.respuesta.resultados.size} planetas")
            PantallaExito(
                respuesta = estado.respuesta,
                modifier = modifier.fillMaxWidth()
            )
        }
        is StarWarsUIState.Error -> {
            android.util.Log.d("PantallaDatos", "Estado: Error")
            PantallaError(modifier = modifier.fillMaxWidth())
        }
    }
}


@Composable
fun PantallaCargando(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.cargando),
        contentDescription = stringResource(R.string.cargando)
    )
}

@Composable
fun PantallaError(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.error),
        contentDescription = stringResource(R.string.error_de_conexion)
    )
}

@Composable
fun PantallaExito(
    respuesta: Respuesta,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = modifier) {
        items(respuesta.resultados) { planeta ->
            Box(
                modifier = Modifier.padding(8.dp)
            ){
                Column(
                    modifier= Modifier.fillMaxWidth()
                ){
                    Text(
                        text = planeta.nombre
                    )
                    Text(
                        text = planeta.diametro
                    )
                    Text(
                        text = planeta.poblacion
                    )
                    HorizontalDivider()
                }

            }
        }
    }
}