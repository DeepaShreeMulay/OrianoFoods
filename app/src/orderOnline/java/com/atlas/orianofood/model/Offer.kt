package com.atlas.orianofood.model

class Offer() {

    var name: String? = null
    var image: String? = null

    constructor(name: String, image: String?) : this() {
        this.name = name
        this.image = image
    }
}
