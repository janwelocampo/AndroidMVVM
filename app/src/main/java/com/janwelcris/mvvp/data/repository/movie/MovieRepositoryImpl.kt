package com.janwelcris.mvvp.data.repository.movie

import androidx.annotation.WorkerThread
import com.janwelcris.mvvp.data.DataState
import com.janwelcris.mvvp.data.remote.*
import com.janwelcris.mvvp.model.response.MoviesResponse
import com.janwelcris.mvvp.model.response.UserTokenResponse
import com.janwelcris.mvvp.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils,
    private val apiService: ApiService
) : MovieRepository {

    @WorkerThread
    override suspend fun getPlayingNowMovies(page: Int): Flow<DataState<MoviesResponse>> {
        return flow {
            apiService.getNowPlaying(page = page).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }

            }.onErrorSuspend {
                emit(DataState.error(message()))
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(DataState.error(stringUtils.somethingWentWrong()))
                }
            }
        }
    }

    @WorkerThread
    override suspend fun getPopularMovies(page: Int): Flow<DataState<MoviesResponse>> {
        return flow {
            apiService.getPopular(page = page).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }

            }.onErrorSuspend {
                emit(DataState.error(message()))
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(DataState.error(stringUtils.somethingWentWrong()))
                }
            }
        }
    }

    @WorkerThread
    override suspend fun getTopRatedMovies(page: Int): Flow<DataState<MoviesResponse>> {
        return flow {
            apiService.getTopRated(page = page).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }

            }.onErrorSuspend {
                emit(DataState.error(message()))
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(DataState.error(stringUtils.somethingWentWrong()))
                }
            }
        }
    }

    @WorkerThread
    override suspend fun getUpcomingMovies(page: Int): Flow<DataState<MoviesResponse>> {
        return flow {
            apiService.getUpcomingMovies(page = page).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }

            }.onErrorSuspend {
                emit(DataState.error(message()))
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(DataState.error(stringUtils.somethingWentWrong()))
                }
            }
        }
    }
}