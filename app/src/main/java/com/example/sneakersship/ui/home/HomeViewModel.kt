package com.example.sneakersship.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sneakersship.models.Media
import com.example.sneakersship.models.ResponseItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray

class HomeViewModel : ViewModel() {

    val TAG = "HomeViewModel"
    var shoeList = MutableLiveData<ArrayList<ResponseItem>>()
    var errorMsg = MutableLiveData<String>()
    val filteredlist = ArrayList<ResponseItem>()
    val filtered = MutableLiveData<ArrayList<ResponseItem>>()

    /*
    * this function returns filterdata based on searchText
    */
    fun getFilteredData(newText: String?, sneakerList: ArrayList<ResponseItem>) {
        filteredlist.clear()
        filteredlist.addAll(sneakerList.filter { it.name?.toLowerCase().toString().contains(newText?.toLowerCase().toString()) })
        if (filteredlist.isEmpty()) {
            errorMsg.postValue("No Data Found..")
        } else {
            filtered.postValue(filteredlist)
        }
    }

    /*this function returns Filtered data based on gender value*/
    fun getFilteredGenderData(text: String, sneakerList: ArrayList<ResponseItem>) {
        filteredlist.clear()
        for (item in sneakerList) {
            if (item.gender?.toLowerCase().toString().contains(text?.toLowerCase().toString())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            errorMsg.postValue("No Data Found..")
        } else {
            filtered.postValue(filteredlist)
        }
    }

    /*this function is used to parse data from Sneaker.Json
    * returns sneakerlist
    */
    @SuppressLint("CheckResult")
    fun retrivedata(applicationContext: Context?) {
        val itemsList = ArrayList<ResponseItem>()

        try {
            val jsonData = applicationContext?.resources?.openRawResource(
                applicationContext?.resources!!.getIdentifier(
                    "sneaker", "raw", applicationContext.packageName
                )
            )?.bufferedReader().use { it?.readText() }

            Observable.fromCallable {
                val outputList = JSONArray(jsonData)

                for (i in 0 until outputList.length()) {
                    val jsonObject = outputList.getJSONObject(i)

                    val item = ResponseItem(
                        id = jsonObject.getString("id"),
                        brand = jsonObject.getString("brand"),
                        colorway = jsonObject.getString("colorway"),
                        gender = jsonObject.getString("gender"),
                        media = Media(
                            imageUrl = jsonObject.getJSONObject("media").getString("imageUrl"),
                            smallImageUrl = jsonObject.getJSONObject("media")
                                .getString("smallImageUrl"),
                            thumbUrl = jsonObject.getJSONObject("media").getString("thumbUrl")
                        ),
                        releaseDate = jsonObject.getString("releaseDate"),
                        retailPrice = jsonObject.getInt("retailPrice"),
                        styleId = jsonObject.getString("styleId"),
                        shoe = jsonObject.getString("shoe"),
                        name = jsonObject.getString("name"),
                        title = jsonObject.getString("title"),
                        year = jsonObject.getInt("year")
                    )
                    itemsList.add(item)
                }
                itemsList
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    shoeList.postValue(it)
                    Log.i(TAG, "retrivedata: ${it[0].brand}")
                }, { error ->
                    Log.i(TAG, "retrivedata: ${error.message}")
                })
        } catch (e: java.lang.Exception) {
            Log.i(TAG, "retrivedata: ${e.message}")
        }
    }
}