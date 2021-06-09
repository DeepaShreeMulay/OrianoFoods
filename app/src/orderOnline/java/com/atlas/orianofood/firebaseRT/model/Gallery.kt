package com.atlas.orianofood.firebaseRT.model

class Gallery() {

    var name: String? = null
    var image: String? = null
    var type: String? = null

    constructor(name: String, image: String?, type: String) : this() {
        this.name = name
        this.image = image
        this.type = type
    }
}
