package com.example.to_dolist.domain.util


sealed class TaskFilter(
    val orderType: OrderType
) {
    class Date(orderType: OrderType, val selectedDate: String): TaskFilter(orderType)
    class Tag(orderType: OrderType, val tagId: Int?): TaskFilter(orderType)
    class Title(orderType: OrderType, val title: String?): TaskFilter(orderType)
}