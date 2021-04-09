package com.example.transflylocal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.transflylocal.databinding.SingleBookingBinding

class BookingAdapter(private var list: ArrayList<RequestBooking>): RecyclerView.Adapter<BookingAdapter.ViewHolder>()
{




    val selectedlist = ArrayList<RequestBooking>()

    inner class ViewHolder: RecyclerView.ViewHolder
    {
        val binding: SingleBookingBinding
        val context: Context

        constructor(itemView: View, context: Context): super(itemView)
        {
            binding = DataBindingUtil.bind<SingleBookingBinding>(itemView)!!
            this.context = context
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater  = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.single_booking,parent,false)
        return  ViewHolder(view,context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.binding.location.text = "FROM ${list[position].minename} TO ${list[position].loading}"
        holder.binding.user.text = "VEHICLE OWNER:- ${list[position].vehicleowner} (${list[position].vehicleownermobile})"
        holder.binding.driver.text = "VEHICLE:- ${list[position].vehicle} CONTACT:- ${list[position].contact}"
        holder.binding.date.text = "BOOKING CREATED AT ${list[position].date}"



        holder.binding.delete.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedlist.add(list[position])
            } else {
                selectedlist.remove(list[position])
            }
        }

    }



    fun getSelectedList(): ArrayList<RequestBooking>
    {
        return selectedlist
    }

    fun checkSubmit(): Boolean
    {
        if(selectedlist.size == 0)
        {
            return false
        }
        else
        {
            return true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(updatedlist: ArrayList<RequestBooking>)
    {
        list = updatedlist
        notifyDataSetChanged()
    }

}