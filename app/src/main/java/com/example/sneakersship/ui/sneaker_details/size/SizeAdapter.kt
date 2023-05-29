package com.example.sneakersship.ui.sneaker_details.size

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersship.R
import com.example.sneakersship.listners.ItemClickListner


class SizeAdapter(
    val mySizeList: MutableList<String>,
    val applicationContext: Context,
    val itemClickListner: ItemClickListner
) :
    RecyclerView.Adapter<SizeAdapter.SizeViewHolder>() {
    var lastCheckedPos = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SizeAdapter.SizeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_size_layout,
            parent, false
        )
        return SizeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SizeAdapter.SizeViewHolder, position: Int) {
        holder.name.text = mySizeList[position]
        if(position == lastCheckedPos) {
            holder.name.background = applicationContext.getDrawable(R.drawable.pink_selected)
            holder.name.setTextColor(applicationContext.getColor(R.color.white))
        } else {
            holder.name.background = applicationContext.getDrawable(R.drawable.pink_border)
            holder.name.setTextColor(applicationContext.getColor(R.color.app_color))
        }

        holder.name.setOnClickListener{
                var prevPos = lastCheckedPos
                lastCheckedPos = position;
                notifyItemChanged(prevPos);
                notifyItemChanged(lastCheckedPos)
            itemClickListner.getPositionData(mySizeList[position].toInt())
            }
    }

    override fun getItemCount(): Int {
        return mySizeList.size
    }

    class SizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_size)

    }
}