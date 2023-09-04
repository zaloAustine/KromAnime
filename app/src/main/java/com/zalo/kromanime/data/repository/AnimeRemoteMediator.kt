import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zalo.kromanime.data.api.ApiService
import com.zalo.kromanime.data.api.models.mappers.toAnimeEntity
import com.zalo.kromanime.data.database.AnimeDatabase
import com.zalo.kromanime.data.database.AnimeEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val apiService: ApiService,
    private val animeDatabase: AnimeDatabase
) : RemoteMediator<Int, AnimeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        try {
            val response = apiService.getAnimeList()

            return if (response.body()?.data != null) {
                animeDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        animeDatabase.animeDao().clearAll()
                    }
                    animeDatabase.animeDao().insertAll(response.body()?.data?.map { it.toAnimeEntity() } ?: emptyList())
                }

                MediatorResult.Success(endOfPaginationReached = response.body()!!.data!!.isEmpty())
            } else {
                MediatorResult.Error(IOException("Empty data response"))
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}
