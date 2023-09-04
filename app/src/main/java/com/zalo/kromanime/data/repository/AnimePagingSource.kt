import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zalo.kromanime.data.api.ApiService
import com.zalo.kromanime.data.api.models.mappers.toAnimeEntity
import com.zalo.kromanime.data.database.AnimeDao
import com.zalo.kromanime.data.database.AnimeEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class AnimePagingSource(
    private val apiService: ApiService,
    private val animeDao: AnimeDao
) : RemoteMediator<Int, AnimeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        try {
            val page = 1 // Always request the first page

            val response = apiService.getAnimeList()
            if (response.isSuccessful) {
                val animeList = response.body()?.data ?: emptyList()
                return insertDataToDatabase(animeList.map { it.toAnimeEntity() })
            } else {
                return MediatorResult.Error(HttpException(response))
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun insertDataToDatabase(data: List<AnimeEntity>): MediatorResult {
        animeDao.insertAll(data)
        return MediatorResult.Success(endOfPaginationReached = data.isEmpty())
    }
}
