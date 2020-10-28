package com.smart.resources.schools_app.features.addMark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.features.students.models.StudentWithMark
import kotlinx.coroutines.launch


class AddMarkViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val listState = ListState()

    private val exams: MutableLiveData<List<StudentWithMark>>
            by lazy { MutableLiveData<List<StudentWithMark>>()
            }


    fun getStudents(examId: Int):
            LiveData<List<StudentWithMark>> {

        fetchStudents(examId)
        return exams
    }


    private fun fetchStudents(examId:Int){
        viewModelScope.launch {
            listState.apply {
                setLoading(true)

                when (val result = RetrofitHelper.examClient.getResultsByExam(examId.toString())) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_students))
                        else {
                            setLoading(false)
                            exams.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }

}