package com.work.hany.kakaoproject.http

/**
 * Created by hany on 2018. 3. 8..
 */
interface ResponseListener<in T> {
    fun onReceived(data: T)
    fun onFailed(msg: String)
}