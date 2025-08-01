package com.example.guardafarma.di

import android.content.Context
import com.example.guardafarma.model.FarmaciaDTO
import com.example.guardafarma.model.GuardiaDTO
import com.example.guardafarma.repository.FarmaciaService
import com.example.guardafarma.repository.GuardiaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
}