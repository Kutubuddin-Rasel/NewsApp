package com.example.newsapp.Screen

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.Constants.Contants
import com.example.newsapp.ViewModel.AllArticleViewModel
import com.example.newsapp.ViewModel.SaveArticleViewModel
import com.example.newsapp.module.Article
import dagger.hilt.android.qualifiers.ActivityContext

@Composable
fun WebScreen(url:String) {
    val viewModel:SaveArticleViewModel= hiltViewModel()
    val allArticleViewModel:AllArticleViewModel= hiltViewModel()
    val listofArticles=allArticleViewModel.article.collectAsState().value
    val context= LocalContext.current
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Button(onClick = {Contants.article?.let {
            if(isAlreadySaved(listofArticles)){Toast.makeText(context, "Already Saved", Toast.LENGTH_SHORT).show()}
            else{ viewModel.saveArticle(it)}
        } }, modifier = Modifier.fillMaxWidth()) {
            Text("Save", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
        AndroidView(factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient() // Ensure the URL opens in the WebView
                loadUrl(url)
            }
        })
    }
}
fun isAlreadySaved(articles:List<Article>):Boolean{
    var value= false
    articles.forEach {
        if(it.title==Contants.article?.title){
            value= true
        }
        else{
            value= false
        }
    }
    return value
}



