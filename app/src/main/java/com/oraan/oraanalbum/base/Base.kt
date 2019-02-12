package com.oraan.oraanalbum.base

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import com.oraan.oraanalbum.R
import com.oraan.oraanalbum.utils.Constant


/*
* This is Base file containing all base functionality
* and to reuse common functions
* */


/*
*   This abstract class is for reusable functionality
*   in whole project's activities
*/
abstract class BaseActivity : AppCompatActivity() {

    // This function must implement in every extended activity to initialize basic functionality
    abstract fun initialize()

    // This function is use to start next activity with finishing current activity
    fun activityChangeWithFinish(target: Class<*>) {
        startActivity(Intent(applicationContext, target))
        overridePendingTransition(R.anim.slide_left, R.anim.slide_prop_left)
        finish()
    }


    /*
    * This function is use to sleep or delay in activity has 2 parameters
    * first parameter 'seconds' has by default value 1
    * and other is higher order function called task
    * task function will be call in handler to delayed activity by given seconds time
    * */
    inline fun thread(seconds: Long = 1, crossinline task: () -> Unit) {
        Handler().postDelayed({ task() }, seconds * 1000)
    }

    // This function is just for page transition changes
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_right, R.anim.slide_prop_right)
    }


    /*
    * This toast function is use to show toast
    * in activity in any direction it has 4 parameters
    * message hold text
    * gravity to set toast postion
    * x to set x axis
    * y to set y axis
    * */
    protected fun showToast(message: Any = "nothing", gravity: Int = Gravity.BOTTOM, x: Int = 0, y: Int = 0) {

        val toast = Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT)

        val xAxis: Int = if (x == 0) toast.xOffset else x
        val yAxis: Int = if (y == 0) toast.yOffset else y

        toast.setGravity(gravity, xAxis, yAxis)
        toast.show()
    }

    // Project extension functions

    // This functions help to clear field text
    operator fun EditText.invoke() {
        this.setText("")
    }

    // This extension is help to set and start animation on any view
    fun View.setAnimationOnView(animationResource: Int): Animation {
        val animationUtils = AnimationUtils.loadAnimation(applicationContext, animationResource)
        this.startAnimation(animationUtils)
        return animationUtils

    }

}

/*
*   This abstract class is for reusable functionality
*   in my whole project's recycler adapter
*/
abstract class BaseRecyclerAdapter<T : RecyclerView.ViewHolder>(var activity: Activity) : RecyclerView.Adapter<T>() {

    // This function is use to start next activity
    fun activityChange(target: Class<*>) {
        activity.startActivity(Intent(activity, target))
        activity.overridePendingTransition(R.anim.slide_left, R.anim.slide_prop_left)

    }

    // This function is use to start next activity with intent data
    fun activityChange(target: Class<*>, extraString: String) {
        activity.startActivity(Intent(activity, target).putExtra(Constant.INTENT_VALUE, extraString))
        activity.overridePendingTransition(R.anim.slide_left, R.anim.slide_prop_left)

    }

}