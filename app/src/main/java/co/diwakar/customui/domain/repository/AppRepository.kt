package co.diwakar.customui.domain.repository

import co.diwakar.customui.domain.model.CustomUI
import co.diwakar.customui.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getCustomUI(): Flow<Resource<CustomUI>>
}