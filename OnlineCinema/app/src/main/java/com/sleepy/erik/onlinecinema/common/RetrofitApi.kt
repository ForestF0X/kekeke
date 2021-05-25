package com.sleepy.erik.onlinecinema.common

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface RetrofitApi {
    @POST("/auth/login")
    fun login(@Body data: LoginData): Call<LoginResponse>

    @POST("/auth/register")
    fun registration(@Body data: RegisterData): Call<Void>

//    @GET("/movies/cover")
//    fun movies(): Call<FilmToken?>?
//
//    @GET("/movies")
//    fun getMovies(@Query("filter") type: String?): Call<List<ScrollFilmItem?>?>?
//
//    @GET("/movies/{movieId}")
//    fun getOneMovie(@Path("movieId") movieId: String?): Call<ScrollFilmItem?>?
//
//    @get:GET("/movies/{movieId}/episodes")
//    val episode: Call<List<Any?>?>?
    object Factory {
        fun create(): RetrofitApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://cinema.areas.su")
                .build()
            return retrofit.create(RetrofitApi::class.java)
        }
    }
}