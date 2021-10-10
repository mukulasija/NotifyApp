package com.example.notifyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notifyapp.databinding.ActivityEnableBinding
import com.example.notifyapp.model.CallReciever
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EnableActivity : AppCompatActivity()  {
    private lateinit var binding : ActivityEnableBinding
    public var userid : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnableBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fun sendValue() {
            userid = "some value"
        }
        val session = Session(this)
        val uid : String = intent.getStringExtra("uid").toString()
        val intent : Intent = Intent(this,CallReciever::class.java)
        intent.putExtra("userid",uid)
        val mdb : FirebaseDatabase = FirebaseDatabase.getInstance()
        val user = mdb.getReference("users").child(uid)
        binding.logout.setOnClickListener {
            session.logoutUser()
            finish()
        }
    }

}