package com.atlas.orianofood.model

class Category() {

    var name: String? = null
    var image: String? = null
    var child: String? = null
    var parent: String? = null

    constructor(name: String, image: String, child: String, parent: String) : this() {
        this.name = name
        this.image = image
        this.child = child
        this.parent = parent
    }
}
