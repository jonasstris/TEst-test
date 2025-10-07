package com.example.bursdagapp385499.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bursdagapp385499.ui.screens.FrontScreen
import com.example.bursdagapp385499.ui.screens.PrefScreen
import com.example.bursdagapp385499.ui.viewmodel.PersonViewmodel

@Composable
fun NavigationGraph(navController: NavHostController, vm: PersonViewmodel) {
    NavHost(navController = navController, startDestination = "front") {
        composable("front") {
            FrontScreen(navController = navController, vm = vm)
        }
        composable("pref") {
            PrefScreen(navController = navController, vm = vm, modifier = Modifier)
        }
    }
}