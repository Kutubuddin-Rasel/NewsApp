package com.example.newsapp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.newsapp.R
import com.example.newsapp.ViewModel.EveryNewsViewModel

@Composable
fun EveryNews(navController: NavController,state:Boolean) {
    val everyNewsViewModel: EveryNewsViewModel = hiltViewModel()
    val everyNews = everyNewsViewModel.everyNews.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(color = 0xFF00897B))
    ) {
        if (everyNews.value == null) {
            Box(modifier = Modifier.fillMaxSize().background(Color(color = 0xFF00897B))) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text("Loading...", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
                    CircularProgressIndicator(modifier = Modifier.size(80.dp), color = Color.Black)
                }
            }
        } else {
            header(everyNewsViewModel)
            LazyColumn {
                everyNews.value!!.articles.let {
                    items(it) {
                        Article(it,navController,state)
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun header(everyNewsViewModel: EveryNewsViewModel) {
    val keyboardController= LocalSoftwareKeyboardController.current
    val topicname = everyNewsViewModel.topicname.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SortDropdownMenu(everyNewsViewModel)
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            value = topicname.value,
            onValueChange = {
                everyNewsViewModel.Topic(it)
            },
            placeholder = { Text(text = "Search Topic", color = Color.White) },
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            everyNewsViewModel.topic(topicname.value)
            if (keyboardController != null) {
                keyboardController.hide()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = " ",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun SortDropdownMenu(everyNewsViewModel: EveryNewsViewModel) {
    var currentSort by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val sortingOptions = listOf("popularity", "relevancy", "publishedAt")
    IconButton(
        onClick = { expanded = true },
        colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
        modifier = Modifier.size(50.dp)
    ) {
        Image(painter = painterResource(R.drawable.sort),
            contentDescription = "",
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.background(Color.White)
    ) {
        sortingOptions.forEach { sortOption ->
            DropdownMenuItem(
                text = { Text(sortOption.toUpperCase(), fontWeight = FontWeight.SemiBold) },
                onClick = {
                    currentSort = sortOption
                    everyNewsViewModel.Sort(sortOption)
                    expanded = false
                }
            )
        }
    }
}