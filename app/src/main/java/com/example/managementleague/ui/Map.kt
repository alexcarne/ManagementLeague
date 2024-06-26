package com.example.managementleague.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.managementleague.R
import com.example.managementleague.databinding.FragmentMapBinding
import com.example.managementleague.usecase.AddressViewmodel
import com.example.managementleague.usecase.LeagueAddViewModel
import com.example.managementleague.utils.MapManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Map : Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private val viewmodel: AddressViewmodel by activityViewModels()
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.buttonSave.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onMapReady(map: GoogleMap) {

        googleMap = map
        googleMap.setOnMapClickListener(this) // Set the map click listener

        CoroutineScope(Dispatchers.Main).launch {
            try {
                var ltlo: Pair<Double, Double>
                if (MapManager.CurrentLocalitation != null) {
                    ltlo = MapManager.CurrentLocalitation!!
                } else {
                    ltlo = MapManager().getLatLngFromAddress(
                        "Calle Pablo Picasso N13",
                        com.example.managementleague.R.string.google_maps_key.toString()
                    )!!
                }
                // Add an initial marker in the obtained location
                val location = LatLng(ltlo.first, ltlo.second)
                currentMarker = googleMap.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title("Ubicación actual")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                viewmodel.updateAddress(MapManager().getAddressFromLatLng(ltlo.first,ltlo.second,getString(R.string.google_maps_key)) ?: "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onMapClick(latLng: LatLng) {
        // Clear the existing marker if it exists
        currentMarker?.remove()

        // Add a new marker at the clicked location
        currentMarker = googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("New Marker")
        )


        // Obtener instancia de ViewModel


        // Lanzar una coroutine para obtener la dirección de forma asíncrona
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val address = withContext(Dispatchers.IO) {
                    MapManager().getAddressFromLatLng(
                        latLng.latitude,
                        latLng.longitude,
                        getString(R.string.google_maps_key)
                    )
                }
                println(address)
                viewmodel.updateAddress(address ?: "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Optionally move the camera to the new marker location
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

