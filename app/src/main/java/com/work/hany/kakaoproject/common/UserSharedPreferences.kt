package com.work.hany.kakaoproject.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.work.hany.kakaoproject.data.KakaoToken
import com.work.hany.kakaoproject.data.Profile

/**
 * Created by hany on 2018. 3. 8..
 */

class UserSharedPreferences(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(Constants.PREFS_FILENAME, Constants.MODE_READ_WRITEABLE)

    var token: KakaoToken
        get() {
            prefs.getString(Constants.PREF_KEY_USER_ACCESS_TOKEN, "").run {
                return when(isEmpty()) {
                    true -> KakaoToken("","","",0,"")
                    else -> Gson().fromJson<KakaoToken>(this,KakaoToken::class.java)
                }
            }
        }

        set(value) {
            val jsonStr = Gson().toJson(value)
            prefs.edit().putString(Constants.PREF_KEY_USER_ACCESS_TOKEN, jsonStr).apply()
        }


    var profile: Profile
        get() {
            prefs.getString(Constants.PREF_KEY_USER_PROFILE, "").run {
                return when(isEmpty()) {
                    true -> Profile("","","","", "","","")
                    else -> Gson().fromJson<Profile>(this,Profile::class.java)
                }
            }
        }

        set(value) {
            val jsonStr = Gson().toJson(value)
            prefs.edit().putString(Constants.PREF_KEY_USER_PROFILE, jsonStr).apply()
        }

    fun removeAll(){
        prefs.edit()
                .remove(Constants.PREF_KEY_USER_PROFILE)
                .remove(Constants.PREF_KEY_USER_ACCESS_TOKEN).apply()
    }
}