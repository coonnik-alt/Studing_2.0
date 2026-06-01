package com.example.test_application.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_application.DataBase.AppDataBase
import com.example.test_application.DataBase.FavoriteCourseEntity
import com.example.test_application.R
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen() {
    val context = LocalContext.current
    val database = AppDataBase.getDatabase(context)
    val dao = database.favoriteCourseDao()
    val scope = rememberCoroutineScope()

    var favoriteCourses by remember {
        mutableStateOf<List<FavoriteCourseEntity>>(emptyList())
    }

    LaunchedEffect(Unit) {
        favoriteCourses = dao.getAllFavorites()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF151515))
            .padding(16.dp)
    ) {
        Text(
            text = "Избранное",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 40.dp)
        )

        if (favoriteCourses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Избранных курсов пока нет",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoriteCourses) { course ->
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
                                .fillMaxWidth()
                                .height(180.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_photo_courses),
                                contentDescription = "Фото курса пример",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                                contentScale = ContentScale.Crop
                            )

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        dao.deleteById(course.id)
                                        favoriteCourses = dao.getAllFavorites()
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 8.dp, top = 8.dp)
                                    .size(36.dp)
                                    .background(
                                        color = Color.White.copy(alpha = 0.75f),
                                        shape = CircleShape
                                    )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_bookmark),
                                    contentDescription = "Избранное",
                                    tint = Color(0xFF12B956),
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 8.dp, bottom = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "★ ${course.rate}",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.White.copy(alpha = 0.22f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = course.startDate.replace("-", "."),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.White.copy(alpha = 0.22f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = course.title,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = course.text,
                                color = Color.White,
                                fontSize = 14.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${course.price} ₽",
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontWeight = Bold
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Подробнее",
                                        color = Color.Green,
                                        fontSize = 14.sp,
                                        fontWeight = Bold
                                    )

                                    Spacer(modifier = Modifier.width(2.dp))

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
            }
        }
    }
}