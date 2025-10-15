package com.example.proyecto1.modelo

import androidx.annotation.StringRes

data class Persona(
    @StringRes val stringNombre: Int,
    @StringRes val stringCorreo: Int,
    @StringRes val stringTfno: Int,
)