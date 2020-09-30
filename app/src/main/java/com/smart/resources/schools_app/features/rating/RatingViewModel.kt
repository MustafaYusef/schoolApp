package com.smart.resources.schools_app.features.rating

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.utils.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.login.CanLogout
import kotlinx.coroutines.*


class RatingViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val listState = ListState()

    private val rating: MutableLiveData<List<RatingModel>>
            by lazy { MutableLiveData<List<RatingModel>>() }

    fun getRatings():
            LiveData<List<RatingModel>> {
        fetchRatings()

        return rating
    }

    private fun fetchRatings() {
        viewModelScope.launch {
            listState.apply {
                setLoading(true)

                val result = GlobalScope.async {
                    RetrofitHelper.ratingClient.fetchRating()
                }.toMyResult()

                when (result) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_rating))
                        else {
                            setLoading(false)
                            rating.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }
}