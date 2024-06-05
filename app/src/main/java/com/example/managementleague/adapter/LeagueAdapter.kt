package com.example.managementleague.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managementleague.databinding.ItemLeagueBinding
import com.example.managementleague.model.entity.League

class LeagueAdapter(
    private val onClick: (l: League) -> Unit,
    private val onDelete: (League) -> Unit
):
    ListAdapter<League, LeagueAdapter.LeagueHost>(LEAGUE_COMPARATOR){
    inner class  LeagueHost(var binding: ItemLeagueBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(league: League){
            with(binding){
                txtTitulo.text=league.name
                txtTeamnumber.text=league.getTeamnumber()
                txtCreatedby.text= league.getUser()
                itemLeague.setOnClickListener {
                    onClick(league)
                }
                itemLeague.setOnLongClickListener {
                    onDelete(league)
                    true
                }
            }

        }
    }
    fun notifyChanged() {
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: LeagueHost, position: Int) {
        //Se accede a un elemento de la lista interna de adapterRv mendiante el método getItem(position)
        //Se accede a la lista interna midiante currentList

        var u = getItem(position)

        holder.bind(u)
    }

    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):LeagueHost{
        val layoutInflater = LayoutInflater.from(parent.context)
        return LeagueHost(ItemLeagueBinding.inflate(layoutInflater,parent,false))
    }
    companion object {
        private val LEAGUE_COMPARATOR = object : DiffUtil.ItemCallback<League>() {
            override fun areItemsTheSame(oldItem: League, newItem: League): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: League, newItem: League): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }
}