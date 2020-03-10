package com.example.food.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.food.R
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class profileFragment : Fragment() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPreferences=this.getActivity()!!.getSharedPreferences(getString(R.string.regin),Context.MODE_PRIVATE)!!
        val name=sharedPreferences.getString("name","")
        val email=sharedPreferences.getString("email","")
        val mobile=sharedPreferences.getString("mobile","")
        print("$name")
        val address=sharedPreferences.getString("address","")
        var n=view?.findViewById<TextView>(R.id.name)
        var e=view?.findViewById<TextView>(R.id.mail)
        var m=view?.findViewById<TextView>(R.id.mobile)
        val ad=view?.findViewById<TextView>(R.id.address)
        n?.text=name
        e?.text=email
        m?.text=mobile
        ad?.text=address
        return inflater.inflate(R.layout.fragment_profile2, container, false)
    }

}
