package com.smart.resources.schools_app.features.login

import com.smart.resources.schools_app.core.util.*
import java.lang.Exception


object LoginRepository{
    private val accountDao=  BackendHelper.retrofit.create(LoginDao::class.java)

    suspend fun loginStudent(phoneNumber:String, password:String): MyResult<String>{
        return login(phoneNumber, password, UserType.STUDENT)
    }

    suspend fun loginTeacher(phoneNumber:String, password:String): MyResult<String>{
        return login(phoneNumber, password,  UserType.TEACHER)
    }

    private suspend fun login(
        phoneNumber: String,
        password: String,
        userType: UserType
    ): MyResult<String> {
        val requestBody = hashMapOf("phone" to phoneNumber, "password" to password)
        return try {

            val myRes = getLoginResult(userType, requestBody)
            if (myRes is Success) Success(myRes.data?.get("token")?.asString) else myRes as MyResult<Nothing>
        } catch (e: Exception) {
            ConnectionError(e)
        }
    }

    private suspend fun getLoginResult(
        userType: UserType,
        requestBody: HashMap<String, String>
    ) =
        with(accountDao) {
            if (userType == UserType.STUDENT) loginStudent(requestBody) else loginTeacher(
                requestBody
            )
        }.toMyResult()

}



