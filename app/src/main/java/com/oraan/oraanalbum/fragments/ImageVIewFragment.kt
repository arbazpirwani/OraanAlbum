package com.oraan.oraanalbum.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.oraan.oraanalbum.R

@SuppressLint("ValidFragment")
class ImageVIewFragment(var url: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_view, container, false)

        val image = view.findViewById<ImageView>(R.id.image_holder)
        Glide.with(view.context).load(url).into(image)

        return view
    }

}
