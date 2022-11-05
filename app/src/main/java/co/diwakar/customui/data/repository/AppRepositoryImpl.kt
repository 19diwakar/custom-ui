package co.diwakar.customui.data.repository

import co.diwakar.customui.data.remote.AppApi
import co.diwakar.customui.domain.model.CustomUI
import co.diwakar.customui.domain.repository.AppRepository
import co.diwakar.customui.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val api: AppApi
) : AppRepository {
    override suspend fun getCustomUI(): Flow<Resource<CustomUI>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteData = try {
                api.getCustomUI()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Something went wrong!"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Something went wrong!"))
                null
            }

            remoteData?.let { data ->
                emit(Resource.Success(data = data))
            }
            emit(Resource.Loading(false))
        }
    }
}