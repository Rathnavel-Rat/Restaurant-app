package com.example.food.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class connectionManager {
    fun checkConnectivity(context: Context):Boolean{
        val connectionManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val active:NetworkInfo?=connectionManager.activeNetworkInfo
        if(active?.isConnected!=null)
        {
            return active.isConnected
        }
        else
            return false
    }
}