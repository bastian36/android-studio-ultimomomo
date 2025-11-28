package ui.blog

import model.BlogResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BlogApiService {
    @GET("api/blogs")
    suspend fun getBlogs(): BlogResponse
}

object BlogRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"
    
    val apiService: BlogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BlogApiService::class.java)
    }
}
