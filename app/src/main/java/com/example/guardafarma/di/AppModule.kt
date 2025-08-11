package com.example.guardafarma.di

import android.content.Context
import com.example.guardafarma.data.local.FarmaciaService
import com.example.guardafarma.data.local.GuardiaService
import com.example.guardafarma.data.network.DirectionsApiService
import com.example.guardafarma.domain.repository.DirectionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFarmaciaService(@ApplicationContext context: Context): FarmaciaService {
        return FarmaciaService(context)
    }

    @Provides
    @Singleton
    fun provideGuardiaService(@ApplicationContext context: Context): GuardiaService {
        return GuardiaService(context)
    }

    // Configuracion de Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Directions Api Service
    @Provides
    @Singleton
    fun provideDirectionsApiService(retrofit: Retrofit): DirectionsApiService {
        return retrofit.create(DirectionsApiService::class.java)
    }
}