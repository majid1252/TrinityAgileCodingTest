package com.example.trinity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubClient.R
import com.example.trinity.data.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ScreenOne : AppCompatActivity() {

    private val mContactsList = mutableListOf<Contact>()
    private lateinit var mRecyclerView: RecyclerView
    lateinit var mSwipeRefresh: SwipeRefreshLayout
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContactsList.addAll(parseJsonToEmployeeList())
        // init without binding
        mRecyclerView = findViewById(R.id.contactRecyclerView)
        mSwipeRefresh = findViewById(R.id.swipeRefresh)
        // result launcher for intent
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // get contact from the changed results
                val contact = result.data?.getParcelableExtra<Contact>(ScreenTwo.DATA_KEY)
                mContactsList.find { it.id == contact?.id }?.let {
                    // update the contact
                    it.firstName = contact?.firstName
                    it.lastName = contact?.lastName
                    it.email = contact?.email
                    it.dob = contact?.dob
                    // notify the adapter
                    mRecyclerView.adapter?.notifyItemChanged(mContactsList.indexOf(it))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initializeRecyclerView()
        mSwipeRefresh.setOnRefreshListener {
            initializeRecyclerView()
            mSwipeRefresh.isRefreshing = false
        }
    }
    private fun launchAnotherActivity(contact: Contact) {
        val intent = Intent(this, ScreenTwo::class.java).apply {
            putExtra(ScreenTwo.DATA_KEY, contact)
        }
        resultLauncher.launch(intent)
    }

    private fun initializeRecyclerView() {
        mRecyclerView.adapter = ContactAdapter(mContactsList){ contact ->
            // open details screen with intent
            launchAnotherActivity(contact)
        }
    }

    private fun parseJsonToEmployeeList(): List<Contact> {
        val jsonData = Constants.contactsList
        val gson = Gson()
        val employeeListType: Type = object : TypeToken<List<Contact>>() {}.type
        return gson.fromJson(jsonData, employeeListType)
    }
}