package com.example.caughtup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.caughtup.ui.navigation.Screen
import kotlinx.coroutines.delay

// Shown once, right when the app opens. Pure black background + the app title,
// then auto-navigates to the Shows tab after a short delay. popUpTo(...){inclusive = true}
// removes "splash" from the back stack entirely, so the back button can never
// return to it once you're past it.
@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1200)
        navController.navigate(Screen.Shows.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Caughtup",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}