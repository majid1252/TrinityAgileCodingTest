package com.example.trinity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.example.githubClient.R
import com.example.githubClient.databinding.ActivityScreenTwoBinding
import com.example.trinity.data.Contact

class ScreenTwo : AppCompatActivity() {

    private var mContact : Contact? = null

    companion object{
        const val DATA_KEY = "data_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_two)
        mContact = intent.getParcelableExtra(DATA_KEY)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.screen_two_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                saveAction()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAction() {
        // Set the result and finish the activity when done
        val resultIntent = Intent().apply {
            // Add any result data you need
            putExtra(DATA_KEY, createContactModel())
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun createContactModel(): Contact {
        val lastName = findViewById<EditText>(R.id.first_name).text.toString()
        val firstName = findViewById<EditText>(R.id.last_name).text.toString()
        val email = findViewById<EditText>(R.id.email).text.toString()
        val dob = findViewById<EditText>(R.id.DOB).text.toString()
        return Contact(mContact?.id, firstName, lastName, email, dob)
    }
}