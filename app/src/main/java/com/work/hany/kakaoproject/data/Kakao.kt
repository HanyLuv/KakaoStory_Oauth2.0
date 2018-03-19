package com.work.hany.kakaoproject.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by hany on 2018. 3. 8..
 */


data class Profile(@SerializedName("nickName") var nickName: String,
                   @SerializedName("profileImageURL") var profileImageURL: String,
                   @SerializedName("thumbnailURL") var thumbnailURL: String,
                   @SerializedName("bgImageURL") var bgImageURL: String,
                   @SerializedName("permalink") var permalink: String,
                   @SerializedName("birthday") var birthday: String,
                   @SerializedName("birthdayType") var birthdayType: String)

data class Comment(@SerializedName("text") var text: String,
                   @SerializedName("writer") var writer: Writer)

data class Writer(@SerializedName("display_name") var display_name: String,
                  @SerializedName("profile_thumbnail_url") var profile_thumbnail_url: String)

data class Actor(@SerializedName("display_name") var display_name: String,
                 @SerializedName("profile_thumbnail_url") var profile_thumbnail_url: String)

data class Like(@SerializedName("emotion") var emotion: String,
                  @SerializedName("actor") var actor: Actor)

data class StoryUser(@SerializedName("isStoryUser") var isStoryUser: String)

data class KakaoStory(@SerializedName("id") var id: String,
                      @SerializedName("url") var url: String,
                      @SerializedName("media_type") var mediaType: String,
                      @SerializedName("created_at") var createdAt: String,
                      @SerializedName("comment_count") var commentCount: Int,
                      @SerializedName("like_count") var likeCount: Int,
                      @SerializedName("content") var content: String,
                      @SerializedName("media") var media: List<Media>,
                      @SerializedName("comments") var comments: List<Comment>,
                      @SerializedName("likes") var likes: List<Like>)

data class Media(@SerializedName("original") var original: String,
                 @SerializedName("xlarge") var xlarge: String,
                 @SerializedName("large") var large: String,
                 @SerializedName("medium") var medium: String,
                 @SerializedName("small") var small: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(original)
        writeString(xlarge)
        writeString(large)
        writeString(medium)
        writeString(small)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Media> = object : Parcelable.Creator<Media> {
            override fun createFromParcel(source: Parcel): Media = Media(source)
            override fun newArray(size: Int): Array<Media?> = arrayOfNulls(size)
        }
    }
}


data class KakaoToken(@SerializedName("access_token") var accessToken: String,
                      @SerializedName("token_type") var tokenType: String,
                      @SerializedName("refresh_token") var refreshToken: String,
                      @SerializedName("expires_in") var expiresIn: Int,
                      @SerializedName("scope") var scope: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(accessToken)
        writeString(tokenType)
        writeString(refreshToken)
        writeInt(expiresIn)
        writeString(scope)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<KakaoToken> = object : Parcelable.Creator<KakaoToken> {
            override fun createFromParcel(source: Parcel): KakaoToken = KakaoToken(source)
            override fun newArray(size: Int): Array<KakaoToken?> = arrayOfNulls(size)
        }
    }

}

data class Logout(@SerializedName("id") var id: String)