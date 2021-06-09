package com.atlas.orianofood.firebaseRT.model

class User() {

    lateinit var name: String
    lateinit var password: String
    lateinit var phone: String
    lateinit var email: String

    constructor(name: String, password: String, phone: String, email: String) : this() {
        this.name = name
        this.password = password
        this.phone = phone
        this.email


    }
}