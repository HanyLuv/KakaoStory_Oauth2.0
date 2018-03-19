package com.work.hany.kakaoproject.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.work.hany.kakaoproject.LoginActivity
import com.work.hany.kakaoproject.R
import com.work.hany.kakaoproject.common.Constants

/**
 * Created by hany on 2018. 3. 8..
 */

class LoginFragment : Fragment(){

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.framgnet_login, container,false)
        val loginButton = rootView.findViewById<ImageButton>(R.id.loginButton)

        loginButton.setOnClickListener {
           activity.startActivityForResult(Intent(activity, LoginActivity::class.java), Constants.REQUEST_CODE_KAKAO_LOGIN)
        }

        return rootView
    }


}

