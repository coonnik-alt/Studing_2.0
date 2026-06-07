package com.example.test_application.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test_application.Data.RetrofitInstance
import com.example.test_application.DataBase.AppDataBase
import com.example.test_application.R
import com.example.test_application.Roboto
import com.example.test_application.ui.VM.CoursesViewModel
import com.example.test_application.ui.VM.CoursesViewModelFactory

@Composable
fun LogIn(
    onLoginClick: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailTouched by remember { mutableStateOf(false) }

    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    val isEmailValid = emailRegex.matches(email)
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF151515))
    ) {
        Text(
            text = "Вход",
            fontFamily = Roboto,
            fontSize = 28.sp,
            color = Color.White,
            modifier = Modifier.padding(
                top = 140.dp,
                start = 16.dp
            )
        )

        Spacer(Modifier.padding(top = 25.dp))

        Text(text = "Email",
            fontFamily = Roboto,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .testTag("email_error")
                .padding(5.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("email_field")
            ,
            value = email,
            onValueChange = { newValue ->
                val hasCyrillic = newValue.any { char ->
                    char in 'А'..'я' || char == 'ё' || char == 'Ё'
                }

                if (!hasCyrillic) {
                    email = newValue
                    emailTouched = true
                }
            },
            placeholder = {
                Text("example@gmail.com")
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF32333B),
                unfocusedContainerColor = Color(0xFF32333B),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        }

        Spacer(Modifier.padding(top = 15.dp))

        Text(text = "Пароль",
            fontFamily = Roboto,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(5.dp)
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = password,
            onValueChange = { password = it
            },
            placeholder = {
                Text("Введите пароль")
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF32333B),
                unfocusedContainerColor = Color(0xFF32333B),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.padding(top = 15.dp))

        Button(onClick = {
            if (isEmailValid && password.isNotBlank()) {
                onLoginClick()
            }
        },  enabled = isEmailValid && password.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF12B956),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFF3A3A3A),
                disabledContentColor = Color(0xFF8A8A8A)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 15.dp))
        {
            Text(text = "Вход",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Нету аккаунта?",
                color = Color.White,
                fontSize = 15.sp
            )

            Text(
                text = "Регистрация",
                color = Color.Green,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable{
                        uriHandler.openUri("Ссылка - нефункциональна по условию задания")
                    })

        }

        Text(text = "Забыл пароль",
            color = Color.Green,
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .wrapContentSize(align = Alignment.Center)
                .clickable{
                    uriHandler.openUri("Кнопка неактивна")
                })

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            thickness = 1.dp,
            color = Color(0xFF4A4B50)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween)
        {

            Button(
                onClick = {
                    uriHandler.openUri("https://vk.com/")
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2D81E0),
                    contentColor = Color.White
                )
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.ic_vk) ,
                    contentDescription = "VK",
                    modifier = Modifier.size(65.dp)

                )
            }

            Button(
                onClick = {
                    uriHandler.openUri("https://ok.ru/")
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF7700),
                    contentColor = Color.White
                )
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ok_ic),
                    contentDescription = "OK",
                    modifier = Modifier.size(65.dp)
                )
            }
        }
    }