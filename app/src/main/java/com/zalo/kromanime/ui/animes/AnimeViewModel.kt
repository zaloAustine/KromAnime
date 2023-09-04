package com.zalo.kromanime.ui.animes


/**
Created by zaloaustine in 9/4/23.
 */
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zalo.kromanime.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    val animeList = animeRepository.getAnimeList()
        .cachedIn(viewModelScope)
        .asLiveData()

    // Use this function to refresh data from the remote API
    fun refreshData() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                animeRepository.getAnimeList()
            }
            result.onSuccess {
                // Data refreshed successfully
            }
            result.onFailure { exception ->
                val errorMessage = "An error occurred: ${exception.localizedMessage}"
            }
        }

    }

}
