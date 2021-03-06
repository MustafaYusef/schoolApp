package com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface HomeworkSolutionClient {

    @Multipart
    @POST("addSolution")
    suspend fun addSolution(
        @Part("homeworkId") homeworkId: RequestBody,
        @Part("studentId") studentId: RequestBody,
        @Part solutionImage: MultipartBody.Part?,
        @Part("solutionText") solutionText: RequestBody
    ): MyResult<HomeworkSolutionModel>

    @GET("homeworkSolution/{homeworkId}")
    suspend fun getHomeworkSolution(@Path("homeworkId") homeworkId:String): MyResult<List<HomeworkSolutionModel>>
}