package com.example.vahan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vahan.models.CollegesListItem
import com.example.vahan.repository.CollegeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository:CollegeRepository): ViewModel(){
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getColleges()
        }
    }

    val colleges: LiveData<List<CollegesListItem>>
    get() = repository.colleges
}