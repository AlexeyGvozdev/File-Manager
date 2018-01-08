package com.example.alexey.filemanager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Alexey on 07.01.2018.
 */
class MyAdapter(private val listener: (FileClass) -> Unit) : RecyclerView.Adapter<MyAdapter.ElementsViewHolder>() {

    fun OnItemClickListener(listener: (View) -> Unit) {}

    private var listElements: List<FileClass> = emptyList()
    fun setListElements(list: List<FileClass>) {
        this.listElements = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ElementsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_file, parent, false)
        return ElementsViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ElementsViewHolder, position: Int) {
        holder.bind(listElements[position])
//        holder.itemView.setOnClickListener { listener(h) }

    }


    override fun getItemCount(): Int = listElements.size


    class ElementsViewHolder(itemView: View, private val listener: (FileClass) -> Unit) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.iv_item)
        val nameElements: TextView = itemView.findViewById(R.id.tv_name_file)
        fun bind(elements: FileClass) {
            image.setImageResource( when (elements.isFolder) {
                true -> R.drawable.folder
                else ->R.drawable.musik
            })
            itemView.setOnClickListener { listener(elements) }
            nameElements.text = elements.nameFile

        }
    }
}