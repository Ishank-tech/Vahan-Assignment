package com.example.vahan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vahan.MainActivity.Data.colleges
import com.example.vahan.models.CollegesListItem
import com.example.vahan.viewmodels.MainViewModel
import com.example.vahan.viewmodels.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var collegeList : RecyclerView
    lateinit var adapter : CollegeAdapter
    lateinit var progressBar: ProgressBar
    lateinit var mainViewModel: MainViewModel
    object Data{
        var colleges = mutableListOf<CollegesListItem>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        collegeList = findViewById(R.id.college_list)

        adapter = CollegeAdapter(this@MainActivity, colleges)
        collegeList.adapter = adapter

        val layoutManager = LinearLayoutManager(this@MainActivity)
        collegeList.layoutManager = layoutManager
        val repository = (application as CollegeApplication).collegeRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(
            MainViewModel::class.java)

        mainViewModel.colleges.observe(this@MainActivity, Observer{
            Log.d("Ishank", it.size.toString())
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        })

        val serviceIntent = Intent(this, ApiRefreshService::class.java)
        startService(serviceIntent)
    }
}