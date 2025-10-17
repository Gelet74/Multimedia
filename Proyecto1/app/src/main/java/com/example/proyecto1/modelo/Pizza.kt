package com.example.proyecto1.modelo

open class Pizza(
    open val nombre: String = "",
    open val opciones: List<String> = emptyList(),
    open val opcionSeleccionada: String = ""
)

class Romana(opcionSeleccionada: String = "") :
    Pizza("Romana", listOf("Con champiñones", "Sin champiñones"), opcionSeleccionada)

class Barbacoa(opcionSeleccionada: String = "") :
    Pizza("Barbacoa", listOf("Carne de cerdo", "Pollo", "Ternera"), opcionSeleccionada)

class Margarita(opcionSeleccionada: String = "") :
    Pizza("Margarita", listOf("Con piña", "Sin piña", "Versión vegana"), opcionSeleccionada)