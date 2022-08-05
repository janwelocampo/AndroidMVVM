package com.janwelcris.mvvp.di

import android.app.Application
import com.janwelcris.mvvp.data.remote.ApiService
import com.janwelcris.mvvp.data.repository.movie.MovieRepository
import com.janwelcris.mvvp.data.repository.movie.MovieRepositoryImpl
import com.janwelcris.mvvp.data.repository.authentication.AuthenticationRepository
import com.janwelcris.mvvp.data.repository.authentication.AuthenticationRepositoryImpl
import com.janwelcris.mvvp.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideStringUtils(app: Application): StringUtils {
        return StringUtils(app)
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(stringUtils: StringUtils, apiService: ApiService): AuthenticationRepository {
        return AuthenticationRepositoryImpl(stringUtils, apiService)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(stringUtils: StringUtils, apiService: ApiService): MovieRepository {
        return MovieRepositoryImpl(stringUtils, apiService)
    }
}