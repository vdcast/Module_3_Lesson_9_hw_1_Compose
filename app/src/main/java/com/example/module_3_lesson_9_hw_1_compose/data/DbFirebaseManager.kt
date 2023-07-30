package com.example.module_3_lesson_9_hw_1_compose.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener

class DbFirebaseManager : DbRepository {

    private val database = FirebaseDatabase.getInstance()
//    private val auth = FirebaseAuth.getInstance()

    override fun sendMessageOld(message: String) {
        val reference = database.reference.child("db").child("chat")
        val messageId = reference.push().key
        messageId?.let {
            reference.child(it).setValue(message)
        }
    }
    override fun getAllMessagesOld(callback: DbCallbackAllMessagesReceivedOld) {
        val messages = arrayListOf<String>()
        val reference = database.reference.child("db").child("chat")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messages.clear()
                for (item in snapshot.children) {
                    val value: String? = item.getValue(String::class.java)
                    value?.let {
                        messages.add(value)
                    }
                }
                callback.onAllMessagesReceivedOld(messages = messages)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun createUser(username: String, password: String) {
        val reference = database.reference.child("db").child("users")
        reference.child(username).setValue(User(username = username, password = password))
    }
    override fun login(
        username: String,
        password: String,
        callbackLoginSuccessful: DbCallbackLoginSuccessful,
        callbackWrongPassword: DbCallbackWrongPassword,
        callbackUserNotFound: DbCallbackUserNotFound
    ) {
        val reference = database.reference.child("db").child("users").child(username)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    if (user.password == password) {
                        Log.d("MYLOG", "Successful login")
                        callbackLoginSuccessful.onSuccessfulLogin(user.username)
                    } else {
                        callbackWrongPassword.onWrongPassword()
                        Log.d("MYLOG", "Wrong password")
                    }
                } else {
                    callbackUserNotFound.onUserNotFound()
                    Log.d("MYLOG", "User not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun sendMessage(sender: String, content: String) {
        val reference = database.reference.child("db").child("chats")
            .child("messages")
        val messageId = reference.push().key
        messageId?.let {
            val message = hashMapOf(
                "sender" to sender,
                "content" to content,
                "timestamp" to ServerValue.TIMESTAMP
            )
            reference.child(it).setValue(message)
        }
    }

    override fun getAllMessages(callback: DbCallbackAllMessagesReceived) {
        val messages = arrayListOf<Message>()
        val reference = database.reference.child("db").child("chats")
            .child("messages")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messages.clear()
                for (item in snapshot.children) {
                    val value: Message? = item.getValue(Message::class.java)
                    value?.let {
                        messages.add(value)
                    }
                }
                messages.sortBy { it.timestamp }
                callback.onAllMessagesReceived(messages = messages)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("MYLOG", "2 | Messages not received :(")
            }

        })
    }
}

interface DbCallbackAllMessagesReceivedOld {
    fun onAllMessagesReceivedOld(messages: ArrayList<String>)
}
interface DbCallbackAllMessagesReceived {
    fun onAllMessagesReceived(messages: ArrayList<Message>)
}
interface DbCallbackLoginSuccessful {
    fun onSuccessfulLogin(currentUser: String)
}
interface DbCallbackWrongPassword {
    fun onWrongPassword()
}
interface DbCallbackUserNotFound {
    fun onUserNotFound()
}