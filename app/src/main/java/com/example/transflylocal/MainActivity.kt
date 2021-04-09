package com.example.transflylocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.transflylocal.databinding.ActivityMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() , LoginFragment.LoginListener, LoginadminFragment.LoginadminListener{
    lateinit var activity: ActivityMainBinding
    lateinit var tag: String
    lateinit var retrofit: Retrofit
    lateinit var api: ApiEndpoints

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = DataBindingUtil.setContentView(this, R.layout.activity_main)
        retrofit = ApiClient.getRetrofitClient()!!
        api = retrofit.create(ApiEndpoints::class.java)
        activity.booking.setOnClickListener {
            Intent(this@MainActivity, BookingActivity::class.java).also {
                startActivity(it)
            }
        }
        activity.transporter.setOnClickListener {

            val fm = supportFragmentManager
            LoginadminFragment.newInstance().apply {
                setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen)
                show(fm, "transporter")
                this.setOnLoginListener(this@MainActivity)
                //this.setOnCategorySelectedListener(this@ClassFragment)
            }
            tag = "transporter"

//            Intent(this@MainActivity,TransporterActivity::class.java).also {
//                it.putExtra("type","transporter")
//                startActivity(it)
//            }
        }

        activity.mines.setOnClickListener {

            val fm = supportFragmentManager
            LoginadminFragment.newInstance().apply {
                setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen)
                show(fm, "mine")
                this.setOnLoginListener(this@MainActivity)
            }
            tag = "mine"

//            Intent(this@MainActivity,TransporterActivity::class.java).also {
//                it.putExtra("type","mine")
//                startActivity(it)
                }



            activity.bookingShow.setOnClickListener {


                Intent(this@MainActivity, ShowbookingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }


    override fun onLogin(user: String, pwd: String) {

    }

    override fun onLoginadmin(user: String, pwd: String) {
        activity.parentOfLoading.visibility = View.VISIBLE

        api.validateAdmin(RequestAdmin().apply {
            mobile = user
            password = pwd
        }).enqueue(object : Callback<ResponseBody>
        {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                activity.parentOfLoading.visibility = View.GONE
                if (response.code() == 200)
                {
                    Intent(this@MainActivity,TransporterActivity::class.java).also {
                      it.putExtra("type",tag)
                        startActivity(it)
                    }
                }
                else
                {
                    Toast.makeText(this@MainActivity,"No user found!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                activity.parentOfLoading.visibility = View.GONE

                Toast.makeText(this@MainActivity,"No user found!", Toast.LENGTH_LONG).show()
            }

        })
    }
}