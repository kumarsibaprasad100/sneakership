package com.example.sneakersship.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sneakersship.models.CartListData


class DataBaseHelper (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "SNEAKER_DATA_BASE"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "MySneaker"
        private const val ID = "id"
        private const val NAME = "name"
        private const val SNEAKER_ID = "sneakerId"
        private const val PRICE = "price"
        private const val SIZE = "size"
        private const val COLOR = "color"
        private const val IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$SNEAKER_ID TEXT, " +
                "$NAME TEXT, " +
                "$PRICE TEXT, " +
                "$SIZE TEXT, " +
                "$COLOR TEXT," +
                "$IMAGE TEXT)"


        db.execSQL(query)
    }

    fun addToCart(name: String, id: String, price: String, size: String, color:String, image: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(SNEAKER_ID, id)
        values.put(NAME, name)
        values.put(PRICE, price)
        values.put(SIZE, size)
        values.put(COLOR, color)
        values.put(IMAGE, image)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun readCartData(): ArrayList<CartListData> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val courseModalArrayList = ArrayList<CartListData>()

        if (cursor.moveToFirst()) {
            do {
                courseModalArrayList.add(
                    CartListData(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return courseModalArrayList
    }

    fun deleteCart(id: String) {
        val db = writableDatabase
        val selection = "sneakerId = ?"
        val selectionArgs = arrayOf(id)
        db.delete(TABLE_NAME, selection,selectionArgs)
        db.close()
    }

    fun deleteAllData() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null,null)
        db.close()
    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}