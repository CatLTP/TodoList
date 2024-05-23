package com.example.to_dolist.helpers

import android.icu.text.SimpleDateFormat
import com.example.to_dolist.domain.model.Task
import java.util.Locale

class DescendingDateComparision: Comparator<Task> {
    override fun compare(date1: Task, date2: Task) : Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val x = sdf.parse(date1.dateCreate)
        val y = sdf.parse(date2.dateCreate)
        return y.compareTo(x)
    }
}