package com.example.managementleague.utils

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class MapManager {
    var CreatingLatLong: LatLng? = null
    suspend fun getLatLngFromAddress(address: String, apiKey: String): Pair<Double, Double>? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "https://maps.googleapis.com/maps/api/geocode/json?address=${address.replace(" ", "+")}&key=$apiKey"

            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseData = response.body?.string() ?: return@withContext null
                val json = JSONObject(responseData)
                val location = json.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location")

                val lat = location.getDouble("lat")
                val lng = location.getDouble("lng")

                return@withContext Pair(lat, lng)
            }
        }
    }

    suspend fun getAddressFromLatLng(lat: Double, lng: Double, apiKey: String): String? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=$lat,$lng&key=$apiKey"

            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseData = response.body?.string() ?: return@withContext null
                val json = JSONObject(responseData)
                val results = json.getJSONArray("results")
                if (results.length() > 0) {
                    val address = results.getJSONObject(0).getString("formatted_address")
                    return@withContext address
                }
                return@withContext null
            }
        }
    }
}