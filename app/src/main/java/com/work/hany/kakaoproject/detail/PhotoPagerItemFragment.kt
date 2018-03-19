package com.work.hany.kakaoproject.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.work.hany.kakaoproject.R
import com.work.hany.kakaoproject.common.Constants
import com.work.hany.kakaoproject.common.setImage
import com.work.hany.kakaoproject.data.Media
import com.work.hany.kakaoproject.detail.view.DetailImageView

/**
 * Created by hany on 2018. 3. 12..
 */


class PhotoPagerItemFragment : Fragment() {


    companion object {
        fun newInstance(media: Media): PhotoPagerItemFragment {
            val fragment = PhotoPagerItemFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constants.BUNDLE_KEY_MEDIA, media)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.pager_item_detail_photo, container, false)
        val media: Media = arguments.getParcelable(Constants.BUNDLE_KEY_MEDIA)
        var imgView = rootView.findViewById<DetailImageView>(R.id.detail_photo_image).setImage(media.large)

        return rootView

    }

}