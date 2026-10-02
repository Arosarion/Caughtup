package com.example.caughtup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.caughtup.ui.CaughtupApp
import com.example.caughtup.ui.theme.CaughtupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaughtupTheme {
                CaughtupApp()
            }
        }
    }
}