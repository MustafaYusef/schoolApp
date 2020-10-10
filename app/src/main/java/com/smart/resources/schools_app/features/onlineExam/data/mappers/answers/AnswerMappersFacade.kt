package com.smart.resources.schools_app.features.onlineExam.data.mappers.answers

import com.smart.resources.schools_app.core.utils.mapList
import com.smart.resources.schools_app.core.utils.mapListIndexed
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.NetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.data.remote.model.PostNetworkAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.model.BaseAnswer
import com.smart.resources.schools_app.features.onlineExam.domain.repository.ListOfAnswers


class AnswerMappersFacade(
    private val localAnswerMapper: (LocalAnswer) -> BaseAnswer<out Any>,
    private val networkAnswerMapper: (NetworkAnswer) -> BaseAnswer<out Any>,
    private val answerToLocalMapper: (BaseAnswer<out Any>, String, String) -> LocalAnswer,
    private val answerToNetworkMapper: (BaseAnswer<out Any>, String, String) -> PostNetworkAnswer
) {

    fun mapLocalAnswer(input: LocalAnswer): BaseAnswer<out Any> = localAnswerMapper(input)
    fun mapNetworkAnswer(input: NetworkAnswer): BaseAnswer<out Any> = networkAnswerMapper(input)
    fun mapAnswerToLocal(
        input: BaseAnswer<out Any>,
        userId: String,
        questionId: String
    ): LocalAnswer = answerToLocalMapper(input, questionId, userId)

    fun mapAnswerToNetwork(
        input: BaseAnswer<out Any>,
        userId: String,
        questionId: String
    ): PostNetworkAnswer = answerToNetworkMapper(input, questionId, userId)

    fun mapNetworkToLocalAnswer(
        input: NetworkAnswer,
        userId: String,
        questionId: String
    ): LocalAnswer = mapAnswerToLocal(mapNetworkAnswer(input), userId, questionId)

    fun mapLocalAnswer(input: List<LocalAnswer>): ListOfAnswers = mapList(
        input = input,
        mapSingle = localAnswerMapper
    )

    fun mapNetworkAnswer(input: List<NetworkAnswer>): ListOfAnswers = mapList(
        input = input,
        mapSingle = networkAnswerMapper
    )

    fun mapAnswerToLocal(
        input: ListOfAnswers,
        questionIds: List<String>,
        userId: String,
    ): List<LocalAnswer> = mapListIndexed(
        input = input,
        mapSingle = { index, e ->
            answerToLocalMapper(e, userId, questionIds[index])
        }
    )

    fun mapAnswerToNetwork(
        input: ListOfAnswers,
        questionIds: List<String>,
        userId: String,
    ): List<PostNetworkAnswer> = mapListIndexed(
        input = input,
        mapSingle = { index, e ->
            answerToNetworkMapper(e, userId, questionIds[index])
        }
    )

    fun mapNetworkToLocalAnswer(
        input: List<NetworkAnswer>,
        questionIds: List<String>,
        userId: String,
    ): List<LocalAnswer> = mapListIndexed(
        input = input,
        mapSingle = { index, e ->
            mapNetworkToLocalAnswer(e, userId, questionIds[index])
        }
    )
}