package com.work.hany.kakaoproject.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.work.hany.kakaoproject.R
import com.work.hany.kakaoproject.data.KakaoStory
import com.work.hany.kakaoproject.common.Constants
import com.work.hany.kakaoproject.data.KakaoToken
import com.work.hany.kakaoproject.http.MyStoriesRequester
import com.work.hany.kakaoproject.http.ResponseListener
import com.work.hany.kakaoproject.common.prefs
import com.work.hany.kakaoproject.http.AccessTokenRequester
import com.work.hany.kakaoproject.feed.adapter.FeedAdapter


/**
 * Created by hany on 2018. 3. 8..
 * 로그인 성공 후 제일 처음 진입 화면.
 */

class FeedFragment : Fragment(), ResponseListener<List<KakaoStory>> {

    private lateinit var requester: MyStoriesRequester
    private lateinit var token: KakaoToken

    private lateinit var storiesFeedRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutManager: LinearLayoutManager

    /** 페이징을 위한 변수들*/
    private lateinit var adapter: FeedAdapter
    private var stories = ArrayList<KakaoStory>()

    private var isLoading = false
    private var isLastPage = false
    private val pageSize = 18 //최대 18개 받아온당.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = arguments.getParcelable(Constants.BUNDLE_KEY_TOKEN)
        requester = MyStoriesRequester(token)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.framgnet_feed, container, false)
        progressBar = rootView.findViewById(R.id.progress_bar)

        storiesFeedRecyclerView = rootView.findViewById(R.id.story_feed_list)
        layoutManager = LinearLayoutManager(context)
        storiesFeedRecyclerView.layoutManager = layoutManager

        storiesFeedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount - visibleItemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        loadMoreItems()
                    }
                }
            }
        })

        progressBar.visibility = View.VISIBLE
        requester.getUserStories(this)

        return rootView
    }

    private fun loadMoreItems() {
        progressBar.visibility = View.VISIBLE
        isLoading = true

        val lastStoryId = stories[(stories.size - 1)].id
        requester.getUserStories(lastStoryId, this)
    }

    /** 카카오 스토리를 가져오는 리퀘스터의 결과를 받는 리스너 */
    override fun onReceived(data: List<KakaoStory>) {
        progressBar.visibility = View.GONE
        isLoading = false
        if (stories.isEmpty()) {
            stories = data as ArrayList
            adapter = FeedAdapter(stories)
            storiesFeedRecyclerView.adapter = adapter

        } else {
            if (data.size < pageSize) {
                isLastPage = true

            }
            adapter.addAll(data)
        }

    }

    override fun onFailed(msg: String) {
        Log.e("FeedFragment", "FAILED: " + msg)
        AccessTokenRequester().updateAccessToken(token, object : ResponseListener<KakaoToken> {
            override fun onReceived(data: KakaoToken) {
                progressBar.visibility = View.VISIBLE
                prefs.token = data
                requester = MyStoriesRequester(token)
                requester.getUserStories(this@FeedFragment)

            }

            override fun onFailed(msg: String) { }
        })
    }

    companion object {
        fun newInstance(token: KakaoToken): FeedFragment {
            val fragment = FeedFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constants.BUNDLE_KEY_TOKEN, token)
            fragment.arguments = bundle
            return fragment
        }

    }

}