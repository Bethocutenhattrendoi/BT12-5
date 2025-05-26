package com.example.bt125

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class UpdateStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_student)

        val editName = findViewById<EditText>(R.id.dialogEditName)
        val editMSSV = findViewById<EditText>(R.id.dialogEditMSSV)
        val btnSave = findViewById<Button>(R.id.dialogBtnSave)
        val btnCancel = findViewById<Button>(R.id.dialogBtnCancel)

        val name = intent.getStringExtra("name") ?: ""
        val mssv = intent.getStringExtra("mssv") ?: ""
        editName.setText(name)
        editMSSV.setText(mssv)

        btnSave.setOnClickListener {
            val newName = editName.text.toString().trim()
            val newMSSV = editMSSV.text.toString().trim()
            if (newName.isNotBlank() && newMSSV.isNotBlank()) {
                val resultIntent = Intent()
                resultIntent.putExtra("name", newName)
                resultIntent.putExtra("mssv", newMSSV)
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
