package com.example.elsol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class SolarAdapter(
    private val images: MutableList<SolarItem>,
    private val onOptionSelected: (String, Int) -> Unit
) : RecyclerView.Adapter<SolarAdapter.SolarViewHolder>() {

    data class SolarItem(val imageRes: Int, val title: String)

    inner class SolarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val toolbar: Toolbar = itemView.findViewById(R.id.toolbar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return SolarViewHolder(view)
    }

    override fun onBindViewHolder(holder: SolarViewHolder, position: Int) {
        val item = images[position]
        holder.imageView.setImageResource(item.imageRes)
        holder.textView.text = item.title

        // Inflar el menú
        holder.toolbar.inflateMenu(R.menu.menu_card)

        // Configurar el listener para el menú
        holder.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.rename -> {
                    onOptionSelected("Renombrar", position)
                    true
                }
                R.id.copy -> {
                    onOptionSelected("Copiar", position)
                    true
                }
                R.id.cut -> {
                    onOptionSelected("Cortar", position)
                    true
                }
                R.id.delete -> {
                    onOptionSelected("Eliminar", position)
                    true
                }
                R.id.move -> {
                    onOptionSelected("Mover", position)
                    true
                }
                else -> false
            }
        }
    }

    override fun getItemCount() = images.size
}
