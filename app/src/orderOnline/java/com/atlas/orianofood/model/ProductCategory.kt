package com.atlas.orianofood.model

class ProductCategory() {

    var name: String? = null
    var image: String? = null
    var child: String? = null
    var parent: String? = null
    var grandparent: String? = null
    var sellingprice: String? = null
    var rate: String? = null
    var isSale: String? = null
    var offer: String? = null

    constructor(
        name: String,
        image: String,
        child: String,
        parent: String,
        grandparent: String,
        sellingprice: String,
        rate: String,
        isSale: String,
        offer: String
    ) : this() {
        this.name = name
        this.image = image
        this.child = child
        this.parent = parent
        this.grandparent = grandparent
        this.sellingprice = sellingprice
        this.rate = rate
        this.isSale = isSale
        this.offer = offer
    }

    constructor(
        name: String,
        image: String,
        child: String,
        parent: String,
        grandparent: String,
        sellingprice: String,
        rate: String
    ) : this() {
        this.name = name
        this.image = image
        this.child = child
        this.parent = parent
        this.grandparent = grandparent
        this.sellingprice = sellingprice
        this.rate = rate
    }
}
