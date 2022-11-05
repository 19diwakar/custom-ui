package co.diwakar.customui.data.remote

import co.diwakar.customui.domain.model.CustomUI
import retrofit2.http.GET

interface AppApi {
    @GET("/mobileapps/android_assignment.json")
    suspend fun getCustomUI(): CustomUI

    companion object {
        const val BASE_URL = "https://demo.ezetap.com/"
    }
}