package com.example.studentupdates.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentupdates.AddList
import com.example.studentupdates.R

class StudentListAdapter(private val studentList: List<StudentListModel>, private val context: Context) :
    RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var regno: TextView = view.findViewById(R.id.txt_regno)
        var name: TextView = view.findViewById(R.id.txt_name)
        var age: TextView = view.findViewById(R.id.txt_age)
        var gender: TextView = view.findViewById(R.id.txt_gender)
        var testno: TextView = view.findViewById(R.id.txt_testno)
        var marks: TextView = view.findViewById(R.id.txt_marks)
        var btn_edit: TextView = view.findViewById(R.id.btn_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_student_list, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val lists = studentList[position]
        holder.regno.text = lists.regno
        holder.name.text = lists.studentname
        holder.age.text = lists.studentage.toString()
        holder.gender.text = lists.studentgender
        holder.testno.text = lists.testno.toString()
        holder.marks.text = lists.testmarks.toString()

        holder.btn_edit.setOnClickListener {
            val intent = Intent(context, AddList::class.java)
            intent.putExtra("Mode", "E")
            intent.putExtra("id", lists.id)
            context.startActivity(intent)
        }
    }
}
