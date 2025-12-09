package com.example.ud6_primero.ui

import retrofit2.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ud6_primero.StarWarsAplicacion
import com.example.ud6_primero.datos.PlanetasRepositorio
import com.example.ud6_primero.modelo.Respuesta
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface StarWarsUIState {
    data class Exito(val respuesta: Respuesta) : StarWarsUIState
    object Error : StarWarsUIState
    object Cargando : StarWarsUIState
}

class StarWarsViewModel(private val planetasRepositorio: PlanetasRepositorio) : ViewModel() {
    var starWarsUIState: StarWarsUIState by mutableStateOf(StarWarsUIState.Cargando)
        private set

    init {
        obtenerPlanetas()
    }

    fun obtenerPlanetas() {
        viewModelScope.launch {
            starWarsUIState = StarWarsUIState.Cargando
            starWarsUIState = try {
                val listaPlanetas = planetasRepositorio.obtenerPlanetas()
                StarWarsUIState.Exito(listaPlanetas)
            } catch (e: IOException) {
                StarWarsUIState.Error
            } catch (e: HttpException) {
                StarWarsUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as StarWarsAplicacion)
                val planetasRepositorio = aplicacion.contenedor.planetasRepositorio
                StarWarsViewModel(planetasRepositorio = planetasRepositorio)
            }
        }
    }
}
