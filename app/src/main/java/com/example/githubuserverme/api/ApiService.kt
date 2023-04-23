package com.example.githubuserverme.api

import com.example.githubuserverme.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListData(@Query("q") q: String) : Call<ResponseUser>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<ResponseDetaiUser>

    @GET("users/{username}/followers")
    fun getFollowerUser(@Path("username") username: String): Call<List<ResponseFollowerItem>>

    @GET("users/{username}/following")
    fun getFollowingUser(@Path("username")username: String): Call<List<ResponseFollowingItem>>
}