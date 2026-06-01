package com.example.test_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AccountScreen() {
    Text(
        text = "Аккаунт",
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF151515))
            .wrapContentSize(Alignment.Center)
    )
}