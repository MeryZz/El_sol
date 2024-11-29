package com.example.elsol

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ComparePlanetsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_planets_detail)

        val planet1Name = intent.getStringExtra("planet1") ?: ""
        val planet2Name = intent.getStringExtra("planet2") ?: ""

        val planet1Details = findViewById<TextView>(R.id.planet1_details)
        val planet2Details = findViewById<TextView>(R.id.planet2_details)

        if (planet1Name.isNotEmpty() && planet2Name.isNotEmpty()) {
            val planetData = (applicationContext as MyApplication).planetData

            val planet1Data = planetData[planet1Name]
            val planet2Data = planetData[planet2Name]

            planet1Data?.let {
                planet1Details.text = """
                    $planet1Name:
                    Diámetro: ${it.first} veces el diámetro de la Tierra
                    Distancia al Sol: ${it.second} UA
                    Densidad: ${it.third} kg/m³
                """.trimIndent()
            }

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
