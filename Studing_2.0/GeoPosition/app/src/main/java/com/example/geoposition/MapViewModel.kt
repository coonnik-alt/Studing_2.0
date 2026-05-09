package com.example.geoposition

import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    val landmarks = listOf(
        Landmark(
            title = "Красная площадь",
            description = "Главная площадь Москвы.",
            latitude = 55.753930,
            longitude = 37.620795
        ),
        Landmark(
            title = "Эрмитаж",
            description = "Один из крупнейших музеев мира.",
            latitude = 59.939832,
            longitude = 30.314559
        ),
        Landmark(
            title = "Казанский Кремль",
            description = "Исторический комплекс в Казани.",
            latitude = 55.798551,
            longitude = 49.106324
        )
    )
}