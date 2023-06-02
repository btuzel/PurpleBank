package com.example.purplebank

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.purplebank.navigation.NavGraph
import com.example.purplebank.server.EmbeddedServer
import com.example.purplebank.ui.theme.PurpleBankTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var embeddedServer: EmbeddedServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        embeddedServer.start()
        setContent {
            PurpleBankTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navHostController = rememberNavController()
                    NavGraph(navHostController = navHostController)
                }
            }
        }
    }
}
