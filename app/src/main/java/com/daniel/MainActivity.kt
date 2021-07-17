package com.daniel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button_register.setOnClickListener {
            val email = email_edit_text_register.text.toString()
            val password = password_edit_text_register.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email or Password must not be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("MainActivity", "signInWithEmail:success")
                    } else {
                        Log.d("MainActivity", "signInWithEmail:failure", it.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Log.d("MainActivity", "Failure to create user ${it.message}")
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        already_have_and_account_text_view.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}