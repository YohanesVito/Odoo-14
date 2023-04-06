package com.example.dvs.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dvs.R
import com.example.dvs.model.NewContactModel


class ContactAdapter(private val mContact: List<NewContactModel>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Inflate the layout for a single chat message item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)

        // Create a new ViewHolder object that contains a reference to the item's UI elements
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        // Bind the data for a single chat message item to the UI elements in its corresponding ViewHolder object
        val mContactItem = mContact[position]

        Glide.with(holder.itemView.context)
            .load(mContactItem.avatar)
            .circleCrop()
            .into(holder.avatar)

        holder.name.text = mContactItem.email

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(mContact[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        // Return the total number of chat messages that will be displayed in the RecyclerView
        return mContact.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: NewContactModel)
    }
    // Define a custom ViewHolder class that contains a reference to the UI elements in a single chat message item
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        val name: TextView = itemView.findViewById(R.id.tv_nama)
    }
}