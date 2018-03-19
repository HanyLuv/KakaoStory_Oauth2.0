package com.work.hany.kakaoproject.http

import android.util.Log
import com.work.hany.kakaoproject.common.Constants
import com.work.hany.kakaoproject.data.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by hany on 2018. 3. 8..
 *
 * @description kakao story 가져오기
 * 기본적으로 헤더에 액세스토큰을 담아 요청합니다.
 */

class MyStoriesRequester(token: KakaoToken) {
    private var failedTokenMessage = "토큰 시간 만료"

    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit
    private var service: KakaoService

    init {
        okHttpClient = createOkHttpClient(token)
        retrofit = Retrofit.Builder()
                .baseUrl(Constants.KAKAO_SROTY_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(KakaoService::class.java)
    }

    /**
     * @description 카카오스토리 데이터 가져오기
     *
     * @param lastId 페이징을 위한 파라메터. 해당 카카오스토리 id를 기준을 시작으로 그 후의 데이터를 불러옴.
     * @param listener 결과값을 받는 리스너
     * */
    fun getUserStories(lastId: String, listener: ResponseListener<List<KakaoStory>>) {

        val callback = object : Callback<List<KakaoStory>> {

            override fun onFailure(call: Call<List<KakaoStory>>?, t: Throwable?) {
                Log.e("MyStoriesRequester", "[getUserStories] failure : " + t?.localizedMessage)
            }

            override fun onResponse(call: Call<List<KakaoStory>>, response: Response<List<KakaoStory>>) {
                when (response.code()) {
                    Constants.KAKAO_RESOPNSE_CODE_200 -> {
                        if (response.body() is List<KakaoStory>) {
                            val kakaoStories = response.body() as List<KakaoStory>
                            listener.onReceived(kakaoStories)

                        }
                    }

                    Constants.KAKAO_RESOPNSE_CODE_401 -> {
                        listener.onFailed(failedTokenMessage)
                    }

                    else ->{
                        listener.onFailed(response.message())
                    }

                }
            }

        }

        val call = service.getMyStories(lastId)
        call.enqueue(callback)

    }

    fun getUserStories(listener: ResponseListener<List<KakaoStory>>) {
        getUserStories("", listener)
    }

    /**
     * @description 카카오스토리 사용자 정보요청
     * */
    fun getUserProfile(listener: ResponseListener<Profile>) {
        val callback = object : Callback<Profile> {

            override fun onFailure(call: Call<Profile>?, t: Throwable?) {
                Log.e("MyStoriesRequester", "[getUserProfile] failure : " + t?.localizedMessage)
            }

            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                when (response.code()) {
                    Constants.KAKAO_RESOPNSE_CODE_200 -> {
                        if (response.body() is Profile) {
                            val profile = response.body() as Profile
                            listener.onReceived(profile)

                        }
                    }

                    Constants.KAKAO_RESOPNSE_CODE_401 -> {
                        listener.onFailed(failedTokenMessage)

                    }

                    else ->{
                        listener.onFailed(response.message())
                    }

                }
            }

        }

        val call = service.getProfile()
        call.enqueue(callback)

    }

    /**
     * @description 카카오스토리의 하나의 스토리에 대한 정보를 요청한다.
     *
     * @param id 상세 정보를 가져올 카카오스토리 아이디.
     * @param listener
     * */
    fun getMyStory(id: String, listener: ResponseListener<KakaoStory>){
        val callback = object : Callback<KakaoStory> {

            override fun onFailure(call: Call<KakaoStory>?, t: Throwable?) {
                Log.e("MyStoriesRequester", "[getMyStory] failure : " + t?.localizedMessage)
            }

            override fun onResponse(call: Call<KakaoStory>, response: Response<KakaoStory>) {
                when (response.code()) {
                    Constants.KAKAO_RESOPNSE_CODE_200 -> {
                        if (response.body() is KakaoStory) {
                            val story = response.body() as KakaoStory
                            listener.onReceived(story)

                        }
                    }

                    Constants.KAKAO_RESOPNSE_CODE_401 -> {
                        listener.onFailed(failedTokenMessage)

                    }

                    else ->{
                        listener.onFailed(response.message())
                    }
                }
            }

        }

        val call = service.getMyStory(id)
        call.enqueue(callback)

    }

    fun logout(listener: ResponseListener<Logout>){
        val callback = object : Callback<Logout> {
            override fun onFailure(call: Call<Logout>?, t: Throwable?) {
                Log.e("MyStoriesRequester", "[logout] failure : " + t?.localizedMessage)
            }

            override fun onResponse(call: Call<Logout>?, response: Response<Logout>) {
                when (response.code()) {
                    Constants.KAKAO_RESOPNSE_CODE_200 -> {
                        if (response.body() is Logout) {
                            val story = response.body() as Logout
                            listener.onReceived(story)
                        }
                    }

                    Constants.KAKAO_RESOPNSE_CODE_401 -> {
                        listener.onFailed(failedTokenMessage)

                    }

                    else ->{
                        listener.onFailed(response.message())
                    }
                }
            }
        }

        val call = service.logout()
        call.enqueue(callback)

    }

    fun isStoryUser(listener: ResponseListener<StoryUser>){
        val callback = object : Callback<StoryUser> {
            override fun onFailure(call: Call<StoryUser>?, t: Throwable?) {
                Log.e("MyStoriesRequester", "[isStoryUser] failure : " + t?.localizedMessage)
            }

            override fun onResponse(call: Call<StoryUser>?, response: Response<StoryUser>) {
                when (response.code()) {
                    Constants.KAKAO_RESOPNSE_CODE_200 -> {
                        if (response.body() is StoryUser) {
                            val story = response.body() as StoryUser
                            listener.onReceived(story)
                        }
                    }

                    Constants.KAKAO_RESOPNSE_CODE_401 -> {
                        listener.onFailed(failedTokenMessage)

                    }

                    else ->{
                        listener.onFailed(response.message())
                    }
                }
            }
        }

        val call = service.isStoryUser()
        call.enqueue(callback)
    }

    //TODO 리스폰스 한번에 묶기...

//    private fun getResponse(): T{
//
//    }

    private fun createOkHttpClient(token: KakaoToken): OkHttpClient {
        val headerValue = StringBuilder()
                .append(token.tokenType)
                .append(" ")
                .append(token.accessToken).toString()

        Log.d("HANY_TAG", "header : Authorization " + headerValue)
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .header("Authorization", headerValue)
                    .build()
            chain.proceed(newRequest)

        }

        //header에 값이 잘들어가는지 확인을 위해 logging.
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    private interface KakaoService {
        @GET("/v1/api/story/mystories")
        fun getMyStories(@Query("last_id") lastID: String): Call<List<KakaoStory>>

        @GET("/v1/api/story/mystory")
        fun getMyStory(@Query("id") id: String): Call<KakaoStory>

        @GET("/v1/api/story/profile?secure_resource=true")
        fun getProfile(): Call<Profile>

        @GET("/v1/api/story/isstoryuser")
        fun isStoryUser(): Call<StoryUser>

        @POST("/v1/user/logout")
        fun logout(): Call<Logout>
    }

}