package com.example.managementleague.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.repository.LeagueRepository

class LeagueFragmentViewmodel: ViewModel() {
    var allleagues = initialLeagues()
    private fun initialLeagues():LiveData<List<League>>{
        return LeagueRepository.getLeagueList().asLiveData()
    }
}