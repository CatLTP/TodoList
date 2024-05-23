package com.example.to_dolist.domain.use_case.task

import com.example.to_dolist.helpers.AscendingDateComparision
import com.example.to_dolist.helpers.DescendingDateComparision
import com.example.to_dolist.domain.model.Task
import com.example.to_dolist.domain.repository.TaskRepository
import com.example.to_dolist.domain.util.OrderType
import com.example.to_dolist.domain.util.TaskFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllTask(
    private val repository: TaskRepository,
    private val ascendingDateComparision: AscendingDateComparision,
    private val descendingDateComparision: DescendingDateComparision,
) {

    operator fun invoke(
        taskFilter: TaskFilter
    ): Flow<List<Task>> {
        return repository.getAllTask().map { tasks ->
            when (taskFilter.orderType) {
                OrderType.AscendingLetter -> {
                    when (taskFilter) {
                        is TaskFilter.Tag -> tasks.filter {
                            if (taskFilter.tagId != null) {
                                it.tagId == taskFilter.tagId
                            } else {
                                true
                            }
                        }.sortedBy {
                            it.title.lowercase()
                        }

                        is TaskFilter.Title -> tasks.filter {
                            if (taskFilter.title != null) {
                                it.title.contains(taskFilter.title)
                            } else {
                                true
                            }
                        }.sortedBy {
                            it.title
                        }

                        is TaskFilter.Date -> tasks.filter {
                            it.deadline == taskFilter.selectedDate
                        }.sortedBy {
                            it.title
                        }
                    }
                }

                OrderType.DescendingLetter -> {
                    when (taskFilter) {
                        is TaskFilter.Tag -> tasks.filter {
                            if (taskFilter.tagId != null) {
                                it.tagId == taskFilter.tagId
                            } else {
                                true
                            }
                        }.sortedByDescending {
                            it.title
                        }

                        is TaskFilter.Title -> tasks.filter {
                            if (taskFilter.title != null) {
                                it.title.contains(taskFilter.title)
                            } else {
                                true
                            }
                        }.sortedByDescending {
                            it.title
                        }

                        is TaskFilter.Date -> tasks.filter {
                            it.deadline == taskFilter.selectedDate
                        }.sortedByDescending {
                            it.title
                        }
                    }
                }

                OrderType.AscendingDateCreated -> {
                    when (taskFilter) {
                        is TaskFilter.Tag -> tasks.filter {
                            if (taskFilter.tagId != null) {
                                it.tagId == taskFilter.tagId
                            } else {
                                true
                            }
                        }.sortedWith(
                            ascendingDateComparision
                        )

                        is TaskFilter.Title -> tasks.filter {
                            if (taskFilter.title != null) {
                                it.title.contains(taskFilter.title)
                            } else {
                                true
                            }
                        }.sortedWith(
                            ascendingDateComparision
                        )

                        is TaskFilter.Date -> tasks.filter {
                            it.deadline == taskFilter.selectedDate
                        }.sortedWith(
                            ascendingDateComparision
                        )
                    }
                }

                OrderType.DescendingDateCreated -> {
                    when (taskFilter) {
                        is TaskFilter.Tag -> tasks.filter {
                            if (taskFilter.tagId != null) {
                                it.tagId == taskFilter.tagId
                            } else {
                                true
                            }
                        }.sortedWith(
                            descendingDateComparision
                        )

                        is TaskFilter.Title -> tasks.filter {
                            if (taskFilter.title != null) {
                                it.title.contains(taskFilter.title)
                            } else {
                                true
                            }
                        }.sortedWith(
                            descendingDateComparision
                        )

                        is TaskFilter.Date -> tasks.filter {
                            it.deadline == taskFilter.selectedDate
                        }.sortedWith(
                            descendingDateComparision
                        )
                    }
                }

                OrderType.DeadlineFirst -> {
                    when (taskFilter) {
                        is TaskFilter.Tag -> tasks.filter {
                            if (taskFilter.tagId != null) {
                                it.tagId == taskFilter.tagId
                            } else {
                                true
                            }
                        }.sortedBy {
                            it.deadline
                        }

                        is TaskFilter.Title -> tasks.filter {
                            if (taskFilter.title != null) {
                                it.title.contains(taskFilter.title)
                            } else {
                                true
                            }
                        }.sortedBy {
                            it.deadline
                        }

                        is TaskFilter.Date -> tasks.filter {
                            it.deadline == taskFilter.selectedDate
                        }.sortedBy {
                            it.deadline
                        }
                    }
                }
            }
        }
    }
}