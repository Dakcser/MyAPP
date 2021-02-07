package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.example.myapp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        login()

        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }



    }

    private fun login() {

        binding.loginButton.setOnClickListener {

            if(TextUtils.isEmpty(binding.usernameInput.text.toString())){
                binding.usernameInput.setError("Please enter username")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.passwordInput.text.toString())){
                binding.passwordInput.setError("Please enter password")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(binding.usernameInput.text.toString(), binding.passwordInput.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText( this@LoginActivity, "Login failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.registerText.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

        }
    }
}