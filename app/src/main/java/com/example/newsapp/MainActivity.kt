package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.newsapp.Api.NewsApi
import com.example.newsapp.Constants.Contants
import com.example.newsapp.Navigation.App
import com.example.newsapp.Screen.EveryNews
import com.example.newsapp.Screen.TopHeadlines
import com.example.newsapp.Screen.WebScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var newsApi: NewsApi
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            NewsAppTheme {
                App()
//                GlobalScope.launch {
//                    val response = newsApi.getEverything("bangladesh",1,Contants.api_key)
//                    Log.d("HEDARVALL",response.body().toString())
//                }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                   Box(modifier = Modifier.padding(innerPadding).fillMaxSize()){
//                       App()
//                   }
//                }
            }
        }
    }
}
