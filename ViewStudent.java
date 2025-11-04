package com.example.sms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewStudentsActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    ArrayList<String> studentList;
    ArrayList<String> studentIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        listView = findViewById(R.id.listView);
        db = new DatabaseHelper(this);
        studentList = new ArrayList<>();
        studentIds = new ArrayList<>();

        loadStudents();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String studentId = studentIds.get(position);
            Intent intent = new Intent(ViewStudentsActivity.this, UpdateStudentActivity.class);
            intent.putExtra("id", studentId);
            startActivity(intent);
        });
    }

    private void loadStudents() {
        Cursor cursor = db.getAllStudents();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No students found", Toast.LENGTH_SHORT).show();
        } else {
            studentList.clear();
            studentIds.clear();
            while(cursor.moveToNext()){
                studentIds.add(cursor.getString(0));
                String info = "Name: " + cursor.getString(1) + "\nEmail: " + cursor.getString(2) + "\nCourse: " + cursor.getString(3);
                studentList.add(info);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudents();
    }
}
