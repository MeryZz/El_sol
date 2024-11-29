package com.example.elsol

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var solarItems: MutableList<SolarAdapter.SolarItem>
    private lateinit var adapter: SolarAdapter

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

    // Variable global para guardar el ítem cortado
    private var cutItemGlobal: SolarAdapter.SolarItem? = null
}

