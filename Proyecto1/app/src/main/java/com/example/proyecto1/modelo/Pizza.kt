package com.example.proyecto1.modelo

open class Pizza(
    open val nombre: String = "",
    open val opciones: List<String> = emptyList(),
    open val opcionSeleccionada: String = ""
) {

    open fun calcularPrecioExtra(): Double {
        return when (this) {
            is Barbacoa -> 1.0  // Precio extra por la carne
            is Margarita -> if (opcionSeleccionada == "Versión vegana") 0.5 else 0.0
            else -> 0.0
        }
    }
}

class Romana(opcionSeleccionada: String = "") :
    Pizza("Romana", listOf("Con champiñones", "Sin champiñones"), opcionSeleccionada)

class Barbacoa(opcionSeleccionada: String = "") :
    Pizza("Barbacoa", listOf("Carne de cerdo", "Pollo", "Ternera"), opcionSeleccionada)

class Margarita(opcionSeleccionada: String = "") :
    Pizza("Margarita", listOf("Con piña", "Sin piña", "Versión vegana"), opcionSeleccionada)