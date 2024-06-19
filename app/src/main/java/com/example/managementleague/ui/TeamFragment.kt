package com.example.managementleague.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managementleague.R
import com.example.managementleague.adapter.TeamAdapter
import com.example.managementleague.databinding.FragmentTeamBinding
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.entity.Team
import com.example.managementleague.model.repository.LeagueRepository
import com.example.managementleague.model.repository.TeamRepository
import com.example.managementleague.model.repository.UserRepository
import com.example.managementleague.usecase.TeamFragmentViewmodel
import com.example.managementleague.utils.AuthManager

class TeamFragment : Fragment() {
    private var _binding: FragmentTeamBinding? = null
    lateinit var adapter: TeamAdapter
    private lateinit var allteams: LiveData<List<Team>>
    lateinit var league: League

    private val viewModel: TeamFragmentViewmodel by viewModels()
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        binding.listTeam.layoutManager = LinearLayoutManager(context)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this

        // Setup adapter
        adapter = TeamAdapter({ team: Team ->
            showDeleteConfirmationDialog(team)
        }, { team: Team ->
            if (league.user_id == UserRepository.getUserByEmail(AuthManager(requireContext()).getCurrentUser()!!.email!!).id) {
                team.addmacthwins()
                TeamRepository.updateTeam(team)
                adapter.notifyChanged()
            }
        }, { team: Team ->
            if (league.user_id == UserRepository.getUserByEmail(AuthManager(requireContext()).getCurrentUser()!!.email!!).id) {
                team.addmactlosr()
                TeamRepository.updateTeam(team)
                adapter.notifyChanged()
            }
        })
        binding.listTeam.adapter = adapter
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup navigation listener
        binding.bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    findNavController().navigate(R.id.action_teamFragment_to_mapLeagueFragment)
                }
            }
        }

        // Fragment result listener to receive league object
        parentFragmentManager.setFragmentResultListener(
            "key",
            this,
            FragmentResultListener { _, result ->
                league = result.getSerializable("league") as League
                TeamRepository.currentLeagueid = league.id
                LeagueRepository.league = league
                allteams = initialTeams()
                observeTeams()
            }
        )

        // Floating action button listener
        binding.floatingActionButton.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("league", league)
            }
            parentFragmentManager.setFragmentResult("key", bundle)
            findNavController().navigate(R.id.action_teamFragment_to_addTeam)
        }
    }

    // Method to observe teams LiveData
    private fun observeTeams() {
        allteams.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_team, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                findNavController().navigate(R.id.action_teamFragment_to_addLeagues)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        TeamRepository.currentLeagueid = league.id
        LeagueRepository.league = null
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (this::league.isInitialized) {
            allteams = initialTeams()
            observeTeams()
        }
    }

    private fun initialTeams(): LiveData<List<Team>> {
        return TeamRepository.getTeamList(league.id).asLiveData()
    }

    private fun showDeleteConfirmationDialog(team: Team) {

        val builder = AlertDialog.Builder(requireContext())
        if (league.user_id == UserRepository.getUserByEmail(AuthManager(requireContext()).getCurrentUser()!!.email!!).id) {
            builder.setTitle("¿Deseas eliminar este Equipo?")
            builder.setPositiveButton("Eliminar") { _, _ ->
                TeamRepository.deleteTeam(team)
                league.minusTeam()
                LeagueRepository.updateLeague(league)
                adapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("Cancel", null)
        } else if (team.user_id == UserRepository.getUserByEmail(AuthManager(requireContext()).getCurrentUser()!!.email!!).id) {
            builder.setTitle("¿Deseas eliminar este Equipo?")
            builder.setPositiveButton("Eliminar") { _, _ ->
                TeamRepository.deleteTeam(team)
                league.minusTeam()
                LeagueRepository.updateLeague(league)
                adapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("Cancel", null)
        } else {
            builder.setTitle("No tiene permisos para eliminar este equipo")
            builder.setNegativeButton("Ok", null)
        }






        builder.show()
    }
}
