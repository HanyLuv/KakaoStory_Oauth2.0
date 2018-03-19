package com.work.hany.kakaoproject.common

import android.graphics.Color
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import java.util.*
import jp.wasabeef.glide.transformations.BlurTransformation
import java.text.SimpleDateFormat


/**
 * Created by hany on 2018. 3. 8..
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.setImage(url: String) {
    Glide.with(context).load(url).into(this)
}

fun ImageView.setImageCircle(url: String) {
    Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(this)
}

fun ImageView.setImageBlur(url: String) {
    Glide.with(context).load(url).apply(bitmapTransform(BlurTransformation(25))).into(this)
}

fun Random.color(): Int{
    return Color.argb(52, nextInt(256), nextInt(256), nextInt(256))
}

fun StringBuilder.commentCountString(count: Int): String {
    return append("댓글").append(" ").append(count.toString()).toString()

}
fun StringBuilder.likeCountString(count: Int): String {
    return append("느낌").append(" ").append(count.toString()).toString()
}

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun String.createAtConvertor(): String{
    val crateFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.getDefault())
    crateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return crateFormat.format(dateFormat.parse(this))
}