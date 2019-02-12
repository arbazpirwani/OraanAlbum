package com.oraan.oraanalbum.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.adapters.UsersListAdapter
import com.oraan.oraanalbum.base.BaseActivity
import com.oraan.oraanalbum.responses.UserResponse
import com.oraan.oraanalbum.utils.ServiceUtils
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.search_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersActivity : BaseActivity() {

    // boolean check for double check exit
    private var doubleBackToExitPressedOnce = false

    // array list for users
    private var userResponseList: ArrayList<UserResponse> = java.util.ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        // calling initialize function
        initialize()

    }

    override fun initialize() {

        ib_search?.setOnClickListener {

            // calling showSearch function to set animation on search field to show
            showSearch()

        }

        ib_close_search?.setOnClickListener {

            // calling hideSearch function to set animation on search field to hide
            hideSearch()

        }

        // adapter for users list
        val userListAdapter = UsersListAdapter(this, userResponseList)

        // calling this method to set adapter on recycler
        setupRecyclerView(userListAdapter)

        // this listener is to update users filter list
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // implementing onTextChanged to filter list
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                userListAdapter.filter.filter(s.toString())
            }
        })

        // calling api to get all users
        ServiceUtils.api.getUsers().enqueue(object : Callback<ArrayList<UserResponse>> {
            override fun onResponse(call: Call<ArrayList<UserResponse>>?, response: Response<ArrayList<UserResponse>>) {

                if (response.isSuccessful) {

                    // iterating body to fill users list
                    response.body().forEach {
                        userResponseList.add(it)
                    }
                    shimmer_view_container.visibility = View.GONE

                    // notifying adapter after getting data from server
                    userListAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponse>>?, t: Throwable?) {
                showToast(t?.message!!)
            }


        })


    }

    // This function is to set adapter on recycler
    private fun setupRecyclerView(adapter: UsersListAdapter) {

        // layout manager for recycler list
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recycler_users.layoutManager = layoutManager
        recycler_users.adapter = adapter
    }

    // overriding this for double check exit
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        showToast(getString(R.string.exit_message), Gravity.CENTER)
        thread(2) { doubleBackToExitPressedOnce = false }
    }


    // This function to set animation on search field to show
    private fun showSearch() {

        // setting visibility visible on search field
        search_layout.visibility = View.VISIBLE

        // setting animation on search field
        search_layout.setAnimationOnView(R.anim.left_to_right_fade_in)
    }

    // This function to set animation on search field to hide
    private fun hideSearch() {

        // setting visibility gone on search field
        search_layout.visibility = View.GONE

        // setting animation on search field
        search_layout.setAnimationOnView(R.anim.right_to_left_fade_out)

        // calling invoke extension to clear search field text
        et_search()

        // trying to save code from crash if keyboard is not opened
        try {
            // to hide opened keyboard
            hideSoftKeyboard(this)
        } catch (ignored: Exception) {

        }
    }

    // This function is hide keyboard
    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (activity.currentFocus != null)
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
    }

}
