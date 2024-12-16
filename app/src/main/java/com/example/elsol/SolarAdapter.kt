package com.example.elsol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para un [RecyclerView] que muestra una lista de elementos solares.
 *
 * @param images Lista mutable de elementos [SolarItem] que contienen la información de cada ítem.
 * @param onOptionSelected Callback para manejar las acciones seleccionadas desde el menú de cada elemento.
 */
class SolarAdapter(
    private val images: MutableList<SolarItem>,
    private val onOptionSelected: (String, Int) -> Unit
) : RecyclerView.Adapter<SolarAdapter.SolarViewHolder>() {

    /**
     * Representa un ítem de la lista, con su recurso de imagen y título.
     *
     * @param imageRes Recurso de imagen asociado al ítem.
     * @param title Título del ítem.
     */
    data class SolarItem(val imageRes: Int, val title: String)

    /**
     * ViewHolder para gestionar las vistas de cada elemento del RecyclerView.
     *
     * @param itemView La vista asociada al elemento.
     */
    inner class SolarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val toolbar: Toolbar = itemView.findViewById(R.id.toolbar)
    }

    /**
     * Crea un nuevo [SolarViewHolder] inflando el diseño del elemento.
     *
     * @param parent El [ViewGroup] al que se adjuntará la nueva vista.
     * @param viewType Tipo de vista, útil para manejar múltiples diseños (no utilizado aquí).
     * @return Un nuevo [SolarViewHolder] inicializado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return SolarViewHolder(view)
    }

    /**
     * Vincula los datos del ítem a las vistas del [SolarViewHolder].
     *
     * @param holder El [SolarViewHolder] que se actualizará.
     * @param position La posición del ítem en la lista.
     */
    override fun onBindViewHolder(holder: SolarViewHolder, position: Int) {
        val item = images[position]
        holder.imageView.setImageResource(item.imageRes)
        holder.textView.text = item.title

        //inflar el menú
        holder.toolbar.inflateMenu(R.menu.menu_card)

        //configurar el listener para el menú
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

    /**
     * Obtiene el número total de elementos en la lista.
     *
     * @return El tamaño de la lista de elementos.
     */
    override fun getItemCount() = images.size
}
