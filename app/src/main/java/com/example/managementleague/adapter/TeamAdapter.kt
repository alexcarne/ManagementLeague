package com.example.managementleague.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managementleague.databinding.ItemTeamBinding
import com.example.managementleague.model.entity.Team
import com.example.managementleague.model.repository.TeamRepository

class TeamAdapter(
    private val onDelete: (Team) -> Unit,

) : ListAdapter<Team, TeamAdapter.TeamHost>(TEAM_COMPARATOR) {
    inner class TeamHost(var binding: ItemTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team) {
            with(binding) {
                txtTeamName.text = team.name
                txtMatchLost.text = team.macth_lost.toString()
                txtMatchWins.text = team.macth_wins.toString()
                txtMatches.text = team.calculateTotalMaches().toString()
                itemTeam.setOnLongClickListener {
                    onDelete(team)
                    true
                }
                binding.imgAddWin.setOnClickListener {
                    team.addmacthwins()
                    TeamRepository.updateTeam(team)
                    notifyChanged()
                }
                binding.imgAddLost.setOnClickListener {
                    team.addmactlosr()
                    TeamRepository.updateTeam(team)
                    notifyChanged()
                }
            }
        }
    }

    fun notifyChanged() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TeamHost, position: Int) {
        //Se accede a un elemento de la lista interna de adapterRv mendiante el método getItem(position)
        //Se accede a la lista interna midiante currentList

        var u = getItem(position)

        holder.bind(u)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHost {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TeamHost(ItemTeamBinding.inflate(layoutInflater, parent, false))
    }

    companion object {
        private val TEAM_COMPARATOR = object : DiffUtil.ItemCallback<Team>() {
            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }
}
