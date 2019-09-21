package com.challenge.developer.fragment

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.developer.R
import com.challenge.developer.activity.UserActivity
import com.challenge.developer.adapter.OrdersRecyclerViewAdapter
import com.challenge.developer.model.Product
import com.challenge.developer.repository.UserRepository
import com.challenge.developer.viewmodel.OrdersViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.user_activity.*

class MainFragment : Fragment() {

    private var ordersArray: ArrayList<Product> = ArrayList()
    private lateinit var ordersAdapter: OrdersRecyclerViewAdapter
    private lateinit var userRepository: UserRepository

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: OrdersViewModel

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        var drawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_chevron_left_black_24dp,
            null
        )
        drawable = DrawableCompat.wrap(drawable!!)
        requireActivity().toolbar.navigationIcon = drawable
        requireActivity().apptitle_text.text = getString(R.string.market)

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        //Subscribe view model to this view
        viewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java)

        //Initialize repository with view model
        userRepository = UserRepository(requireActivity() as AppCompatActivity,requireContext())

        //Create order adapter with empty list
        ordersList!!.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        ordersAdapter = OrdersRecyclerViewAdapter(ordersArray)
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        ordersList!!.layoutManager = gridLayoutManager
        ordersList!!.adapter = ordersAdapter

        //Subscribe view with observer
        val loginObserver = Observer<List<Product>?> { response ->

            //Update the orders adapter with filled list
            ordersAdapter = OrdersRecyclerViewAdapter(response as java.util.ArrayList<Product>)
            ordersList!!.adapter = ordersAdapter
        }

        viewModel.orders.observe(this,loginObserver)

        //Call the get orders service
        userRepository.getOrders(viewModel)

        logoutButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(getString(R.string.logouttext))
            builder.setPositiveButton(getString(R.string.confirm)) { dialog, which -> dialog.dismiss()

                val sharedPreference = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putBoolean("rememberOption",false)
                editor.apply()

                val i = Intent(requireActivity(), UserActivity::class.java)
                startActivity(i)
                requireActivity().finish()
            }
            builder.setNegativeButton(getString(R.string.cancel)) { dialog, which -> dialog.dismiss()  }

            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
        }


    }

}
