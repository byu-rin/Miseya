package com.example.miseya

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miseya.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    fun loadDustInfo(city: String, area: String) {
        viewModelScope.launch {
            try {
                val params = hashMapOf(
                    "sidoName" to city,
                    "stationName" to area,
                    "dataTerm" to "daily",
                    "ver" to "1.3",
                    "apiKey" to "YOUR_API_KEY"
                )
                val response = NetWorkClient.dustNetWork.getDust(params)
                response.response.dustBody.dustItem?.firstOrNull()?.let {
                    Log.d("DustInfo", "PM10: ${it.pm10Value}")
                }
            } catch (e: Exception) {
                Log.e("DustInfo", "Error fetching dust info", e)
            }
        }
    }
}