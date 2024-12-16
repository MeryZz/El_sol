package com.example.elsol;

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad para comparar planetas dentro del sistema solar.
 *
 * Permite al usuario seleccionar planetas y visualizar información
 * sobre su diámetro, distancia al Sol y densidad.
 */
class ComparePlanetsActivity : AppCompatActivity() {

    //lista de planetas disponibles para la comparación
    private val planets = listOf(
        "Mercurio", "Venus", "Tierra", "Marte", "Júpiter", "Saturno", "Urano", "Neptuno", "Plutón"
    )

    //mapa que contiene información sobre cada planeta
    private val planetData = mapOf(
        "Mercurio" to PlanetInfo(0.382, 0.387, 5400),
        "Venus" to PlanetInfo(0.949, 0.723, 5250),
        "Tierra" to PlanetInfo(1.0, 1.0, 5520),
        "Marte" to PlanetInfo(0.53, 1.542, 3960),
        "Júpiter" to PlanetInfo(11.2, 5.203, 1350),
        "Saturno" to PlanetInfo(9.41, 9.539, 700),
        "Urano" to PlanetInfo(3.38, 19.81, 1200),
        "Neptuno" to PlanetInfo(3.81, 30.07, 1500),
        "Plutón" to PlanetInfo(0.18, 39.44, 1800) //suposición para Plutón
    )

    /**
     * Inicializa la actividad y configura los elementos de UI.
     * Asocia los datos de los planetas con los campos de selección (AutoCompleteTextView).
     *
     * @param savedInstanceState Estado previamente guardado, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_planets)

        //elementos de la interfaz de usuario
        val planet1View = findViewById<AutoCompleteTextView>(R.id.planet1)
        val planet2View = findViewById<AutoCompleteTextView>(R.id.planet2)
        val planet1Details = findViewById<TextView>(R.id.planet1_details)
        val planet2Details = findViewById<TextView>(R.id.planet2_details)

        //configurar AutoCompleteTextViews
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, planets)
        planet1View.setAdapter(adapter)
        planet2View.setAdapter(adapter)

        //listeners para mostrar los detalles cuando se seleccione un planeta
        planet1View.setOnItemClickListener { _, _, position, _ ->
            val planetName = planet1View.adapter.getItem(position).toString()
            showPlanetDetails(planetName, planet1Details)
        }

        planet2View.setOnItemClickListener { _, _, position, _ ->
            val planetName = planet2View.adapter.getItem(position).toString()
            showPlanetDetails(planetName, planet2Details)
        }
    }

    /**
     * Muestra los detalles de un planeta seleccionado en el campo correspondiente.
     *
     * @param planetName Nombre del planeta.
     * @param detailsView Elemento de la interfaz donde se mostrarán los detalles.
     */
    private fun showPlanetDetails(planetName: String, detailsView: TextView) {
        planetData[planetName]?.let { planetInfo ->
            detailsView.text = """
                Diámetro: ${planetInfo.diameter} radios terrestres
                Distancia al sol: ${planetInfo.distance} UA
                Densidad: ${planetInfo.density} kg/m³
            """.trimIndent()
        }
    }

    /**
     * Clase de datos que representa información de un planeta.
     *
     * @property diameter Diámetro relativo al de la Tierra.
     * @property distance Distancia al Sol en unidades astronómicas (UA).
     * @property density Densidad promedio en kg/m³.
     */
    data class PlanetInfo(val diameter: Double, val distance: Double, val density: Int)
}
