package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.myapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    lateinit var auth: FirebaseAuth
    var  dataBaseReference: DatabaseReference? = null
    var dataBase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        dataBase = FirebaseDatabase.getInstance()
        dataBaseReference = dataBase?.reference!!.child("profile")

        register()
    }

    private fun register() {

        binding.registerButton.setOnClickListener {

            if(TextUtils.isEmpty(binding.firstnameInput.text.toString())){
                binding.firstnameInput.setError("Please enter first name")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.lastnameInput.text.toString())){
                binding.lastnameInput.setError("Please enter last name")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.usernameInput.text.toString())){
                binding.usernameInput.setError("Please enter username")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.passwordInput.text.toString())){
                binding.passwordInput.setError("Please enter password")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(binding.usernameInput.text.toString(), binding.passwordInput.text.toString())
                .addOnCompleteListener {

                    if(it.isSuccessful) {

                        val currentUser = auth.currentUser
                        val currentUserDb = dataBaseReference?.child((currentUser?.uid!!))

                        currentUserDb?.child("firstname")?.setValue(binding.firstnameInput.text.toString())
                        currentUserDb?.child("lastname")?.setValue(binding.lastnameInput.text.toString())

                        Toast.makeText( this@RegisterActivity, "Registration was successful! ", Toast.LENGTH_LONG).show()
                        finish()

                    }else {
                        Toast.makeText( this@RegisterActivity, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

}