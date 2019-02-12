package com.oraan.oraanalbum.adapters

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.base.BaseRecyclerAdapter
import com.oraan.oraanalbum.responses.PhotoResponse
import com.oraan.oraanalbum.utils.BasicStaticFunction
import com.oraan.oraanalbum.utils.Constant

class PhotoListAdapter(activity: Activity, val array: ArrayList<PhotoResponse>) :
    BaseRecyclerAdapter<PhotoListAdapter.CustomVIewHolder>(activity) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListAdapter.CustomVIewHolder {
        return PhotoListAdapter.CustomVIewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_photo_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = array.size

    override fun onBindViewHolder(holder: CustomVIewHolder, position: Int) {

        val photoModel = array[position]

        Glide.with(activity).load(photoModel.thumbnailUrl).into(holder.view_thumbnail_photo)

        holder.main_layout.setOnClickListener {

            val intent = Intent(activity, ImageViewerActivity::class.java)
            intent.putExtra(Constant.INTENT_VALUE,BasicStaticFunction.convertToJson(array))
            intent.putExtra("position",position)

            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_left, R.anim.slide_prop_left)

        }
    }

    class CustomVIewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var main_layout: LinearLayout = view.findViewById(R.id.main_layout)

        var view_thumbnail_photo: ImageView = view.findViewById(R.id.view_thumbnail_photo)
    }
}