package com.work.hany.kakaoproject.detail.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.work.hany.kakaoproject.R
import com.work.hany.kakaoproject.common.inflate
import com.work.hany.kakaoproject.common.setImageCircle
import com.work.hany.kakaoproject.data.Comment

/**
 * Created by hany on 2018. 3. 11..
 */


class CommentAdapter(private var comments: ArrayList<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_story_detail_comment_row, false))
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(comments[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var contents: TextView = view.findViewById(R.id.comment_user_comment)
        private var userImg: ImageView = view.findViewById(R.id.comment_user_img)
        private var userName: TextView = view.findViewById(R.id.comment_user_nickname)

        fun bind(comment: Comment) {
            contents.text = comment.text
            userName.text = comment.writer.display_name
            userImg.setImageCircle(comment.writer.profile_thumbnail_url)
        }


    }
}
