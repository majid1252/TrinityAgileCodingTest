package com.example.trinity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import com.example.githubClient.R
import com.example.githubClient.databinding.ActivityScreenTwoBinding
import com.example.trinity.data.Contact

class ScreenTwo : AppCompatActivity() {

    private var mContact: Contact? = null

    // for some reason the binding is not working
    private lateinit var etLastName: EditText
    private lateinit var etFirstName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etDob: EditText
    private lateinit var datePicker: AppCompatImageView

    companion object {
        const val DATA_KEY = "data_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_two)
        mContact = intent.getParcelableExtra(DATA_KEY)
        // find view by ids
        etLastName = findViewById(R.id.last_name)
        etFirstName = findViewById(R.id.first_name)
        etEmail = findViewById(R.id.email)
        etDob = findViewById(R.id.DOB)
        // update data if contact is not null
        mContact?.let {
            etLastName.setText(it.lastName)
            etFirstName.setText(it.firstName)
            etEmail.setText(it.email)
            etDob.setText(it.dob)
        }
        // set on click listener for date picker
        datePicker = findViewById(R.id.date_picker)
        datePicker.setOnClickListener {
            // show date picker
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        // show date picker
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            // set date
            etDob.setText("$dayOfMonth/${month + 1}/$year")
            Toast.makeText(this, "Date set", Toast.LENGTH_SHORT).show()
        }
        datePickerDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.screen_two_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                // if data is valid save
                if (dataIsValid())
                    saveAction()
                true
            }

            else             -> super.onOptionsItemSelected(item)
        }
    }

    // check validity of data which is mandatory first name and last name
    private fun dataIsValid(): Boolean {
        if (etLastName.text.toString().isEmpty()) {
            etLastName.error = "Last name is required"
            return false
        }
        if (etFirstName.text.toString().isEmpty()) {
            etFirstName.error = "First name is required"
            return false
        }
        return true
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
        val lastName = etLastName.text.toString()
        val firstName = etFirstName.text.toString()
        val email = etEmail.text.toString()
        val dob = etDob.text.toString()
        return Contact(mContact?.id, firstName, lastName, email, dob)
    }
}