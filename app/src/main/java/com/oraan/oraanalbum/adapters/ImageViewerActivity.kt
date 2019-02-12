package com.oraan.oraanalbum.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.base.BaseActivity
import com.oraan.oraanalbum.fragments.ImageVIewFragment
import com.oraan.oraanalbum.responses.PhotoResponse
import com.oraan.oraanalbum.utils.Constant
import kotlinx.android.synthetic.main.activity_image_viewer.*
import kotlinx.android.synthetic.main.dark_transparent_toolbar.*

class ImageViewerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)
        initialize()
    }

    override fun initialize() {

        ib_header_back.setOnClickListener {
            onBackPressed()
        }
        val position = intent.getIntExtra("position", 0)
        val albumData = Gson().fromJson<java.util.ArrayList<PhotoResponse>>(
            intent.getStringExtra(Constant.INTENT_VALUE),
            object : TypeToken<java.util.ArrayList<PhotoResponse>>() {
            }.type
        )

        val imageList = ArrayList<Fragment>()
        albumData.forEach {
            imageList.add(ImageVIewFragment(it.url!!))
        }

        val pagerAdapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getCount() = imageList.size

            override fun getItem(position: Int) = imageList[position]

        }

        image_pager.adapter = pagerAdapter

        image_pager.currentItem = position
    }

}
