package com.example.food.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food.R
import com.example.food.util.connectionManager
import org.json.JSONObject
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity(){
   lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mobile=findViewById<EditText>(R.id.et_mobile).text
        val password=findViewById<EditText>(R.id.et_password).text
        val but=findViewById<Button>(R.id.login)
        val forgpass=findViewById<TextView>(R.id.txt_forgotpassword)
        val signup=findViewById<TextView>(R.id.txt_reg)
        sharedPreferences=getSharedPreferences(getString(R.string.regin),Context.MODE_PRIVATE)
        val loggedin=sharedPreferences.getBoolean("loggedin",false)

        but.setOnClickListener {
            if(connectionManager().checkConnectivity(this@LoginActivity))
            {
               val queue=Volley.newRequestQueue(this@LoginActivity as Context)
                val url="http://13.235.250.119/v2/login/fetch_result"
                val jsonparam=JSONObject()
                jsonparam.put("mobile_number",mobile.toString())
                jsonparam.put("password",password.toString())

                val jsonObject=object :JsonObjectRequest(Request.Method.POST,url,jsonparam, Response.Listener {
                    var jsonObj=it.getJSONObject("data")
                    var item=jsonObj.getBoolean("success")

                    if(item)
                    {
                        sharedPreferences.edit().putBoolean("loggedin",true).apply()
                        val data=jsonObj.getJSONObject("data")
                        sharedPreferences.edit().putString("name",data.getString("name"))
                        sharedPreferences.edit().putString("userid",data.getString("user_id"))
                        sharedPreferences.edit().putString("email",data.getString("email"))
                        sharedPreferences.edit().putString("mobile",data.getString("mobile_number"))
                        sharedPreferences.edit().putString("address",data.getString("address"))
                       val intent=Intent(this@LoginActivity,RestActivity::class.java)
                        startActivity(intent)
                        finish()


                    }
                    else
                    {
                        Toast.makeText(this@LoginActivity,"opps Wrong Details",Toast.LENGTH_SHORT).show()
                    }



                },
                    Response.ErrorListener {  }){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["content-type"] = "application/json"
                        headers["token"] = "d23aeaf212a0f0"
                        return headers
                    }
                }
                queue.add(jsonObject)
            }
            else{
                val dialog= AlertDialog.Builder(this@LoginActivity  as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Please connect to internet")
                dialog.setPositiveButton("ok") { text,listener->
                    val settingsOpen= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsOpen)
                    this@LoginActivity.finish()

                }
                dialog.create()
                dialog.show()
            }
        }
        forgpass.setOnClickListener {
            val intent=Intent(this@LoginActivity,ForgotActivity::class.java)
            startActivity(intent)
        }
        signup.setOnClickListener {

            val intent=Intent(this@LoginActivity,RegistrationActivity::class.java)
            startActivity(intent)
        }

    }
}
