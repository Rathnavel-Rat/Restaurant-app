package com.example.food.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food.R
import com.example.food.util.connectionManager
import org.json.JSONArray
import org.json.JSONObject


class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        title="Register Yourself"
        var name =findViewById<EditText>(R.id.et_Rname).text
        var  email=findViewById<EditText>(R.id.et_REmail).text
        var address=findViewById<EditText>(R.id.et_RDeliveryAdd).text
        var mobile=findViewById<EditText>(R.id.et_RMobile).text
        var password=findViewById<EditText>(R.id.et_RPassword).text
        var cpassword=findViewById<EditText>(R.id.et_RConPassword).text
        val log=findViewById<Button>(R.id.bt_Register)
        val url=" http://13.235.250.119/v2/register/fetch_result"

        log.setOnClickListener {
            if (name.toString().isNullOrBlank()  && email.toString().isNullOrBlank() && address.toString().isNullOrBlank() && mobile.toString().isNullOrBlank() && password.toString().isNullOrBlank() && cpassword.toString().isNullOrBlank()) {
                Toast.makeText(this, "please Fill All", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if(!EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(this, "ops Seems not email Address", Toast.LENGTH_SHORT).show()
                }//Email validation
                else {
                    if (password.length > 4) {
                        if (password.toString().equals(cpassword.toString())) {

                            REG(
                                url,
                                name.toString(),
                                address.toString(),
                                mobile.toString(),
                                password.toString(),
                                email.toString()
                            )
                        } else {
                            Toast.makeText(this, "password doesn't match", Toast.LENGTH_SHORT)
                                .show()
                        }//password match validation end

                    } else {
                        Toast.makeText(this, "minimum 4 character is need", Toast.LENGTH_SHORT)
                            .show()
                    }//password length verify
                }
            }
        }//onclick end
    }
    fun REG(url:String,name:String,address:String,mobile:String,password:String,email:String)
    {

        if(connectionManager().checkConnectivity(this@RegistrationActivity as Context)) {
            print("connection manager  got")
            val queue = Volley.newRequestQueue(this@RegistrationActivity)

            val jsonParams=JSONObject()
            jsonParams.put("name", name)
            jsonParams.put("mobile_number", mobile)
            jsonParams.put("password", password)
            jsonParams.put("address",address)
            jsonParams.put("email", email)



            val jsonRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                val k=it.getJSONObject("data")
                print("$it")
                try {


                    if (k.getBoolean("success")) {
                        val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@RegistrationActivity,"login now",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RegistrationActivity,"mobile or email is already registerd",Toast.LENGTH_SHORT).show()
                    }
                }
                catch(e:Exception){
                    print("catvh $e")}

                },
                Response.ErrorListener {
                    Toast.makeText(this@RegistrationActivity," Try again Later",Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["content-type"] = "application/json"
                    headers["token"] = "d23aeaf212a0f0"
                    return headers
                }
            }
            queue.add(jsonRequest)


        }
        else
        {  Toast.makeText(this@RegistrationActivity,"no connection",Toast.LENGTH_SHORT).show()
            val dialog=AlertDialog.Builder(this@RegistrationActivity  as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Please connect to internet")
            dialog.setPositiveButton("ok") { text,listener->
                val settingsOpen=Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsOpen)
                this@RegistrationActivity.finish()

            }
            dialog.create()
            dialog.show()
        }


    }
}
