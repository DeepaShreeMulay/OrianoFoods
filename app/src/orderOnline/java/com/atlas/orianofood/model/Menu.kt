package com.atlas.orianofood.model

class Menu() {

    var name: String? = null
    var image: String? = null
    var child: String? = null

    constructor(name: String, image: String?, child: String) : this() {
        this.name = name
        this.image = image
        this.child = child
    }
}
