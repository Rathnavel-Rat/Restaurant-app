package com.example.food.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food.Adaptor.AdaptorMenu
import com.example.food.Adaptor.RecylerAdaptorRest
import com.example.food.R
import com.example.food.util.Menus

class Hotelmenu : AppCompatActivity() {
lateinit var RecyclerMenu: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapmenu:AdaptorMenu
    val menulist=arrayListOf<Menus>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotelmenu)
        var id:String?=null
        var name:String?=null
        var toolbar=findViewById<Toolbar>(R.id.toolbars)
        val progressLayout = findViewById<RelativeLayout>(R.id.progressLayout)
        val progressBar=findViewById<ProgressBar>(R.id.progress)
        progressLayout.visibility= View.VISIBLE

        if(intent!=null)
        {
            id=intent.getStringExtra("hotel_id")
            name=intent.getStringExtra("resname")
        }
        supportActionBar?.title=name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        RecyclerMenu=findViewById(R.id.recyclermenu)
        layoutManager= LinearLayoutManager(this@Hotelmenu)

        val queue= Volley.newRequestQueue(this@Hotelmenu)
        val url= "http://13.235.250.119/v2/restaurants/fetch_result/$id"
        val jsonrequest=object:JsonObjectRequest(Request.Method.GET,url,null, Response.Listener{
            progressLayout.visibility= View.GONE
            val data=it.getJSONObject("data")
            val success=data.getBoolean("success")
            if (success) {
                val data = data.getJSONArray("data")
                println("response is $data")

                print("$data")
                for (i in 0 until data.length()) {
                    val bso = data.getJSONObject(i)
                    val bookObj = Menus(
                        bso.getString("id"),
                        bso.getString("name"),
                        bso.getString("cost_for_one"),
                        bso.getString("restaurant_id")

                    )
                    menulist.add(bookObj)
                    adapmenu =AdaptorMenu(this@Hotelmenu as Context, menulist)
                    RecyclerMenu.adapter = adapmenu
                    RecyclerMenu.layoutManager = layoutManager


                }}
        }, Response.ErrorListener {  }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["content-type"] = "application/json"
                headers["token"] = "d23aeaf212a0f0"
                return headers
            }
        }
        queue.add(jsonrequest)
    }
}
