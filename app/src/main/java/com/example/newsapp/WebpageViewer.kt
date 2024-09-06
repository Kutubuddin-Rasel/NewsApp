package com.example.newsapp
import androidx.navigation.NavController
import com.example.newsapp.module.Article
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavController.navigateToWebPage(url: String) {
    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    this.navigate("${Routes.webpage}/$encodedUrl")
}
