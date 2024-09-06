package com.example.newsapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.ViewModel.AllArticleViewModel

@Composable
fun SavedArticle(navController:NavController,state:Boolean) {
    val viewModel:AllArticleViewModel= hiltViewModel()
    val article = viewModel.article.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(color = 0xFF00897B))
    ) {
        if (article.value.isEmpty()) {
            Row(horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("No Saved Article", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
            }
        } else {
            Column {
                LazyColumn {
                    items(article.value){
                        Article(it,navController,state)
                    }
                }
            }
        }
    }
}