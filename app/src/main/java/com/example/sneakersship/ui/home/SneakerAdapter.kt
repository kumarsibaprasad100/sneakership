package com.example.sneakersship.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersship.R
import com.example.sneakersship.models.ResponseItem
import com.example.sneakersship.listners.ItemClickListner

class SneakerAdapter(
    var sneakerList: ArrayList<ResponseItem>,
    val context: Context,
    private val itemClickListner: ItemClickListner
) :
    RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SneakerAdapter.SneakerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_sneakers_layout,
            parent, false
        )
        return SneakerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SneakerAdapter.SneakerViewHolder, position: Int) {
        holder.name.text = sneakerList.get(position).name
        holder.price.text = "$${sneakerList.get(position).retailPrice.toString()}"
        holder.cl_container.setOnClickListener {
            itemClickListner?.getPositionData(position)
        }
    }

    override fun getItemCount(): Int {
        return sneakerList.size
    }

    /*filtered data based on search and gender sort*/
    fun filterList(filteredlist: ArrayList<ResponseItem>) {
        sneakerList = filteredlist
        notifyDataSetChanged()
    }

    class SneakerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_product)
        val price: TextView = itemView.findViewById(R.id.tv_productPrice)
        val cl_container: ConstraintLayout = itemView.findViewById(R.id.cl_container)
    }
}