package com.example.transflylocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.transflylocal.databinding.ActivityBookingBinding
import com.example.transflylocal.databinding.ActivityLoginBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {
    lateinit var activity: ActivityLoginBinding
    lateinit var retrofit: Retrofit
    lateinit var api: ApiEndpoints


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = DataBindingUtil.setContentView(this,R.layout.activity_login)
        retrofit = ApiClient.getRetrofitClient()!!
        api = retrofit.create(ApiEndpoints::class.java)
        activity.login.setOnClickListener {
            activity.parentOfLoading.visibility = View.VISIBLE
            api.validateBackUser(RequestAdmin().apply {
                mobile = activity.user.text.toString()
                password = activity.password.text.toString()
            }).enqueue(object : Callback<ResponseBody>
            {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    activity.parentOfLoading.visibility = View.GONE
                    if (response.code() == 200)
                    {
                        Intent(this@LoginActivity,MainActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }
                    else
                    {
                        Toast.makeText(this@LoginActivity,"No user found!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    activity.parentOfLoading.visibility = View.GONE
                    Toast.makeText(this@LoginActivity,"No user found!",Toast.LENGTH_LONG).show()
                }

            })
        }
    }
}