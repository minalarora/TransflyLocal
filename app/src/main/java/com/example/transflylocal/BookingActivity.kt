package com.example.transflylocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.transflylocal.databinding.ActivityBookingBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class BookingActivity : AppCompatActivity() {
    lateinit var activity: ActivityBookingBinding
    lateinit var retrofit: Retrofit
    lateinit var api: ApiEndpoints

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       activity = DataBindingUtil.setContentView(this,R.layout.activity_booking)
        activity.confirmButton.setOnClickListener {
            validateUser(activity.mobileNumber.text.toString().trim())
        }
        retrofit = ApiClient.getRetrofitClient()!!
        api = retrofit.create(ApiEndpoints::class.java)
    }

    private fun validateUser(m: String)
    {

        activity.parentOfLoading.visibility = View.VISIBLE
        api.validateUser(RequestUser().apply{
            mobile = m

        }).enqueue(object :Callback<ResponseBody>
        {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                activity.parentOfLoading.visibility = View.INVISIBLE
                if(response.code() == 200) {
                    next()
                }
                else {
                    fail()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                activity.parentOfLoading.visibility = View.INVISIBLE
                fail()

            }

        })
    }

    private fun next()
    {
        Intent(this,BookingActivity2::class.java).also {
            it.putExtra("mobile",activity.mobileNumber.text.toString())
            startActivity(it)
            finish()
        }
    }

    private fun fail()
    {
        Toast.makeText(this,"No user found!",Toast.LENGTH_LONG).show()
    }
}