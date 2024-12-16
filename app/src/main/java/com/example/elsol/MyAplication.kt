package com.example.elsol

import android.app.Application

/**
 * La clase MyApplication extiende [Application] y actúa como la clase principal de la aplicación.
 * Es utilizada para almacenar datos globales accesibles desde cualquier parte de la aplicación.
 */
class MyApplication : Application() {

    /**
     * Mapa que contiene datos de los planetas del sistema solar.
     * La clave es el nombre del planeta y el valor es un [Triple] con la información siguiente:
     * - **Diámetro relativo** al de la Tierra.
     * - **Distancia al Sol** medida en Unidades Astronómicas (UA).
     * - **Densidad** en kg/m³.
     */
    val planetData = mapOf(
        "Mercurio" to Triple(0.382, 0.387, 5400),
        "Venus" to Triple(0.949, 0.723, 5250),
        "Tierra" to Triple(1.0, 1.0, 5520),
        "Marte" to Triple(0.53, 1.542, 3960),
        "Júpiter" to Triple(11.2, 5.203, 1350),
        "Saturno" to Triple(9.41, 9.539, 700),
        "Urano" to Triple(3.38, 19.81, 1200),
        "Neptuno" to Triple(3.81, 30.07, 1500),
        "Plutón" to Triple(0.18, 39.44, 1700) //datos aproximados para Plutón
    )
}
