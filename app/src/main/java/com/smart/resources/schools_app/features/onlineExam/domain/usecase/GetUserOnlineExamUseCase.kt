package com.smart.resources.schools_app.features.onlineExam.domain.usecase

import com.hadiyarajesh.flower.Resource
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.repository.IOnlineExamsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserOnlineExamUseCase @Inject constructor(
    private val onlineExamsRepository: IOnlineExamsRepository,
) : IGetOnlineExamUseCase {
    override fun invoke(examId: String): Flow<Resource<OnlineExam>> {
        return onlineExamsRepository.getOnlineExam(examId)
    }
}