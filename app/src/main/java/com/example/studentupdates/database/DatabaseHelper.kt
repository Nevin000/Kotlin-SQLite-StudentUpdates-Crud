package com.example.studentupdates.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "student_updates.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "StudentList"
        private const val ID = "id"
        private const val STUDENT_REGNO = "studentregisterno"
        private const val STUDENT_NAME = "studentname"
        private const val STUDENT_AGE = "studentage"
        private const val STUDENT_GENDER = "studentgender"
        private const val TEST_NO = "testno"
        private const val TEST_MARKS = "testmarks"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY,$STUDENT_REGNO TEXT,$STUDENT_NAME TEXT,$STUDENT_AGE INTEGER,$STUDENT_GENDER TEXT,$TEST_NO INTEGER,$TEST_MARKS FLOAT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun getAllList(): List<StudentListModel> {
        val studentList = ArrayList<StudentListModel>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor? = db.rawQuery(selectQuery, null)
        cursor?.use {
            val idIndex = it.getColumnIndex(ID)
            val regNoIndex = it.getColumnIndex(STUDENT_REGNO)
            val nameIndex = it.getColumnIndex(STUDENT_NAME)
            val ageIndex = it.getColumnIndex(STUDENT_AGE)
            val genderIndex = it.getColumnIndex(STUDENT_GENDER)
            val testNoIndex = it.getColumnIndex(TEST_NO)
            val testMarksIndex = it.getColumnIndex(TEST_MARKS)

            while (it.moveToNext()) {
                val list = StudentListModel()
                if (idIndex != -1) list.id = it.getInt(idIndex)
                if (regNoIndex != -1) list.regno = it.getString(regNoIndex)
                if (nameIndex != -1) list.studentname = it.getString(nameIndex)
                if (ageIndex != -1) list.studentage = it.getInt(ageIndex)
                if (genderIndex != -1) list.studentgender = it.getString(genderIndex)
                if (testNoIndex != -1) list.testno = it.getInt(testNoIndex)
                if (testMarksIndex != -1) list.testmarks = it.getFloat(testMarksIndex)
                studentList.add(list)
            }
        }
        cursor?.close()
        return studentList
    }
    //insert
    fun addlist(listModel: StudentListModel):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STUDENT_REGNO,listModel.regno)
        values.put(STUDENT_NAME,listModel.studentname)
        values.put(STUDENT_AGE,listModel.studentage)
        values.put(STUDENT_GENDER,listModel.studentgender)
        values.put(TEST_NO,listModel.testno)
        values.put(TEST_MARKS,listModel.testmarks)
        val _success = db.insert(TABLE_NAME,null,values)
        db.close()
        return (Integer.parseInt("$_success") != -1)


    }
    //select the data of particular id
    fun getlist(_id: Int): StudentListModel {
        val lists = StudentListModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.use {
            val idIndex = it.getColumnIndex(ID)
            val regNoIndex = it.getColumnIndex(STUDENT_REGNO)
            val nameIndex = it.getColumnIndex(STUDENT_NAME)
            val ageIndex = it.getColumnIndex(STUDENT_AGE)
            val genderIndex = it.getColumnIndex(STUDENT_GENDER)
            val testNoIndex = it.getColumnIndex(TEST_NO)
            val testMarksIndex = it.getColumnIndex(TEST_MARKS)

            cursor.moveToFirst()
            if (idIndex != -1) lists.id = it.getInt(idIndex)
            if (regNoIndex != -1) lists.regno = it.getString(regNoIndex)
            if (nameIndex != -1) lists.studentname = it.getString(nameIndex)
            if (ageIndex != -1) lists.studentage = it.getInt(ageIndex)
            if (genderIndex != -1) lists.studentgender = it.getString(genderIndex)
            if (testNoIndex != -1) lists.testno = it.getInt(testNoIndex)
            if (testMarksIndex != -1) lists.testmarks = it.getFloat(testMarksIndex)
        }
        cursor?.close()
        return lists
    }

    fun deletelist (_id: Int): Boolean{
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return  Integer.parseInt("$_success") != -1

    }

    fun updatelist(listModel: StudentListModel) : Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STUDENT_REGNO,listModel.regno)
        values.put(STUDENT_NAME,listModel.studentname)
        values.put(STUDENT_AGE,listModel.studentage)
        values.put(STUDENT_GENDER,listModel.studentgender)
        values.put(TEST_NO,listModel.testno)
        values.put(TEST_MARKS,listModel.testmarks)
        val _success = db.update(TABLE_NAME,values, ID + "=?", arrayOf(listModel.id.toString())).toLong()
        db.close()
        return  Integer.parseInt("$_success") != -1
    }
}
