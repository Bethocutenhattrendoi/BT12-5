package com.example.bt125

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private val studentList = mutableListOf<String>()
    private var selectedPosition = -1
    private lateinit var adapter: ArrayAdapter<String>

    private lateinit var addStudentLauncher: ActivityResultLauncher<Intent>
    private lateinit var updateStudentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridViewStudents)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val name = data?.getStringExtra("name")
                val mssv = data?.getStringExtra("mssv")
                if (name != null && mssv != null) {
                    studentList.add("$name - $mssv")
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show()
                }
            }
        }

        updateStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && selectedPosition != -1) {
                val data = result.data
                val name = data?.getStringExtra("name")
                val mssv = data?.getStringExtra("mssv")
                if (name != null && mssv != null) {
                    studentList[selectedPosition] = "$name - $mssv"
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    selectedPosition = -1
                }
            }
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        gridView.adapter = adapter

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            addStudentLauncher.launch(intent)
        }

        btnUpdate.setOnClickListener {
            if (selectedPosition != -1) {
                val student = studentList[selectedPosition].split(" - ")
                val intent = Intent(this, UpdateStudentActivity::class.java)
                intent.putExtra("name", student[0])
                intent.putExtra("mssv", student[1])
                updateStudentLauncher.launch(intent)
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên để cập nhật", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            if (selectedPosition != -1) {
                showDeleteConfirmationDialog()
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên để xóa", Toast.LENGTH_SHORT).show()
            }
        }

        gridView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            Toast.makeText(this, "Đã chọn: ${studentList[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xoá")
            .setMessage("Bạn có chắc muốn xoá sinh viên này không?")
            .setPositiveButton("Xoá") { _, _ ->
                studentList.removeAt(selectedPosition)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Đã xoá sinh viên", Toast.LENGTH_SHORT).show()
                selectedPosition = -1
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}
