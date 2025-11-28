package ui.profile

import model.ImageUploadRequest
import model.ImageUploadResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageApiService {
    @POST("/api/imagenes")
    suspend fun uploadImage(@Body request: ImageUploadRequest): ImageUploadResponse
    
    companion object {
        private const val BASE_URL = "http://10.0.2.2:3000" // Para emulador Android
        
        fun create(): ImageApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ImageApiService::class.java)
        }
    }
}
