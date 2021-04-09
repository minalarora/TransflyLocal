package com.example.transflylocal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.transflylocal.databinding.FragmentLoginBinding
import com.example.transflylocal.databinding.FragmentLoginadminBinding

class LoginadminFragment : DialogFragment() {

    lateinit var  list: ArrayList<String>
    lateinit var fragment: FragmentLoginadminBinding
    lateinit var mCallback: LoginadminListener




    interface LoginadminListener {
        fun onLoginadmin(user: String, pwd: String)
    }

    fun setOnLoginListener(callback: LoginadminListener) {
        this.mCallback = callback
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            list  = it.getStringArrayList("list") as ArrayList<String>
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loginadmin, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment= DataBindingUtil.bind<FragmentLoginadminBinding>(view)!!
        fragment.cancel.setOnClickListener {
            dismiss()
        }


        fragment.submit.setOnClickListener {
            mCallback.onLoginadmin(fragment.email.text.toString(),
            fragment.password.text.toString()
                    )
            dismiss()
        }
//        initRecycler()
//        initView()


    }
    override fun onResume() {
        var params  = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params
        super.onResume()

    }


    companion object {

        @JvmStatic
        fun newInstance() = LoginadminFragment()

    }


}