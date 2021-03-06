package com.example.notifyapp.model

import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast
import com.example.notifyapp.EnableActivity
import com.example.notifyapp.MainActivity
import com.example.notifyapp.Session
import com.example.notifyapp.Session.Companion.uid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class CallReciever : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

var oldstate : String?  = TelephonyManager.EXTRA_STATE_IDLE
        if(p1!!.getStringExtra(TelephonyManager.EXTRA_STATE)==TelephonyManager.EXTRA_STATE_OFFHOOK){
           showToastMsg(p0!!,"Phone call is started")
//            oldstate = TelephonyManager.EXTRA_STATE
        }
        else
            if(p1.getStringExtra(TelephonyManager.EXTRA_STATE)==TelephonyManager.EXTRA_STATE_IDLE){

                showToastMsg(p0!!,"Phone call Ended")
//                oldstate= TelephonyManager.EXTRA_STATE_IDLE
            }
        else
//                    && oldstate!!.compareTo(TelephonyManager.EXTRA_STATE_IDLE)==0
            {
//                val action: String = p1.getAction() ?: return
//
//                if (action != "android.intent.action.PHONE_STATE") return
//                var curState = p1.getStringExtra(TelephonyManager.EXTRA_STATE)
//                if(TelephonyManager.EXTRA_STATE_RINGING.equals(curState)&& TelephonyManager.EXTRA_STATE_IDLE.equals(oldstate)){
//                    val incNum : String = p1.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)!!
//                }
//                oldstate = curState
//
//                String curState = aIntent.getStringExtra(TelephonyManager.EXTRA_STATE);
//
//                if ((TelephonyManager.EXTRA_STATE_RINGING.equals(curState))
//                    &&(TelephonyManager.EXTRA_STATE_IDLE.equals(oldState)))){
//                String incNumber = aIntent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//
//                // do something here
//            }
//                oldState=curState;

                if (p1.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_RINGING) {

                    val phoneno = p1.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    if(!phoneno.equals("") && phoneno!=null && !phoneno.isEmpty()) {
                            showToastMsg(p0!!, "Incomming call" + phoneno)
                        val mdb : FirebaseDatabase = FirebaseDatabase.getInstance()
                        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                        val cur = mAuth.currentUser?.uid.toString()
                        val user = mdb.getReference("usersData").child(cur)
                        val id = user.push().key.toString()
                        val time = Calendar.getInstance().time
                        user.child(id).child("phone no").setValue(phoneno)
                        user.child(id).child("time").setValue(time)
                    }
                    }
                }
            }
    }

    fun showToastMsg(c: Context, msg: String)
    {
        val toast = Toast.makeText(c,msg,Toast.LENGTH_LONG).show()
    }


