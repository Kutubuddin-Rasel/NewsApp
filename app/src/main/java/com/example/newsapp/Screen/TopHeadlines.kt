package com.example.newsapp.Screen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.newsapp.ViewModel.TopHeadlinesViewModel


@Composable
fun TopHeadlines(navController: NavController,state:Boolean) {
    val viewModel: TopHeadlinesViewModel = hiltViewModel()
    val news = viewModel.news.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(color = 0xFF00897B))
    ) {
        if (news.value == null) {
            Row(horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Loading...", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
                CircularProgressIndicator(modifier = Modifier.size(80.dp), color = Color.Black)
            }
        } else {
            Column {
                header2(viewModel)
                LazyColumn {
                    news.value!!.articles.let {
                        items(it) {
                            Article(it,navController,state)
                        }
                    }

                }
            }
        }
    }

}
@Composable
fun header2(viewModel: TopHeadlinesViewModel){
    val selectedButton = remember { mutableStateOf("") }
    val buttonColors = ButtonDefaults.buttonColors(Color(0xFF1AAF9F))

    val categories = listOf("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")
    val textColors = categories.map { category ->
        if (selectedButton.value == category) Color.Black else Color.White
    }

    @Composable
    fun CategoryButton(category: String, textColor: Color, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            colors = buttonColors,
            border = BorderStroke(1.dp, color = Color.White),
            modifier = Modifier.padding(horizontal = 4.dp) // Adds space between buttons
        ) {
            Text(category, fontSize = 17.sp, color = textColor)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()) // Enable horizontal scrolling
    ) {
        Row(modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom = 5.dp), // Padding around the row
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            categories.forEachIndexed { index, category ->
                CategoryButton(
                    category = category,
                    textColor = textColors[index],
                    onClick = {
                        viewModel.setvalue(category.lowercase())
                        selectedButton.value = category
                    }
                )
            }
        }
    }

}