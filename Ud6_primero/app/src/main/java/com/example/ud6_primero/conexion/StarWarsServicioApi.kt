package com.example.ud6_primero.conexion

import com.example.ud6_primero.modelo.Respuesta
import retrofit2.http.GET

interface StarWarsServicioApi {
    @GET ("planets")
    suspend fun obtenerPlanetas(): Respuesta
}