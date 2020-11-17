package com.atlas.orianofood.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import com.atlas.orianofood.model.Order


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "orianofoodsDB.db"
        private val DB_VERSION = 1
        private val TABLE_NAME = "OrderDetail"
        private val KEY_ID = "ProductId"
        private val KEY_NAME = "ProductName"
        private val KEY_QUANTITY = "Quantity"
        private val KEY_PRICE = "Price"
        private val KEY_DISCOUNT = "Discount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_QUANTITY + " TEXT,"
                + KEY_PRICE + " TEXT," + KEY_DISCOUNT + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }


    //method to insert data
    /*fun addEmployee(emp: EmpModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_EMAIL,emp.userEmail ) // EmpModelClass Phone
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/

    fun createTable() {
        val db: SQLiteDatabase = this.writableDatabase
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_QUANTITY + " TEXT,"
                + KEY_PRICE + " TEXT," + KEY_DISCOUNT + " TEXT" + ")")
        db.execSQL(CREATE_CONTACTS_TABLE)
    }

    fun addToCart(order: Order) {
        val db: SQLiteDatabase = this.writableDatabase
        val query = String.format(
            "INSERT INTO OrderDetail(ProductId, ProductName,Quantity,Price,Discount)VALUES('%S','%S','%S','%S','%S');",
            order.productId, order.productName, order.quantity, order.price, order.discount
        )

        db.execSQL(query)
    }

    fun updateCart(order: Order) {
        val db: SQLiteDatabase = this.writableDatabase
        val query = String.format(
            "INSERT INTO OrderDetail(ProductId, ProductName,Quantity,Price,Discount)VALUES('%S','%S','%S','%S','%S');",
            order.productId, order.productName, order.quantity, order.price, order.discount
        )

        db.execSQL(query)
    }


    //method to read data
    /*fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val emp= EmpModelClass(userId = userId, userName = userName, userEmail = userEmail)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }*/

    fun getcartItem(order: Order) {
        val dbr = this.readableDatabase
        val dbw = this.writableDatabase
        val selectQuery = ("SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_NAME + " = '" + order.productName + "'")
        Log.d("CHeck Item", selectQuery)
        val c = dbr.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            val qty = Integer.parseInt(c.getString(c.getColumnIndex(KEY_QUANTITY))) +
                    Integer.parseInt(order.quantity)

            val updateQuery = ("UPDATE " + TABLE_NAME + " SET " + KEY_QUANTITY + " = "
                    + (qty).toString() + " WHERE "
                    + KEY_NAME + " = '" + order.productName + "'")

            val c1 = dbw.rawQuery(updateQuery, null)
        } else {
            addToCart(order)

        }
    }

    fun getCarts(): List<Order> {
        val db: SQLiteDatabase = this.writableDatabase
        val q = SQLiteQueryBuilder()
        val select_column = arrayOf<String>(
            "ProductId", "ProductName", "Quantity", "Price", "Discount"
        )
        val table = "OrderDetail"
        q.tables = table
        val cursor: Cursor = q.query(db, select_column, null, null, null, null, null)

        val result = ArrayList<Order>()

        if (cursor.moveToFirst()) {
            do {
                result.add(
                    Order(
                        cursor.getString(cursor.getColumnIndex("ProductId")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))
                    )
                )
            } while (cursor.moveToNext())
        }

        return result
    }

    //method to update data
    /* fun updateEmployee(emp: EmpModelClass):Int{
         val db = this.writableDatabase
         val contentValues = ContentValues()
         contentValues.put(KEY_ID, emp.userId)
         contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
         contentValues.put(KEY_EMAIL,emp.userEmail ) // EmpModelClass Email

         // Updating Row
         val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)
         //2nd argument is String containing nullColumnHack
         db.close() // Closing database connection
         return success
     }*/
    //method to delete data
    /*fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/

    fun cleanCart() {
        val db: SQLiteDatabase = this.writableDatabase
        val query =
            "DELETE FROM OrderDetail"

        db.execSQL(query)
    }
}