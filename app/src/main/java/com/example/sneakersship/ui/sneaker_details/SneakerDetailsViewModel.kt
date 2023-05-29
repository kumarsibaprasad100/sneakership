package com.example.sneakersship.ui.sneaker_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sneakersship.R
import com.example.sneakersship.models.ResponseItem

class SneakerDetailsViewModel : ViewModel() {

    val TAG = "SneakerDetailsViewModel"
    var allsizeList = MutableLiveData<ArrayList<String>>()
    var colorList = MutableLiveData<ArrayList<String>>()
    lateinit var imageList: ArrayList<Int>
    var allimageList = MutableLiveData<ArrayList<Int>>()
    var isValid = MutableLiveData<Boolean>()

    /*adding static sizes to list*/
    fun getSizedata() {
        allsizeList.postValue(arrayListOf("7", "8", "9","10"))
    }

    /*adding static colors to list*/
    fun getColorData() {
        colorList.postValue(arrayListOf("#3F51B5", "#EF8C85", "#009688","#FF03DAC5"))
    }

    /*this function adds Static Sneakers data.
    * add real imagedata from "sneakerListData"
    * */
    fun getSneakerImageData(sneakerListData: ResponseItem) {
        imageList = ArrayList()
        imageList.add(R.drawable.ic_shoe)
        imageList.add(R.drawable.ic_nike)
        imageList.add(R.drawable.ic_shoe)
        allimageList.postValue(imageList)
    }

    /*this function return selected validate size and color*/
    fun validateFields(size: String, color: String) {
        isValid.postValue(size.isNotEmpty() && color.isNotEmpty())
    }

}