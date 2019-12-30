package com.smart.resources.schools_app.database.dao

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path




interface AccountDao{
    companion object{
        private const val USERNAME_PATH= "username"
        private const val PASSWORD_PATH= "password"
    }

    @GET("login/{username}&{password}")
    suspend fun login(@Path(USERNAME_PATH) username:String, @Path(PASSWORD_PATH) password:String): Response<Unit>


}