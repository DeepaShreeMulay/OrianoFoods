package com.atlas.orianofood.model

class Order() {

    lateinit var orderId: String
    lateinit var orderDate: String
    lateinit var orderDeliverAt: String
    lateinit var orderTotalAmount: String
    lateinit var orderType: String
    lateinit var orderItems: String
    lateinit var orderStatus: String
    lateinit var paymentMode: String
    lateinit var phoneNumber: String
    lateinit var OrderedBy: String

    constructor(
        orderId: String,
        orderDate: String,
        orderDeliverAt: String,
        orderTotalAmount: String,
        orderType: String,
        orderItems: String,
        orderStatus: String,
        paymentMode: String,
        phoneNumber: String,
        OrderedBy: String
    ) : this() {
        this.orderId = orderId
        this.orderDate = orderDate
        this.orderDeliverAt = orderDeliverAt
        this.orderTotalAmount = orderTotalAmount
        this.orderType = orderType
        this.paymentMode = paymentMode
        this.orderItems = orderItems
        this.orderStatus = orderStatus
        this.phoneNumber = phoneNumber
        this.OrderedBy = OrderedBy
    }
}