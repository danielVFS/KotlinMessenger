package com.daniel

import android.content.Intent
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
    companion object {
        const val TAG = "LoginActivity"
    }

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
                    Log.d(TAG, "signInWithEmail:success")

                    val intent = Intent(this, LatestMessagesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    Log.d(TAG, "signInWithEmail:failure", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failure to create user ${it.message}")
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}