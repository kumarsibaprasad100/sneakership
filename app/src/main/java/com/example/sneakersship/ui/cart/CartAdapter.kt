package com.example.sneakersship.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersship.R
import com.example.sneakersship.listners.DeleteClickListner
import com.example.sneakersship.models.CartListData

class CartAdapter(var dataList: ArrayList<CartListData>, val context: Context, val deleteClickListner: DeleteClickListner) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cart,
            parent, false
        )
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        holder.name.text = dataList.get(position).name
        holder.price.text = "$${dataList.get(position).retailPrice}"
        holder.delete.setOnClickListener {
            deleteClickListner.getDeletData(position,dataList.get(position).id)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun notifyItem(readCourses: ArrayList<CartListData>) {
      this.dataList = readCourses
        notifyDataSetChanged()
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_productName)
        val price: TextView = itemView.findViewById(R.id.tv_productPrice)
        val delete: ImageView = itemView.findViewById(R.id.iv_delete)
    }
}