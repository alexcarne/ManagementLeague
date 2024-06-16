package com.example.managementleague.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.managementleague.R
import com.example.managementleague.databinding.FragmentMapLeagueBinding
import com.example.managementleague.model.repository.LeagueRepository
import com.example.managementleague.model.repository.TeamRepository
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

class MapLeagueFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapLeagueBinding? = null
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null
    private val league = LeagueRepository.getLeagueList(TeamRepository.currentLeagueid)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapLeagueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val location = MapManager().getLatLngFromAddress(
                    league.address,
                    getString(R.string.google_maps_key)
                )
                if (location != null) {
                    val latLng = LatLng(location.first, location.second)
                    currentMarker = googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title("Ubicación liga")
                    )
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}