package com.janwelcris.mvvp.data.repository.movie

import com.janwelcris.mvvp.data.DataState
import com.janwelcris.mvvp.model.response.MoviesResponse
import com.janwelcris.mvvp.model.response.UserTokenResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPlayingNowMovies(page:Int): Flow<DataState<MoviesResponse>>

    suspend fun getPopularMovies(page:Int): Flow<DataState<MoviesResponse>>

    suspend fun getTopRatedMovies(page:Int): Flow<DataState<MoviesResponse>>

    suspend fun getUpcomingMovies(page:Int): Flow<DataState<MoviesResponse>>
}