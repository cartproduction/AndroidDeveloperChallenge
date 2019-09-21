package com.challenge.developer.repository

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.challenge.developer.R
import com.challenge.developer.model.User
import com.challenge.developer.viewmodel.OrdersViewModel
import com.challenge.developer.viewmodel.UserViewModel
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.lmntrx.android.library.livin.missme.ProgressDialog
import com.raventech.fujibas.interfaces.ApiClient
import com.raventech.fujibas.interfaces.NetworkAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class UserRepository(activity: AppCompatActivity, private var context: Context) {

    private var apiService: Retrofit
    private var progressDialog : ProgressDialog

    init {

        //Initialize api client service
        apiService = ApiClient.getClient(context)

        //Alert dialog creating with context
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage(context.getString(R.string.loading))
        progressDialog.setCancelable(false)

    }

    //User login service

    fun login(email : String, password : String,rememberOption: Boolean, viewModel: UserViewModel) {

        progressDialog.show()

        val user = User()
        user.userName = email.trim()
        user.userPassword = password.trim()

        val login = apiService.create(NetworkAPI::class.java)
            .login(user)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->


                },
                {

                    progressDialog.dismiss()

                    if (user.userName == "kariyer" && user.userPassword == "2019ADev") {

                        viewModel.user.value = user

                        if (rememberOption) {

                            val sharedPreference = context.getSharedPreferences(
                                "UserPreferences",
                                Context.MODE_PRIVATE
                            )
                            val editor = sharedPreference.edit()
                            editor.putBoolean("rememberOption", rememberOption)
                            editor.apply()
                        }

                    }else{

                        var errorMsg = ""

                        if (user.userName != "kariyer")
                            errorMsg =  context.getString(R.string.usernameerror)
                        else if (user.userPassword != "2019ADev")
                            errorMsg = context.getString(R.string.passworderror)

                        SuperActivityToast.create(
                            context,
                            Style(),
                            Style.TYPE_STANDARD
                        )
                            .setText(errorMsg)
                            .setDuration(Style.DURATION_SHORT)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(context.resources.getColor(R.color.colorAccent))
                            .setAnimations(Style.ANIMATIONS_POP).show()

                    }

                }
            )
    }


    // Get orders service
    fun getOrders(viewModel: OrdersViewModel) {

        progressDialog.show()

        val getProducts = apiService.create(NetworkAPI::class.java)
            .getProducts()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    progressDialog.dismiss()
                    viewModel.orders.value = response

                },
                {

                    progressDialog.dismiss()

                        SuperActivityToast.create(
                            context,
                            Style(),
                            Style.TYPE_STANDARD
                        )
                            .setText(it.message)
                            .setDuration(Style.DURATION_SHORT)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(context.resources.getColor(R.color.colorAccent))
                            .setAnimations(Style.ANIMATIONS_POP).show()


                }
            )
    }

}