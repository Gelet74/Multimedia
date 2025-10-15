package com.example.proyecto1.datos

import com.example.proyecto1.R
import com.example.proyecto1.modelo.Persona

class Cliente {
    fun cargarPersona(): List<Persona> = listOf(
        Persona(R.string.nombre, R.string.correo, R.string.tfno)
    )
}