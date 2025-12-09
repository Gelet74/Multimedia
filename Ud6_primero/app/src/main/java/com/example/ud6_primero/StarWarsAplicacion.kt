package com.example.ud6_primero

import com.example.ud6_primero.datos.ContenedorApp
import com.example.ud6_primero.datos.StarWarsContenedorApp
import android.app.Application

class StarWarsAplicacion : Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = StarWarsContenedorApp()
    }
}