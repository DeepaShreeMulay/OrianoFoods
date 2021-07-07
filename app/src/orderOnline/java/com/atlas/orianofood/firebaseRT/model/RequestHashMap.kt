package com.atlas.orianofood.firebaseRT.model

class RequestHashMap() {

    lateinit var phone: String
    lateinit var name: String
    lateinit var address: String
    lateinit var total: String
    lateinit var foods: HashMap<Int, Int>

    constructor(
            phone: String,
            name: String,
            address: String,
            total: String,
            foods: HashMap<Int, Int>
    ) : this() {
        this.phone = phone
        this.name = name
        this.address = address
        this.total = total
        this.foods = foods
    }
}