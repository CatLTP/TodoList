package com.example.to_dolist.domain.util

enum class OrderType(
    val title: String,
) {
    AscendingLetter(title = "Bảng chữ cái A -> Z"),
    DescendingLetter(title = "Bảng chữ cái Z -> A"),
    AscendingDateCreated(title = "Thời gian tạo công việc mới nhất",),
    DescendingDateCreated(title = "Thời gian tạo công việc cũ nhất",),
    DeadlineFirst(title = "Ngày và giờ đến hạn"),
}