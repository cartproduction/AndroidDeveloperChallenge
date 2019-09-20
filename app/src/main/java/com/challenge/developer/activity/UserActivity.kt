package com.challenge.developer.activity

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.challenge.developer.R
import com.challenge.developer.fragment.UserFragment
import kotlinx.android.synthetic.main.user_activity.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)

        initToolbar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.rootView, UserFragment())
                .commitNow()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // Specify that tabs should be displayed in the action bar.
        actionBar!!.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)

        val mInflater = LayoutInflater.from(this)
        val mCustomView = mInflater.inflate(R.layout.toolbar_layout, null)
        val params = ActionBar.LayoutParams(
            //Center the textview in the ActionBar !
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.MATCH_PARENT,
            Gravity.CENTER)

        actionBar.setCustomView(mCustomView, params)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setStackedBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext,R.color.colorAccent)))
        actionBar.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext,R.color.colorAccent)))
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }

}
