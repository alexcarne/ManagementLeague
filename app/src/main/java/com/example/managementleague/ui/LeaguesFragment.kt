package com.example.managementleague.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managementleague.R
import com.example.managementleague.adapter.LeagueAdapter
import com.example.managementleague.databinding.FragmentLeaguesBinding
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.repository.LeagueRepository
import com.example.managementleague.model.repository.TeamRepository
import com.example.managementleague.model.repository.UserRepository
import com.example.managementleague.usecase.LeagueFragmentViewmodel
import com.example.managementleague.utils.AuthManager

class LeaguesFragment : Fragment() {

    private var _binding: FragmentLeaguesBinding? = null
    lateinit var adapter: LeagueAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val viewModel: LeagueFragmentViewmodel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLeaguesBinding.inflate(inflater, container, false)
        binding.listLeagues.layoutManager = LinearLayoutManager(context)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LeagueAdapter({ l: League ->
            var bundle = Bundle().apply {
                putSerializable("league", l)
            }
            parentFragmentManager.setFragmentResult("key", bundle)
            findNavController().navigate(R.id.action_leaguesFragment_to_teamFragment)
        }) { l: League ->
            showDeleteConfirmationDialog(l)
        }
        binding.listLeagues.adapter = adapter
        viewModel.allleagues.observe(viewLifecycleOwner) {
            it.let { adapter.submitList(it) }
        }
        binding.btnAddLeagues.setOnClickListener {
            findNavController().navigate(R.id.action_leaguesFragment_to_addLeagues)
        }
    }

    private fun showDeleteConfirmationDialog(league: League) {

        val builder = AlertDialog.Builder(requireContext())
        if (league.user_id != UserRepository.getUserByEmail(AuthManager(requireContext()).getCurrentUser()!!.email!!).id) {
            builder.setTitle("No puede borrar una liga que no a creado")
            builder.setNegativeButton("Ok", null)
        } else {
            builder.setTitle("¿Deseas eliminar este Liga?")
            builder.setPositiveButton("Eliminar") { _, _ ->
                TeamRepository.deleteTeams(league.id)
                Thread.sleep(1000)
                LeagueRepository.deleteLeague(league)
                adapter.notifyChanged()
            }
            builder.setNegativeButton("Cancel", null)
        }






        builder.show()
    }

}
