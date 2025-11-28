package ui.products

import model.ApiProductResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProductApiService {
    @GET("api/producto")
    suspend fun getProducts(): ApiProductResponse
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"
    
    val apiService: ProductApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }
}
