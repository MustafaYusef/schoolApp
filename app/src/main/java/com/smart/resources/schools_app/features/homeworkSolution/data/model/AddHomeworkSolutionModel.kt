package com.smart.resources.schools_app.features.homeworkSolution.data.model

import java.io.File

data class AddHomeworkSolutionModel(
    val solution:String="",
    val attachmentImage:File?
)