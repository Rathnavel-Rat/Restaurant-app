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
import com.example.food.util.Hotels
import com.example.food.util.Menus
import com.squareup.picasso.Picasso

class AdaptorMenu (val context: Context, val itemList:ArrayList<Menus>): RecyclerView.Adapter<AdaptorMenu.MenuViewHolder>() {
 var count=0
 lateinit var idarray:ArrayList<String>
   lateinit var namearray:ArrayList<String>
   lateinit var costarray:ArrayList<String>
    var resid:String?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.simple_list_item_1, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: AdaptorMenu.MenuViewHolder, position: Int) {
        val menu = itemList[position]
        holder.id.text=menu.id
        holder.name.text=menu.name
        holder.cost.text=menu.price
        holder.layout?.visibility = View.GONE


    }
    class MenuViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val id=view.findViewById<TextView>(R.id.foodid)
        val name=view.findViewById<TextView>(R.id.foodName)
        val cost=view.findViewById<TextView>(R.id.foodPrice)
        val add=view.findViewById<Button>(R.id.add)
        val layout=view.findViewById<Button>(R.id.next)
    }

}



