package com.example.transflylocal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.transflylocal.databinding.ActivityBooking2Binding
import com.example.transflylocal.databinding.ActivityShowbookingBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.Type

class ShowbookingActivity : AppCompatActivity() , LoginFragment.LoginListener{

    lateinit var  activity: ActivityShowbookingBinding
    lateinit var retrofit: Retrofit
    lateinit var api: ApiEndpoints
    var bookinglist: ArrayList<RequestBooking> = ArrayList()
    var minelist: ArrayList<ResponseMine> = ArrayList()
     var all: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = DataBindingUtil.setContentView(this,R.layout.activity_showbooking)
        retrofit = ApiClient.getRetrofitClient()!!
        api = retrofit.create(ApiEndpoints::class.java)


        activity.parentOfLoading.visibility = View.VISIBLE

        api.getMines().enqueue(object : Callback<ResponseBody>
        {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                if(response.code() == 200)
                {
                    val collectionType: Type = object : TypeToken<ArrayList<ResponseMine>>() {}.type
                    minelist.addAll(Gson().fromJson(response.body()?.string().toString(), collectionType))
                    activity.spinner.adapter = SpinnerMineAdapter(this@ShowbookingActivity,minelist)

                    api.getBookings().enqueue(object : Callback<ResponseBody>
                    {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            activity.parentOfLoading.visibility = View.GONE

                            if(response.code() == 200)
                            {
                                val collectionType: Type = object : TypeToken<ArrayList<RequestBooking>>() {}.type
                                bookinglist.addAll(Gson().fromJson(response.body()?.string().toString(), collectionType))

                                if (bookinglist.isNotEmpty()) {
                                    activity.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                        ) {

                                            activity.recycler.adapter = BookingAdapter(bookinglist.filter {
                                                it.minename == (activity.spinner.selectedItem as ResponseMine).name
                                            } as ArrayList<RequestBooking>)

                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>?) {

                                        }

                                    }
                                }
                                else
                                {
                                    activity.parentOfLoading.visibility = View.GONE
                                    Toast.makeText(this@ShowbookingActivity,"No bookings found!", Toast.LENGTH_LONG).show()
                                    finish()
                                }


                                //  activity.spinner.adapter = SpinnerMineAdapter(this@ShowbookingActivity,minelist)

                            }
                            else
                            {
                                activity.parentOfLoading.visibility = View.GONE
                                Toast.makeText(this@ShowbookingActivity,"No bookings found!", Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            activity.parentOfLoading.visibility = View.GONE
                            Toast.makeText(this@ShowbookingActivity,"No bookings found!", Toast.LENGTH_LONG).show()
                            finish()
                        }

                    })
                }
                else
                {
                    activity.parentOfLoading.visibility = View.GONE
                    Toast.makeText(this@ShowbookingActivity,"No bookings found!", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                activity.parentOfLoading.visibility = View.GONE
                Toast.makeText(this@ShowbookingActivity,"No bookings found!", Toast.LENGTH_LONG).show()
                finish()
            }

        })



        activity.deleteSelected.setOnClickListener {

            all  = false

            val fm = supportFragmentManager
            LoginFragment.newInstance().apply {
                setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen)
                show(fm, "mine")
                this.setOnLoginListener(this@ShowbookingActivity)
            }

//            var deletebooking = (activity.recycler.adapter as BookingAdapter).getSelectedList().filter {
//                it.minename == (activity.spinner.selectedItem as ResponseMine).name
//            } as ArrayList<RequestBooking>
//



        }

        activity.deleteAll.setOnClickListener {

            all = true

            val fm = supportFragmentManager
            LoginFragment.newInstance().apply {
                setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen)
                show(fm, "mine")
                this.setOnLoginListener(this@ShowbookingActivity)
            }


        }
    }


    private fun deleteBooking(list: ArrayList<RequestBooking>, u: String)
    {
        if(list.isNotEmpty()) {
            var stringlist = list.map {
                it.id ?: 0
            }

            api.deleteBooking(DeleteBooking().apply {
                bookingid = stringlist
                user = u
            }).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    activity.parentOfLoading.visibility = View.GONE
                    if (response.code() == 200) {
                        Toast.makeText(this@ShowbookingActivity,"Bookings deleted!",Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@ShowbookingActivity,"Server Error4",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    activity.parentOfLoading.visibility = View.GONE
                    Toast.makeText(this@ShowbookingActivity,"Server Error3",Toast.LENGTH_LONG).show()
                }

            })
        }
        else
        {
            Toast.makeText(this@ShowbookingActivity,"Please select some bookings to delete",Toast.LENGTH_LONG).show()
            activity.parentOfLoading.visibility = View.GONE
        }

        
    }

    override fun onLogin(user: String, pwd: String) {
        activity.parentOfLoading.visibility = View.VISIBLE
        api.validateBackUser(RequestAdmin().apply {
            mobile = user
            password = pwd
            Log.d("minal",user + " " + pwd)
        }).enqueue(object : Callback<ResponseBody>
        {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.code() == 200)
                {
                    if (all)
                    {
                        var deletebookinglist =  bookinglist.filter {
                            it.minename == (activity.spinner.selectedItem as ResponseMine).name
                        } as ArrayList<RequestBooking>

                        deleteBooking(deletebookinglist,user)
                    }
                    else
                    {
                        var deletebookinglist = (activity.recycler.adapter as BookingAdapter).
                        getSelectedList().filter {
                            it.minename == (activity.spinner.selectedItem as ResponseMine).name
                                                 } as ArrayList<RequestBooking>
                        deleteBooking(deletebookinglist,user)
                    }
                }
                else
                {
                    activity.parentOfLoading.visibility = View.GONE
                    Toast.makeText(this@ShowbookingActivity,"Server Error2",Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                activity.parentOfLoading.visibility = View.GONE
                Toast.makeText(this@ShowbookingActivity,"Server Error1",Toast.LENGTH_LONG).show()

            }

        })

    }
}