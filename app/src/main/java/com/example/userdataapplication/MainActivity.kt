package com.example.userdataapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db= Firebase.firestore




        saveBtn.setOnClickListener {
            var name = edTxtName.text.toString()
            var number = edTxtNumber.text.toString()
            var address = edTxtAddress.text.toString()
           var id = java.util.UUID.randomUUID().toString()
            addUserToDb(id,name, number, address)
            edTxtName.setText("")
            edTxtNumber.setText("")
            edTxtAddress.setText("")

        }
        dataIcon.setOnClickListener {
            var i= Intent(this,
                MainActivity2::class.java)
            startActivity(i)
        }



    }

    fun addUserToDb(id:String,name:String,number:String,address:String){
        val user= hashMapOf("id" to id,"name" to name,"number" to number,"address" to address)
        db.collection("Users")
                .add(user)
                .addOnSuccessListener {documentReference->
                    Toast.makeText(applicationContext,"User added successfully", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,"User does not added", Toast.LENGTH_SHORT).show()

                }
    }
}