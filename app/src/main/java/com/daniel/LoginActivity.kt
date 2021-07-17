package com.daniel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            performSignIn()
        }

        back_to_register_text_view.setOnClickListener {
            finish()
        }
    }

    private fun performSignIn() {
        val email = email_edit_text_login.text.toString()
        val password = password_edit_text_login.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("LoginActivity", "signInWithEmail:success")
                    val userEmail = Firebase.auth.currentUser?.email
                    Toast.makeText(this, "Welcome: $userEmail", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("LoginActivity", "signInWithEmail:failure", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Log.d("LoginActivity", "Failure to create user ${it.message}")
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}