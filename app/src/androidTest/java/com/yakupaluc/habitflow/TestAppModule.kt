package com.yakupaluc.habitflow.di

import android.content.Context
import androidx.room.Room
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.yakupaluc.habitflow.data.local.HabitDatabase
import com.yakupaluc.habitflow.data.local.dao.HabitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class, WorkManagerModule::class],
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideInMemoryDatabase(
        @ApplicationContext context: Context,
    ): HabitDatabase =
        Room.inMemoryDatabaseBuilder(context, HabitDatabase::class.java)
            .build()

    @Provides
    fun provideHabitDao(database: HabitDatabase): HabitDao = database.habitDao()

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context,
    ): WorkManager {
        val config = Configuration.Builder()
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        return WorkManager.getInstance(context)
    }
}