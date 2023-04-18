package com.example.dvs.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dvs.R
import com.example.dvs.model.ChatModel

class ChatAdapter(private val mChat: List<ChatModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // View types for sent and received messages
    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            // Inflate the layout for a sent chat message item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.sent_msg, parent, false)
            SentMessageViewHolder(view)
        } else {
            // Inflate the layout for a received chat message item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.receive_msg, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Bind the data for a single chat message item to the UI elements in its corresponding ViewHolder object
        val mChatItem = mChat[position]

        if (holder is SentMessageViewHolder) {
            holder.sentMessage.text = mChatItem.content
        } else if (holder is ReceivedMessageViewHolder) {
            holder.receivedMessage.text = mChatItem.content
        }

    }

    override fun getItemViewType(position: Int): Int {
        // Determine the view type based on the message sender
        val mChatItem = mChat[position]
        return if (mChatItem.senderId == mUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        // Return the total number of chat messages that will be displayed in the RecyclerView
        return mChat.size
    }

    // Define a custom ViewHolder class that contains a reference to the UI elements in a single chat message item
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage: TextView = itemView.findViewById(R.id.tv_receive_msg)
        val senderMessage: TextView = itemView.findViewById(R.id.tv_sent_msg)
        val timestamp: TextView = itemView.findViewById(R.id.tv_timestamp)
    }

    // Define a custom ViewHolder class for sent chat messages
    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById(R.id.tv_sent_msg)
        val timestamp: TextView = itemView.findViewById(R.id.tv_timestamp)
    }

    // Define a custom ViewHolder class for received chat messages
    class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivedMessage: TextView = itemView.findViewById(R.id.tv_receive_msg)
        val timestamp: TextView = itemView.findViewById(R.id.tv_timestamp)
    }
}