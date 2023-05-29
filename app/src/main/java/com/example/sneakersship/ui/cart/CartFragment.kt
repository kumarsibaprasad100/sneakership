package com.example.sneakersship.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersship.R
import com.example.sneakersship.common.Utility
import com.example.sneakersship.database.DataBaseHelper
import com.example.sneakersship.databinding.FragmentCartBinding
import com.example.sneakersship.listners.DeleteClickListner
import com.example.sneakersship.models.CartListData


class CartFragment : Fragment(), DeleteClickListner {

    private lateinit var dataBaseHelper: DataBaseHelper
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private var mBinding: FragmentCartBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         cartViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                CartViewModel::class.java
            )

        mBinding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dataBaseHelper = DataBaseHelper(requireActivity().applicationContext)
        initView()
        return root
    }

    /*initialize Views and handled Click events*/
    private fun initView() {
        cartViewModel.getCartData(dataBaseHelper)
        subScribeCartDetails()
        subscribeDeleteData()
        mBinding?.ivBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        mBinding?.btnCheckout?.setOnClickListener {
            Utility.toast(requireActivity().applicationContext,getString(R.string.success))
            dataBaseHelper.deleteAllData();
            requireActivity().onBackPressed()
        }
    }

    /*this function Observes delete data
     *and update cart list
    */
    private fun subscribeDeleteData() {
        cartViewModel?.cartdataList?.observe(this){
            cartAdapter.notifyItem(it)
            setCartValues(it)
        }
    }

    /*this function Observes Cartdata  from db
    * setting views with data
    */
    private fun subScribeCartDetails() {
        cartViewModel?.cartdataList?.observe(this){
            setCartValues(it)
            setCartListRv(it)
        }
    }

    /*set up cart RecyclerView*/
    private fun setCartListRv(it: ArrayList<CartListData>) {
        cartAdapter = CartAdapter(it, requireActivity().applicationContext,this)
        val linearLayoutManager = LinearLayoutManager(requireActivity().applicationContext)
        mBinding?.rvCart?.setLayoutManager(linearLayoutManager)
        mBinding?.rvCart?.setAdapter(cartAdapter)
    }

    /*set up Views based on cart-data
    * calculate price
    */
    private fun setCartValues(dataList: ArrayList<CartListData>) {
        mBinding?.group?.visibility = if (dataList.size > 0) View.VISIBLE else View.GONE
        mBinding?.ivEmptyCart?.visibility = if (dataList.size > 0) View.GONE else View.VISIBLE
        val total = if (dataList.size > 0) dataList.sumBy { it.retailPrice.toInt() } else 0
        mBinding?.tvSubTotal?.text = "${requireActivity().getText(com.example.sneakersship.R.string.subtotal)} $$total"
        mBinding?.tvTax?.text = "${requireActivity().getText(com.example.sneakersship.R.string.taxes_and_charges)} $${Utility.TAX}"
        val finalTotal = total + Utility.TAX
        mBinding?.tvTotalAmount?.text = "$$finalTotal"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    /*this function returns deleted cart id
    * call delete func from db
    */
    override fun getDeletData(position: Int, id: String) {
        cartViewModel.deleteData(id,dataBaseHelper)
    }
}