package com.example.userdataapplication

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userdataapplication.adapter.UsersAdapter
import com.example.userdataapplication.model.Users
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class MainActivity2 : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        db= Firebase.firestore
        progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Loading ....")
        progressDialog.setCancelable(false)
        progressDialog.show()
        getUsers()
    }


    private fun getUsers(){
        val users=mutableListOf<Users>()
        db.collection("Users")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){

                    users.add(
                        Users(document.getString("id"),document.getString("name"),document.getString("number"),document.getString("address")
                            )
                    )

                    rvUsers.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,false)
                    rvUsers.setHasFixedSize(true)
                    val restAdapter = UsersAdapter(this,users)
                    rvUsers.adapter=restAdapter
                    progressDialog.dismiss()
                }

            }
            .addOnFailureListener { exception ->
                Log.e("nor", exception.message!!)
                progressDialog.dismiss()
            }
    }
}