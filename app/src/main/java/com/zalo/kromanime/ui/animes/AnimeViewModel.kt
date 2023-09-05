package com.zalo.kromanime.ui.animes


/**
Created by zaloaustine in 9/4/23.
 */
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zalo.kromanime.data.repository.AnimeRepository
import com.zalo.kromanime.utils.InternetUtils
import com.zalo.kromanime.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {
    private val _animeRefreshLiveEvent = SingleLiveEvent<String>()
    val animeRefreshLiveEvent: SingleLiveEvent<String>
        get() = _animeRefreshLiveEvent

    val animeList = animeRepository.getAnimeList()
        .cachedIn(viewModelScope)
        .asLiveData()

    fun refreshData() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                animeRepository.getAnimeList()
            }
            result.onSuccess {
                _animeRefreshLiveEvent.postValue("Refreshed")
            }
            result.onFailure {
                _animeRefreshLiveEvent.postValue("An error occurred")
            }

            if (!InternetUtils.isInternetAvailable()) {
                animeRefreshLiveEvent.postValue("No Internet. Swipe to refresh")
            }
        }
    }

    fun getFilteredAnimeList(query: String) = animeRepository.getFilteredAnimeList(query).asLiveData()

}
