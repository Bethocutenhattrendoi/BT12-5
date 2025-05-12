package com.example.bt125

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bt125.R

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private val studentList = mutableListOf<String>()
    private var selectedPosition = -1
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridViewStudents)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        gridView.adapter = adapter

        btnAdd.setOnClickListener {
            showStudentDialog(isUpdate = false)
        }

        btnUpdate.setOnClickListener {
            if (selectedPosition != -1) {
                showStudentDialog(isUpdate = true)
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

    private fun showStudentDialog(isUpdate: Boolean) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_student)

        val editName = dialog.findViewById<EditText>(R.id.dialogEditName)
        val editMSSV = dialog.findViewById<EditText>(R.id.dialogEditMSSV)
        val btnSave = dialog.findViewById<Button>(R.id.dialogBtnSave)
        val btnCancel = dialog.findViewById<Button>(R.id.dialogBtnCancel)

        if (isUpdate && selectedPosition != -1) {
            val student = studentList[selectedPosition].split(" - ")
            editName.setText(student[0])
            editMSSV.setText(student[1])
        }

        btnSave.setOnClickListener {
            val name = editName.text.toString().trim()
            val mssv = editMSSV.text.toString().trim()

            if (name.isNotBlank() && mssv.isNotBlank()) {
                val studentInfo = "$name - $mssv"
                if (isUpdate) {
                    studentList[selectedPosition] = studentInfo
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    selectedPosition = -1
                } else {
                    studentList.add(studentInfo)
                    Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ họ tên và MSSV", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
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
