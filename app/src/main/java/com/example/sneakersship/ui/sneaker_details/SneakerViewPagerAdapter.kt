package com.example.sneakersship.ui.sneaker_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.sneakersship.R
import java.util.*
import kotlin.collections.ArrayList

class SneakerViewPagerAdapter(val context: Context, val imageList: ArrayList<Int>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = mLayoutInflater.inflate(R.layout.item_sneaker_image, container, false)
        val imageView: AppCompatImageView = itemView.findViewById<View>(R.id.iv_fullImage) as AppCompatImageView
        imageView.setImageResource(imageList.get(position))
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun getCount(): Int {
        return imageList.size
    }
}