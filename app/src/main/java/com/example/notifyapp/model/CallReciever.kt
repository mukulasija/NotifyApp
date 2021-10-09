package com.example.notifyapp.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast





class CallReciever : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

var oldstate : String?  = TelephonyManager.EXTRA_STATE_IDLE
        if(p1!!.getStringExtra(TelephonyManager.EXTRA_STATE)==TelephonyManager.EXTRA_STATE_OFFHOOK){
           showToastMsg(p0!!,"Phone call is started")
            oldstate = TelephonyManager.EXTRA_STATE
        }
        else
            if(p1!!.getStringExtra(TelephonyManager.EXTRA_STATE)==TelephonyManager.EXTRA_STATE_IDLE){

                showToastMsg(p0!!,"Phone call Ended")
                oldstate= TelephonyManager.EXTRA_STATE_IDLE
            }
        else
                if(p1!!.getStringExtra(TelephonyManager.EXTRA_STATE)==TelephonyManager.EXTRA_STATE_RINGING && oldstate!!.compareTo(TelephonyManager.EXTRA_STATE_IDLE)==0){
                    val phoneno = p1.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    showToastMsg(p0!!,"Incomming call"+phoneno)
                    oldstate = p1.getStringExtra(TelephonyManager.EXTRA_STATE)
                }
    }

    fun showToastMsg(c: Context, msg: String)
    {
        val toast = Toast.makeText(c,msg,Toast.LENGTH_LONG).show()
    }

}
