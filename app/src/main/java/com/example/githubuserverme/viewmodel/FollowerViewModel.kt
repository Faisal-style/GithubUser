package com.example.githubuserverme.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserverme.api.ApiConfig
import com.example.githubuserverme.model.ResponseFollower
import com.example.githubuserverme.model.ResponseFollowerItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    private val _followeruser = MutableLiveData<List<ResponseFollowerItem>>()
    val followeruser: LiveData<List<ResponseFollowerItem>> = _followeruser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =   _isLoading

    fun findUserFollower(query: String) {
        _isLoading.value = true
        val client = ApiConfig.endPoint.getFollowerUser(query)
        client.enqueue(object : Callback<List<ResponseFollowerItem>>{
            override fun onResponse(
                call: Call<List<ResponseFollowerItem>>,
                response: Response<List<ResponseFollowerItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followeruser.value = response.body()
                } else {
                    Log.e(TAG, "On Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseFollowerItem>>, t: Throwable) {
                Log.e(TAG, "On Failure : ${t.message.toString()}")
            }

        })

    }


    companion object{
        private const val TAG = "FollowerViewModel"
    }
}