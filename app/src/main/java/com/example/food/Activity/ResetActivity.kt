package com.example.food.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food.R
import com.example.food.util.connectionManager
import org.json.JSONObject

class ResetActivity : AppCompatActivity() {
    lateinit var mobile:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
         var otp=findViewById<EditText>(R.id.et_REotp)
        var conpass=findViewById<EditText>(R.id.et_REconpassword)
        var pass=findViewById<EditText>(R.id.et_REpassword)
        var next=findViewById<Button>(R.id.bt_REsubmit)
            mobile=intent.getStringExtra("mobile")
        next.setOnClickListener {
            if(connectionManager().checkConnectivity(this@ResetActivity)) {
                if (pass.text.toString() == conpass.text.toString()) {
                    val jsonparams = JSONObject()
                    val queue = Volley.newRequestQueue(this@ResetActivity)
                    jsonparams.put("mobile_number", mobile)


                    jsonparams.put("password", pass.text.toString())
                    jsonparams.put("otp", otp.text.toString())
                    val url = "http://13.235.250.119/v2/reset_password/fetch_result"

                    val jsonRequest = object :
                        JsonObjectRequest(Request.Method.POST, url, jsonparams, Response.Listener {
                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")

                            print("$it this $success")
                            if (success) {
                                val intent = Intent(this@ResetActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@ResetActivity,
                                    "CHECK AGAIN",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                            Response.ErrorListener { }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["content-type"] = "application/json"
                            headers["token"] = "d23aeaf212a0f0"
                            return headers
                        }
                    }
                    queue.add(jsonRequest)
                }
            }
            else{
                val dialog= AlertDialog.Builder(this@ResetActivity  as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Please connect to internet")
                dialog.setPositiveButton("ok") { text,listener->
                    val settingsOpen= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsOpen)
                    this@ResetActivity.finish()

                }
                dialog.create()
                dialog.show()
            }

    }
}}
