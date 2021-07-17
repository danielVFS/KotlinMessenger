package com.daniel

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button_register.setOnClickListener {
            performRegister()
        }

        already_have_and_account_text_view.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        select_photo_button_register.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPhotoUri = data.data

            // deprecated code for screen the image on button
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            val bitMapDrawable = BitmapDrawable(bitmap)
            select_photo_button_register.setBackgroundDrawable(bitMapDrawable)
        }
    }

    private fun performRegister() {
        val email = email_edit_text_register.text.toString()
        val password = password_edit_text_register.text.toString()

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email or Password must not be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("RegisterActivity", "createUserWithEmail:success")

                    uploadImageToFirebaseStorage()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("RegisterActivity", "createUserWithEmail:failure:failure", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "Failure to create user ${it.message}")
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if(selectedPhotoUri == null) return;

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/${filename}")

        ref.putFile(selectedPhotoUri!!)
            .addOnCompleteListener {
                Log.d("RegisterActivity", "uploadImage:success" )

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "File location $it" )

                    saveUserToDatabase(it.toString())
                }
            }
    }

    private fun saveUserToDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val email = email_edit_text_register.text.toString()

        val user = User(uid, email, profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Saved user to Firebase" )
            }
    }
}

class User(val uid: String, val username: String, val profileImageUrl: String)