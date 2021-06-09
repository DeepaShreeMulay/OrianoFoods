package com.atlas.orianofood.firebaseRT.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import com.atlas.orianofood.firebaseRT.model.Address
import com.atlas.orianofood.firebaseRT.model.Cart
import com.atlas.orianofood.firebaseRT.model.Order


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "orianofoodsDB.db"
        private val DB_VERSION = 2

        private val CartDetails_TABLE = "CartDetails"
        private val CartDetails_KEY_ID = "ProductId"
        private val CartDetails_KEY_NAME = "ProductName"
        private val CartDetails_KEY_QUANTITY = "Quantity"
        private val CartDetails_KEY_PRICE = "Price"
        private val CartDetails_KEY_DISCOUNT = "Discount"

        private val Address_TABLE = "Address"
        private val Address_KEY_ID = "AddressId"
        private val Address_KEY_DELIVER_TO = "DeliverToName"
        private val Address_KEY_CITY = "City"
        private val Address_KEY_LOCATION = "Location"
        private val Address_KEY_HOUSE_NO = "HouseNo"
        private val Address_KEY_STREET = "Street"
        private val Address_KEY_LANDMARK = "Landmark"
        private val Address_KEY_PINCODE = "Pincode"
        private val Address_KEY_TYPE = "Type"
        private val Address_KEY_IS_DEFAULT = "IsDefault"

        private val OrderDetails_TABLE = "OrderDetails"
        private val OrderDetails_KEY_ID = "OrderId"
        private val OrderDetails_KEY_DATE = "OrderDate"
        private val OrderDetails_KEY_DELIVER_AT = "OrderDeliverAt"
        private val OrderDetails_KEY_TOTAL = "OrderTotalAmount"
        private val OrderDetails_KEY_TYPE = "OrderType"
        private val OrderDetails_KEY_PAYMENT_MODE = "PaymentMode"
        private val OrderDetails_KEY_ORDER_ITEMS = "OrderItems"
        private val OrderDetails_KEY_STATUS = "OrderStatus"
        private val OrderDetails_KEY_ORDERED_BY = "OrderBy"
        private val OrderDetails_KEY_PHONE = "PhoneNo"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CART_DETAILS_TABLE = ("CREATE TABLE IF NOT EXISTS " + CartDetails_TABLE + "("
                + CartDetails_KEY_ID + " TEXT," + CartDetails_KEY_NAME + " TEXT," + CartDetails_KEY_QUANTITY + " TEXT,"
                + CartDetails_KEY_PRICE + " TEXT," + CartDetails_KEY_DISCOUNT + " TEXT" + ")")
        db?.execSQL(CREATE_CART_DETAILS_TABLE)

        val CREATE_ADDRESS_DETAIL_TABLE = ("CREATE TABLE IF NOT EXISTS " + Address_TABLE + "("
                + Address_KEY_ID + " TEXT," + Address_KEY_DELIVER_TO + " TEXT," + Address_KEY_CITY + " TEXT,"
                + Address_KEY_LOCATION + " TEXT," + Address_KEY_HOUSE_NO + " TEXT," + Address_KEY_STREET + " TEXT,"
                + Address_KEY_LANDMARK + " TEXT," + Address_KEY_PINCODE + " TEXT,"
                + Address_KEY_TYPE + " TEXT," + Address_KEY_IS_DEFAULT + " TEXT" + ")")
        db?.execSQL(CREATE_ADDRESS_DETAIL_TABLE)

        val CREATE_ORDER_DETAIL_TABLE = ("CREATE TABLE IF NOT EXISTS " + OrderDetails_TABLE + "("
                + OrderDetails_KEY_ID + " TEXT," + OrderDetails_KEY_DATE + " TEXT," + OrderDetails_KEY_DELIVER_AT + " TEXT,"
                + OrderDetails_KEY_ORDER_ITEMS + " TEXT," + OrderDetails_KEY_TOTAL + " TEXT," + OrderDetails_KEY_PAYMENT_MODE + " TEXT,"
                + OrderDetails_KEY_STATUS + " TEXT," + OrderDetails_KEY_TYPE + " TEXT," + OrderDetails_KEY_PHONE + " TEXT," + OrderDetails_KEY_ORDERED_BY + " TEXT" + ")")
        db?.execSQL(CREATE_ORDER_DETAIL_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS $CartDetails_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $Address_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $OrderDetails_TABLE")
        onCreate(db)
    }

    fun createAddressTable() {
        val db: SQLiteDatabase = this.writableDatabase
        val createAddressDetailsTable = ("CREATE TABLE IF NOT EXISTS " + Address_TABLE + "("
                + Address_KEY_ID + " TEXT," + Address_KEY_DELIVER_TO + " TEXT," + Address_KEY_CITY + " TEXT,"
                + Address_KEY_LOCATION + " TEXT," + Address_KEY_HOUSE_NO + " TEXT," + Address_KEY_STREET + " TEXT,"
                + Address_KEY_LANDMARK + " TEXT," + Address_KEY_PINCODE + " TEXT,"
                + Address_KEY_TYPE + " TEXT," + Address_KEY_IS_DEFAULT + " TEXT" + ")")
        db.execSQL(createAddressDetailsTable)
    }

    fun createOrderDetailsTable() {
        val db: SQLiteDatabase = this.writableDatabase
        val createOrderDetailTable = ("CREATE TABLE IF NOT EXISTS " + OrderDetails_TABLE + "("
                + OrderDetails_KEY_ID + " TEXT," + OrderDetails_KEY_DATE + " TEXT," + OrderDetails_KEY_DELIVER_AT + " TEXT,"
                + OrderDetails_KEY_ORDER_ITEMS + " TEXT," + OrderDetails_KEY_TOTAL + " TEXT," + OrderDetails_KEY_PAYMENT_MODE + " TEXT,"
                + OrderDetails_KEY_STATUS + " TEXT," + OrderDetails_KEY_TYPE + " TEXT," + OrderDetails_KEY_PHONE + " TEXT," + OrderDetails_KEY_ORDERED_BY + " TEXT" + ")")
        db.execSQL(createOrderDetailTable)
    }

    fun createOrderTable() {
        val db: SQLiteDatabase = this.writableDatabase
        val createCartDetailsTable = ("CREATE TABLE IF NOT EXISTS " + CartDetails_TABLE + "("
                + CartDetails_KEY_ID + " TEXT," + CartDetails_KEY_NAME + " TEXT," + CartDetails_KEY_QUANTITY + " TEXT,"
                + CartDetails_KEY_PRICE + " TEXT," + CartDetails_KEY_DISCOUNT + " TEXT" + ")")
        db.execSQL(createCartDetailsTable)
    }


    // Cart Table Functions
    private fun addToCart(cart: Cart) {
        val db: SQLiteDatabase = this.writableDatabase
        val query = String.format(
            "INSERT INTO CartDetails(ProductId,ProductName,Quantity,Price,Discount)VALUES('%s','%s','%s','%s','%s');",
            cart.productId, cart.productName, cart.quantity, cart.price, cart.discount
        )
        db.execSQL(query)
    }

    fun updateCart(cart: Cart) {
        val db: SQLiteDatabase = this.writableDatabase
        val query = String.format(
            "INSERT INTO CartDetails(ProductId, ProductName,Quantity,Price,Discount)VALUES('%s','%s','%s','%s','%s');",
            cart.productId, cart.productName, cart.quantity, cart.price, cart.discount
        )

        db.execSQL(query)
    }

    fun getCarts(): List<Cart> {
        val db: SQLiteDatabase = this.writableDatabase
        val q = SQLiteQueryBuilder()
        val selectColumn = arrayOf<String>(
            "ProductId", "ProductName", "Quantity", "Price", "Discount"
        )
        val table = "CartDetails"
        q.tables = table
        val cursor: Cursor = q.query(db, selectColumn, null, null, null, null, null)

        val result = ArrayList<Cart>()

        if (cursor.moveToFirst()) {
            do {
                result.add(
                    Cart(
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

    fun getOrderedItemsFromCart(): String {
        val db: SQLiteDatabase = this.writableDatabase
        val q = SQLiteQueryBuilder()
        val selectColumn = arrayOf<String>(
            "ProductId", "ProductName", "Quantity", "Price", "Discount"
        )
        val table = "CartDetails"
        q.tables = table
        val cursor: Cursor = q.query(db, selectColumn, null, null, null, null, null)

        val result = ArrayList<Cart>()

        if (cursor.moveToFirst()) {
            do {
                result.add(
                    Cart(
                        cursor.getString(cursor.getColumnIndex("ProductId")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))
                    )
                )
            } while (cursor.moveToNext())
        }

        var string = ""
        for (i in result) {
            string += " ${i.productName} x ${i.quantity},"
        }
        if (result.size > 0) {
            val myString: StringBuilder = StringBuilder(string)
            myString.setCharAt(string.lastIndex, ' ')
            string = myString.toString()
        }

        return string
    }

    fun getCartItem(cart: Cart) {
        val db: SQLiteDatabase = this.writableDatabase
        val q = SQLiteQueryBuilder()
        val selectColumn = arrayOf<String>(
            "ProductId", "ProductName", "Quantity", "Price", "Discount"
        )
        val table = "CartDetails"
        q.tables = table
        val cursor: Cursor = q.query(
            db,
            selectColumn, /*"$KEY_NAME ='${order.productName}'"*/
            null,
            null,
            null,
            null,
            null
        )

        val result = ArrayList<Cart>()
        Log.e("CheckItems", cursor.count.toString())

        if (cursor.moveToFirst()) {
            do {
                Log.e("CheckItems", cursor.getString(cursor.getColumnIndex("ProductName")))
                Log.e("CheckItems", cart.productName)
                if (cursor.getString(cursor.getColumnIndex("ProductName")).toString()
                        .capitalize() == cart.productName.capitalize()
                ) {
                    result.add(
                        Cart(
                            cursor.getString(cursor.getColumnIndex("ProductId")),
                            cursor.getString(cursor.getColumnIndex("ProductName")),
                            cursor.getString(cursor.getColumnIndex("Quantity")).toInt()
                                .plus(cart.quantity.toInt()).toString(),
                            cursor.getString(cursor.getColumnIndex("Price")).toDouble()
                                .plus(cart.price.toDouble()).toString(),
                            cursor.getString(cursor.getColumnIndex("Discount"))
                        )
                    )
                }
            } while (cursor.moveToNext())
        }

        if (result.isEmpty()) {
            addToCart(cart)
        } else {
            val updateQuery = String.format(
                "UPDATE $CartDetails_TABLE SET $CartDetails_KEY_QUANTITY = '${result[0].quantity}' WHERE $CartDetails_KEY_NAME = '${cart.productName}'",
                null
            )
            db.execSQL(updateQuery)
        }
    }

    fun cleanCart() {
        val db: SQLiteDatabase = this.writableDatabase
        val query =
            "DELETE FROM CartDetails"

        db.execSQL(query)
    }


    // Address Table Functions
    private fun addToAddress(address: Address) {
        val db: SQLiteDatabase = this.writableDatabase
        val query = String.format(
            "INSERT INTO $Address_TABLE($Address_KEY_ID,$Address_KEY_DELIVER_TO,$Address_KEY_CITY,$Address_KEY_LOCATION," +
                    "$Address_KEY_HOUSE_NO,$Address_KEY_STREET,$Address_KEY_LANDMARK," +
                    "$Address_KEY_PINCODE,$Address_KEY_TYPE,$Address_KEY_IS_DEFAULT)" +
                    "VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",
            address.AddressId, address.DeliverTo, address.City, address.Location, address.HouseNo,
            address.Street, address.Landmark, address.Pincode, address.Type, address.IsDefault
        )

        db.execSQL(query)
    }

    fun addAddressItem(address: Address) {
        val db: SQLiteDatabase = this.writableDatabase
        val q = SQLiteQueryBuilder()
        val selectColumn = arrayOf<String>(
            Address_KEY_ID,
            Address_KEY_DELIVER_TO,
            Address_KEY_CITY,
            Address_KEY_LOCATION,
            Address_KEY_HOUSE_NO,
            Address_KEY_STREET,
            Address_KEY_LANDMARK,
            Address_KEY_PINCODE,
            Address_KEY_TYPE,
            Address_KEY_IS_DEFAULT
        )
        val table = Address_TABLE
        q.tables = table
        val cursor: Cursor = q.query(
            db,
            selectColumn, /*"$KEY_NAME ='${order.productName}'"*/
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.count == 0) {
            address.AddressId = cursor.count.toString()
            addToAddress(address)
        } else {
            address.AddressId = cursor.count.toString()
            if (address.IsDefault == "Yes") {
                val updateQuery = String.format(
                    "UPDATE $Address_TABLE SET $Address_KEY_IS_DEFAULT = 'No'",
                    null
                )
                db.execSQL(updateQuery)
            }
            addToAddress(address)
        }

    }

    fun getAddresses(): List<Address> {
        val db: SQLiteDatabase = this.writableDatabase
        val q = SQLiteQueryBuilder()
        val selectColumn = arrayOf<String>(
            Address_KEY_ID,
            Address_KEY_DELIVER_TO,
            Address_KEY_CITY,
            Address_KEY_LOCATION,
            Address_KEY_HOUSE_NO,
            Address_KEY_STREET,
            Address_KEY_LANDMARK,
            Address_KEY_PINCODE,
            Address_KEY_TYPE,
            Address_KEY_IS_DEFAULT
        )
        val table = Address_TABLE
        q.tables = table
        val cursor: Cursor = q.query(db, selectColumn, null, null, null, null, null)

        val result = ArrayList<Address>()

        if (cursor.moveToFirst()) {
            do {
                result.add(
                    Address(
                        cursor.getString(cursor.getColumnIndex(Address_KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_DELIVER_TO)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_CITY)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_HOUSE_NO)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_STREET)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_LANDMARK)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_PINCODE)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_TYPE)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_IS_DEFAULT))
                    )
                )
            } while (cursor.moveToNext())
        }

        return result
    }


    fun getDefaultAddress(): String {
        val db: SQLiteDatabase = this.writableDatabase
        val q = SQLiteQueryBuilder()
        val selectColumn = arrayOf<String>(
            Address_KEY_ID,
            Address_KEY_DELIVER_TO,
            Address_KEY_CITY,
            Address_KEY_LOCATION,
            Address_KEY_HOUSE_NO,
            Address_KEY_STREET,
            Address_KEY_LANDMARK,
            Address_KEY_PINCODE,
            Address_KEY_TYPE,
            Address_KEY_IS_DEFAULT
        )
        val table = Address_TABLE
        q.tables = table
        val cursor: Cursor = q.query(
            db,
            selectColumn,
            "$Address_KEY_IS_DEFAULT ='Yes'",
            null,
            null,
            null,
            null
        )


        val result = ArrayList<Address>()

        if (cursor.moveToFirst()) {
            do {
                result.add(
                    Address(
                        cursor.getString(cursor.getColumnIndex(Address_KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_DELIVER_TO)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_CITY)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_HOUSE_NO)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_STREET)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_LANDMARK)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_PINCODE)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_TYPE)),
                        cursor.getString(cursor.getColumnIndex(Address_KEY_IS_DEFAULT))
                    )
                )
            } while (cursor.moveToNext())
        }

        var str = if (result.isEmpty()) {
            "No Default Address Available"
        } else {
            result[0].DeliverTo +
                    "\n${result[0].HouseNo},${result[0].Street},${result[0].Landmark}" +
                    "\n${result[0].Location},${result[0].City}-${result[0].Pincode}"
        }

        return str
    }


    // Order Details Table Functions
    fun addToOrderDetails(order: Order) {
        val db: SQLiteDatabase = this.writableDatabase

        val query = String.format(
            "INSERT INTO $OrderDetails_TABLE($OrderDetails_KEY_ID,$OrderDetails_KEY_DATE," +
                    "$OrderDetails_KEY_DELIVER_AT,$OrderDetails_KEY_TOTAL,$OrderDetails_KEY_TYPE," +
                    "$OrderDetails_KEY_ORDER_ITEMS,$OrderDetails_KEY_STATUS,$OrderDetails_KEY_PAYMENT_MODE" +
                    ",$OrderDetails_KEY_PHONE,$OrderDetails_KEY_ORDERED_BY)" +
                    "VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",
            order.orderId,
            order.orderDate,
            order.orderDeliverAt,
            order.orderTotalAmount,
            order.orderType,
            order.orderItems,
            order.orderStatus,
            order.paymentMode,
            order.phoneNumber,
            order.OrderedBy
        )
        db.execSQL(query)
    }

    fun getOrderDetails(): List<Order> {
        val db: SQLiteDatabase = this.writableDatabase
        val q = SQLiteQueryBuilder()
        val selectColumn = arrayOf<String>(
            OrderDetails_KEY_ID,
            OrderDetails_KEY_DATE,
            OrderDetails_KEY_DELIVER_AT,
            OrderDetails_KEY_TOTAL,
            OrderDetails_KEY_TYPE,
            OrderDetails_KEY_ORDER_ITEMS,
            OrderDetails_KEY_STATUS,
            OrderDetails_KEY_PAYMENT_MODE,
            OrderDetails_KEY_PHONE,
            OrderDetails_KEY_ORDERED_BY
        )
        val table = OrderDetails_TABLE
        q.tables = table
        val cursor: Cursor = q.query(db, selectColumn, null, null, null, null, null)

        val result = ArrayList<Order>()

        if (cursor.moveToFirst()) {
            do {
                result.add(
                    Order(
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_DELIVER_AT)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_TOTAL)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_TYPE)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_ORDER_ITEMS)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_STATUS)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_PAYMENT_MODE)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_PHONE)),
                        cursor.getString(cursor.getColumnIndex(OrderDetails_KEY_ORDERED_BY))
                    )
                )
            } while (cursor.moveToNext())
        }
        return result
    }

}