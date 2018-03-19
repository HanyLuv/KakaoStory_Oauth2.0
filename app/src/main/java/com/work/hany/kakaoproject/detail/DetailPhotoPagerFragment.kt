package com.work.hany.kakaoproject.detail

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.work.hany.kakaoproject.DetailActivity
import com.work.hany.kakaoproject.R
import com.work.hany.kakaoproject.common.Constants
import com.work.hany.kakaoproject.data.Media

/**
 * Created by hany on 2018. 3. 12..
 */


class DetailPhotoPagerFragment : Fragment() {

    companion object {
        fun newInstance(selectPosition: Int, medias: ArrayList<Media>): DetailPhotoPagerFragment {
            var fragment = DetailPhotoPagerFragment()
            var bundle = Bundle()
            bundle.putParcelableArrayList(Constants.BUNDLE_KEY_MEDIAS, medias)
            bundle.putInt(Constants.BUNDLE_KEY_MEDIA_SELECTED, selectPosition)
            fragment.arguments = bundle
            return fragment
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.framgnet_detail_photo_pager, container, false)
        var photosViewPager = rootView.findViewById<ViewPager>(R.id.detail_photo_pager)
        var dotsTabLayout = rootView.findViewById<TabLayout>(R.id.detail_tab_dots)
        dotsTabLayout.setupWithViewPager(photosViewPager)

        var medias: ArrayList<Media> = arguments.getParcelableArrayList(Constants.BUNDLE_KEY_MEDIAS)
        var selectPosition: Int = arguments.getInt(Constants.BUNDLE_KEY_MEDIA_SELECTED)

        if (medias.size <= 1) dotsTabLayout.visibility = View.GONE

        if (activity is DetailActivity){
            var activity = activity as DetailActivity
            activity.actionBar(true)
        }

        photosViewPager.adapter = PhotoViewPager(fragmentManager, medias)
        photosViewPager.setCurrentItem(selectPosition,false)
        return rootView
    }

    private class PhotoViewPager(fm: FragmentManager, var photos: ArrayList<Media>) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = photos.size

        override fun getItem(position: Int): Fragment {
            return PhotoPagerItemFragment.newInstance(photos[position])
        }


    }
}