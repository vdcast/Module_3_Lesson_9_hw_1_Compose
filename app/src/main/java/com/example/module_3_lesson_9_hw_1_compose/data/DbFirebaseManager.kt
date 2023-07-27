package com.example.module_3_lesson_9_hw_1_compose.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DbFirebaseManager : DbRepository {

    private val database = FirebaseDatabase.getInstance()

    override fun sendMessage(message: String) {
        val reference = database.reference.child("db").child("chat")
        val messageId = reference.push().key
        messageId?.let {
            reference.child(it).setValue(message)
        }
    }

    override fun getAllMessages(callback: DbCallback) {
        val messages = arrayListOf<String>()
        val reference = database.reference.child("db").child("chat")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messages.clear()
                for (item in snapshot.children) {
                    val value: String? = item.value as? String
                    value?.let {
                        messages.add(value)
                    }
                }
                callback.onAllMessagesReceived(messages = messages)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

interface DbCallback {
    fun onAllMessagesReceived(messages: ArrayList<String>)
}