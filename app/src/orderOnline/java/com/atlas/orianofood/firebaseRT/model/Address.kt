package com.atlas.orianofood.firebaseRT.model

class Address() {

    lateinit var AddressId: String
    lateinit var DeliverTo: String
    lateinit var City: String
    lateinit var Location: String
    lateinit var HouseNo: String
    var Street: String? = null
    var Landmark: String? = null
    lateinit var Pincode: String
    lateinit var Type: String
    lateinit var IsDefault: String

    constructor(
        AddressId: String, DeliverTo: String, City: String, Location: String, HouseNo: String,
        Street: String?, Landmark: String?, Pincode: String, Type: String, IsDefault: String
    ) : this() {

        this.AddressId = AddressId
        this.DeliverTo = DeliverTo
        this.City = City
        this.Location = Location
        this.HouseNo = HouseNo
        this.Street = Street
        this.Landmark = Landmark
        this.Pincode = Pincode
        this.Type = Type
        this.IsDefault = IsDefault

    }
}