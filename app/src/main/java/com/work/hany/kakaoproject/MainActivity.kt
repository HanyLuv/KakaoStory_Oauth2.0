@file:JvmName("Contants")

package com.work.hany.kakaoproject

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.work.hany.kakaoproject.common.*
import com.work.hany.kakaoproject.data.KakaoToken
import com.work.hany.kakaoproject.data.Logout
import com.work.hany.kakaoproject.data.Profile
import com.work.hany.kakaoproject.data.StoryUser
import com.work.hany.kakaoproject.login.LoginFragment
import com.work.hany.kakaoproject.feed.FeedFragment
import com.work.hany.kakaoproject.http.AccessTokenRequester
import com.work.hany.kakaoproject.http.MyStoriesRequester
import com.work.hany.kakaoproject.http.ResponseListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : ToolbarActivity() {

    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var fragment = when (prefs.token.accessToken.isEmpty()) {
            true -> LoginFragment.newInstance()
            else -> {
                initUserInfoNaviHederView(prefs.token)
                FeedFragment.newInstance(prefs.token)
            }
        }

        replaceFragmentInActivity(fragment, R.id.fragmentContainer)
    }


    private fun initActionBar() {

        var isEmpty = prefs.token.accessToken.isEmpty()

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(!isEmpty)
            setHomeButtonEnabled(!isEmpty)
        }

        drawerLayout.setDrawerLockMode(if (isEmpty) DrawerLayout.LOCK_MODE_LOCKED_CLOSED else DrawerLayout.LOCK_MODE_UNLOCKED)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close).apply {
            drawerLayout.addDrawerListener(this)
            this.syncState()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_menu_logout -> {
                    Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
                    MyStoriesRequester(prefs.token).logout(object : ResponseListener<Logout> {
                        override fun onReceived(data: Logout) {
                            prefs.removeAll()
                            initActionBar()
                            replaceFragmentInActivity(LoginFragment(), R.id.fragmentContainer)

                        }

                        override fun onFailed(msg: String) { }
                    })
                }
            }

            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        initActionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (actionBarDrawerToggle != null) {
            true -> if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_CODE_KAKAO_LOGIN) {
            if (resultCode == RESULT_OK) {
                var authorizeCode = data?.extras?.get(Constants.EXTRA_KEY_AUTHORIZE_CODE) as String
                var requester = AccessTokenRequester()

                requester.getAccessToken(authorizeCode, object : ResponseListener<KakaoToken> {
                    override fun onReceived(data: KakaoToken) {
                        prefs.token = data
                        checkIsStoryUser(prefs.token)
                    }

                    override fun onFailed(msg: String) { }
                })
            }

        }
    }

    private fun checkIsStoryUser(token: KakaoToken) {
        MyStoriesRequester(token).isStoryUser(object : ResponseListener<StoryUser> {
            override fun onFailed(msg: String) { }

            override fun onReceived(data: StoryUser) {
                if (data.isStoryUser.toBoolean()) {
                    initUserInfoNaviHederView(prefs.token)
                    replaceFragmentInActivity(FeedFragment.newInstance(prefs.token), R.id.fragmentContainer)

                } else {
                    Toast.makeText(baseContext, "어머나! 카카오 스토리 사용자가 아니시군요. ;(", Toast.LENGTH_SHORT).show()
                }


            }
        })
    }

    private fun initUserInfoNaviHederView(token: KakaoToken) {
        MyStoriesRequester(token).getUserProfile(object : ResponseListener<Profile> {
            override fun onFailed(msg: String) { }

            override fun onReceived(data: Profile) {
                prefs.profile = data

                fragmentContainer?.run {
                    var welcome = StringBuilder().append("안녕하세요. ").append(data.nickName).append("님!").toString()
                    Snackbar.make(this, welcome, Snackbar.LENGTH_LONG).show()
                }

                var currentHeaderView = navigationView.getHeaderView(0)
                if (currentHeaderView != null) {
                    navigationView.removeHeaderView(currentHeaderView)
                }

                var headerView = LayoutInflater.from(this@MainActivity).inflate(R.layout.navigation_header_user_info, null)
                headerView.findViewById<ImageView>(R.id.navi_user_photo).setImageCircle(data.profileImageURL)
                headerView.findViewById<ImageView>(R.id.navi_user_background).setImageBlur(data.profileImageURL)
                headerView.findViewById<TextView>(R.id.navi_user_name).text = data.nickName

                navigationView.addHeaderView(headerView)
                initActionBar()

            }
        })
    }

}

