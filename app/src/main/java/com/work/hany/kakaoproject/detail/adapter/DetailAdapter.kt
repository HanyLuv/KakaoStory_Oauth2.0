package com.work.hany.kakaoproject.detail.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.work.hany.kakaoproject.R
import com.work.hany.kakaoproject.common.*
import com.work.hany.kakaoproject.data.KakaoStory
import com.work.hany.kakaoproject.data.Media
import com.work.hany.kakaoproject.data.Profile
import com.work.hany.kakaoproject.detail.DetailPhotoPagerFragment
import java.util.*

class DetailAdapter(private var fm: FragmentManager,
                    private var story: KakaoStory,
                    private var profile: Profile) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var medias: ArrayList<Media> = if (story.media != null && story.media is ArrayList<Media>) ArrayList(story.media) else ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.HEADER_POSITION -> {
                var view = parent.inflate(R.layout.item_story_detail_contents_row, false)
                ContentsViewHolder(view)
            }

            else -> {
                var view = parent.inflate(R.layout.item_story_detail_photo_row, false)
                PhotoViewHolder(view, medias)
            }

        }

    }

    override fun getItemViewType(position: Int): Int = position


    override fun getItemCount(): Int {
        var itemCount = 1 //헤더 값
        if (story.media != null) {
            itemCount += story.media.size
        }
        return itemCount
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is PhotoViewHolder) {
            holder.run {
                var position = position - 1 // hearder값 빼야함.
                bind(fm, story.media[position], position)
            }

        } else if (holder is ContentsViewHolder) {
            holder.run {
                bind(story, profile)
            }

        }

    }


    private class PhotoViewHolder(view: View, private var medias: ArrayList<Media>) : RecyclerView.ViewHolder(view) {
        private var detailImage: ImageView = view.findViewById(R.id.detail_image)

        fun bind(fm: FragmentManager, media: Media, position: Int) {
            detailImage.setImage(media.large)

            medias.isEmpty().apply {
                detailImage.setOnClickListener {
                    var fragment = DetailPhotoPagerFragment.newInstance(position, medias)
                    fm.beginTransaction()
                            .addSharedElement(detailImage, ViewCompat.getTransitionName(detailImage))
                            .add(R.id.detail_fragment_container, fragment)
                            .addToBackStack("")
                            .commit()

                }
            }

        }


    }

    //콘텐츠 부분 댓글이랑 출력해주는 요소가 같은뎅... 공통으로 빼서 하면되지않을까? 인터페이스
    private class ContentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var contents: TextView = view.findViewById(R.id.detail_contents_textview)
        private var username: TextView = view.findViewById(R.id.detail_username_textview)
        private var create: TextView = view.findViewById(R.id.detail_created_at_textview)
        private var usernameImg: ImageView = view.findViewById(R.id.detail_user_imageview)


        fun bind(story: KakaoStory, profile: Profile) {
            contents.text = story.content
            username.text = profile.nickName
            usernameImg.setImageCircle(profile.profileImageURL)
            create.text = story.createdAt?.let{
                it.createAtConvertor()
            }

        }
    }

}


