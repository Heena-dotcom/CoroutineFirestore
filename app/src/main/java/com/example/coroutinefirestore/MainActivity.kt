package com.example.coroutinefirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class Person(
    val name: String = "",
    val age: Int = -1
)

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val testDocument = Firebase.firestore
            .collection("coroutines")
            .document("test")
        val jim = Person("Jim",25)
        GlobalScope.launch(Dispatchers.IO) {
            testDocument.set(jim).await()
            val person = testDocument.get().await().toObject(Person::class.java)
            withContext(Dispatchers.Main){
                findViewById<TextView>(R.id.tvData).text = person.toString()
            }
        }
    }
}