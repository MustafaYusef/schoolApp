package com.smart.resources.schools_app.features.onlineExam.di

import com.smart.resources.schools_app.core.appDatabase.AppDatabase
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.AnswersDao
import com.smart.resources.schools_app.features.onlineExam.data.mappers.answers.*
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.AnswersClient
import com.smart.resources.schools_app.features.onlineExam.data.repository.AnswersRepository
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IAnswersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AnswersModule {
    @Provides
    fun provideAnswersDao(appDatabase: AppDatabase): AnswersDao {
        return appDatabase.getAnswersDao()
    }

    @Provides
    fun provideAnswersClient(): AnswersClient {
        return RetrofitHelper.answersClient
    }

    @Provides
    fun provideAnswersRepository(
        AnswersDao: AnswersDao,
        AnswersClient: AnswersClient,
        answerMappersFacade: AnswerMappersFacade
    ): IAnswersRepository {
        return AnswersRepository(
            answersDao = AnswersDao,
            answersClient = AnswersClient,
            answerMappersFacade = answerMappersFacade,
        )
    }

    @Provides
    fun provideAnswersFacade() = AnswerMappersFacade(
        localAnswerMapper = ::mapLocalAnswer,
        networkAnswerMapper = ::mapNetworkAnswer,
        answerToLocalMapper = {answer, questionId, userId ->
            mapAnswerToLocal(answer, questionId, userId)
        },
        answerToNetworkMapper = {answer, questionId, userId ->
            mapAnswerToNetwork(answer, questionId, userId)
        },
    )
}