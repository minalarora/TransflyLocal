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

class LoginFragment : DialogFragment() {

    lateinit var  list: ArrayList<String>
    lateinit var fragment: FragmentLoginBinding
    lateinit var mCallback: LoginListener




    interface LoginListener {
        fun onLogin(user: String, pwd: String)
    }

    fun setOnLoginListener(callback: LoginListener) {
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment= DataBindingUtil.bind<FragmentLoginBinding>(view)!!
        fragment.cancel.setOnClickListener {
            dismiss()
        }


        fragment.submit.setOnClickListener {
            mCallback.onLogin(fragment.email.text.toString(),
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
        fun newInstance() = LoginFragment()

    }


}