package com.example.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity() {

     lateinit var database: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = ContactDatabase.getDatabase(this)
//        val database2 = ContactDatabase.getDatabase(this)


        GlobalScope.launch {
            database.contactDao().insertContact(Contact(0, "Jay Shri ram", "768888", Date(), 1))
        }

    }

    fun getData(view: View) {
        database.contactDao().getContact().observe(this) {
            Log.d("CheezyCode", it.toString())
        }
    }
}