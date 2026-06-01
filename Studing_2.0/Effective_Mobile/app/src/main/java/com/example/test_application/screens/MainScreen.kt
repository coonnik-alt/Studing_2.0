package com.example.test_application.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_application.Data.CourseDto
import com.example.test_application.Data.RetrofitInstance
import com.example.test_application.DataBase.AppDataBase
import com.example.test_application.R
import com.example.test_application.ui.VM.CoursesViewModel
import com.example.test_application.ui.VM.CoursesViewModelFactory

@Composable
fun MainScreen( ) {

    val context = LocalContext.current
    val database = AppDataBase.getDatabase(context)

    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val viewModel: CoursesViewModel = viewModel(
        factory = CoursesViewModelFactory(
            api = RetrofitInstance.api,
            favoriteCourseDao = database.favoriteCourseDao(),
            prefs = prefs
        )
    )

    LaunchedEffect(Unit) {
        viewModel.refreshFavoriteFlags()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF151515))
            .padding(16.dp)
            .padding(top = 35.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = "",
                onValueChange = {},
                enabled = false,
                placeholder = {
                    Text(
                        "Search courses...",
                        color = Color(0xFF9A9A9A)
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = Color(0xFF24252B),
                    disabledIndicatorColor = Color.Transparent,
                    disabledPlaceholderColor = Color(0xFF9A9A9A),
                    disabledTextColor = Color.White)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .size(56.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF24252B),
                    contentColor = Color.White)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Фильтр",
                    modifier = Modifier
                        .size(28.dp),
                    tint = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp)
                .clickable{
                    viewModel.sortByPublishDate()
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "По дате добавления",
                color = Color.Green,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                painter = painterResource(R.drawable.ic_sort),
                contentDescription = "Стрелки сортировки",
                modifier = Modifier.size(14.dp),
                tint = Color.Green
            )
        }


        Spacer(
            modifier = Modifier
                .padding(top = 16.dp)
        )

        when {
            viewModel.isLoading -> {
                CircularProgressIndicator(
                    color = Color(0xFF12B956),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }

            viewModel.errorText != null -> {
                Text(
                    text = viewModel.errorText ?: "",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }


            else -> {

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(viewModel.courses) { course ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(0xFF24252B),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            ) {

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(180.dp)
                                )
                                {

                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(16.dp)),
                                        painter = painterResource(id = R.drawable.ic_photo_courses),
                                        contentDescription = "Фото курса пример",
                                        contentScale = ContentScale.Crop
                                    )

                                    IconButton ( onClick = {
                                        viewModel.toggleLike(course)
                                    },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(end = 10.dp, top = 10.dp)
                                            .size(36.dp)
                                            .background(
                                                color = if (course.hasLike == true) {
                                                    Color.White.copy(alpha = 0.8f)
                                                } else {
                                                    Color.Black.copy(alpha = 0.7f)
                                                },
                                                shape = CircleShape
                                            )) {

                                        Icon(
                                            painter = painterResource(R.drawable.ic_bookmark),
                                            contentDescription = "Избранное",
                                            tint = if (course.hasLike == true) Color(0xFF12B956) else Color.White,
                                            modifier = Modifier.size(22.dp)

                                        )

                                    }

                                    Row(
                                        modifier = Modifier
                                            .padding(start = 5.dp, bottom = 5.dp)
                                            .align(Alignment.BottomStart),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = "★ ${course.rate ?: ""}",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = Bold,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(20.dp))
                                                .background(Color.Black.copy(alpha = 0.6f))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        )

                                        Spacer(
                                            modifier = Modifier
                                                .width(4.dp)
                                        )

                                        Text(
                                            text = course.startDate?.replace("-", ".") ?: "",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(20.dp))
                                                .background(Color.Black.copy(alpha = 0.6f))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = course.title ?: "",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                                Text(
                                    text = course.text ?: "",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(11.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "${course.price ?: ""} ₽",
                                        color = Color.White,
                                        fontSize = 19.sp,
                                        fontWeight = Bold,
                                        modifier = Modifier
                                            .padding(10.dp)

                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(10.dp)
                                    ) {

                                        Text(
                                            text = "Подробнее",
                                            color = Color.Green,
                                            fontSize = 14.sp,
                                            fontWeight = Bold
                                        )

                                        Icon(
                                            painter = painterResource(R.drawable.ic_arrow_right),
                                            contentDescription = "Стрелка вправо",
                                            modifier = Modifier.size(15.dp),
                                            tint = Color.Green
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .align(Alignment.TopCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF151515),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                }
            }
            }
        }
    }

