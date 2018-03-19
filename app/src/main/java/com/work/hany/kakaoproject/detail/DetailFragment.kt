package com.work.hany.kakaoproject.detail

import android.animation.LayoutTransition
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.work.hany.kakaoproject.DetailActivity
import com.work.hany.kakaoproject.R
import com.work.hany.kakaoproject.detail.adapter.CommentAdapter
import com.work.hany.kakaoproject.detail.adapter.DetailAdapter
import com.work.hany.kakaoproject.common.Constants
import com.work.hany.kakaoproject.common.commentCountString
import com.work.hany.kakaoproject.common.likeCountString
import com.work.hany.kakaoproject.common.prefs
import com.work.hany.kakaoproject.data.Comment
import com.work.hany.kakaoproject.data.KakaoStory
import com.work.hany.kakaoproject.http.MyStoriesRequester
import com.work.hany.kakaoproject.http.ResponseListener

/**
 * Created by hany on 2018. 3. 12..
 */


class DetailFragment : Fragment(), ResponseListener<KakaoStory> {
    private lateinit var mContext: Context
    private lateinit var commentViewExpandedListener: OnCommentViewExpandedListener

    private lateinit var progressBar: ProgressBar

    private lateinit var detailDimBackground: View
    private lateinit var commentContainerLayout: ViewGroup

    private lateinit var detailStoryLikeCount: TextView
    private lateinit var detailStoryCommentCount: TextView

    private lateinit var commentListRecyclerView: RecyclerView
    private lateinit var detailListRecyclerView: RecyclerView



    private var isCommentViewExpanded = false
        set(value) {
            field = value
            if (value) {
                detailDimBackground.visibility = View.VISIBLE

            } else {
                detailDimBackground.visibility = View.GONE
            }

        }

    interface OnCommentViewExpandedListener{
        fun onCommentViewExpanded(isExpanded: Boolean)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context != null) {
            this.mContext = context
        }

        if (mContext is DetailActivity){
            commentViewExpandedListener = mContext as DetailActivity as OnCommentViewExpandedListener
        }

    }

    override fun onReceived(data: KakaoStory) {
        detailStoryCommentCount.text = StringBuilder().commentCountString(data.commentCount)
        detailStoryLikeCount.text = StringBuilder().likeCountString(data.likeCount)

        initDetailView(data)

        if (data.comments != null) {
            initCommentView(data.comments as ArrayList<Comment>)
        }
    }

    override fun onFailed(msg: String) { progressBar.visibility = View.GONE }


    companion object {
        fun newInstance(id: String): DetailFragment {
            var fragment = DetailFragment()
            var bundle = Bundle()
            bundle.putString(Constants.EXTRA_KEY_KAKAO_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        var id = arguments.getString(Constants.EXTRA_KEY_KAKAO_ID)
        MyStoriesRequester(prefs.token).getMyStory(id, this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var rootView = inflater.inflate(R.layout.framgnet_detail, container, false)

        detailDimBackground = rootView.findViewById(R.id.detail_dim_background)
        detailStoryLikeCount = rootView.findViewById(R.id.detail_story_like_count)
        commentListRecyclerView = rootView.findViewById(R.id.comment_list)
        detailListRecyclerView = rootView.findViewById(R.id.detail_list)
        progressBar = rootView.findViewById(R.id.progress_bar)
        commentContainerLayout = rootView.findViewById(R.id.comment_container_layout)
        detailStoryCommentCount = rootView.findViewById(R.id.detail_story_comment_count)

        detailDimBackground.setOnTouchListener { v, event -> true }

        return rootView
    }

    private fun initDetailView(story: KakaoStory) {
        detailListRecyclerView.layoutManager = LinearLayoutManager(mContext)
        detailListRecyclerView.adapter = DetailAdapter(fragmentManager, story, prefs.profile)
        progressBar.visibility = View.GONE

    }

    private fun initCommentView(comment: ArrayList<Comment>) {
        commentListRecyclerView.layoutManager = LinearLayoutManager(mContext)
        commentListRecyclerView.adapter = CommentAdapter(comment)
        commentContainerLayout.setOnClickListener({
            expandCommentView()

        })

        commentListRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                commentListRecyclerView.measure(
                        View.MeasureSpec.makeMeasureSpec(commentListRecyclerView.width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                commentListRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                if (commentListRecyclerView.measuredHeight > resources.getDimensionPixelOffset(R.dimen.story_comment_list_max_height)) {
                    var params = commentListRecyclerView.layoutParams
                    params.height = resources.getDimensionPixelOffset(R.dimen.story_comment_list_max_height)
                }

            }
        })
    }


    open fun expandCommentView() {
        if (!isCommentViewExpanded) {
            isCommentViewExpanded = true
            commentContainerLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

            var anim = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    super.applyTransformation(interpolatedTime, t)
                    val params = commentContainerLayout.layoutParams as RelativeLayout.LayoutParams
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    params.removeRule(RelativeLayout.BELOW)
                    commentContainerLayout.layoutParams = params
                }
            }

            commentContainerLayout.startAnimation(anim)

        } else {
            isCommentViewExpanded = false
            commentContainerLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

            var anim = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    super.applyTransformation(interpolatedTime, t)
                    val params = commentContainerLayout.layoutParams as RelativeLayout.LayoutParams
                    params.addRule(RelativeLayout.BELOW, R.id.detail_contents_layout)
                    params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    commentContainerLayout.layoutParams = params
                }
            }

            commentContainerLayout.startAnimation(anim)
        }

        commentViewExpandedListener.onCommentViewExpanded(isCommentViewExpanded)
    }
}