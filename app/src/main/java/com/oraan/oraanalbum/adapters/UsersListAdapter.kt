package com.oraan.oraanalbum.adapters

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RelativeLayout
import android.widget.TextView
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.activities.AlbumActivity
import com.oraan.oraanalbum.base.BaseRecyclerAdapter
import com.oraan.oraanalbum.responses.UserResponse
import com.oraan.oraanalbum.utils.BasicStaticFunction
import java.util.*

class UsersListAdapter(activity: Activity, val array: ArrayList<UserResponse>, var filteredItems: ArrayList<UserResponse> = array) :
    BaseRecyclerAdapter<UsersListAdapter.CustomVIewHolder>(activity),
    Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVIewHolder {
        return CustomVIewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_users_list, parent, false))
    }

    override fun getItemCount() = filteredItems.size

    override fun onBindViewHolder(holder: CustomVIewHolder, position: Int) {
        val userModel = filteredItems[position]

        holder.user_name.text = userModel.name
        holder.email.text = userModel.email
        holder.letter_view_text.text = (userModel.name)!![0].toString()
        (holder.letter_view_background.background as GradientDrawable).setColor(getRandomColor())

        holder.main_layout.setOnTouchListener(BasicStaticFunction.FOCUS_TOUCH_LISTENER)

        holder.main_layout.setOnClickListener {
            if (userModel.id != null)
                activityChange(AlbumActivity::class.java, userModel.id!!)
            else
                Log.e(activity.callingPackage, "something went wrong!")
        }


    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                filteredItems = if (charString.isEmpty()) {
                    array
                } else {
                    val filteredList = java.util.ArrayList<UserResponse>()
                    for (row in array) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name!!.toLowerCase().contains(charString.toLowerCase()) || row.email!!.toLowerCase().contains(
                                charString.toLowerCase()
                            )
                        )
                            filteredList.add(row)
                    }

                    filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filteredItems
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                filteredItems = (filterResults.values as java.util.ArrayList<UserResponse>)
                notifyDataSetChanged()
            }
        }
    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    class CustomVIewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var main_layout: RelativeLayout = view.findViewById(R.id.main_layout)
        var letter_view_background: View = view.findViewById(R.id.letter_view_background)
        var letter_view_text: TextView = view.findViewById(R.id.letter_view_text)
        var user_name: TextView = view.findViewById(R.id.user_name)
        var email: TextView = view.findViewById(R.id.email)
    }

}