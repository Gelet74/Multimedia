package com.example.ud6_primero.datos

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.ud6_primero.conexion.StarWarsServicioApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface ContenedorApp {
val planetasRepositorio: PlanetasRepositorio
}

class StarWarsContenedorApp : com.example.ud6_primero.datos.ContenedorApp {
    private val baseUrl = "https://swapi.dev/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofit: StarWarsServicioApi by lazy {
        retrofit.create(StarWarsServicioApi::class.java)
    }

    override val planetasRepositorio: PlanetasRepositorio by lazy {
        ConexionPlanetaRepositorio(servicioRetrofit)
    }
}