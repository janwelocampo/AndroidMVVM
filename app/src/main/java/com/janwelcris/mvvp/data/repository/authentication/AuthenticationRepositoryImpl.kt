package com.janwelcris.mvvp.data.repository.authentication

import androidx.annotation.WorkerThread
import com.janwelcris.mvvp.data.DataState
import com.janwelcris.mvvp.data.remote.*
import com.janwelcris.mvvp.model.response.SessionIDResponse
import com.janwelcris.mvvp.model.response.UserTokenResponse
import com.janwelcris.mvvp.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils,
    private val apiService: ApiService
) : AuthenticationRepository {

    @WorkerThread
    override suspend fun getGuestSessionID(): Flow<DataState<SessionIDResponse>> {
        return flow {
            apiService.getGuestSessionID().apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
            }.onErrorSuspend {
                emit(DataState.error<SessionIDResponse>(message()))

                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error<SessionIDResponse>(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(DataState.error(stringUtils.somethingWentWrong()))
                }
            }
        }
    }

    @WorkerThread
    override suspend fun getUserToken(): Flow<DataState<UserTokenResponse>> {
        return flow {
            apiService.getUserToken().apply {
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