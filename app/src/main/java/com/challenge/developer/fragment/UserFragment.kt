package com.challenge.developer.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import android.os.Handler
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.challenge.developer.R
import com.challenge.developer.activity.MainActivity
import com.challenge.developer.model.User
import com.challenge.developer.repository.UserRepository
import com.challenge.developer.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.user_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.user_activity.*

class UserFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var userRepository: UserRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        var drawable = ResourcesCompat.getDrawable(
          resources,
          R.drawable.ic_chevron_left_black_24dp,
          null
        )
        drawable = DrawableCompat.wrap(drawable!!)
        requireActivity().toolbar.navigationIcon = drawable
        requireActivity().apptitle_text.text = getString(R.string.login)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_fragment, container, false)
  }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

            android.R.id.home -> {
                requireActivity().onBackPressed()
            }


            else ->
                super.onOptionsItemSelected(item)
        }

        return true

    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

      //Subscribe view model to this view
      viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

      //Initialize repository with view model
      userRepository = UserRepository(requireActivity() as AppCompatActivity,requireContext())


      //Keyback handler like onBackPressed
      view.isFocusableInTouchMode = true
      view.requestFocus()
      view.setOnKeyListener { v, keyCode, event ->
          if( keyCode == KeyEvent.KEYCODE_BACK)
          {
              requireActivity().finish()
              return@setOnKeyListener true
          }
          return@setOnKeyListener false

      }

      //Sign in button action
    btnSignIn.setOnClickListener(View.OnClickListener {
      val email = loginEmail.text.toString()
      val password = loginPass.text.toString()

      if (email.isEmpty()) {
        SuperActivityToast.create(
            requireContext(),
            Style(),
            Style.TYPE_STANDARD
        )
                .setText(getString(R.string.usernameempty))
                .setDuration(Style.DURATION_SHORT)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(resources.getColor(R.color.colorAccent))
                .setAnimations(Style.ANIMATIONS_POP).show()
        loginEmail.requestFocus()
        return@OnClickListener
      }

      if (password.isEmpty()) {
          SuperActivityToast.create(
              requireContext(),
              Style(),
              Style.TYPE_STANDARD
          )
                  .setText(getString(R.string.passwordempty))
                  .setDuration(Style.DURATION_SHORT)
                  .setFrame(Style.FRAME_LOLLIPOP)
                  .setColor(resources.getColor(R.color.colorAccent))
                  .setAnimations(Style.ANIMATIONS_POP).show()
        loginPass.requestFocus()
        return@OnClickListener
      }

      if (password.length<6) {
          SuperActivityToast.create(
              requireContext(),
              Style(),
              Style.TYPE_STANDARD
          )
                  .setText(getString(R.string.passworddigit))
                  .setDuration(Style.DURATION_SHORT)
                  .setFrame(Style.FRAME_LOLLIPOP)
                  .setColor(resources.getColor(R.color.colorAccent))
                  .setAnimations(Style.ANIMATIONS_POP).show()
        loginPass.requestFocus()
        return@OnClickListener
      }

          val im = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
          im?.hideSoftInputFromWindow(loginEmail.windowToken,
          InputMethodManager.RESULT_UNCHANGED_SHOWN
      )

        //Subscribe view with observer
        val loginObserver = Observer<User?> { response ->
            Handler().postDelayed({
                //The following code will execute after the 5 seconds.

                try {
                    val i = Intent(requireActivity(), MainActivity::class.java)
                    val bundle =  Bundle()
                    bundle.putSerializable("user", response)
                    i.putExtras(bundle)
                    requireActivity().startActivity(i)
                    requireActivity().finish()

                } catch (ignored: Exception) {
                    ignored.printStackTrace()
                }
            }, 500)  // Give a 5 seconds delay.

        }

        viewModel.user.observe(this,loginObserver)

        userRepository.login(loginEmail.text.toString(),loginPass.text.toString(),rememberOption.isEnabled,viewModel)


    })

  }


}