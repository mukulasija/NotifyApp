package com.example.notifyapp

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
    private lateinit var session : Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE,android.Manifest.permission.READ_CALL_LOG),369)
        }
        auth = Firebase.auth
        session = Session(this)
        if(session.isLoggedin())
        {
            var intent = Intent(applicationContext, SignupActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("uid", session.getUserid())
            startActivity(intent)
            finish()
        }
        binding.signupBtn.setOnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener {
            val em= binding.loginEmailid.text.toString()
            val pass = binding.loginPassword.text.toString()
            auth.signInWithEmailAndPassword(em, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("Logged in Successfully")
                    // Toast.makeText(this, "tudu tudu", Toast.LENGTH_SHORT).show()
                    val usid = auth.currentUser!!.uid.toString()
                    //toast(usid)
                    session.createLoginSession(usid)
                    val i = Intent(this, SignupActivity::class.java)
                    i.putExtra("uid", usid)
                    startActivity(i)
                    finish()
                } else {
                    toast("${task.exception?.message}")
                }
            }
//            auth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener {
//                if(it.isSuccessful)
//                {
//                    Toast.makeText(this,"user created",Toast.LENGTH_LONG).show()
//                }
//                else
//                {
//                    Toast.makeText(this, it.exception?.message,Toast.LENGTH_LONG).show()
//                }
//            }
        }


    }
    private fun validateInput(email: String, password: String): String? {

        if (email.isEmpty()) {
            return "Email cannot be empty"
        }

        if (!email.isEmailValid()) {
            return "Email is not valid"
        }

        if (password.isEmpty()) {
            return "Password cannot be empty"
        }

        if (password.length < 8 || password.length >= 20) {
            return "Password must be at least 8 characters"
        }

        return null
    }
}

private fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
