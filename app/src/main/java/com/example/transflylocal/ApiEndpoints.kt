package com.example.transflylocal

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoints {

    @POST("/local/vehicles")
    fun  getVehicle(@Body user: RequestUser): Call<ResponseBody>

    @GET("/local/mines")
    fun getMines(): Call<ResponseBody>

    @GET("/local/loadings")
    fun getLoadings(): Call<ResponseBody>

    @GET("/local/transporters")
    fun getTransporters(): Call<ResponseBody>

    @POST("/local/validate")
    fun validateUser(@Body user: RequestUser): Call<ResponseBody>

    @POST("/local/booking")
    fun createBooking(@Body booking: ResponseBooking): Call<ResponseBody>

    @GET("/local/bookings")
    fun getBookings(): Call<ResponseBody>

    @POST("/local/admin")
    fun validateAdmin(@Body user: RequestAdmin): Call<ResponseBody>

    @POST("/local/user")
    fun validateBackUser(@Body user: RequestAdmin): Call<ResponseBody>

    @POST("/local/delete")
    fun deleteBooking(@Body bookings: DeleteBooking): Call<ResponseBody>



}