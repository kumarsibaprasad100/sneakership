package com.example.sneakersship.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sneakersship.database.DataBaseHelper
import com.example.sneakersship.models.CartListData

class CartViewModel : ViewModel() {
    var cartdataList = MutableLiveData<ArrayList<CartListData>>()

    /*this function is to Read data from Database.*/
    fun getCartData(dataBaseHelper: DataBaseHelper) {
        cartdataList.postValue(dataBaseHelper.readCartData())
    }

    /*this function delete data based on sneaker id*/
    fun deleteData(id: String, dataBaseHelper: DataBaseHelper) {
        dataBaseHelper.deleteCart(id)
        cartdataList.postValue(dataBaseHelper.readCartData())
    }
}