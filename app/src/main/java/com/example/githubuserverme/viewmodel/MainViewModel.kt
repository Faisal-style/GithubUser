package com.example.githubuserverme.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserverme.api.ApiConfig
import com.example.githubuserverme.model.ItemsItem
import com.example.githubuserverme.model.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _searchuser = MutableLiveData<List<ItemsItem>>()
    val searchuser: LiveData<List<ItemsItem>> = _searchuser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =   _isLoading

    fun findUserGithub(query: String) {
        _isLoading.value = true
        val client = ApiConfig.endPoint.getListData(query)
        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchuser.value = response.body()?.items
                }else{
                    Log.e(TAG, "on failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"on failure: ${t.message.toString()}")
            }

        })
    }


    companion object{
        private const val TAG = "MainViewModel"
    }
}