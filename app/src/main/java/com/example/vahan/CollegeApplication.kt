package com.example.vahan

import android.app.Application
import android.content.Intent
import com.example.vahan.api.CollegeService
import com.example.vahan.api.RetrofitHelper
import com.example.vahan.repository.CollegeRepository

class CollegeApplication:Application() {
    lateinit var collegeRepository: CollegeRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
        val serviceIntent = Intent(this, ApiRefreshService::class.java)
        startService(serviceIntent)
    }

    private fun initialize() {
        val collegeService = RetrofitHelper.getInstance().create(CollegeService::class.java)
        collegeRepository = CollegeRepository(collegeService, applicationContext)
    }
}