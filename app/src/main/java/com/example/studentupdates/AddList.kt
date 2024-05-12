package com.example.studentupdates

import MainActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.studentupdates.database.DatabaseHelper
import com.example.studentupdates.model.StudentListModel

class AddList : AppCompatActivity() {

    lateinit var btn_save: Button
    lateinit var btn_del: Button
    lateinit var et_regno: EditText
    lateinit var et_stname: EditText
    lateinit var et_stage: EditText
    lateinit var et_stgender: EditText
    lateinit var et_testno: EditText
    lateinit var et_testmarks: EditText
    var dbHandler: DatabaseHelper? = null
    var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btn_save = findViewById(R.id.btn_submit)
        btn_del = findViewById(R.id.btn_delete)
        et_regno = findViewById(R.id.st_regno)
        et_stname = findViewById(R.id.st_name)
        et_stage = findViewById(R.id.st_age)
        et_stgender = findViewById(R.id.st_gender)
        et_testno = findViewById(R.id.st_testno)
        et_testmarks = findViewById(R.id.st_testmarks)

        dbHandler = DatabaseHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E") {
            //updatedata
            isEditMode = true
            btn_save.text = "update Data"
            btn_del.visibility = View.VISIBLE
            val id = intent.getIntExtra("id", 0)
            val lists: StudentListModel? = dbHandler?.getlist(id)

            if (lists != null) {
                et_regno.setText(lists.regno)
                et_stname.setText(lists.studentname)
                et_stage.setText(lists.studentage.toString()) // Convert Int to String
                et_stgender.setText(lists.studentgender)
                et_testno.setText(lists.testno.toString()) // Convert Int to String
                et_testmarks.setText(lists.testmarks.toString()) // Convert Float to String
            } else {
                //insert new data
                isEditMode = false
                btn_save.text = "Save Data"
                btn_del.visibility = View.GONE
            }
            btn_save.setOnClickListener {
                var success: Boolean = false
                val studentListModel: StudentListModel = StudentListModel()
                if (isEditMode) {
                    // update
                    studentListModel.id = intent.getIntExtra("Id", 0)
                    studentListModel.regno = et_regno.text.toString()
                    studentListModel.studentname = et_stname.text.toString()
                    studentListModel.studentage = et_stage.text.toString().toIntOrNull() ?: 0 // Convert string to Int, or default to 0 if conversion fails
                    studentListModel.studentgender = et_stgender.text.toString()
                    studentListModel.testno = et_testno.text.toString().toIntOrNull() ?: 0 // Convert string to Int, or default to 0 if conversion fails
                    studentListModel.testmarks = et_testmarks.text.toString().toFloatOrNull() ?: 0f // Convert string to Float, or default to 0 if conversion fails

                    success = dbHandler?.updatelist(studentListModel) as Boolean
                } else {
                    // insert
                    studentListModel.regno = et_regno.text.toString()
                    studentListModel.studentname = et_stname.text.toString()
                    studentListModel.studentage = et_stage.text.toString().toIntOrNull() ?: 0 // Convert string to Int, or default to 0 if conversion fails
                    studentListModel.studentgender = et_stgender.text.toString()
                    studentListModel.testno = et_testno.text.toString().toIntOrNull() ?: 0 // Convert string to Int, or default to 0 if conversion fails
                    studentListModel.testmarks =
                        (et_testmarks.text.toString().toIntOrNull() ?: 0).toFloat() // Convert string to Int, or default to 0 if conversion fails

                    success = dbHandler?.addlist(studentListModel) as Boolean
                }
                if (success) {
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Something went wrong",
                        Toast.LENGTH_LONG
                    ).show() // Added .show() to display the toast
                }
            }

            btn_del.setOnClickListener {
                val dialog = AlertDialog.Builder(this).setTitle("Info")
                    .setMessage("Click yes If You want to delete")
                    .setPositiveButton("Yes") { dialog, i ->
                        val success =
                            dbHandler?.deletelist(intent.getIntExtra("Id", 0)) as Boolean
                        if (success)
                            finish()
                        dialog.dismiss()
                    }
                    .setNegativeButton("No") { dialog, i ->
                        dialog.dismiss()
                    }
                dialog.show()
            }
        }
    }
}
