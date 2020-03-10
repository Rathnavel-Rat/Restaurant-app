package com.example.food.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.food.Adaptor.RecylerAdaptorRest

import com.example.food.R
import com.example.food.R.id.scost
import com.example.food.util.Hotels
import com.example.food.util.connectionManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_rest.*
import kotlinx.android.synthetic.main.recyler_rest_single_row.*
import java.lang.Exception

class RestarurantFragment : Fragment() {
    lateinit var  RecylerRest:RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var Restadaptor:RecylerAdaptorRest
    val restlist=arrayListOf<Hotels>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
            // Inflate the layout for this fragment

            val view=inflater.inflate(R.layout.frag_res, container, false)


            RecylerRest= view?.findViewById(R.id.recyclerRest)!!
            layoutManager=LinearLayoutManager(activity)

            if(connectionManager().checkConnectivity(activity as Context))
            {
                val queue= Volley.newRequestQueue(activity as Context)
                val url="http://13.235.250.119/v2/restaurants/fetch_result/"
                val jsonrequest=object :JsonObjectRequest(Request.Method.GET,url,null, Response.Listener {

                    val data = it.getJSONObject("data")
                    val success=data.getBoolean("success")

                        if (success) {

                            val data = data.getJSONArray("data")
                            println("response is $data")

                            print("$data")
                            for (i in 0 until data.length()) {
                                val bso = data.getJSONObject(i)
                                val bookObj = Hotels(
                                    bso.getString("id"),
                                    bso.getString("image_url"),
                                    bso.getString("name"),
                                    bso.getString("rating"),
                                    bso.getString("cost_for_one")
                                )


                                restlist.add(bookObj)
                                Restadaptor = RecylerAdaptorRest(activity as Context, restlist)
                                RecylerRest.adapter = Restadaptor
                                RecylerRest.layoutManager = layoutManager


                            }
                            print(restlist)

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
                queue.add(jsonrequest)


            }
            else{
                val dialog= AlertDialog.Builder(this@RestarurantFragment  as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Please connect to internet")
                dialog.setPositiveButton("ok") { text,listener->
                    val settingsOpen= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsOpen)


                }
                dialog.create()
                dialog.show()
            }









            return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }


    }


