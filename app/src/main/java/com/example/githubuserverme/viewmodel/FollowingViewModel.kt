package com.example.githubuserverme.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserverme.api.ApiConfig
import com.example.githubuserverme.model.ResponseFollowerItem
import com.example.githubuserverme.model.ResponseFollowingItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    private val _followinguser = MutableLiveData<List<ResponseFollowingItem>>()
    val followinguser: LiveData<List<ResponseFollowingItem>> = _followinguser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =   _isLoading

    fun findUserFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.endPoint.getFollowingUser(query)
        client.enqueue(object : Callback<List<ResponseFollowingItem>> {
            override fun onResponse(
                call: Call<List<ResponseFollowingItem>>,
                response: Response<List<ResponseFollowingItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followinguser.value = response.body()
                } else {
                    Log.e(TAG, "On Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseFollowingItem>>, t: Throwable) {
                Log.e(TAG, "On Failure : ${t.message.toString()}")
            }

        })

    }


    companion object{
        private const val TAG = "FollowingViewModel"
    }
}