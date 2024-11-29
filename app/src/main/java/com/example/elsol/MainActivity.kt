package com.example.elsol

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    private lateinit var solarItems: MutableList<SolarAdapter.SolarItem>
    private lateinit var adapter: SolarAdapter

    // Mapa con datos de los planetas (Diámetro, Distancia al sol, Densidad)
    private val planetData = mapOf(
        "Mercurio" to Triple(0.382, 0.387, 5400),
        "Venus" to Triple(0.949, 0.723, 5250),
        "Tierra" to Triple(1.0, 1.0, 5520),
        "Marte" to Triple(0.53, 1.542, 3960),
        "Júpiter" to Triple(11.2, 5.203, 1350),
        "Saturno" to Triple(9.41, 9.539, 700),
        "Urano" to Triple(3.38, 19.81, 1200),
        "Neptuno" to Triple(3.81, 30.07, 1500),
        "Plutón" to Triple(0.18, 39.44, 1700) // Datos aproximados para Plutón
    )

    private var cutItemGlobal: SolarAdapter.SolarItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Lista mutable para permitir cambios dinámicos
        solarItems = mutableListOf(
            SolarAdapter.SolarItem(R.drawable.corona_solar, "Corona Solar"),
            SolarAdapter.SolarItem(R.drawable.erupcionsolar, "Erupción Solar"),
            SolarAdapter.SolarItem(R.drawable.espiculas, "Espículas"),
            SolarAdapter.SolarItem(R.drawable.filamentos, "Filamentos"),
            SolarAdapter.SolarItem(R.drawable.magnetosfera, "Magnetosfera"),
            SolarAdapter.SolarItem(R.drawable.manchasolar, "Mancha Solar")
        )

        // Crear adaptador y pasar la lista mutable
        adapter = SolarAdapter(solarItems) { option, position ->
            when (option) {
                "Eliminar" -> {
                    solarItems.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    Toast.makeText(this, "Ítem eliminado", Toast.LENGTH_SHORT).show()
                }
                "Copiar" -> {
                    val copiedItem = solarItems[position]
                    solarItems.add(copiedItem)
                    adapter.notifyItemInserted(solarItems.size - 1)
                    Toast.makeText(this, "Ítem copiado", Toast.LENGTH_SHORT).show()
                }
                "Renombrar" -> {
                    // Mostrar un dialogo para renombrar
                    val dialogView = layoutInflater.inflate(R.layout.dialog_rename, null)
                    val editText = dialogView.findViewById<EditText>(R.id.editText)

                    AlertDialog.Builder(this)
                        .setTitle("Renombrar")
                        .setView(dialogView)
                        .setPositiveButton("Aceptar") { _, _ ->
                            val newName = editText.text.toString()
                            if (newName.isNotEmpty()) {
                                solarItems[position] = solarItems[position].copy(title = newName)
                                adapter.notifyItemChanged(position)
                                Toast.makeText(this, "Ítem renombrado", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                }
                "Cortar" -> {
                    // Cortar el ítem y guardarlo para pegar en otro lugar
                    val cutItem = solarItems[position]
                    solarItems.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    // Guardamos el ítem cortado en una variable global para pegarlo
                    cutItemGlobal = cutItem
                    Toast.makeText(this, "Ítem cortado", Toast.LENGTH_SHORT).show()
                }
                "Mover" -> {
                    // Mover el ítem a una nueva posición
                    cutItemGlobal?.let { itemToMove ->
                        solarItems.add(position, itemToMove)
                        adapter.notifyItemInserted(position)
                        Toast.makeText(this, "Ítem movido", Toast.LENGTH_SHORT).show()
                        cutItemGlobal = null // Limpiar la variable global
                    } ?: run {
                        Toast.makeText(this, "Primero corta un ítem", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    Toast.makeText(this, "Opción no válida", Toast.LENGTH_SHORT).show()
                }
            }
        }

        recyclerView.adapter = adapter
    }

    // Inflar el menú con la opción de comparar planetas
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_compare, menu)
        return true
    }

    // Manejar la selección de la opción "Comparar Planetas"
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_compare -> {
                // Iniciar la nueva actividad para la comparación de planetas
                val intent = Intent(this, ComparePlanetsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showPlanetComparisonDialog() {
        val dialogView = layoutInflater.inflate(R.layout.activity_compare_planets, null)

        val planetNames = planetData.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, planetNames)

        val planet1 = dialogView.findViewById<AutoCompleteTextView>(R.id.planet1)
        val planet2 = dialogView.findViewById<AutoCompleteTextView>(R.id.planet2)

        planet1.setAdapter(adapter)
        planet2.setAdapter(adapter)

        AlertDialog.Builder(this)
            .setTitle("Comparar Planetas")
            .setView(dialogView)
            .setPositiveButton("Comparar") { _, _ ->
                val planet1Name = planet1.text.toString()
                val planet2Name = planet2.text.toString()

                if (planetData.containsKey(planet1Name) && planetData.containsKey(planet2Name)) {
                    // Abrir la nueva actividad con los planetas seleccionados
                    val intent = Intent(this, ComparePlanetsDetailActivity::class.java)
                    intent.putExtra("planet1", planet1Name)
                    intent.putExtra("planet2", planet2Name)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Planeta no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

}
