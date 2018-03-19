package com.work.hany.kakaoproject

import android.os.Bundle
import com.work.hany.kakaoproject.common.*
import com.work.hany.kakaoproject.detail.DetailFragment


/**
 * Created by hany on 2018. 3. 10..
 */

class DetailActivity : ToolbarActivity(), DetailFragment.OnCommentViewExpandedListener {
    private var isCommentViewExpanded: Boolean = false


    override fun onCommentViewExpanded(isExpanded: Boolean) {
        isCommentViewExpanded = isExpanded
    }


    open fun actionBar(isHide: Boolean) {
        if (isHide) supportActionBar?.hide() else supportActionBar?.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val fragment = DetailFragment.newInstance(intent.extras.getString(Constants.EXTRA_KEY_KAKAO_ID))
        replaceFragmentInActivity(fragment, R.id.detail_fragment_container)
    }

    override fun onBackPressed() {
        supportActionBar?.let {
            if (!it.isShowing) it.show()
        }

        if (isCommentViewExpanded) {
            val fragment = supportFragmentManager.findFragmentById(R.id.detail_fragment_container)
            if (fragment is DetailFragment) {
                fragment.expandCommentView()
            }

        } else {
            super.onBackPressed()

        }

    }


}



