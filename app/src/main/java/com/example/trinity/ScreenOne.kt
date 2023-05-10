package com.example.trinity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import androidx.recyclerview.widget.RecyclerView
import com.example.githubClient.R
import com.example.trinity.data.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ScreenOne : AppCompatActivity() {

    private val mContactsList = mutableListOf<Contact>()
    lateinit var mRecyclerView: RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContactsList.addAll(parseJsonToEmployeeList(Constants.contactsList))
        mRecyclerView = findViewById(R.id.contactRecyclerView)
    }

    override fun onStart() {
        super.onStart()
        mRecyclerView.adapter = ContactAdapter(mContactsList){ contact ->
            // open details screen with intent
            Intent(this, ScreenTwo::class.java).apply {
                putExtra(ScreenTwo.DATA_KEY, contact)
                startActivity(this)
            }
        }
    }

    private fun parseJsonToEmployeeList(jsonData: String): List<Contact> {
        val gson = Gson()
        val employeeListType: Type = object : TypeToken<List<Contact>>() {}.type
        return gson.fromJson(jsonData, employeeListType)
    }
}