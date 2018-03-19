package com.work.hany.kakaoproject.common

import android.app.Application
/**
 * Created by hany on 2018. 3. 8..
 */


val prefs: UserSharedPreferences by lazy {
    //prefs를 즉시 초기화 하지만 처음 사용하기전까지 설정되지않는다. 오홍 좋은뎅?
    App.prefs!!
}

class App : Application() {
    companion object {
        var prefs: UserSharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = UserSharedPreferences(applicationContext)
    }
}

