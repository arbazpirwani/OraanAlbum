package com.oraan.oraanalbum.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.activities.GalleryActivity
import com.oraan.oraanalbum.base.BaseRecyclerAdapter
import com.oraan.oraanalbum.responses.AlbumResponse
import com.oraan.oraanalbum.utils.BasicStaticFunction

class AlbumListAdapter(activity: Activity, val array: ArrayList<AlbumResponse>) :
    BaseRecyclerAdapter<AlbumListAdapter.CustomVIewHolder>(activity) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListAdapter.CustomVIewHolder {
        return AlbumListAdapter.CustomVIewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_album_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = array.size

    override fun onBindViewHolder(holder: CustomVIewHolder, position: Int) {

        val albumModel = array[position]

        holder.main_layout.setOnTouchListener(BasicStaticFunction.FOCUS_TOUCH_LISTENER)

        holder.album_title.text = albumModel.title

        holder.total_counter.text = activity.getString(R.string.photo_count, albumModel.photoResponseList.size)

        Glide.with(activity).load(albumModel.photoResponseList[0].thumbnailUrl).into(holder.view_thumbnail_album)

        holder.main_layout.setOnClickListener {
            if (albumModel.id != null)
                activityChange(GalleryActivity::class.java, BasicStaticFunction.convertToJson(albumModel))
            else
                Log.e(activity.callingPackage, activity.getString(R.string.error_message))
        }
    }

    class CustomVIewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var main_layout: LinearLayout = view.findViewById(R.id.main_layout)
        var view_thumbnail_album: ImageView = view.findViewById(R.id.view_thumbnail_album)
        var album_title: TextView = view.findViewById(R.id.album_title)
        var total_counter: TextView = view.findViewById(R.id.total_counter)
    }
}