package com.example.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.contacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val launcher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getContacts()
            } else {
                Toast.makeText(this, "permission is not Granted", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()
    }

    private fun checkPermissions() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getContacts()
        } else {
            launcher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun getContacts() {
        val contentUri = ContactsContract.Contacts.CONTENT_URI
        val contactsProjection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
        )

        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val phoneProjection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val phoneSelection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?"

        val stringBuilder = StringBuilder()

        contentResolver.query(
            contentUri,
            contactsProjection,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )?.use { cursor ->

            val idIndex = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
            val nameIndex = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
            val hasPhoneIndex = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex) ?: "Без имени"
                val hasPhone = cursor.getInt(hasPhoneIndex) > 0
                val contactId = cursor.getString(idIndex)

                stringBuilder.append(name).append(": ")

                if (hasPhone) {
                    var hasAnyNumber = false

                    contentResolver.query(
                        phoneUri,
                        phoneProjection,
                        phoneSelection,
                        arrayOf(contactId),
                        null
                    )?.use { phoneCursor ->
                        val numberIndex = phoneCursor.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )

                        while (phoneCursor.moveToNext()) {
                            val number = phoneCursor.getString(numberIndex)
                            if (!number.isNullOrBlank()) {
                                if (hasAnyNumber) {
                                    stringBuilder.append(", ")
                                }
                                stringBuilder.append(number)
                                hasAnyNumber = true
                            }
                        }
                    }

                    if (!hasAnyNumber) {
                        stringBuilder.append("no phone")
                    }
                } else {
                    stringBuilder.append("no phone")
                }

                stringBuilder.append("\n")
            }
        }

        binding.textView.text = stringBuilder.toString()
    }
}