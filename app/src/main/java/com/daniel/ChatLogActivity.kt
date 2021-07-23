package com.daniel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {
    companion object {
        const val TAG = "ChatLogActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = Html.fromHtml("<font color=\"#ffffff\">${user?.username}</font>")

        setDummyData()

        sendbutton_chat_log.setOnClickListener {
            performSendMessage()
        }
    }

    private fun performSendMessage() {
        val text = edittext_chat_log.text.toString()

        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()

        val id = reference.key
        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user?.uid
        val timestamp = System.currentTimeMillis()

        if(fromId == null) return

        val chatMessage = ChatMessage(id!!, text, fromId, toId!!, timestamp)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message")
            }
    }

    private fun setDummyData() {
        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatFromItem("From Message"))
        adapter.add(ChatToItem("From to"))

        recyclerview_chat_log.adapter = adapter
    }
}