package com.example.to_dolist.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.to_dolist.presentation.common.BottomBarScreen
import com.example.to_dolist.domain.model.Screen
import com.example.to_dolist.presentation.calendar.CalendarScreen
import com.example.to_dolist.presentation.calendar.CalendarViewModel
import com.example.to_dolist.presentation.edit_task.EditTaskScreen
import com.example.to_dolist.presentation.edit_task.EditTaskViewModel
import com.example.to_dolist.presentation.edit_task.components.TaskNote
import com.example.to_dolist.presentation.tag.TagScreen
import com.example.to_dolist.presentation.tag.TagViewModel
import com.example.to_dolist.presentation.task.TaskScreen
import com.example.to_dolist.presentation.task.TaskViewModel
import com.example.to_dolist.presentation.theme.TodoListTheme
import com.example.to_dolist.presentation.theme.Typography
import com.example.to_dolist.presentation.theme.gray
import com.example.to_dolist.presentation.theme.lightBlue
import com.example.to_dolist.presentation.theme.white
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                MainScreen(this)
            }
        }
    }
}


@Composable
fun MainScreen(
    context: Context
) {
    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val screens = listOf(
        BottomBarScreen.Task,
        BottomBarScreen.Calendar,
        BottomBarScreen.Profile,
    )

    showBottomBar = screens.any {
        it.route == navBackStackEntry?.destination?.route
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) BottomBar(navController = navController, screens = screens)
        },
        content = {
            Column(
                Modifier.padding(it)
            ) {
                BottomNavGraph(navController = navController, context = context)
            }
        }
    )
}


@Composable
fun BottomNavGraph(navController: NavHostController, context: Context) {
    val editTaskViewModel = hiltViewModel<EditTaskViewModel>()
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val calendarViewModel = hiltViewModel<CalendarViewModel>()

    NavHost(
        navController = navController,
        startDestination = "task",
    ) {
        navigation(
            startDestination = BottomBarScreen.Task.route,
            route = "task"
        ) {
            composable(route = BottomBarScreen.Task.route) {
                val taskState by taskViewModel.taskState.collectAsStateWithLifecycle()
                TaskScreen(
                    context = context,
                    viewModel = taskViewModel,
                    taskState = taskState,
                    navController = navController,
                )
            }
            composable(route = Screen.EditTask.route + "/{id}") { navBackStack ->
                val taskId = navBackStack.arguments!!.getString("id")
                val state by editTaskViewModel.state.collectAsStateWithLifecycle()
                EditTaskScreen(
                    taskId = taskId!!.toInt(),
                    viewModel = editTaskViewModel,
                    navController = navController,
                    state = state,
                )
            }
            composable(route = Screen.TaskNote.route) {
                val state by editTaskViewModel.state.collectAsStateWithLifecycle()
                TaskNote(state, editTaskViewModel, navController)
            }
        }

        composable(route = BottomBarScreen.Calendar.route) {
            val state by calendarViewModel.calendarState.collectAsStateWithLifecycle()
            CalendarScreen(
                viewModel = calendarViewModel,
                state = state,
                navController = navController,
            )
        }
        composable(route = Screen.TagManagement.route) {
            val viewModel = hiltViewModel<TagViewModel>(it)
            val tagState by viewModel.tagState.collectAsStateWithLifecycle()
            TagScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                state = tagState,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, screens: List<BottomBarScreen>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        contentColor = lightBlue,
        backgroundColor = white
    ) {
        screens.forEach { screen ->
            BottomNavigationItem(
                unselectedContentColor = gray,
                label = {
                    Text(
                        text = screen.title,
                        style = Typography.headlineSmall
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = "Navigation Icon",
                        modifier = Modifier.size(30.dp)
                    )
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

