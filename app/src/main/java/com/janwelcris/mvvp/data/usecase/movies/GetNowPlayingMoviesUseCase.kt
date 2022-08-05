package com.janwelcris.mvvp.data.usecase.movies

import com.janwelcris.mvvp.data.repository.movie.MovieRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(pageNum: Int = 1) = repository.getPlayingNowMovies(pageNum)
}