package com.luismiguel.retrofitvolley.api

import com.luismiguel.retrofitvolley.model.bean.Users
import com.luismiguel.retrofitvolley.BuildConfig
import retrofit2.Response
import retrofit2.http.GET

interface IRetrofit {
    @GET(BuildConfig.Usuarios)
    suspend fun consultarusuarios(): Response<Users>?
}
