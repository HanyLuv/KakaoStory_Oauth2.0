package com.work.hany.kakaoproject

import android.app.Activity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import com.work.hany.kakaoproject.common.Constants

/**
 * Created by hany on 2018. 3. 8..
 *
 * @description kakao Login Activity
 */

class LoginActivity : ToolbarActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                val redirectURI = request.url

                return when (redirectURI.toString().indexOf(Constants.KAKAO_REDIRECT_URI_CODE) > -1) {
                    true -> {
                        val intent = Intent()
                        intent.putExtra(Constants.EXTRA_KEY_AUTHORIZE_CODE, redirectURI.getQueryParameter(Constants.KAKAO_QUERY_CODE))
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                        true }

                    else -> false
                }
            }

        }

        val strAuthorizeURI = StringBuilder()
                .append("https://kauth.kakao.com/oauth/authorize?")
                .append("client_id=").append(Constants.KAKAO_REST_API_KEY)
                .append("&redirect_uri=").append(Constants.KAKAO_REDIRECT_URI)
                .append("&response_type=").append(Constants.KAKAO_QUERY_CODE).toString()

        webview.loadUrl(strAuthorizeURI.trim())

    }

}