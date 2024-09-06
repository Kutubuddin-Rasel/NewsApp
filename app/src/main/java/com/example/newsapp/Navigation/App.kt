package com.example.newsapp.Navigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.R
import com.example.newsapp.Routes
import com.example.newsapp.Screen.EveryNews
import com.example.newsapp.Screen.SavedArticle
import com.example.newsapp.Screen.TopHeadlines
import com.example.newsapp.Screen.WebScreen
import com.example.newsapp.ViewModel.StateViewModel

@Composable
fun App() {
    val navController = rememberNavController()
    val viewModel:StateViewModel= viewModel()
    val navItemList = listOf(
        detail(Routes.topHeadlines, "Top Headlines",R.drawable.head),
        detail(Routes.everything, "Everything",R.drawable.everything),
        detail(Routes.saveArticle, "Save Article",R.drawable.save)
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, navItemList = navItemList,viewModel)
        }
    ) { paddingValues ->
        NavHostContainer(navController = navController, modifier = Modifier.padding(paddingValues),viewModel)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, navItemList: List<detail>,viewModel: StateViewModel) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    NavigationBar(
       containerColor = Color(0xFF1AAF9F),
        modifier = Modifier.fillMaxWidth()
    ) {
        navItemList.forEach { detail ->
            NavigationBarItem(
                selected = currentRoute == detail.route,
                onClick = {
                    if(detail.route==Routes.saveArticle)
                    {viewModel.changeState(true)}
                    else{viewModel.changeState(false)}
                    navController.navigate(detail.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Image(painter = painterResource(detail.image),"", modifier = Modifier.size(40.dp)) },
                label = { Text(detail.screenName, fontSize = 15.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White, // Color of the selected icon
                    unselectedIconColor = Color.Gray, // Color of the unselected icon
                    selectedTextColor = Color.White, // Color of the selected label text
                    unselectedTextColor = Color.Gray, // Color of the unselected label text
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun NavHostContainer(navController: NavHostController, modifier: Modifier,viewModel: StateViewModel) {
    val state = viewModel.state.collectAsState()
    NavHost(navController = navController, startDestination = Routes.topHeadlines, modifier = modifier) {
        composable(Routes.topHeadlines) { TopHeadlines(navController,state.value) }
        composable(Routes.everything) { EveryNews(navController,state.value) }
        composable(Routes.saveArticle) { SavedArticle(navController,state.value) }
        composable(
            route = "${Routes.webpage}/{url}",
            arguments = listOf(
                navArgument("url",) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url")
          //  val url = java.net.URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
            if (encodedUrl != null) {
                WebScreen(encodedUrl)
            }
        }
    }
}
