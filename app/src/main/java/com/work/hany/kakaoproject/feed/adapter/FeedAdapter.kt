package com.work.hany.kakaoproject.feed.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.work.hany.kakaoproject.R
import com.work.hany.kakaoproject.DetailActivity
import com.work.hany.kakaoproject.common.*
import com.work.hany.kakaoproject.data.KakaoStory
import java.util.*

/**
 * Created by hany on 2018. 3. 11..
 */
class FeedAdapter(private var stories: ArrayList<KakaoStory>) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            Type.NOTE.ordinal -> {
                var view = parent.inflate(R.layout.item_story_note_row, false)
                NoteViewHolder(view)
            }

            Type.PHOTO.ordinal -> {
                var view = parent.inflate(R.layout.item_story_photo_row, false)
                PhotoViewHolder(view)
            }

            else -> {
                var view = parent.inflate(R.layout.item_story_empty_row, false)
                EmptyViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = stories.size

    override fun getItemViewType(position: Int): Int {
        return when (stories[position].mediaType) {
            Constants.STORY_MEDIA_TYPE_PHOTO -> {
                Type.PHOTO.ordinal
            }

            Constants.STORY_MEDIA_TYPE_NOTE -> {
                Type.NOTE.ordinal
            }

            else -> {
                Type.NOT_SUPPORTED.ordinal
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stories[position], position)
    }

    private fun add(story: KakaoStory) {
        stories.add(story)
        notifyItemInserted(stories.size - 1)
    }

    fun addAll(stories: List<KakaoStory>) {
        for (story in stories) {
            add(story)
        }
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var context = view.context
        private var commentCount = view.findViewById<TextView>(R.id.story_comment_count)
        private var likeCount = view.findViewById<TextView>(R.id.story_like_count)

        private var lastPosition = -1


        open fun bind(story: KakaoStory, position: Int) {
            commentCount.text = StringBuilder().commentCountString(story.commentCount)
            likeCount.text =StringBuilder().likeCountString(story.likeCount)


            itemView.setOnClickListener {
                var intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Constants.EXTRA_KEY_KAKAO_ID, story.id)
                context.startActivity(intent)

            }

            setAnimation(itemView, position)

        }

        private fun setAnimation(view: View, position: Int) {
            if (position > lastPosition) {
                val animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
                view.startAnimation(animation)
                lastPosition = position
            }
        }

    }


    class PhotoViewHolder(var view: View) : ViewHolder(view) {
        override fun bind(story: KakaoStory, position: Int) {
            super.bind(story, position)

            var image = view.findViewById<ImageView>(R.id.story_image)

            if (story.media != null && story.media.isNotEmpty()) {
                image.setImage(story.media[0].original)
            }
        }


    }

    class NoteViewHolder(var view: View) : ViewHolder(view) {
        private val bgColor = Random().color()

        override fun bind(story: KakaoStory, position: Int) {
            super.bind(story, position)

            var note = view.findViewById<TextView>(R.id.story_text)
            note.setBackgroundColor(bgColor)
            note.text = story.content
        }
    }

    class EmptyViewHolder(var view: View) : ViewHolder(view)

}



enum class Type(var code: Int) {
    PHOTO(0),
    NOTE(1),
    NOT_SUPPORTED(2),

}