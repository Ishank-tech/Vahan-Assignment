package com.example.vahan.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vahan.api.CollegeService
import com.example.vahan.models.CollegesListItem
import com.example.vahan.utils.NetworkUtils

class CollegeRepository(private val collegeService: CollegeService,
                        private val context: Context) {

    private val collegeLiveData = MutableLiveData<List<CollegesListItem>>()
    val colleges:LiveData<List<CollegesListItem>>
    get() = collegeLiveData

    suspend fun getColleges(){
        if(NetworkUtils.isNetworkAvailable(context)){
            val res = collegeService.getColleges()
            if(res?.body() != null){
                collegeLiveData.postValue(res.body())
            }
        }
    }
}