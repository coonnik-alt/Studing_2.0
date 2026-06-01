package com.example.test_application.Data

import retrofit2.http.GET
import retrofit2.http.Query

interface CoursesApi{

        @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
        suspend fun getCourses() : CoursesResponseDto

}