package com.janwelcris.mvvp.data.usecase.authentication

import com.janwelcris.mvvp.data.repository.authentication.AuthenticationRepository
import javax.inject.Inject

class GetSessionIDUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke() = repository.getGuestSessionID()
}