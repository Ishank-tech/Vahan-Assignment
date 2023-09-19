package com.example.vahan.api

import com.example.vahan.models.CollegesListItem
import retrofit2.Response
import retrofit2.http.GET

interface CollegeService {
    @GET("/search")
    suspend fun getColleges() : Response<List<CollegesListItem>>
}