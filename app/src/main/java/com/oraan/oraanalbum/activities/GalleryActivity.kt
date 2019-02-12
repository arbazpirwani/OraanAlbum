package com.oraan.oraanalbum.activities

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.adapters.PhotoListAdapter
import com.oraan.oraanalbum.base.BaseActivity
import com.oraan.oraanalbum.responses.AlbumResponse
import com.oraan.oraanalbum.responses.PhotoResponse
import com.oraan.oraanalbum.utils.Constant
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.dark_normal_toolbar.*

class GalleryActivity : BaseActivity() {

    // array list for photos
    private var photoResponseList: ArrayList<PhotoResponse> = java.util.ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        // calling initialize function
        initialize()
    }

    override fun initialize() {

        // getting current album data from intent extra
        val albumData = Gson().fromJson(intent.getStringExtra(Constant.INTENT_VALUE), AlbumResponse::class.java)

        ib_header_back.setOnClickListener {
            onBackPressed()
        }

        // setting page title
        page_name.text = albumData.title

        photoResponseList = albumData.photoResponseList

        // adapter for photos list
        val photoListAdapter = PhotoListAdapter(this, photoResponseList)

        // calling this method to set adapter on recycler
        setupRecyclerView(photoListAdapter)

        // calling thread with 2 second delay to completed shimmer animation
        thread(2) {
            shimmer_view_container.visibility = View.GONE
        }


    }

    // This function is to set adapter on recycler
    private fun setupRecyclerView(adapter: PhotoListAdapter) {

        // layout manager for recycler list
        val layoutManager = GridLayoutManager(this, 3)

        recycler_photo.layoutManager = layoutManager
        recycler_photo.adapter = adapter
    }


}
