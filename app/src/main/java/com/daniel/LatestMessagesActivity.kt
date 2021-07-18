package com.daniel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LatestMessagesActivity : AppCompatActivity() {
    companion object {
        const val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        supportActionBar?.title = Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.app_name) + "</font>")

        verifyIfUserIsLoggedIn()

        welcomeMessage()
    }

    private fun welcomeMessage() {
        val userEmail = Firebase.auth.currentUser?.email
        Toast.makeText(this, "Welcome: $userEmail", Toast.LENGTH_SHORT).show()
    }

    private fun verifyIfUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid

        if(uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_new_message -> {
                Log.d(TAG, "menu message clicked")
            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}