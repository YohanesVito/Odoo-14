package com.example.dvs.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dvs.R
import com.example.dvs.model.ChatModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val mChat: List<ChatModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // View types for sent and received messages
    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val mUserId = currentUser?.uid

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
        val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        val date = Date(mChatItem.messages[0].timestamp)
        if (holder is SentMessageViewHolder) {
            holder.sentMessage.text = mChatItem.messages[0].content
            // Format the date as a String and set it to the timestamp TextView
            holder.timestamp.text = dateFormat.format(date)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.receivedMessage.text = mChatItem.messages[0].content
            holder.timestamp.text = dateFormat.format(date)
        }

    }

    override fun getItemViewType(position: Int): Int {
        // Determine the view type based on the message sender
        val mChatItem = mChat[position]
        return if (mChatItem.messages[0].senderId == mUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        // Return the total number of chat messages that will be displayed in the RecyclerView
        return mChat.size
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