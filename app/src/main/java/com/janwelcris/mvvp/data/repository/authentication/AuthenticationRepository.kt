package com.janwelcris.mvvp.data.repository.authentication

import com.janwelcris.mvvp.data.DataState
import com.janwelcris.mvvp.model.response.SessionIDResponse
import com.janwelcris.mvvp.model.response.UserTokenResponse
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun getGuestSessionID(): Flow<DataState<SessionIDResponse>>

    suspend fun getUserToken(): Flow<DataState<UserTokenResponse>>
}