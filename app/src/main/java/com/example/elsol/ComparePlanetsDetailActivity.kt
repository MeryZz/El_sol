package com.example.elsol

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity que muestra una comparación detallada entre los planetas seleccionados.
 * Obtiene los datos de los planetas desde la clase MyApplication y los muestra en la interfaz de usuario.
 */
class ComparePlanetsDetailActivity : AppCompatActivity() {

    /**
     * Método de ciclo de vida que se llama al crear la actividad.
     * Configura la interfaz y muestra la información de los planetas seleccionados.
     *
     * @param savedInstanceState Contiene el estado previamente guardado de la actividad si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_planets_detail)

        //obtiene los nombres de los planetas desde el intent que inicia la actividad
        val planet1Name = intent.getStringExtra("planet1") ?: ""
        val planet2Name = intent.getStringExtra("planet2") ?: ""

        //referencias a los elementos de la interfaz donde se mostrará la información
        val planet1Details = findViewById<TextView>(R.id.planet1_details)
        val planet2Details = findViewById<TextView>(R.id.planet2_details)

        //comprueba que los nombres de los planetas no estén vacíos
        if (planet1Name.isNotEmpty() && planet2Name.isNotEmpty()) {
            //obtiene los datos de los planetas desde la clase MyApplication
            val planetData = (applicationContext as MyApplication).planetData

            val planet1Data = planetData[planet1Name]
            val planet2Data = planetData[planet2Name]

            //si los datos del planeta 1 existen, los muestra en el TextView correspondiente
            planet1Data?.let {
                planet1Details.text = """
                    $planet1Name:
                    Diámetro: ${it.first} veces el diámetro de la Tierra
                    Distancia al Sol: ${it.second} UA
                    Densidad: ${it.third} kg/m³
                """.trimIndent()
            }

            //si los datos del planeta 2 existen, los muestra en el TextView correspondiente
            planet2Data?.let {
                planet2Details.text = """
                    $planet2Name:
                    Diámetro: ${it.first} veces el diámetro de la Tierra
                    Distancia al Sol: ${it.second} UA
                    Densidad: ${it.third} kg/m³
                """.trimIndent()
            }
        }
    }
}
