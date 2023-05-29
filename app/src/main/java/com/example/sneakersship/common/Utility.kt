package com.example.sneakersship.common

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class Utility {

    companion object {
        val TAX: Int = 40
        val ZERO: Int = 0
        val ONE: Int = 1
        val TWO: Int = 2
        val EMPTY: String = ""

        fun getYear(releaseDate: String?): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(releaseDate)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            return year.toString()
        }

        fun getUniqueNo(): String {
            var randomNo = 0
            val random = Random(System.currentTimeMillis())
            val uniqueNumbers = generateSequence { random.nextInt(100, 1000) }
                .distinct()
                .take(3)
                .toList()

            for (number in uniqueNumbers) {
                randomNo = number
            }
            return  randomNo.toString()
        }

        fun toast(context: Context,text : String){
            Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
        }
    }
}