package com.example.vahan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vahan.models.CollegesListItem

class CollegeAdapter(val context: Context, val colleges: List<CollegesListItem>):
    RecyclerView.Adapter<CollegeAdapter.CollegeViewHolder>() {
    class CollegeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var college = itemView.findViewById<TextView>(R.id.college_name)
        var country = itemView.findViewById<TextView>(R.id.country)
        var link = itemView.findViewById<TextView>(R.id.link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollegeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return CollegeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return colleges.size
    }

    override fun onBindViewHolder(holder: CollegeViewHolder, position: Int) {
        val college = colleges[position]
        holder.college.text = college.name
        holder.country.text = college.country
        holder.link.text = college.web_pages[0]
        holder.link.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("URL", college.web_pages[0])
            context.startActivity(intent)
        }
    }
}