package com.janwelcris.mvvp.data.remote

import com.janwelcris.mvvp.model.response.MoviesResponse
import com.janwelcris.mvvp.model.response.SessionIDResponse
import com.janwelcris.mvvp.model.response.UserTokenResponse
import com.janwelcris.mvvp.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("authentication/token/new")
    suspend fun getUserToken(
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<UserTokenResponse>

    @GET("authentication/token/new")
    suspend fun getGuestSessionID(
        @Query("api_key") apiKey: String = API_KEY
    ): ApiResponse<SessionIDResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): ApiResponse<MoviesResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): ApiResponse<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): ApiResponse<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
    ): ApiResponse<MoviesResponse>



    companion object {
        const val BASE_API_URL = "https://api.themoviedb.org/3/"
    }
}