package com.smart.resources.schools_app.features.onlineExam.data.remote.model

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class NetworkOnlineExam(
    val idOnlineExam: Long?,
    val teacherId: Long?,
    val date: LocalDate?,
    val time: LocalTime?,
    val examTime: Long?,
    val nuOfRequiredQuestion: Int?,
    val examKey: String?,
    val isFinished: Int?,
    val status: Int?
)