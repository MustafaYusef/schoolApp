package com.smart.resources.schools_app.features.students.models

import com.google.gson.annotations.SerializedName

open class Student(
    @SerializedName("idStudent")
    val id: String,
    val name: String,
)