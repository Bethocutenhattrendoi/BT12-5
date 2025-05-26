package com.example.bt125

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_student)

        val editName = findViewById<EditText>(R.id.dialogEditName)
        val editMSSV = findViewById<EditText>(R.id.dialogEditMSSV)
        val btnSave = findViewById<Button>(R.id.dialogBtnSave)
        val btnCancel = findViewById<Button>(R.id.dialogBtnCancel)

        btnSave.setOnClickListener {
            val name = editName.text.toString().trim()
            val mssv = editMSSV.text.toString().trim()
            if (name.isNotBlank() && mssv.isNotBlank()) {
                val resultIntent = Intent()
                resultIntent.putExtra("name", name)
                resultIntent.putExtra("mssv", mssv)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ họ tên và MSSV", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}
