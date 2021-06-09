package com.atlas.orianofood.firebaseRT.model

class Offer() {

    var name: String? = null
    var image: String? = null

    constructor(name: String, image: String?) : this() {
        this.name = name
        this.image = image
    }
}
