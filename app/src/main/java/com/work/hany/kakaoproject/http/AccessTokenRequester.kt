package com.work.hany.kakaoproject.http

import android.util.Log
import com.work.hany.kakaoproject.data.KakaoToken
import com.work.hany.kakaoproject.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * Created by hany on 2018. 3. 7..
 *
 *@description 카카오 인증 토큰 얻어오기
 *
 */

class AccessTokenRequester {

    private var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.KAKAO_OAUTH_BASE_URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create()).build()


    /**
     * @description 얻어온 권한 코드로 access token을 요청한다.
     *
     * @param authorizeCode 로그인해서 얻어 온 사용자의 카카오 권한 코드.
     * @param listener
     * */

    fun getAccessToken(authorizeCode: String, listener: ResponseListener<KakaoToken>) {

        val oauthService = retrofit.create(OauthService::class.java)

        val queryMap = hashMapOf(Constants.KAKAO_QUERY_CLIENT_ID to Constants.KAKAO_REST_API_KEY,
                Constants.KAKAO_QUERY_GRANT_TYPE to Constants.KAKAO_VALUE_AUTH_CODE,
                Constants.KAKAO_QUERY_REDIRECT_URI to Constants.KAKAO_REDIRECT_URI,
                Constants.KAKAO_QUERY_CODE to authorizeCode)

        val callback = object : Callback<KakaoToken> {
            override fun onResponse(call: Call<KakaoToken>, response: Response<KakaoToken>) {

                val responseCode = response.code()
                when (responseCode) {
                    Constants.KAKAO_RESOPNSE_CODE_200 -> {
                        if (response.body() is KakaoToken) {
                            val token = response.body() as KakaoToken
                            listener.onReceived(token)
                        }
                    }

                    Constants.KAKAO_RESOPNSE_CODE_401 -> {
                        listener.onFailed("토큰 시간 만료")

                    }

                    else -> {
                        listener.onFailed(response.message())

                    }
                }

            }

            override fun onFailure(call: Call<KakaoToken>?, t: Throwable?) {
                Log.e("AccessTokenRequester", "failure : "+ t?.localizedMessage)
            }
        }

        val call = oauthService.getResponse(queryMap)
        call.enqueue(callback)


    }

    //토큰 갱신
    fun updateAccessToken(token: KakaoToken, listener: ResponseListener<KakaoToken>){

    }

    private interface OauthService {
        @POST("/oauth/token")
        fun getResponse(@QueryMap prams: Map<String, String>): Call<KakaoToken>
    }

}

