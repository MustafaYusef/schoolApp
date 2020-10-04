package com.smart.resources.schools_app.core.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ClassCastException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefHelper @Inject constructor(@ApplicationContext context: Context) {
    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    var currentUserId: String?
        get() = try{
            mSharedPreferences.getString(USER_ID, "")
        }catch (e: ClassCastException){
            // in case of value with same key is stored but with different type like INT-> this happens in older versions of app
            ""
        }
        set(uid) {
                mSharedPreferences.edit().putString(USER_ID, uid).apply()
        }


    companion object {
        lateinit var instance: SharedPrefHelper
        private const val PREF_NAME = "mySettingsPref"
        private const val USER_ID = "userID"
//        fun init(context: Context) {
//            instance =
//                SharedPrefHelper(
//                    context
//                )
//        }
    }


}