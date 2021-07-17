package com.daniel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edit_text_login.text.toString()
            val password = password_edit_text_login.text.toString()

        }

        back_to_register_text_view.setOnClickListener {
            finish()
        }
    }
}