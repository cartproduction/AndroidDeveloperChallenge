package com.challenge.developer.repository

import android.content.Context
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.challenge.developer.R
import com.challenge.developer.model.User
import com.challenge.developer.viewmodel.UserViewModel
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.raventech.fujibas.interfaces.ApiClient
import com.raventech.fujibas.interfaces.NetworkAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class UserRepository(private var context: Context, private var viewModel: UserViewModel) {

    private var apiService: Retrofit
    private var progressDialog : SweetAlertDialog

    init {

        //Initialize api client service
        apiService = ApiClient.getClient(context)

        //Alert dialog creating with context
        progressDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog.progressHelper.barColor = ContextCompat.getColor(context, R.color.colorAccent)
        progressDialog.titleText = context.getString(R.string.loading)
        progressDialog.progressHelper.circleRadius = 30
        progressDialog.setCancelable(false)

    }


    //User login service

    fun login(email : String, password : String) {

        progressDialog.show()

        val user = User()
        user.userName = email
        user.userPassword = password

        val login = apiService.create(NetworkAPI::class.java)
            .login(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->


                },
                {

                    progressDialog.dismiss()

                    if (user.userName == "kariyer" && user.userPassword == "2019ADev") {

                        viewModel.user.value = user

                    }else{

                        val errorMsg = if (user.userName != "kariyer")
                                            context.getString(R.string.usernameerror)
                                        else (user.userPassword != "2019ADev")
                                            context.getString(R.string.passworderror)

                        SuperActivityToast.create(
                            context,
                            Style(),
                            Style.TYPE_STANDARD
                        )
                            .setText(errorMsg.toString())
                            .setDuration(Style.DURATION_SHORT)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(context.resources.getColor(R.color.colorAccent))
                            .setAnimations(Style.ANIMATIONS_POP).show()

                    }

                }
            )
    }

}