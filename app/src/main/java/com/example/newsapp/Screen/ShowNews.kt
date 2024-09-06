package com.example.newsapp.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newsapp.Constants.Contants
import com.example.newsapp.ViewModel.AllArticleViewModel
import com.example.newsapp.ViewModel.DeleteViewModel
import com.example.newsapp.module.Article
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import com.example.newsapp.navigateToWebPage


@Composable
fun Article(article: Article,navController: NavController,state:Boolean) {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(15.dp),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .clickable {
                    //viewmodel.setArticle(article)
                    navController.navigateToWebPage(article.url)
                    Contants.article = article
                },
            colors = CardDefaults.cardColors(Color(color = 0xFFE2FAF7)),
            border = BorderStroke(2.dp, color = Color.Black)
        ) {

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(5.dp)
            ) {
                // Using Coil to load image from URL
                Column {
                    AsyncImage(
                        modifier = Modifier.size(135.dp, 79.dp),
                        model = article.urlToImage,
                        contentDescription = "Article Image"
                    )
                    Text(
                        formatDate(article.publishedAt),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = article.title,
                        maxLines = 2,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 2.dp),
                        fontSize = 16.sp
                    )
                    Box(
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth(.8f)
                            .height(2.dp)
                    )
                    Text(
                        text = article.description,
                        maxLines = 2,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp),
                        fontSize = 15.sp
                    )
                }
            }
        }
        if(state){
            delete(article)
        }
    }
}

@Composable
fun delete(article: Article) {
    val viewModel:DeleteViewModel= hiltViewModel()
    Icon(imageVector = Icons.Default.Delete,
        contentDescription = "",
        tint = Color.LightGray,
        modifier = Modifier
            .size(25.dp,25.dp)
            .clickable {
            viewModel.deleteArticle(article)
        }
    )
}

fun formatDate(publishedAt: String): String {
    return try {
        // Parse the input date string
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateTime = LocalDateTime.parse(publishedAt, formatter)
        val zonedDateTime = ZonedDateTime.of(dateTime, ZoneOffset.UTC)

        // Format to a desired output format, e.g., "September 5, 2024"
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
        zonedDateTime.format(outputFormatter)
    } catch (e: Exception) {
        "Unknown date"
    }
}

