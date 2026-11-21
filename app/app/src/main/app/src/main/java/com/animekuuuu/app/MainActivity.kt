package com.animekuuuu.app

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
fun App() {
    var video by remember { mutableStateOf<String?>(null) }
    val bg = Color(0xFF090912)
    val purple = Color(0xFF9B72FF)
    MaterialTheme(colorScheme = darkColorScheme(primary = purple, background = bg, surface = Color(0xFF19192A))) {
        Surface(Modifier.fillMaxSize(), color = bg) {
            if (video != null) {
                Column {
                    TextButton(onClick = { video = null }) { Text("← Kembali") }
                    YouTube(video!!)
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    item {
                        Text("Animekuuuu", style = MaterialTheme.typography.headlineLarge, color = purple, fontWeight = FontWeight.ExtraBold)
                        Text("Anime favoritmu dalam satu tempat", color = Color.Gray)
                        Spacer(Modifier.height(20.dp))
                        Box(Modifier.fillMaxWidth().background(Color(0xFF25203F), RoundedCornerShape(18.dp)).padding(20.dp)) {
                            Column {
                                Text("ANIME • YOUTUBE", color = purple)
                                Text("Temukan tontonan favoritmu", style = MaterialTheme.typography.titleLarge)
                                Text("Pemutar video tertanam", color = Color.LightGray)
                            }
                        }
                        Spacer(Modifier.height(24.dp))
                        Text("Video contoh", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(12.dp))
                    }
                    item { VideoRow("Video contoh 1", "M7lc1UVf-VE") { video = it } }
                    item { VideoRow("Video contoh 2", "aqz-KE-bpKQ") { video = it } }
                    item { Text("YouTube API belum dikonfigurasi. Jangan commit API key ke repository publik.", color = Color.Gray, modifier = Modifier.padding(top = 18.dp)) }
                }
            }
        }
    }
}

@Composable
fun VideoRow(title: String, id: String, onPlay: (String) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(bottom = 12.dp).background(Color(0xFF19192A), RoundedCornerShape(14.dp)).clickable { onPlay(id) }.padding(14.dp)) {
        Column {
            Text(title, fontWeight = FontWeight.Bold)
            Text("Tekan untuk memutar", color = Color(0xFF9B72FF))
        }
    }
}

@Composable
fun YouTube(id: String) {
    AndroidView(Modifier.fillMaxWidth().aspectRatio(16f / 9f), factory = { c ->
        WebView(c).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
            val html = "<html><meta name='viewport' content='width=device-width,initial-scale=1'><body style='margin:0;background:black'><iframe width='100%' height='220' src='https://www.youtube.com/embed/" + id + "?playsinline=1' frameborder='0' allow='autoplay; encrypted-media; picture-in-picture' allowfullscreen></iframe></body></html>"
            loadDataWithBaseURL("https://www.youtube.com/", html, "text/html", "UTF-8", null)
        }
    })
}
