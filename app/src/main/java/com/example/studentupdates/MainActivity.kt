import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentupdates.AddList
import com.example.studentupdates.R
import com.example.studentupdates.adapter.StudentListAdapter
import com.example.studentupdates.database.DatabaseHelper

class MainActivity : AppCompatActivity() {

    lateinit var recyclerList: RecyclerView
    lateinit var btnAdd: Button
    var studentListAdapter: StudentListAdapter? = null
    var dbHandler: DatabaseHelper? = null
    var studentList: List<StudentListModel> = ArrayList<StudentListModel>()
    var linearlayoutManager : LinearLayoutManager ?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerList = findViewById(R.id.rv_list)
        btnAdd = findViewById(R.id.btn_add_items)

        dbHandler = DatabaseHelper(this)

        // Set layout manager for RecyclerView
        recyclerList.layoutManager = LinearLayoutManager(this)

        // Fetch list from database and set adapter
        fetchList()

        btnAdd.setOnClickListener{
            val i = Intent(applicationContext, AddList::class.java)
            startActivity(i)
        }
    }

    private fun fetchList() {
        studentList = dbHandler!!.getAllList()
        studentListAdapter = StudentListAdapter(studentList, applicationContext)
        recyclerList.adapter = studentListAdapter
        linearlayoutManager = LinearLayoutManager(applicationContext)
        recyclerList.layoutManager = linearlayoutManager
        studentListAdapter?.notifyDataSetChanged()
    }
}
