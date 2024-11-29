package com.example.elsol;

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ComparePlanetsActivity : AppCompatActivity() {

    private val planets = listOf(
        "Mercurio", "Venus", "Tierra", "Marte", "Júpiter", "Saturno", "Urano", "Neptuno", "Plutón"
    )

    private val planetData = mapOf(
        "Mercurio" to PlanetInfo(0.382, 0.387, 5400),
        "Venus" to PlanetInfo(0.949, 0.723, 5250),
        "Tierra" to PlanetInfo(1.0, 1.0, 5520),
        "Marte" to PlanetInfo(0.53, 1.542, 3960),
        "Júpiter" to PlanetInfo(11.2, 5.203, 1350),
        "Saturno" to PlanetInfo(9.41, 9.539, 700),
        "Urano" to PlanetInfo(3.38, 19.81, 1200),
        "Neptuno" to PlanetInfo(3.81, 30.07, 1500),
        "Plutón" to PlanetInfo(0.18, 39.44, 1800) // Suposición para Plutón
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_planets)

        val planet1View = findViewById<AutoCompleteTextView>(R.id.planet1)
        val planet2View = findViewById<AutoCompleteTextView>(R.id.planet2)
        val planet1Details = findViewById<TextView>(R.id.planet1_details)
        val planet2Details = findViewById<TextView>(R.id.planet2_details)

        // Configurar AutoCompleteTextViews
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, planets)
        planet1View.setAdapter(adapter)
        planet2View.setAdapter(adapter)

        // Establecer listeners para mostrar los detalles cuando se seleccione un planeta
        planet1View.setOnItemClickListener { _, _, position, _ ->
            val planetName = planet1View.adapter.getItem(position).toString()
            showPlanetDetails(planetName, planet1Details)
        }

        planet2View.setOnItemClickListener { _, _, position, _ ->
            val planetName = planet2View.adapter.getItem(position).toString()
            showPlanetDetails(planetName, planet2Details)
        }
    }

    private fun showPlanetDetails(planetName: String, detailsView: TextView) {
        planetData[planetName]?.let { planetInfo ->
            detailsView.text = """
                Diámetro: ${planetInfo.diameter} radios terrestres
                Distancia al sol: ${planetInfo.distance} UA
                Densidad: ${planetInfo.density} kg/m³
            """.trimIndent()
        }
    }

    // Data class para almacenar la información de los planetas
    data class PlanetInfo(val diameter: Double, val distance: Double, val density: Int)
}
