package com.example.userdataapplication.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.userdataapplication.R
import com.example.userdataapplication.model.Users
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.user_details.view.*


class UsersAdapter(var activity: Activity, var data: MutableList<Users>) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>()  {

    lateinit var db: FirebaseFirestore
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img_user = itemView.img_user
        val name = itemView.name
        val number= itemView.number
        val address= itemView.address
        val tvDelete= itemView.delete



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.user_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        db= Firebase.firestore

        holder.img_user.text = data[position].name!![0].toUpperCase().toString()
        holder.name.text = data[position].name
        holder.number.text = data[position].number
        holder.address.text = data[position].address



        holder.tvDelete.setOnClickListener {
            var alertDialog= AlertDialog.Builder(activity)
            alertDialog.setTitle("Delete")
            alertDialog.setMessage("Are you sure?")
            alertDialog.setCancelable(false)
            alertDialog.setIcon(R.drawable.ic_baseline_delete_24)

            alertDialog.setPositiveButton("Yes") { dialogInterface, i ->
                db.collection("Users").whereEqualTo("id",data[position].id)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            db.collection("Users").document(querySnapshot.documents[0].id)
                                    .delete()
                                    .addOnSuccessListener { querySnapshot ->
                                        Toast.makeText(
                                                activity as Context,
                                                "deleted successfully",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                    }.addOnFailureListener { exception ->
                                        Toast.makeText(activity as Context, exception.message, Toast.LENGTH_SHORT)
                                                .show()
                                    }
                        }
                data.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(holder.adapterPosition,data.size)
            }

            alertDialog.setNegativeButton("No") { dialogInterface, i ->
                Toast.makeText(activity, "No", Toast.LENGTH_SHORT).show()
            }

            alertDialog.setNeutralButton("Cancel") { dialogInterface, i ->
                Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show()
            }

            alertDialog.create().show()

        }







    }






}