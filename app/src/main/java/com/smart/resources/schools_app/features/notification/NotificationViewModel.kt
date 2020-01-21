package com.smart.resources.schools_app.features.notification

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.profile.AccountManager
import kotlinx.coroutines.*


class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val listState = ListState()

    private val notifications: MutableLiveData<List<NotificationModel>>
            by lazy { MutableLiveData<List<NotificationModel>>() }

    fun getNotifications(notificationType: NotificationType):
            LiveData<List<NotificationModel>> {
        fetchNotifications(notificationType)

        return notifications
    }

    val userType= if(AccountManager.instance?.getCurrentUser()?.userType==0) UserType.STUDENT else UserType.TEACHER


    fun fetchNotifications(notificationType: NotificationType){

        viewModelScope.launch {
            listState.apply {
                setLoading(true)


            val result = GlobalScope.async {
                if(userType == UserType.STUDENT)getStudentNotifications(notificationType)
                else  BackendHelper.notificationDao.fetchTeacherNotifications()
            }.toMyResult()


                when (result) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_notifications))
                        else {
                            setLoading(false)
                            notifications.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }


        }
    }

    private suspend fun getStudentNotifications(notificationType: NotificationType) =
        with(BackendHelper.notificationDao){
            if (notificationType == NotificationType.STUDENT) fetchNotifications()
            else fetchSectionNotifications()
        }

}