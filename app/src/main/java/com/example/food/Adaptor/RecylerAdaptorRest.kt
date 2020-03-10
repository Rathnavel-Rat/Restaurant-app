package com.example.food.Adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Activity.Hotelmenu
import com.example.food.R
import com.example.food.fragments.RestarurantFragment
import com.example.food.util.Hotels
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyler_rest_single_row.view.*

class RecylerAdaptorRest(val context:Context,val itemList:ArrayList<Hotels>): RecyclerView.Adapter<RecylerAdaptorRest.RestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecylerAdaptorRest.RestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyler_rest_single_row, parent, false)
        return RestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size

    }


    override fun onBindViewHolder(holder: RecylerAdaptorRest.RestViewHolder, position: Int) {
        val hotel = itemList[position]
        holder.restname.text = hotel.name
        holder.price.text = "Rs."+hotel.cost
        holder.rate.text = hotel.rating
        Picasso.get().load(hotel.image).into(holder.ImageView)
        holder.llcontent.setOnClickListener {
          val intent= Intent( context,Hotelmenu::class.java)
            intent.putExtra("hotel_id",hotel.id)
            intent.putExtra("resname",hotel.name)

            context.startActivity(intent)
        }



    }

    class RestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ImageView: ImageView = view.findViewById(R.id.imgFoodImage)
        val restname: TextView = view.findViewById(R.id.Restname)
        val price: TextView = view.findViewById(R.id.RestPrice)
        val rate: TextView = view.findViewById(R.id.RestRating)
        val add: TextView = view.findViewById(R.id.Restadd)
        val llcontent:LinearLayout=view.findViewById(R.id.llcontent)
    }
}