package com.atlas.orianofood.firebaseRT.utils

const val CATEGORY_EXTRA = "Category"
const val SUB_CATEGORY_EXTRA = "SubCategory"
const val PRODUCT_CATEGORY_EXTRA = "ProductCategory"
const val MENU_EXTRA = "Menu"
const val OFFERS_EXTRA = "Offers"
const val PRODUCT_EXTRA = "Product"
const val TOP_SELLING = "Top Selling"
val selectedProductIDsList: HashMap<Int, Int> = HashMap<Int, Int>() // HashMap<Id,Qty>


/*
    productId: Int -> update quantity for given productId in HashMap : selectedProductIDsList
    operator: Boolean -> {in quantity}
                         true for plus
                         false for minus

    return type Boolean : true for Add into cart
                          false for update quantity already exists in cart
 */
fun UpdateItemToProductIdMap(productId: Int, operator: Boolean): Boolean {
    if (selectedProductIDsList.containsKey(productId)) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            if (operator)
                selectedProductIDsList.replace(productId, selectedProductIDsList.get(productId)?.plus(1)
                        ?: 1)
            else
                selectedProductIDsList.replace(productId, selectedProductIDsList.get(productId)?.minus(1)
                        ?: 1)
        } else {

            val qty: Int = if (operator)
                selectedProductIDsList.get(productId)?.plus(1) ?: 1
            else
                selectedProductIDsList.get(productId)?.plus(1) ?: 1

            selectedProductIDsList.remove(productId)
            selectedProductIDsList.put(productId, qty)
        }
        return false
    } else {
        selectedProductIDsList.put(productId, 1)
        return true
    }
}

fun UpdateItemToProductIdMap(productId: Int, quantity: Int) {

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        selectedProductIDsList.replace(productId, quantity)
    } else {
        selectedProductIDsList.remove(productId)
        selectedProductIDsList.put(productId, quantity)
    }

}

