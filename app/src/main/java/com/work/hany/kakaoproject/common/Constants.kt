package com.work.hany.kakaoproject.common


/**
 * Created by hany on 2018. 3. 8..
 * 공통 코드 정리.
 */

class Constants {
    companion object {
        const val REQUEST_CODE_KAKAO_LOGIN = 1001

        const val KAKAO_OAUTH_BASE_URL = "https://kauth.kakao.com"
        const val KAKAO_SROTY_BASE_URL = "https://kapi.kakao.com"

        const val KAKAO_REST_API_KEY = "52c3528c09363c6a4b5366396a96858e"
        const val KAKAO_REDIRECT_URI = "https://github.com/hanyluv"
        const val KAKAO_REDIRECT_URI_CODE = "https://github.com/hanyluv?code="
        const val KAKAO_VALUE_AUTH_CODE = "authorization_code"

        const val KAKAO_QUERY_CODE = "code"
        const val KAKAO_QUERY_CLIENT_ID = "client_id"
        const val KAKAO_QUERY_GRANT_TYPE = "grant_type"
        const val KAKAO_QUERY_REDIRECT_URI = "redirect_uri"

        const val BUNDLE_KEY_TOKEN = "token"
        const val BUNDLE_KEY_MEDIA = "media"
        const val BUNDLE_KEY_MEDIAS = "medias"
        const val BUNDLE_KEY_MEDIA_SELECTED = "media_selected"
        const val EXTRA_KEY_AUTHORIZE_CODE = "authorizeCode"
        const val EXTRA_KEY_KAKAO_ID = "kakao_id"

        const val KAKAO_RESOPNSE_CODE_401 = 401
        const val KAKAO_RESOPNSE_CODE_200 = 200

        //shared preferences key
        const val PREFS_FILENAME = "prefs"
        const val PREF_KEY_USER_ACCESS_TOKEN = "userAccessToken"
        const val PREF_KEY_USER_PROFILE = "userProfile"
        const val MODE_READ_WRITEABLE = 0

        const val HEADER_POSITION = 0
        const val STORY_MEDIA_TYPE_PHOTO = "PHOTO"
        const val STORY_MEDIA_TYPE_NOTE = "NOTE" //글쓰기

    }
}