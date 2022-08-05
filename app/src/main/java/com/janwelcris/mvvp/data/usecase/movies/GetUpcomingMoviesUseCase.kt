package com.janwelcris.mvvp.data.usecase.movies

import com.janwelcris.mvvp.data.repository.movie.MovieRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(pageNum: Int = 1) = repository.getUpcomingMovies(pageNum)
}