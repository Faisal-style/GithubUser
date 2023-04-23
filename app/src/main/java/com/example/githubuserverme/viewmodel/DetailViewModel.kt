package com.example.githubuserverme.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserverme.api.ApiConfig
import com.example.githubuserverme.model.ResponseDetaiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel: ViewModel() {
    private val _detailUser = MutableLiveData<ResponseDetaiUser>()
    val detailUser : LiveData<ResponseDetaiUser> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun userDetail(username : String) {
        _isLoading.value = true
        val client = ApiConfig.endPoint.getDetailUser(username)
        client.enqueue(object : Callback<ResponseDetaiUser> {
            override fun onResponse(
                call: Call<ResponseDetaiUser>,
                response: Response<ResponseDetaiUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<ResponseDetaiUser>, t: Throwable) {
                Log.e(TAG, "On Failure : ${t.message.toString()}")
            }

        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }




}