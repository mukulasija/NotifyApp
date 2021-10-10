package com.example.notifyapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.notifyapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE,android.Manifest.permission.READ_CALL_LOG),369)
        }
        auth = Firebase.auth

        binding.loginbtn.setOnClickListener {
            val em= binding.email.text.toString()
            val pass = binding.password.text.toString()
            auth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener {
                if(it.isSuccessful)
                {
                    Toast.makeText(this,"user created",Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this, it.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}