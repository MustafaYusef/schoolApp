package com.smart.resources.schools_app.features.students

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.core.network.RetrofitHelper
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetClassStudentsUseCase @Inject constructor(): IGetClassStudentsUseCase {
    override suspend operator fun invoke(classId:String): ApiResponse<List<Student>> {
        return RetrofitHelper.studentClient.getStudentsByClassId(classId).first()
    }
}