package com.example.ud6_primero.datos

import com.example.ud6_primero.conexion.StarWarsServicioApi
import com.example.ud6_primero.modelo.Respuesta

interface PlanetasRepositorio {

    suspend fun obtenerPlanetas(): Respuesta
}

class ConexionPlanetaRepositorio(
    private val starWarsServicioApi: StarWarsServicioApi
) : PlanetasRepositorio {
    override suspend fun obtenerPlanetas(): Respuesta = starWarsServicioApi.obtenerPlanetas()
}