package com.example.sneakersship.ui.sneaker_details.color

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersship.R
import com.example.sneakersship.listners.ItemClickListner
import com.example.sneakersship.listners.ItemColorListner

class ColorAdapter(
    val myColorList: ArrayList<String>,
    val applicationContext: Context,
    val itemColorListner: ItemColorListner
) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    var lastCheckedPos = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColorAdapter.ColorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_color_layout,
            parent, false
        )
        return ColorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ColorAdapter.ColorViewHolder, position: Int) {
        holder.color.setBackgroundColor(Color.parseColor(myColorList[position % 4]))
        if(position == lastCheckedPos) {
            holder.color.setImageResource(R.drawable.baseline_check)
        } else {
            holder.color.setBackgroundColor(Color.parseColor(myColorList[position % 4]))
            holder.color.setImageResource(0)
        }

        holder.color.setOnClickListener{
            var prevPos = lastCheckedPos
            lastCheckedPos = position;
            notifyItemChanged(prevPos);
            notifyItemChanged(lastCheckedPos)
            itemColorListner.getColorData(myColorList[position])
        }
    }

    override fun getItemCount(): Int {
        return myColorList.size
    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val color: ImageView = itemView.findViewById(R.id.tv_color)

    }
}