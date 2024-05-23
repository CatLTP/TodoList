package com.example.to_dolist.domain.model

sealed class Screen(
    val route : String,
    val title: String
) {
    data object TagManagement: Screen(
        route = "tag",
        title = "Quản lý danh mục"
    )
    data object EditTask: Screen(
        route = "detail",
        title = "Chỉnh sửa công việc"
    )
    data object TaskNote: Screen(
        route = "note",
        title = "Thêm ghi chú"
    )
}