package com.example.transflylocal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.transflylocal.databinding.ActivityTransporterBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class TransporterActivity : AppCompatActivity() ,SmoothDateRangePickerFragment.OnDateRangeSetListener {

    lateinit var activity: ActivityTransporterBinding
    lateinit var type: String
    lateinit var retrofit: Retrofit
    lateinit var api: ApiEndpoints

    var transporterlist: ArrayList<ResponseTransporter> = ArrayList()
    var minelist: ArrayList<ResponseMine> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = DataBindingUtil.setContentView(this, R.layout.activity_transporter)
        type  = intent.getStringExtra("type").toString()
        retrofit = ApiClient.getRetrofitClient()!!
        api = retrofit.create(ApiEndpoints::class.java)

        if(type == "transporter")
        {
            api.getTransporters().enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        val collectionType: Type = object : TypeToken<ArrayList<ResponseTransporter>>() {}.type
                        transporterlist.addAll(Gson().fromJson(response.body()?.string().toString(), collectionType))
                        activity.spinner.adapter = SpinnerTransporterAdapter(this@TransporterActivity, transporterlist)
                    } else {
                        finish()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    finish()
                }

            })
        }
        else
        {
            api.getMines().enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        val collectionType: Type = object : TypeToken<ArrayList<ResponseMine>>() {}.type
                        minelist.addAll(Gson().fromJson(response.body()?.string().toString(), collectionType))
                        activity.spinner.adapter = SpinnerMineAdapter(this@TransporterActivity, minelist)
                    } else {
                        finish()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    finish()
                }

            })
        }
        activity.confirmButton.setOnClickListener {
            val now = Calendar.getInstance()

            val smoothDateRangePickerFragment: SmoothDateRangePickerFragment = SmoothDateRangePickerFragment.newInstance({ view: SmoothDateRangePickerFragment?, yearStart: Int, monthStart: Int, dayStart: Int, yearEnd: Int, monthEnd: Int, dayEnd: Int -> this@TransporterActivity.onDateRangeSet(view, yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd) }, now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DAY_OF_MONTH])

            smoothDateRangePickerFragment.accentColor = R.color.purple_200

            smoothDateRangePickerFragment.isThemeDark = false

            smoothDateRangePickerFragment.show(fragmentManager, "smoothDateRangePicker")


        }

    }



    override fun onDateRangeSet(view: SmoothDateRangePickerFragment?, yearStart: Int, monthStart: Int, dayStart: Int, yearEnd: Int, monthEnd: Int, dayEnd: Int) {
        if(type == "transporter")
        {
            var uri: Uri = Uri.parse("https://transflyhome.club/local/reporttransporter" + "?mobile=" + (activity.spinner.selectedItem as ResponseTransporter).mobile + "&from="+yearStart+"-"+(monthStart+1)+"-"+dayStart+"&"+"to="+yearEnd+"-"+(monthEnd+1)+"-"+dayEnd )
            Intent(Intent.ACTION_VIEW,uri).also {
                startActivity(it)

            }
        }
        else
        {
            var uri: Uri = Uri.parse("https://transflyhome.club/local/reportmine" + "?id=" + (activity.spinner.selectedItem as ResponseMine).id + "&from="+yearStart+"-"+(monthStart+1)+"-"+dayStart+"&"+"to="+yearEnd+"-"+(monthEnd+1)+"-"+dayEnd )
            Intent(Intent.ACTION_VIEW,uri).also {
                startActivity(it)

            }
        }

    }


}