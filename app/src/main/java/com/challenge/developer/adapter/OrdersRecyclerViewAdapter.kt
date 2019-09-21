package com.challenge.developer.adapter

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.developer.databinding.ItemOrderBinding
import com.challenge.developer.model.Product
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.util.*

class OrdersRecyclerViewAdapter(
    private val ordersList: ArrayList<Product>
) : RecyclerView.Adapter<OrdersRecyclerViewAdapter.ViewHolder>() {

    private var expanded : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the layout file
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context))
        this.binding = binding!!

        return ViewHolder(binding)
    }

    private lateinit var binding: ItemOrderBinding

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ordersList[position])
    }

    override fun getItemCount(): Int = ordersList.size

    inner class ViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root){
        lateinit var item: Product
        fun bind(item: Product) {
            this.item = item
            binding.data = item
            binding.month.text = getMonth(DecimalFormat().parse(item.month).toInt())

            when {
                item.productState == "Yolda" -> {
                    binding.imageView.setColorFilter(Color.GREEN)
                    binding.productState.setTextColor(Color.GREEN)
                }
                item.productState == "Hazırlanıyor" -> {
                    binding.imageView.setColorFilter(Color.BLUE)
                    binding.productState.setTextColor(Color.BLUE)

                }
                item.productState == "Onay Bekliyor" -> {
                    binding.imageView.setColorFilter(Color.RED)
                    binding.productState.setTextColor(Color.RED)

                }
            }

            binding.root.setOnClickListener {

                expanded = binding.detailPanel.layoutParams.height != 0

                if (!expanded) {

                    expanded = true
                    expandPanel(expanded)
                }else{

                    expanded = false
                    expandPanel(expanded)
                }
            }

            binding.executePendingBindings()
        }

        //Expand/collapse the detail panel
        private fun expandPanel(expand: Boolean) {

            val anim = if (expand)
                ValueAnimator.ofInt(0, 100)
            else
                ValueAnimator.ofInt(100, 0)

            anim.addUpdateListener { valueAnimator ->
                val `val` = valueAnimator.animatedValue as Int
                val layoutParams = binding.detailPanel.layoutParams
                layoutParams.height = `val`
                binding.detailPanel.layoutParams = layoutParams
            }
            anim.duration = 500
            anim.start()
        }

    }

    // Get month name
    fun getMonth(month: Int): String {
        return DateFormatSymbols().months[month - 1]
    }


}