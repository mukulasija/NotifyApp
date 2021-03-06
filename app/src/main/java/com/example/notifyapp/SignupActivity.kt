package com.example.notifyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.example.notifyapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var session: Session
    lateinit var mdb : DatabaseReference
    private lateinit var binding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        makeclickable()
        session = Session(this)
        auth = Firebase.auth
        val mAuth= FirebaseAuth.getInstance()
        binding.signUpBtn.setOnClickListener {

            val email = binding.userEmailId.text.toString()
            val password = binding.password.text.toString()
            val confirmpass = binding.confirmPassword.text.toString()

            val inputResult =  validateInput(email, password,confirmpass)
            inputResult?.let {
                toast(inputResult)
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                val usid = mAuth.currentUser?.uid.toString()
                session.createLoginSession(usid)
                val ref = FirebaseDatabase.getInstance().getReference("users").child(usid)
                ref.child("email").setValue(email)
                val intent = Intent(this,EnableActivity::class.java)
                intent.putExtra("uid",usid)
                startActivity(intent)
                finishAffinity()
                toast("Signed Up successfully")
            }
        }
    }
    private fun makeclickable()
    {
        val spannableString = SpannableString(binding.alreadyUser.text)
        val clickableSpan = object : ClickableSpan()
        {
            override fun onClick(widget: View) {
                onBackPressed()
            }
        }
        spannableString.setSpan(clickableSpan,22,32, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.alreadyUser.text = spannableString
        binding.alreadyUser.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun validateInput(email: String, password: String,confirmpass : String): String?{

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
        if(confirmpass!=password)
        {
            return "Password and Confirm Password should be same"
        }
        return null
    }}