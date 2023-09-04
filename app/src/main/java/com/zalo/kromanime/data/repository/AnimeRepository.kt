package com.zalo.kromanime.data.repository


/**
Created by zaloaustine in 9/4/23.
 */
import AnimeRemoteMediator
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.zalo.kromanime.data.api.ApiService
import com.zalo.kromanime.data.database.AnimeDatabase
import com.zalo.kromanime.data.database.AnimeEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AnimeRepository @Inject constructor(
    private val apiService: ApiService,
    private val animeDatabase: AnimeDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getAnimeList(): Flow<PagingData<AnimeEntity>> {
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = PREFETCH_DISTANCE
        )

        val remoteMediator = AnimeRemoteMediator(apiService, animeDatabase)

        return Pager(
            config = pagingConfig,
            remoteMediator = remoteMediator,
            pagingSourceFactory = { animeDatabase.animeDao().getAllAnime() }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 3
    }
}
