package com.challenge.developer.activity

import android.content.Context
import android.content.Intent
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

            //Get remember option info if exist
            val sharedPreference = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val rememberOption = sharedPreference.getBoolean("rememberOption", false)

            if (rememberOption){

                //Go to orders activity
                val i = Intent(this@UserActivity, MainActivity::class.java)
                startActivity(i)
                finish()

            }else{

                //Go to user login
                supportFragmentManager.beginTransaction()
                    .replace(R.id.rootView, UserFragment())
                    .commitNow()
            }
        }
    }

    //Set toolbar layout and initilaize to action bar
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
