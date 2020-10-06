package com.example.proj_keep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.item_keep_simple.view.*
import lv.romstr.mobile.rtu_android.Note


class NotesRecyclerAdapter(private val items: List<Note>) :
    RecyclerView.Adapter<NotesRecyclerAdapter.NoteViewHolder>() {

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_keep_simple, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.itemView.textTitle.text = item.title
        /*  holder.itemView.shoppingQuantity.text = context.resources
             .getString(R.string.quantity_text, item.quantity, item.unit)
         */
        holder.itemView.setOnClickListener {
            Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
        }
    }
}