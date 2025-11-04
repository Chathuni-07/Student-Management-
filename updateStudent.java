package com.example.sms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {

    EditText etName, etEmail, etCourse;
    Button btnUpdate, btnDelete;
    DatabaseHelper db;
    String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etCourse = findViewById(R.id.etCourse);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        db = new DatabaseHelper(this);

        studentId = getIntent().getStringExtra("id");
        loadStudentData();

        btnUpdate.setOnClickListener(v -> {
            boolean updated = db.updateStudent(studentId, etName.getText().toString(), etEmail.getText().toString(), etCourse.getText().toString());
            if(updated){
                Toast.makeText(this, "Student Updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Student")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean deleted = db.deleteStudent(studentId);
                        if(deleted){
                            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void loadStudentData() {
        Cursor cursor = db.getAllStudents();
        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(studentId)){
                etName.setText(cursor.getString(1));
                etEmail.setText(cursor.getString(2));
                etCourse.setText(cursor.getString(3));
            }
        }
    }
}
