package com.oraan.oraanalbum.activities

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.adapters.AlbumListAdapter
import com.oraan.oraanalbum.base.BaseActivity
import com.oraan.oraanalbum.responses.AlbumResponse
import com.oraan.oraanalbum.responses.PhotoResponse
import com.oraan.oraanalbum.utils.Constant
import com.oraan.oraanalbum.utils.ServiceUtils
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.dark_normal_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumActivity : BaseActivity() {

    // array list for albums
    private var albumResponseList: ArrayList<AlbumResponse> = java.util.ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        // calling initialize function
        initialize()
    }

    override fun initialize() {

        ib_header_back.setOnClickListener {
            onBackPressed()
        }

        // setting page title
        page_name.text = getString(R.string.album)

        // adapter for album list
        val albumListAdapter = AlbumListAdapter(this, albumResponseList)

        // calling this method to set adapter on recycler
        setupRecyclerView(albumListAdapter)

        // calling api to get albums by selected user's id
        ServiceUtils.api.getAlbumById(

            // getting user's id from intent extra
            intent.getStringExtra(Constant.INTENT_VALUE)
        )
            .enqueue(object : Callback<ArrayList<AlbumResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<AlbumResponse>>?,
                    response: Response<ArrayList<AlbumResponse>>
                ) {
                    if (response.isSuccessful) {

                        // iterating body to fill album list
                        response.body().forEach { album ->

                            // calling inner api to get photos by album's id
                            ServiceUtils.api.getPhotosByAlbumId(album.id!!)
                                .enqueue(object : Callback<ArrayList<PhotoResponse>> {
                                    override fun onResponse(
                                        call: Call<ArrayList<PhotoResponse>>?,
                                        response: Response<ArrayList<PhotoResponse>>
                                    ) {
                                        if (response.isSuccessful) {

                                            // array list for photos
                                            val photoResponseList: ArrayList<PhotoResponse> = java.util.ArrayList()

                                            // iterating body to fill photo list
                                            response.body().forEach {
                                                photoResponseList.add(it)
                                            }

                                            // adding photos to album modal
                                            album.photoResponseList = photoResponseList

                                            // adding album to album list
                                            albumResponseList.add(album)

                                            shimmer_view_container.visibility = View.GONE

                                            // notifying adapter after getting data from server
                                            albumListAdapter.notifyDataSetChanged()
                                        }
                                    }

                                    override fun onFailure(call: Call<ArrayList<PhotoResponse>>?, t: Throwable?) {
                                        showToast(t?.message!!)
                                    }

                                })
                        }

                    }
                }

                override fun onFailure(call: Call<ArrayList<AlbumResponse>>?, t: Throwable?) {
                    showToast(t?.message!!)

                }

            })
    }

    // This function is to set adapter on recycler
    private fun setupRecyclerView(adapter: AlbumListAdapter) {

        // layout manager for recycler list
        val layoutManager = GridLayoutManager(this, 2)

        recycler_album.layoutManager = layoutManager
        recycler_album.adapter = adapter
    }


}
