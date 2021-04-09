package com.example.transflylocal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.transflylocal.databinding.ActivityBooking2Binding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.Type

class BookingActivity2 : AppCompatActivity() {

    lateinit var  activity: ActivityBooking2Binding
    lateinit var m: String
    lateinit var retrofit: Retrofit
    lateinit var api: ApiEndpoints
    var loadinglist: ArrayList<ResponseLoading> = ArrayList()
    var minelist: ArrayList<ResponseMine> = ArrayList()
    var vehiclelist: ArrayList<ResponseVehicle> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = DataBindingUtil.setContentView(this,R.layout.activity_booking2)
        m = intent.getStringExtra("mobile").toString()
        retrofit = ApiClient.getRetrofitClient()!!
        api = retrofit.create(ApiEndpoints::class.java)

        api.getLoadings().enqueue(object : Callback<ResponseBody>
        {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if(response.code() == 200)
                {
                    val collectionType: Type = object : TypeToken<ArrayList<ResponseLoading>>() {}.type
                    loadinglist.addAll(Gson().fromJson(response.body()?.string().toString(), collectionType))
                    activity.to.adapter = SpinnerLoadingAdapter(this@BookingActivity2,loadinglist)
                }
                else
                {

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

        })

        api.getMines().enqueue(object : Callback<ResponseBody>
        {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if(response.code() == 200)
                {
                    val collectionType: Type = object : TypeToken<ArrayList<ResponseMine>>() {}.type
                    minelist.addAll(Gson().fromJson(response.body()?.string().toString(), collectionType))
                    activity.from.adapter = SpinnerMineAdapter(this@BookingActivity2,minelist)
                }
                else
                {

                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

        })


        api.getVehicle(RequestUser().apply {
            this.mobile = m
        }).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                if(response.code() == 200)
                {
                    val collectionType: Type = object : TypeToken<ArrayList<ResponseVehicle>>() {}.type
                    vehiclelist.addAll(Gson().fromJson(response.body()?.string().toString(), collectionType))
                    activity.vehicle.adapter = SpinnerVehicleAdapter(this@BookingActivity2,vehiclelist)
                    if(vehiclelist.isEmpty())
                    {
                        Toast.makeText(this@BookingActivity2,"No Registered Vehicles",Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else
                    {
                      activity.vehicle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
                      {
                          override fun onItemSelected(
                              parent: AdapterView<*>?,
                              view: View?,
                              position: Int,
                              id: Long
                          ) {
                              activity.driver.setText(vehiclelist.get(position).contact)
                          }

                          override fun onNothingSelected(parent: AdapterView<*>?) {

                          }

                      }


                    }

                }
                else
                {

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

        })


        activity.confirmButton.setOnClickListener {

            createBooking()

        }




    }

    fun createBooking()
    {
        activity.parentOfLoading.visibility = View.VISIBLE
        api.createBooking(ResponseBooking().apply {
            this.mobile = m
            this.vehicle = (activity.vehicle.selectedItem as ResponseVehicle).number
            this.mineid = (activity.from.selectedItem as ResponseMine).id
            this.minename = (activity.from.selectedItem as ResponseMine).name
            this.loading = (activity.to.selectedItem as ResponseLoading).loadingname
            this.contact = activity.driver.text.toString()


        }).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                activity.parentOfLoading.visibility = View.INVISIBLE
                if(response.code() == 200)
                {
                    Toast.makeText(this@BookingActivity2,"Booking Successful",Toast.LENGTH_LONG).show()
                    finish()
                }
                else
                {
                    Toast.makeText(this@BookingActivity2,"Server Error",Toast.LENGTH_LONG).show()

                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@BookingActivity2,"Server Error",Toast.LENGTH_LONG).show()

            }

        })
    }




}