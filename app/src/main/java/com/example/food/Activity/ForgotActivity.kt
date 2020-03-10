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
import java.lang.Exception

class ForgotActivity : AppCompatActivity() {
    lateinit var Nnext:Button
lateinit var mobile:EditText
    lateinit var mail:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot2)
         Nnext=findViewById(R.id.bt_Fnext)
         mobile=findViewById(R.id.et_Fmobile)
         mail=findViewById(R.id.et_Fmail)
        Nnext.setOnClickListener {


            if(connectionManager().checkConnectivity(this@ForgotActivity))
            {
                val queue= Volley.newRequestQueue(this@ForgotActivity as Context)
                val jsonparam=JSONObject()
                jsonparam.put("mobile_number",mobile.text.toString())
                jsonparam.put("email",mail.text.toString())
                val url="http://13.235.250.119/v2/forgot_password/fetch_result"
                val jsonRequest=object: JsonObjectRequest(Request.Method.POST,url,jsonparam,Response.Listener {
                    print("$it")
                    val data=it.getJSONObject("data")
                    val obj=data.getBoolean("success")
                    print("$obj")
                    try{
                        if(obj)
                        {
                            val intent=Intent(this@ForgotActivity,ResetActivity::class.java)

                            intent.putExtra("mobile",mobile.text.toString())
                            startActivity(intent)



                            finish()
                        }
                        else{
                            Toast.makeText(this@ForgotActivity,"Wrong Details",Toast.LENGTH_SHORT).show()
                        }



                    }
                    catch(e:Exception)
                    {

                    }
                }, Response.ErrorListener {  })
                    {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["content-type"] = "application/json"
                            headers["token"] = "d23aeaf212a0f0"
                            return headers
                        }
                }
                queue.add(jsonRequest)


            }
            else{
                val dialog= AlertDialog.Builder(this@ForgotActivity  as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Please connect to internet")
                dialog.setPositiveButton("ok") { text,listener->
                    val settingsOpen= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsOpen)
                    this@ForgotActivity.finish()

                }
                dialog.create()
                dialog.show()
            }
        }
        }



        }


